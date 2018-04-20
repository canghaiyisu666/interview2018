# C60服务配置优化

## HDFS

|      属性名   |     建议值    |   说明    |
|      ---        |        ---       |    ---     |
|    dfs.datanode.data.dir   |       用多个路径来配置多个磁盘并发读写      |    hdfs文件在本地文件系统的实际存储位置   |
|    dfs.namenode.handler.count  |    设置为集群大小的自然对数乘以20，即20logN   |   NameNode有一个工作线程池用来处理客户端的远程过程调用及集群守护进程的调用。处理程序数量越多意味着要更大的池来处理来自不同DataNode的并发心跳以及客户端并发的元数据操作。  |
|    dfs.replication   |        2      |    文件在hdfs中的副本数    |
|    hadoop.log.maxbackupindex        |        10       |    归档的运行日志保留个数，默认值100过大     |


## YARN

|      属性名   |     建议值    |   说明    |
|      ---        |        ---       |    ---     |
|      yarn.nodemanager.local-dirs      |        配置不同磁盘的多个路径       |    计算任务本地化后的存储位置，多磁盘可以增加读写效率     |
|      mapreduce.task.timeout        |        1小时     |    mapreduce的超时时间，    |
|      yarn.am.liveness-monitor.expiry-interval-ms     |       1小时       |   认为NodeManager处于失效状态前的等待时间，默认十分钟     |
|      mapreduce.job.ubertask.enable        |        true       |    是否启用小计算任务的优化     |
|      mapreduce.job.ubertask.maxmaps        |        5       |    多少个map以内被判定使用uber优化     |
|      mapreduce.map.memory.mb        |        2G       |    map的默认内存 ，默认4G     |
|      mapreduce.reduce.memory.mb        |        2G       |    reduce的默认内存 ，默认4G     |
|      mapreduce.map.java.opts        |        -Xmx1536M       |     map的最大堆内存 ，默认2G     |
|      mapreduce.reduce.java.opts        |        -Xmx1536M       |     reduce的最大堆内存 ，默认2G     |
|      RES_CPUSET_PERCENTAGE        |        90       |    YARN可以使用的CPU核数比例，默认值100     |
|      yarn.nodemanager.resource.cpu-vcores        |        ---       |    根据实际配置决定，不要超过物理线程数的90%     |
|      yarn.scheduler.maximum-allocation-mb        |        16G       |    可以调度的最大容器内存，默认64G     |
|      mapreduce.output.fileoutputformat.compress        |        true       |    job的输出是否启用压缩，默认false     |
|      mapreduce.output.fileoutputformat.compress.codec        |    mapreduce.output.fileoutputformat.compress.codec   |    job启用压缩后的规则，建议snappy，纯文本压缩率达20%     |
|      yarn.nodemanager.delete.debug-delay-sec        |        180000       |    应用程序完成之后 NodeManager 的 DeletionService 删除应用程序的本地化文件和日志目录之前的时间（秒数）。默认为0    |


## HIVE

|      属性名   |     建议值    |   说明    |
|      ---        |        ---       |    ---     |
|  HIVE_GC_OPTS    |     -Xms4G -Xmx8G     |    修改HiveServer的内存大小，默认最小2G过小     |
|  METASTORE_GC_OPTS    |    -Xms4G -Xmx8G      |  修改MetaStore的内存大小，默认1G过小   |



## SPARK

|      属性名   |     建议值    |   说明    |
|      ---        |        ---       |    ---     |
|      spark.sql.shuffle.partitions        |       128    |   shuffle read task的并行度，该值默认是200，对于小集群偏大  |
|      SPARK_DAEMON_MEMORY        |        2G       |    JobHistory的内存大小，默认1G     |
|      spark.driver.maxResultSize        |        2G       |    Driver端接收数据的最大值。大数据量计算需要把driver内存同时设大     |
|      log4j.appender.sparklog.MaxBackupIndex     |      20       |    日志文件的保留数量，默认100个过大  |
