
# 一、	外部关联配置
## 1.	数据生命周期
### 冷热分离
mmapfs
>stores the shard index on the file system by mapping a file into memory (mmap). 

niofs
>stores the shard index on the file system using NIO. 

    PUT /my_index
    {
    "settings": {
    "index.store.type": "niofs"
                }
    }

### 减少内存开销---段合并
查看一个索引所有segment的memory占用情况：

    GET  /_cat/segments?v
    
减少data node上的segment 内存占用，有三种方法：

* 删除不用的索引。
* 关闭索引（文件仍然存在于磁盘，只是释放掉内存）。需要的时候可重新打开。
* 定期对不再更新的索引做force merge


    POST /my_index/_forcemerge?max_num_segments=1

### 副本管理

    PUT /my_index/_settings
    {  "index" : {
        "number_of_replicas" : 1
                }
    }

### 统计监控，日志跟踪

|                   |   count        |
|   --------    | -------------|
|   Index1    |                     |
|   Index2    |                    |

|     Memory   |   Disk usage |
|   --------    | -------------|
|   Node1    |                     |
|   Node2    |                    |

## 2.	其他属性

|   属性名         |           修改值     |           所属配置        |           说明|
|       ------      |           -------     |               -----           |       ------   |
|index.refresh_interval	|   30s  |    Setting      | 新增的数据过多久可以被检索到。增大该时间可以增大索引段的大小。  |
|     index属性            |       analyzed / not_analyzed /no 	 |   Mapping | analyzed进行分词, not_analyzed为当作关键词对待，no为不创建索引|
|    docvalue属性       |         false/true          |     Mapping	    |               Sorting, aggregations  |

# 二、	平台内部配置
## 1.	操作系统配置
### a)	关闭OS交换分区

    swappoff -a
    echo “vm.swappiness = 1” >> /etc/sysctl.conf
    sysctl –p
    
同时需要注释掉/etc/fstab中包含swap的挂载。(vm.swappiness 设置为0可能会导致OOM)

### b)	锁定es内存
在elasticsearch.yml中需要配置bootstrap.memory_lock

    bootstrap.memory_lock: true

在文件/etc/security/limits.conf尾部追加：

    * soft memlock ulimited
    * hard memlock ulimited

在$ES_HOME/config/jvm.options中配置内存大小(jvm超过32G指针压缩失效)

    -Xms30g
    -Xmx30g

配置好后可以通过以下几口查看是否生效。

    GET _nodes?filter_path=**.mlockall

### c)	修改最大用户进程数
修改/etc/security/limits.d/90-nproc.conf ，把文件中的1024修改为4096

### d)	修改OS对mmap的限制
es存储索引时，默认需要使用mmapfs或niofs。操作系统对于mmap数的默认配置太小，这可能会导致出现内存问题。
在Linux系统中，可以使用root执行如下命令来增加mmap数的限制。

    sysctl -w vm.max_map_count=262144

要让设置永久生效，需要在文件/etc/sysctl.conf中写入vm.max_map_count的值。重启机器后可以执行sysctl vm.max_map_count来确认是否生效。

    echo “vm.max_map_count = 262144” >> /etc/sysctl.conf
    sysctl -p

### e)  修改File Descriptors (Linux Only)
es需要使用大量的文件描述符和句柄。耗尽文件描述符是灾难性的，会导致数据丢失。 确保将es的文件描述符的数量限制增加到65,536或更高。
在文件/etc/security/limits.conf尾部追加：

    * soft nofile 204800
    * hard nofile 204800

配置好后可以通过节点状态api来确认max_file_descriptors是否生效：

    GET _nodes/stats/process?filter_path=**.max_file

## 2.	服务相关配置
### a)	角色规划
角色官方释义：

Master-eligible node
>node.master设置为true(默认)的节点。这使得这个节点可以被选举为控制整个集群的主节点。

Data node
>node.data设置为true(默认)的节点。数据节点存储数据并执行诸如CRUD、搜索和聚合等相关操作。

Ingest node
>node.ingest设置为true(默认)的节点。支持数据在创建索引之前进行转换和强化操作。使用logstash则可把此配置修改为false。

资源消耗：

|角色|说明|
|----|----|
| Master-eligible node|CPU和内存 消耗一般|
|Data node | 磁盘，内存|

