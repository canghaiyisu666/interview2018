
# TenforFlow

* [GitHub地址](https://github.com/tensorflow/tensorflow)
* [官网地址](www.tensorflow.org)
* [API文档地址](https://www.tensorflow.org/api_docs/)

## 概述
TensorFlow支持各种异构的平台，支持多CPU/GPU，服务器，移动设备，具有良好的跨平台的特性；
TensorFlow架构灵活，能够支持各种网络模型，具有良好的通用性。

TensorFlow最初由Google大脑的研究员和工程师开发出来，用于机器学习和神经网络方面的研究，于2015.10宣布开源，
在众多深度学习框架中脱颖而出，在Github上获得了最多的Star量。

TensorFlow 是一个编程系统, 使用图来表示计算任务. 图中的节点被称之为 op (operation 的缩写). 一个 op 获得 0 个或多个 Tensor, 执行计算, 产生 0 个或多个 Tensor. 
每个 Tensor 是一个类型化的多维数组. 例如, 你可以将一小组图像集表示为一个四维浮点数数组, 这四个维度分别是 [batch, height, width, channels].

一个 TensorFlow 图描述了计算的过程. 为了进行计算, 图必须在 会话 里被启动. 会话 将图的 op 分发到诸如 CPU 或 GPU 之类的 设备 上, 同时提供执行 op 的方法. 
这些方法执行后, 将产生的 tensor 返回. 

## 架构

![tensorflow架构图](https://upload-images.jianshu.io/upload_images/2254249-bf86142555d23538.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/629)

TensorFlow的系统架构以`C API`为界，将整个系统分为`前端`和`后端`两个子系统：

- 前端系统：提供编程模型，负责构造计算图；
- 后端系统：提供运行时环境，负责执行计算图。

重点关注如下4个组件，它们是系统分布式运行机制的核心。

`Client`
Client是前端系统的主要组成部分，它是一个支持多语言的编程环境。它提供基于计算图的编程模型，方便用户构造各种复杂的计算图，实现各种形式的模型设计。

Client通过Session为桥梁，连接TensorFlow后端的`运行时`，并启动计算图的执行过程。

`Distributed Master`
在分布式的运行时环境中，Distributed Master根据Session.run的Fetching参数，从计算图中反向遍历，找到所依赖的`最小子图`。

然后，Distributed Master负责将该`子图`再次分裂为多个`子图片段`，以便在不同的进程和设备上运行这些`子图片段`。

最后，Distributed Master将这些`子图片段`派发给Work Service；随后Work Service启动`子图片段`的执行过程。

`Worker Service`
对于每以个任务，TensorFlow都将启动一个Worker Service。Worker Service将按照计算图中节点之间的依赖关系，根据当前的可用的硬件环境(GPU/CPU)，
调用OP的Kernel实现完成OP的运算(一种典型的多态实现技术)。

另外，Worker Service还要负责将OP运算的结果发送到其他的Work Service；或者接受来自其他Worker Service发送给它的OP运算的结果。

`Kernel Implements` Kernel是OP在某种硬件设备的特定实现，它负责执行OP的运算。


## 优劣势
* 优点
    - `可用性`
TensorFlow 工作流程相对容易，API 稳定，兼容性好，并且 TensorFlow 与 Numpy 完美结合，这使大多数精通 Python 数据科学家很容易上手。与其他一些库不同，TensorFlow 不需要任 何编译时间， 这允许你可以更快地迭代想法。在TensorFlow 之上 已经建立了多个高级 API，例如Keras 和 SkFlow，这给用户使用TensorFlow 带来了极大的好处
    - `灵活性`
TensorFlow 能够在各种类型的机器上运行，从超级计算机到嵌入式系统。它的分布式架构使大量数据集的模型训练不需要太多的时 间。TensorFlow 可以同时在多个 CPU，GPU 或者两者混合运行。
    - `效率`
自 TensorFlow 第一次发布以来，开发团队花费了大量的时间和努力 来改进TensorFlow 的大部分的实现代码。 随着越来越多的开发人 员努力，TensorFlow 的效率不断提高。
    - `支持`
TensorFlow 由谷歌提供支持，谷歌投入了大量精力开发 TensorFlow，它希望 TensorFlow 成为机器学习研究人员和开发人员的通用语言。此外，谷歌在自己的日常工作中也使用 TensorFlow，并且持续对其提供支持，在 TensorFlow 周围形成了 一个强大的社区。谷歌已经在 TensorFlow 上发布了多个预先训练好的机器学习模型，他们可以自由使用。

  缺点：文档学习成本高；不同版本兼容性存在问题；显存占用高。






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

## 支持算法列表
监督学习（GLM、GBM、Deep Learning、Distributed Random Forest、Naive Bayes、Stacked Ensembles），
非监督学习（GLRM、K-Means、PCA）以及Word2vec模型。




# PaddlePaddle(百度)

* [官网地址](http://paddlepaddle.org/)
* [使用文档](http://www.paddlepaddle.org/docs/develop/documentation/zh/getstarted/index_cn.html)
* [API文档地址](http://www.paddlepaddle.org/docs/develop/api/en/overview.html)

##  概述
支持多种深度学习模型 DNN（深度神经网络）、CNN（卷积神经网络）、 RNN（递归神经网络），以及 NTM 这样的复杂记忆模型。
基于 Spark，与它的整合程度很高。
支持 Python 和 C++ 语言。
支持分布式计算。作为它的设计初衷，这使得 PaddlePaddle 能在多 GPU，多台机器上进行并行计算。

## 特点
支持多种深度学习模型 DNN（深度神经网络）、CNN（卷积神经网络）、 RNN（递归神经网络），以及 NTM 这样的复杂记忆模型。
基于 Spark，与它的整合程度很高。
支持 Python 和 C++ 语言。
支持分布式计算。作为它的设计初衷，这使得 PaddlePaddle 能在多 GPU，多台机器上进行并行计算。

## 优劣势
* 优点
    - `易用性`
相比偏底层的谷歌 TensorFlow，PaddlePaddle 的特点非常明显：它能让开发者聚焦于构建深度学习模型的高层部分。项目负责人徐伟介绍：
“在PaddlePaddle的帮助下，深度学习模型的设计如同编写伪代码一样容易，设计师只需关注模型的高层结构，而无需担心任何琐碎的底层问题。未来，程序员可以快速应用深度学习模型来解决医疗、金融等实际问题，让人工智能发挥出最大作用。”
抛开底层编码，使得 TensorFlow 里需要数行代码来实现的功能，可能在 PaddlePaddle 里只需要一两行。徐伟表示，用 PaddlePaddle 编写的机器翻译程序只需要“其他”深度学习工具四分之一的代码。这显然考虑到该领域广大的初入门新手，为他们降低开发机器学习模型的门槛。这带来的直接好处是，开发者使用 PaddlePaddle 更容易上手。
    - `速度`
PaddlePaddle 上的代码更简洁，用它来开发模型显然能为开发者省去一些时间。这使得 PaddlePaddle 很适合于工业应用，尤其是需要快速开发的场景。
另外，自诞生之日起，它就专注于充分利用 GPU 集群的性能，为分布式环境的并行计算进行加速。这使得在 PebblePebble 上，用大规模数据进行 AI 训练和推理可能要比 TensorFlow 这样的平台要快很多。


# 总结与未来方向
网络仍然是分布式机器学习应用的一个瓶颈。提供更好的数据/模型分级比更先进的通用数据数据流平台更有用；应该将数据/模型看作头等公民。
但是，可能会有一些让人惊奇和微妙的地方。
在 Spark 中，CPU 开销会先于网络限制变成瓶颈。Spark 使用的编程语言 Scala/JVM 显著影响了其性能表现。
因此分布式机器学习平台尤其需要更好的监控和/或性能预测工具。最近已经有人提出了一些解决 Spark 数据处理应用的问题的工具，比如 Ernest 和 CherryPick。
在机器学习运行时的分布式系统支持上还有很多悬而未决的问题，比如资源调度和运行时的性能提升。
对应用使用运行时监控/性能分析，下一代分布式机器学习平台应该会提供任务运行的计算、内存、网络资源的详细的运行时弹性配置/调度。
