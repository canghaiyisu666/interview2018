
# TenforFlow

[GitHub地址](https://github.com/tensorflow/tensorflow)
[官网地址](www.tensorflow.org)

## 概述
TensorFlow支持各种异构的平台，支持多CPU/GPU，服务器，移动设备，具有良好的跨平台的特性；
TensorFlow架构灵活，能够支持各种网络模型，具有良好的通用性；此外，TensorFlow架构具有良好的可扩展性，对OP的扩展支持，Kernel特化方面表现出众。

TensorFlow最初由Google大脑的研究员和工程师开发出来，用于机器学习和神经网络方面的研究，于2015.10宣布开源，
在众多深度学习框架中脱颖而出，在Github上获得了最多的Star量。

TensorFlow 是一个编程系统, 使用图来表示计算任务. 图中的节点被称之为 op (operation 的缩写). 一个 op 获得 0 个或多个 Tensor, 执行计算, 产生 0 个或多个 Tensor. 
每个 Tensor 是一个类型化的多维数组. 例如, 你可以将一小组图像集表示为一个四维浮点数数组, 这四个维度分别是 [batch, height, width, channels].

一个 TensorFlow 图描述了计算的过程. 为了进行计算, 图必须在 会话 里被启动. 会话 将图的 op 分发到诸如 CPU 或 GPU 之类的 设备 上, 同时提供执行 op 的方法. 
这些方法执行后, 将产生的 tensor 返回. 


## 优劣势
  优点：由软件巨头 Google支持，非常大的社区，低级和高级接口网络训练，比基于 Theano配置更快的模型编译，完全地多 GPU支持。

  缺点：虽然 Tensorflow正在追赶，但是在许多基准上比基于 Theano的慢，RNN支持仍不如 Theano。






# H2O

## 支持算法列表
监督学习（GLM、GBM、Deep Learning、Distributed Random Forest、Naive Bayes、Stacked Ensembles），
非监督学习（GLRM、K-Means、PCA）以及Word2vec模型。




# PaddlePaddle(百度)

文档: https://cloud.baidu.com/product/paddle.html
官网: [PaddlePaddle ---- PArallel Distributed Deep LEarning](http://paddlepaddle.org/)

##  概述
支持多种深度学习模型 DNN（深度神经网络）、CNN（卷积神经网络）、 RNN（递归神经网络），以及 NTM 这样的复杂记忆模型。
基于 Spark，与它的整合程度很高。
支持 Python 和 C++ 语言。
支持分布式计算。作为它的设计初衷，这使得 PaddlePaddle 能在多 GPU，多台机器上进行并行计算。
相比现有深度学习框架，PaddlePaddle 对开发者来说有什么优势？
首先，是易用性。
相比偏底层的谷歌 TensorFlow，PaddlePaddle 的特点非常明显：它能让开发者聚焦于构建深度学习模型的高层部分。项目负责人徐伟介绍：
“在PaddlePaddle的帮助下，深度学习模型的设计如同编写伪代码一样容易，设计师只需关注模型的高层结构，而无需担心任何琐碎的底层问题。未来，程序员可以快速应用深度学习模型来解决医疗、金融等实际问题，让人工智能发挥出最大作用。”
抛开底层编码，使得 TensorFlow 里需要数行代码来实现的功能，可能在 PaddlePaddle 里只需要一两行。徐伟表示，用 PaddlePaddle 编写的机器翻译程序只需要“其他”深度学习工具四分之一的代码。这显然考虑到该领域广大的初入门新手，为他们降低开发机器学习模型的门槛。这带来的直接好处是，开发者使用 PaddlePaddle 更容易上手。
其次，是更快的速度。
如上所说，PaddlePaddle 上的代码更简洁，用它来开发模型显然能为开发者省去一些时间。这使得 PaddlePaddle 很适合于工业应用，尤其是需要快速开发的场景。
另外，自诞生之日起，它就专注于充分利用 GPU 集群的性能，为分布式环境的并行计算进行加速。这使得在 PebblePebble 上，用大规模数据进行 AI 训练和推理可能要比 TensorFlow 这样的平台要快很多。

## 特点
支持多种深度学习模型 DNN（深度神经网络）、CNN（卷积神经网络）、 RNN（递归神经网络），以及 NTM 这样的复杂记忆模型。
基于 Spark，与它的整合程度很高。
支持 Python 和 C++ 语言。
支持分布式计算。作为它的设计初衷，这使得 PaddlePaddle 能在多 GPU，多台机器上进行并行计算。


# 总结与未来方向
机器学习/深度学习应用的并行处理让人为难，而且从并发算法（concurrent algorithms）的角度看并不非常有趣。可以相当肯定地说参数服务器方法在分布式机器学习平台的训练上更好。
至于局限性方面，网络仍然是分布式机器学习应用的一个瓶颈。提供更好的数据/模型分级比更先进的通用数据数据流平台更有用；应该将数据/模型看作头等公民。
但是，可能会有一些让人惊奇和微妙的地方。在 Spark 中，CPU 开销会先于网络限制变成瓶颈。Spark 使用的编程语言 Scala/JVM 显著影响了其性能表现。因此分布式机器学习平台尤其需要更好的监控和/或性能预测工具。最近已经有人提出了一些解决 Spark 数据处理应用的问题的工具，比如 Ernest 和 CherryPick。
在机器学习运行时的分布式系统支持上还有很多悬而未决的问题，比如资源调度和运行时的性能提升。对应用使用运行时监控/性能分析，下一代分布式机器学习平台应该会提供任务运行的计算、内存、网络资源的详细的运行时弹性配置/调度。
最后，在编程和软件工程支持方面也有一些待解决的问题。什么样的（分布式）编程抽象思想适用于机器学习应用？另外在分布式机器学习应用的检验和验证（尤其是使用有问题的输入来测试 DNN）上也还需要更多研究。