分配原则：
* master节点尽量和data节点分离，否则对数据的操作有可能影响master对集群状态的管理。
* master分配1个时容易出现单点故障，分配2个容易出现脑裂问题，建议配置三个。
* 当硬件资源有限时，或者单节点超大内存时，建议在一个节点上规划多个运行实例。目的是提高内存利用率。


### b)	elasticsearch.yml

|属性名|修改值|说明|
|------|-------|-----|
|path.data		| | 多磁盘配置用逗号隔开  |
|transport.tcp.compress         	|true	 |   节点交互时是否压缩数据。CPU和磁盘IO不高，但网络传输高负载，设为true；当CPU负载过大的时候此项可以设置为false。 |
|threadpool.index.queue_size	|50     |	服务端index操作线程池队列大小(默认200)  |
|threadpool.delete.queue_size	|50	|        服务端delete操作线程池队列大小(默认200)  |
|threadpool.bulk.queue_size		|     |   服务端bulk操作线程池队列大小(默认50)  |
		
### c)	setting 


|属性名|修改值|说明|
|-------|------|-----|
|index._all.enabled	                             |          false	|   关闭_all域提高性能 |
|index.translog.durability	                     |         async|	为了保证不丢失数据，每次 index、bulk、delete、update 完成的时候，触发刷新 translog 到磁盘上，才给请求返安回 200 OK。|
|index.indexing.slowlog.threshold.index.warn	 | 10s      	|   慢索引阈值|
|index.indexing.slowlog.threshold.index.info	 |    5s	    |   慢索引阈值|
|index.indexing.slowlog.threshold.index.debug    |2s	    |   慢索引阈值|
|index.indexing.slowlog.threshold.index.trace   |500ms	|   慢索引阈值|
|index.indexing.slowlog.level                    | 	info	    |   慢索引级别|
|index.indexing.slowlog.source                   |10	|   创建索引过慢时记录源数据的条数。（false或0 将不记录源数据，true为记录全部的源数据）|
|index.store.throttle.type	                     |     none	|   加数据的过程中不进行段合并，等不再加载数据时再更新setting配置为merge|
|index.number_of_replicas	                     |        0|	副本数|
|index.number_of_shards	                         |  30GB/20亿/每data一分片 	|  分片数|                                                                                                                                                                



# 三、	FAQ
## 1.	启动 elasticsearch 如出现异常  can not run elasticsearch as root  
解决方法：创建elastic 账户，修改文件夹 文件 所属用户组。

## 2.	启动异常：ERROR: bootstrap checks failed
>system call filters failed to install; check the logs and fix your configuration or disable system call filters at your own risk

原因：因为Centos6不支持SecComp，而ES5.2.1默认bootstrap.system_call_filter为true进行检测，所以导致检测失败，失败后直接导致ES不能启动。详见 : https://github.com/elastic/elasticsearch/issues/22899

解决方法：在elasticsearch.yml中配置bootstrap.system_call_filter为false

    bootstrap.system_call_filter: false

## 3.	max number of threads [1024] for user [lish] likely too low, increase to at least [2048]
解决：切换到root用户，进入limits.d目录下修改配置文件。
vim /etc/security/limits.d/90-nproc.conf 
修改如下内容：

    * soft nproc 1024
修改为

    * soft nproc 4096

## 4.	Bulk队列设置(自身实现导致)
现象：对es集群进行大量index或delete操作时，报bulk队列溢出异常。

原因：TransportDeleteAction > TransportSingleItemBulkWriteAction > TransportBulkAction   (https://github.com/elastic/elasticsearch/pull/21964)  影响范围5.3+
官方测试发现一条记录的index/delete和作为单条bulk请求处理，底层操作性能开销几乎没有分别。 为了减少代码路径，做了这个操作的合并。 因为index/delete/bulk现在共享bulk的线程池，默认的线程池设置可能不够用。

##5.	内存监控与管理
* 索引需要常驻内存，无法GC，需要关注监控data node上segment memory增长趋势。
* 根据GC日志，确认heap各个代配置比例是否正常，评估是否有必要使用G1GC

> 早期版本的JDK 8存在一些问题，当G1GC收集器启用时，会导致索引损坏。
受影响的版本为JDK 8u40之前的版本。评估GC收集器时需考虑到JDK的版本。
* 使用命令把异常的内存dump出来，使用MAT进行分析


    jmap -dump:format=b,file=文件名 [pid]
    
附:  
[es分析内存分配过程案例]( https://github.com/elastic/elasticsearch/issues/22013)

[MAT的使用说明](http://blog.csdn.net/bohu83/article/details/51124060)



