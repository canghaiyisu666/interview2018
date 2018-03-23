# H2O

## 简介
H2O是开源的，分布式的，基于内存的，可扩展的机器学习和预测分析框架，
适合在企业环境中构建大规模机器学习模型。

H2O核心代码使用Java编写，数据和模型通过分布式 Key/Value 存储在各个集群节点的内存中。
H2O的算法使用Map/Reduce框架实现，并使用了Java Fork/Join框架来实现多线程。

H2O目前支持的机器学习算法有：
监督学习（GLM、GBM、Deep Learning、Distributed Random Forest、Naive Bayes、Stacked Ensembles），
非监督学习（GLRM、K-Means、PCA）以及Word2vec模型。

Spark1.5MLlib已支持的H2O算法：
Distributed Random Forest 、Naive Bayes 、 K-Means 、PCA 、Word2vec

## 架构
![架构图](http://docs.h2o.ai/h2o/latest-stable/h2o-docs/_images/h2o_stack.png)
* JVM 组件：

一个H2O云包含一个或多个节点。每一个节点是一个单独的JVM进程。每一个JVM进程被分割成了三层：语言，算法和核心框架.

语言层包含了一个R和scala的表达式评估引擎。

算法层包含了H2O自动提供的算法。主要是用来处理输入数据的解析算法，数学和机器学习算法，例如GLM, 还有预测打分引擎。

底层（核心）用来处理资源管理。内存和CPU在这里被管理。

* 内存管理

`Fluid Vector Frame` 暴露给用户的基本数据单元 。

`Distributed K/V store` 在集群中的原子分布式内存存储方式。 

* CPU管理

`Job` Jobs是有进度条的大型工作，可以再webUI上进行监控。模型的创建也是job的一种。

`MRTask` 指H2O 基于内存的MapReduce任务，和Hadoop的MapReduce任务不同。

## 重要概念

### POJOs and  MOJOs
POJOs: Plain Old Java Object  普通java对象，其中有一些属性及其getter setter方法的类,没有业务逻辑，不允许有业务方法。
MOJOs: Model ObJect, Optimized  包含了阅读器和解释器的
H2O 允许你把已经构建好的POJO和MOJO进行转换。

[如何使用h2o生成的POJO和MOJO模型](http://docs.h2o.ai/h2o/latest-stable/h2o-genmodel/javadoc/index.html)

从H2O提取生成的模型目前仅支持如下方式：(不支持java)
* From the H2O Flow Web UI
* From R
* From Python
* From Sparkling Water

### getmodel.jar
做预测时所必须依赖的开发包，H2O生成的MOJO和POJO模型必须依赖getmodel.jar才可正常使用。


## 集成方式

### 外部H2O集群（KMEANS在FLOW中的实际操作流程）
1. 数据输入：输入，解析，得到frame

        importFiles [ "http://s3.amazonaws.com/h2o-public-test-data/smalldata/flow_examples/seeds_dataset.txt" ]
        
        setupParse paths: [ "http://s3.amazonaws.com/h2o-public-test-data/smalldata/flow_examples/seeds_dataset.txt" ]
        
        parseFiles
          paths: ["http://s3.amazonaws.com/h2o-public-test-data/smalldata/flow_examples/seeds_dataset.txt"]
          destination_frame: "seeds_dataset.hex"
          parse_type: "CSV"
          separator: 9
          number_columns: 8
          single_quotes: false
          column_types: ["Numeric","Numeric","Numeric","Numeric","Numeric","Numeric","Numeric","Numeric"]
          delete_on_done: true
          check_header: -1
          chunk_size: 4194304
          
        getFrameSummary "seeds_dataset.hex"

2. 配置算法进行模型训练
    
        buildModel 'kmeans', {"model_id":"kmeans-d1cc0a90-3a54-4eac-8b06-1d66f1f57741","training_frame":"seeds_dataset.hex","ignored_columns":["C7"],"k":"3","max_iterations":"100","init":"PlusPlus","standardize":false,"seed":1425597869002366000}

3. 获取模型，进行打分预测
        
        getModel "kmeans-d1cc0a90-3a54-4eac-8b06-1d66f1f57741"      //模型的导出
        
        predict model: "kmeans-d1cc0a90-3a54-4eac-8b06-1d66f1f57741", frame: "seeds_dataset.hex", predictions_frame: "prediction-2e8d0460-8bc0-4dc1-9cad-df0a3a9d2eb1"
        
4. 得到预测结果

        getFrameSummary "prediction-93633524-0524-43b9-a627-043906e89a4f"


### 内部整合hadoop平台
支持的hadoop平台列表：
cdh5.4
cdh5.5
cdh5.6
cdh5.7
cdh5.8
cdh5.10
cdh5.13
cdh5.14
hdp2.2
hdp2.3
hdp2.4
hdp2.5
hdp2.6
mapr4.0
mapr5.0
mapr5.1
mapr5.2
iop4.2



### 内部整合Sparkling Water  
#### 概述

Sparkling Water允许用户把H2O高效的机器学习算法和spark结合起来。
通过Sparkling Water,用户可以执行 Scala/R/Python的计算任务或是在flowUI上执行，给应用开发者提供了一个便捷的平台。


#### 兼容性
1. 支持的数据源
  * RDD 可以转换为H2O RDD
  * 文件：local filesystem；HDFS；S3；HTTP/HTTPS
2. 数据存储格式
  * CSV
  * SVMLight
  * ARFF
  * Parquet
3. 支持的spark环境
  * as a local cluster (where the master node is local or local[ * ])
  * as a standalone cluster 1
  * in a YARN environment 2


#### 两种后端服务模式
1. internal模式
Sparkling water在spark的executor里启动，即在spark application提交之后自动创建。
这种方式部署容易，但是当yarn杀掉executor时H2O集群会挂掉，因为H2O不支持HA.

2. external模式
外部模式下sparkling water 独立启动，并且和spark的driver进行通信。优点是spark的excutor被kill之后不会影响用户使用。


#### 兼容的spark版本
spark1.5.0   ???
spark2.0.2
spark2.1.2
spark2.2.1





几点问题：
model的导出暂不支持java，可以使用java-rest代替

预测打分时需要输入模型后再编译



使用态和集成态
底层是如何执行决定了是开发算子集成还是外部调用集成。

打分预测的三种方式： 1.web service通过自带ing算子；   3. 整合离线批处理。的steam实现； [steam](https://www.jianshu.com/p/c158c4826c5d) 2. 整合实时stream