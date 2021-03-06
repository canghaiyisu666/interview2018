
## 隧道检测

**原理** 
DNS 通道是隐蔽通道的一种，通过将其他协议封装在DNS协议中进行数据传输。
由于大部分防火墙和入侵检测设备很少会过滤DNS流量，这就给DNS作为隐蔽通道提供了条件，
从而可以利用它实现诸如远程控制、文件传输等操作。

**策略** 
 * 1 根据域名服务器IP地址之间的距离、解析应答中包含的txt、cname、ipv4、ipv6、mx、ns等类型数量、访问客户端IP个数等特征，提取非隐蔽通信域名服务器的白名单IP
 * 2 选择一段时间dns解析记录，对txt经过清洗处理后生成ltxt，对cname、ipv4、ip6、mx、ns等字段按去重并重新内部排序，生成word序列
 * 3 统计word序列中每个word出现的次数，生成word白名单
 * 4 对所有域名服务器为白名单IP、或者word为白名单的DNS应答记录标识为0
 * 5 对于相同域 名服务器IP，或者相同word，如果存在标识为0的记录，则都标识为0；
 * 6 第五步重复迭代三次
 * 7 提取相同客户端IP、服务端IP，且存在标识为1的记录，作为可疑线索

**输出格式** 


## 僵尸网络

### fast-flux分析和检测
**原理** 
Fast-flux技术是指不断改变域名和IP地址映射关系的一种技术，
也就是说在短时间内查询使用Fast-flux技术部署的域名，会得到不同的结果。
一个域名拥有一个不断变化的IP地址列表，这个列表可能会有几百到上千条。为了实现频繁的变化IP地址，
控制者提供最底层域名服务器，这个服务器会返回频繁变化的C&C服务器IP地址

**策略** 
 * 1 筛选使用对同一个源IP发起的域名请求解析, 在一段时间内对同一个域名解析得到的IP地址的个数, 大于10个
 * 2 同时查看其返回的TTL数值, 若设置比较小的也可能是
 * 3 查看其它记录, 如TXT, MX记录里返回的内容, 若不一致, 可能是fast-flux
 * 4 最后输出结果是源IP, 可能是被fast-flux控制的C&C服务器, 进一步查看其请求dns返回的内容是否可疑来确定

**输出格式** 

## 恶意域名
**原理** 
DNS解析日志，通过黑名单、白名单的过滤，筛选出dga随机域名，钓鱼域名（支持向量机SVM）

**策略** 
https://blog.csdn.net/mlp750303040/article/details/79307134
 

**输出格式** 

## 其他

### 放大攻击
**原理** DNS放大攻击是利用回复包比请求包大的特点，伪造请求包的源IP地址，
将应答包引向被攻击的目标；

**判断策略** 
特征为：单位时间内，同一源IP发送大量的DNS请求（超过2000次），就认为该IP受到DNS攻击。
kafka输入--->时间窗口设置--->sparksql group by--->输出

**输出格式** 
源IP地址，滑动时间窗开始时间，请求总次数



