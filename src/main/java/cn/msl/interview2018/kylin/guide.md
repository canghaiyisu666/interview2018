# Kylin使用指南

## 核心概念

* `表(table)`：This is definition of hive tables as source of cubes，在构建 cube 之前，必须同步在 kylin中。
* `模型(model)`：模型描述了一个星型模式的数据结构，它定义了一个事实表和多个纬度表的连接和过滤关系。
* `Cube 描述`：描述一个Cube实例的定义和配置选项，包括使用了哪个数据模型、包含哪些维度和度量、如何将数据进行分区、如何处理自动合并等等。
* `Cube实例`：通过Cube描述Build得到，包含一个或者多个Cube Segment。
* `分区(Partition)`：用户可以在Cube描述中使用一个DATA/STRING的列作为分区的列，从而将一个Cube按照日期分割成多个segment。
* `立方体段(cube segmetn)`：它是立方体构建（build）后的数据载体，一个 segment 映射hbase中的一张表，立方体实例构建（build）后，会产生一个新的segment，一旦某个已经构建的立方体的原始数据发生变化，只需刷新（fresh）变化的时间段所关联的segment即可。
* `聚合组`：每一个聚合组是一个维度的子集，在内部通过组合构建cuboid。
* `作业(job)`：对立方体实例发出构建（build）请求后，会产生一个作业。该作业记录了立方体实例build时的每一步任务信息。作业的状态信息反映构建立方体实例的结果信息。如作业执行的状态信息为RUNNING 时，表明立方体实例正在被构建；若作业状态信息为FINISHED ，表明立方体实例构建成功；若作业状态信息为ERROR ，表明立方体实例构建失败！

## Cube详解
　　一个Cube(多维立方体)其实就是一个多维数组，例如定义一个三维数组int array[M][N][K]，每一个维度分别有M、N和K个成员，同样对于一个Cube而言，
也可以有三个维度，假设分别为time、location和product，每一个维度的distinct值（称为维度的cardinality）分别是M、N和K个，
而数组中的每一个值则是每一个维度取一个值对应的聚合结果，例如array[0][1][2]，它就相当于time取第一个值(假设为2016-01-01)，
location取第二个值(假设为HangZhou)、product取第三个值(假设为Food)对应的一个聚合结果，假设这里的聚合函数为COUNT(1)，
那么求得的值相当于执行了SELECT COUNT(1) from table where time = ‘2016-01-01’ and location = ‘HangZhou’ and product = ‘Food’，
这样当我们有多个聚合函数呢，那么就相当于数组中的每一个元素是一个包含多个值的结构体：

    struct{
        int count;          //保存COUNT(1)的值
        double sales;    //保存SUM(sales)的值
        double cost;     //保存SUM(cost)的值
        }
一个多维数组和Cube模型还是有一些区别，例如在多维数组中我们必须在每一维指定一个值（下标）才能对应得到一个确定的值，但是在Cube中，虽然定义了三个维度，
但是我可以只指定两个维度，甚至一个维度都不指定而进行查询，例如执行SELECT COUNT(1) from table where time = ‘2016-01-01’ and location = ‘HangZhou’，
这就相当于求数组中array[0][1]的值，但这个返回的是一个数组，而SQL返回的是一个值，因此需要将这个数组中的每一个data cell取出来再进行聚合运算（例如计数、相加等）
得到的值才是真正的结果。 

Kylin与传统的OLAP一样，无法应对数据Update的情况（更新数据会导致Cube的失效，需要重建整个Cube）。
面对每天甚至每两个小时这样固定周期的增量数据，Kylin使用了一种增量Cubing技术来进行快速响应。

Kylin的Cube可以根据时间段划分成多个Segment。在Cube第一次Build完成之后会有一个Segment，
在每次增量Build后会产生一个新的Segment。增量Cubing依赖已有的CubeSegments和增量的原始数据。
增量Cubing的步骤和新建 Cube的步骤类似，Segment之间以时间段进行区分。

增量Cubing所需要面对的原始数据量更小，因此增量Cubing的速度是非常快的。然而随着CubeSegments的数目增加，一定程度上会影响到查询的进行，
所以在Segments数目到一定数量后可能需要进行CubeSegments的合并操作，
实际上MergeCube是合成了一个新的大的CubeSegment来替代，Merge操作是一个异步的在线操作，不会对前端的查询业务产生影响。


## Cube的构建算法

### 逐层算法
逻辑分层

### 快速算法
数据切割

## Cube构建逻辑流程
1. 构建一个中间平表(Hive Table)：将Model中的事实表和纬度表构建成一个大的Flat Hive Table。
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

## Cube构建操作
1. 在`Cubes`页面中，点击cube栏右侧的`Action`下拉按钮并选择`Build`操作。
2. 选择后会出现一个弹出窗口。点击END DATE输入框选择增量构建这个cube的结束日期。
3. 点击`Submit`提交请求。
4. 提交请求成功后，你将会看到`Monitor`页面新建了job。
5. 如要放弃这个job，点击`Discard`按钮。

## Job监控
* 在Monitor页面，点击job详情按钮查看显示于右侧的详细信息。
* 点击每个步骤显示的图标按钮查看详情：`Parameters`、`Log`、`MRJob`。

## CubeAction的分类

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
* `DISCARD`：无论job的状态,用户可以结束它和释放资源。

## Kylin界面功能

|  insight   |  model  |  monitor   |    system   | 
|    ---     |    -----    |   ------       |  -----       |
|   结果管理 | 模型管理，Cube管理, 数据源管理| job管理|  系统管理       |

## 参考文章
[一个创建Model的示例](http://www.mamicode.com/info-detail-1721758.html)

[一个创建Cube的示例](http://blog.51cto.com/10120275/1905936)

[相关要点详细说明](https://www.cnblogs.com/en-heng/p/5239311.html)

 

 
 
 