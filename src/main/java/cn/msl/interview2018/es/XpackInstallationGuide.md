
# 一 、安装步骤
* 下载安装包，并上传至服务器/tmp目录下

    
    https://artifacts.elastic.co/downloads/packs/x-pack/x-pack-5.4.1.zip

* 使用elasticsearch-plugin命令给es离线安装x-pack插件


    bin/elasticsearch-plugin install file:///tmp/x-pack-5.4.1.zip

* 使用kibana-plugin命令给kibana离线安装x-pack插件


    bin/kibana-plugin install file:///tmp/x-pack-5.4.1.zip

* _(可选)使用logstash-plugin命令给logstash离线安装x-pack插件_


    bin/logstash-plugin install file:///path/to/file/x-pack-5.4.1.zip

* 配置x-pack的可用模块：
默认情况下x-pack的所有模块都是开启的，开启或关闭需要同时在elasticsearch.yml 和 kibana.yml设置：


    xpack.ml.enabled: false
    xpack.security.enabled: false
    xpack.graph.enabled: false
    xpack.watcher.enabled: false
    xpack.monitoring.enabled: true


# 二、License
Xpack默认有效期一个月，注册basic license可延长一年使用期，到期之前需要更新license。(不需要重启服务)
Basic license注册地址：

>https://register.elastic.co/xpack_register

注册成功后会得到license的json文件，使用如下POST接口进行更新：

    curl -XPOST 'http://ES-IP:<port>/_xpack/license?acknowledge=true' -H "Content-Type: application/json" -d @license.json

# 三、其他配置
在安装X-Pack时，默认启用monitoring模块。可以在elastisearch.yml,kibana.yml和logstash.yml中对monitoring进行配置。

|  配置项  |   修改值  |    说明  |
|   -----    |  --------  |    ----  |
|xpack.monitoring.collection.interval  |	10s   |    	收集数据的时间间隔。|
|xpack.monitoring.history.duration	|   7d	  |   监控信息的保留天数，最小值是1d|

# 四、使用地址

    http://localhost:5601/app/monitoring
 
