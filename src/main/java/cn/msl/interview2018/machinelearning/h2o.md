# H2O

## 简介
H2O是开源的，分布式的，基于内存的，可扩展的机器学习和预测分析框架，
适合在企业环境中构建大规模机器学习模型。

H2O核心代码使用Java编写，数据和模型通过分布式 Key/Value 存储在各个集群节点的内存中。
H2O的算法使用Map/Reduce框架实现，并使用了Java Fork/Join框架来实现多线程。

H2O目前支持的机器学习算法有：
监督学习（GLM、GBM、Deep Learning、Distributed Random Forest、Naive Bayes、Stacked Ensembles），
非监督学习（GLRM、K-Means、PCA）以及Word2vec模型。

Spark1.5MLlib支持的H2O算法：
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


## 三种使用方式

### H2O local


### H2O hadoop 


### Sparkling Water  
Sparkling Water允许用户把H2O高效可扩展的机器学习算法和spark结合起来。通过Sparkling Water,
用户可以执行 Scala/R/Python的计算任务或是在flowUI上执行，给应用开发者提供了一个便捷的平台。


使用态和集成态
底层是如何执行决定了是开发算子集成还是外部调用集成。

预测的三种方式： 1.web service  2.实时streaming   3. 离线批处理