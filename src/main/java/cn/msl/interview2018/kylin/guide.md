# Kylin使用指南

## 核心概念

* `表(table)`：This is definition of hive tables as source of cubes，在build cube 之前，必须同步在 kylin中。
* `模型(model)`：模型描述了一个星型模式的数据结构，它定义了一个事实表（Fact Table）和多个查找表（Lookup Table）的连接和过滤关系。
* `Cube 描述`：描述一个Cube实例的定义和配置选项，包括使用了哪个数据模型、包含哪些维度和度量、如何将数据进行分区、如何处理自动合并等等。
* `Cube实例`：通过Cube描述Build得到，包含一个或者多个Cube Segment。
* `分区(Partition)`：用户可以在Cube描述中使用一个DATA/STRING的列作为分区的列，从而将一个Cube按照日期分割成多个segment。
* `立方体段(cube segmetn)`：它是立方体构建（build）后的数据载体，一个 segment 映射hbase中的一张表，立方体实例构建（build）后，会产生一个新的segment，一旦某个已经构建的立方体的原始数据发生变化，只需刷新（fresh）变化的时间段所关联的segment即可。
* `聚合组`：每一个聚合组是一个维度的子集，在内部通过组合构建cuboid。
* `作业(job)`：对立方体实例发出构建（build）请求后，会产生一个作业。该作业记录了立方体实例build时的每一步任务信息。作业的状态信息反映构建立方体实例的结果信息。如作业执行的状态信息为RUNNING 时，表明立方体实例正在被构建；若作业状态信息为FINISHED ，表明立方体实例构建成功；若作业状态信息为ERROR ，表明立方体实例构建失败！

## CubeAction的种类

* `BUILD`：给定一个分区列指定的时间间隔，对Cube进行Build，创建一个新的cube Segment。
* `REFRESH`：这个操作，将在一些分期周期内对cube Segment进行重新build。
* `MERGE`：这个操作将合并多个cube segments。这个操作可以在构建cube时，设置为自动完成。
* `PURGE`：清理一个Cube实例下的segment，但是不会删除HBase表中的Tables。

## Job的状态

* `NEW`：表示一个job已经被创建。
* `PENDING`：表示一个job已经被job Scheduler提交，等待执行资源。
* `RUNNING`：表示一个job正在运行。
* `FINISHED`：表示一个job成功完成。
* `ERROR`：表示一个job因为错误退出。
* `DISCARDED`：表示一个job被用户取消。

## Job执行后的操作

* `RESUME`：这个操作将从失败的Job的最后一个成功点继续执行该Job。
* `DISCARD`：无论工作的状态,用户可以结束它和释放资源。

## Kylin界面功能

|  insight   |  model  |  monitor   |    system   | 
|    ---     |    -----    |   ------       |  -----       |
|   结果管理 | 模型管理，Cube管理, 数据源管理| job管理|  系统管理       |


## Cube的构建算法

### 逐层算法
分层

### 快速算法
分数据

## Cube构建逻辑流程
1. 构建一个中间平表(Hive Table)：将Model中的fact表和look up表构建成一个大的Flat Hive Table。
2. 重新分配Flat Hive Tables。
3. 从事实表中抽取维度的Distinct值。
4. 对所有维度表进行压缩编码，生成维度字典。
5. 计算和统计所有的维度组合，并保存，其中，每一种维度组合，称为一个Cubeid。
6. 创建HTable。
7. 构建最基础的Cubeid数据。
8. 利用算法构建N维到0维的Cuboid数据。
9. 构建Cube。
10. 将Cubeid数据转换成HFile。
11. 将HFile直接加载到HBase Table中。
12. 更新Cube信息。
13. 清理Hive。

## Cube构建操作
1. 在`Cubes`页面中，点击cube栏右侧的`Action`下拉按钮并选择`Build`操作。
2. 选择后会出现一个弹出窗口。点击END DATE输入框选择增量构建这个cube的结束日期。
3. 点击`Submit`提交请求。
4. 提交请求成功后，你将会看到`Monitor`页面新建了job。
5. 如要放弃这个job，点击`Discard`按钮。

## Job监控
* 在Monitor页面，点击job详情按钮查看显示于右侧的详细信息。
* 点击每个步骤显示的图标按钮查看详情：`Parameters`、`Log`、`MRJob`。
 
 
## 工程管理
 
 
 