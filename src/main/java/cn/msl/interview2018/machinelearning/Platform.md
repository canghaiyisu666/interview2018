
# TenforFlow

## 概述
 用于数值计算的使用数据流图的开源软件库。

  TensorFlow 是较低级别的符号库（比如 Theano）和较高级别的网络规范库（比如Blocks 和Lasagne）的混合。虽然它是Python 深度学习库集合的最新成员，不过在Google Brain 团队支持下，它已经是最大的活跃社区了。它支持在多GPUs 上运行深度学习模型，为高效的数据流水线提供使用程序，并具有用于模型的检查，可视化和序列化的内置模块。且TensorFlow支持 Keras（一个很优秀的深度学习库）。

  优点：由软件巨头 Google支持，非常大的社区，低级和高级接口网络训练，比基于 Theano配置更快的模型编译，完全地多 GPU支持。

  缺点：虽然 Tensorflow正在追赶，但是在许多基准上比基于 Theano的慢，RNN支持仍不如 Theano。

## 数据源
## 计算
## 存储
## 外部交互
## 自身管理
## 优劣势






# Caffe


# H2O



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


# Angel(腾讯)

# DTPAI(阿里)