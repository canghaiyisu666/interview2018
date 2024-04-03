# DNS协议

## 概念
DNS（Domain Name System，域名系统），万维网上作为域名和IP地址相互映射的一个分布式数据库，
能够使用户更方便的访问互联网，而不用去记住能够被机器直接读取的IP数串。通过域名，
最终得到该域名对应的IP地址的过程叫做域名解析（或主机名解析）。DNS协议运行在UDP协议之上，使用端口号53。

## 功能
每个IP地址都可以有一个主机名，主机名由一个或多个字符串组成，字符串之间用小数点隔开。有了主机名，
就不要死记硬背每台IP设备的IP地址，只要记住相对直观有意义的主机名就行了。这就是DNS协议的功能。
主机名到IP地址的映射有两种方式：
   *  静态映射，每台设备上都配置主机到IP地址的映射，各设备独立维护自己的映射表，而且只供本设备使用；
   *  动态映射，建立一套域名解析系统（DNS），只在专门的DNS服务器上配置主机到IP地址的映射，网络上需要使用主机名通信的设备，首先需要到DNS服务器查询主机所对应的IP地址。  
   
通过主机名，最终得到该主机名对应的IP地址的过程叫做域名解析（或主机名解析）。在解析域名时，可以首先采用静态域名解析的方法，如果静态域名解析不成功，再采用动态域名解析的方法。
可以将一些常用的域名放入静态域名解析表中，这样可以大大提高域名解析效率。

## DNS冗余
为保证服务的高可用性，DNS要求使用多台名称服务器冗余支持每个区域。
某个区域的资源记录通过手动或自动方式更新到单个主名称服务器（称为主 DNS服务器）上，主 DNS 服务器可以是一个或几个区域的权威名称服务器。
其它冗余名称服务器（称为辅 DNS 服务器）用作同一区域中主服务器的备份服务器，以防主服务器无法访问或宕机。
辅 DNS服务器定期与主 DNS 服务器通讯，确保它的区域信息保持最新。
如果不是最新信息，辅 DNS服务器就会从主服务器获取最新区域数据文件的副本。
这种将区域文件复制到多台名称服务器的过程称为区域复制。

## 域名结构
通常 Internet 主机域名的一般结构为：主机名.三级域名.二级域名.顶级域名。
 Internet 的顶级域名由 Internet网络协会域名注册查询负责网络地址分配的委员会进行登记和管理，
 它还为 Internet的每一台主机分配唯一的 IP 地址。全世界现有三个大的网络信息中心： 
 位于美国的 Inter-NIC，负责美国及其他地区； 位于荷兰的RIPE-NIC，负责欧洲地区；位于日本的APNIC ，
 负责亚太地区。
 
 ## 解析器
 解析器，或另一台DNS服务器递归代表的情况下，域名解析器，协商使用递归服务，使用查询头位。
 解析通常需要遍历多个名称服务器，找到所需要的信息。然而，一些解析器的功能更简单地只用一个名称服务器进行通信。
 这些简单的解析器依赖于一个递归名称服务器（称为“存根解析器”），为他们寻找信息的执行工作。
 
 ## DNS服务器
 提供DNS服务的是安装了DNS服务器端软件的计算机。服务器端软件既可以是基于类linux操作系统，
 也可以是基于Windows操作系统的。装好DNS服务器软件后，您就可以在您指定的位置创建区域文件了，
 所谓区域文件就是包含了此域中名字到IP地址解析记录的一个文件，如文件的内容可能是这样的：
 primary name server = dns2（主服务器的主机名是 ）
 serial = 2913 （序列号=2913、这个序列号的作用是当辅域名服务器来复制这个文件的时候，如果号码增加了就复制）
 refresh = 10800 (3 hours) （刷新=10800秒、辅域名服务器每隔3小时查询一个主服务器）
 retry = 3600 (1 hour) （重试=3600秒、当辅域名服务试图在主服务器上查询更新时，而连接失败了，辅域名服务器每隔1小时访问主域名服务器）
 expire = 604800 (7 days) （到期=604800秒、辅域名服务器在向主服务更新失败后，7天后删除中的记录。）
 default TTL = 3600 (1 hour) （默认生存时间=3600秒、缓存服务器保存记录的时间是1小时。也就是告诉缓存服务器保存域的解析记录为1小时）

## SDNS
中国互联网络信息中心(CNNIC)研发出我国首个面向下一代互联网的域名服务平台——SDNS。

## DNS查询方法
查询DNS服务器上的资源记录
在Windows平台下，使用命令行工具，输入nslookup，返回的结果包括域名对应的IP地址（A记录）、别名（CNAME记录）等。除了以上方法外，还可以通过一些DNS查询站点
如国外的国内的 查询域名的DNS信息。
常用的资源记录类型
A 地址 此记录列出特定主机名的 IP 地址。这是名称解析的重要记录。
CNAME 标准名称 此记录指定标准主机名的别名。
MX邮件交换器此记录列出了负责接收发到域中的电子邮件的主机。
NS名称服务器此记录指定负责给定区域的名称服务器。
FQDN名的解析过程查询
若想跟踪一个FQDN名的解析过程，在LinuxShell下输入dig www +trace，返回的结果包括从根域开始的递归或迭代过程，一直到权威域名服务器。
GeniePro DNS 应对DNS劫持和DNS缓存中毒攻击的关键性机制：一致性检查
每个Geniepro节点将自身的DNS记录发送给工作组内其他节点请求一致性检查；
每个Geniepro节点将自身的记录与收到的记录进行比较；
每个Geniepro工作组的通信协调节点将获得的DNS记录更新发送给其他组的通信协调节点请求一致性检查；
每个Genipro工作组的通信协调节点向上一级DNS服务器请求更新记录并与收到的其他通信协调节点的记录进行比较。
一致性仲裁
如果一致性检查发现记录不一致情况，则根据策略（少数服从多数、一票否决等）决定是否接受记录的变化 根据结果，
各Geniepro节点将自身记录进行统一 通信协调节点选举 选举出的通信协调节点在任期内具有更新组内节点的权限 
选举过程满足不可预测性和不可重复性DNS资源记录　如前所述，每个 DNS 数据库都由资源记录构成。
一般来说，资源记录包含与特定主机有关的信息，如 IP 地址、主机的所有者或者提供服务的类型。

## 故障解决
当DNS解析出现错误，例如把一个域名解析成一个错误的IP地址，或者根本不知道某个域名对应的IP地址是什么时，就无法通过域名访问相应的站点了，这就是DNS解析故障。出现DNS解析故障最大的症状就是访问站点对应的IP地址没有问题，然而访问他的域名就会出现错误。
（1）用nslookup(网路查询)来判断是否真的是DNS解析故障：
要想百分之百判断是否为DNS解析故障就需要通过系统自带的NSLOOKUP来解决了。
第一步：确认自己的系统是windows 2000和windows xp以上操作系统，然后通过“开始->运行->输入CMD”后回车进入命令行模式。
第二步：输入nslookup命令后回车，将进入DNS解析查询界面。
第三步：命令行窗口中会显示出当前系统所使用的DNS服务器地址，例如笔者的DNS服务器IP为202.106.0.20。
第四步：接下来输入无法访问的站点对应的域名。假如不能访问的话，那么DNS解析应该是不能够正常进行的，会收到DNS request timed out，timeout was 2 seconds的提示信息。这说明本地计算机确实出现了DNS解析故障。
小提示：如果DNS解析正常的话，会反馈回正确的IP地址。
（2）查询DNS服务器工作是否正常：
这时候要看本地计算机使用的DNS地址是多少了，并且查询他的运行情况。
第一步：通过“开始->运行->输入CMD”后回车进入命令行模式。
第二步：输入ipconfig/all命令来查询网络参数。
第三步：在ipconfig /all显示信息中能够看到一个地方写着DNS SERVERS，这个就是本地的DNS服务器地址。例如笔者的是202.106.0.20和202.106.46.151。从这个地址可以看出是个外网地址，如果使用外网DNS出现解析错误时，可以更换一个其他的DNS服务器地址即可解决问题。
第四步：如果在DNS服务器处显示的是个人公司的内部网络地址，那么说明该公司的DNS解析工作是交给公司内部的DNS服务器来完成的，这时需要检查这个DNS服务器，在DNS服务器上进行nslookup操作看是否可以正常解析。解决DNS服务器上的DNS服务故障，一般来说问题也能够解决。
（3）清除DNS缓存信息法：
第一步：通过“开始->运行->输入CMD”进入命令行模式。
第二步：在命令行模式中我们可以看到在ipconfig /?中有一个名为/flushdns的参数，这个就是清除DNS缓存信息的命令。
第三步：执行ipconfig /flushdns命令，当出现“successfully flushed the dns resolver cache”的提示时就说明当前计算机的缓存信息已经被成功清除。
第四步：接下来我们再访问域名时，就会到DNS服务器上获取最新解析地址，再也不会出现因为以前的缓存造成解析错误故障了。
（4）修改HOSTS（主机）文件法：
第一步：通过“开始->搜索”，然后查找名叫hosts的文件。
第二步：当然对于已经知道他的路径的读者可以直接进入c:\windows\system32\drivers\etc目录中找到HOSTS文件。如果你的系统是windows 2000，那么应该到c:\winnt\system32\drivers\etc目录中寻找。
第三步：双击HOSTS文件，然后选择用“记事本”程序将其打开。
第四步：之后我们就会看到HOSTS文件的所有内容了，默认情况下只有一行内容“127.0.0.1 localhost”。（其他前面带有#的行都不是真正的内容，只是帮助信息而已）
第五步：将你希望进行DNS解析的条目添加到HOSTS文件中。具体格式是先写该域名对应的IP地址，然后空格接域名信息。
第六步：设置完毕后我们访问网址时就会自动根据是在内网还是外网来解析了。


## DNS安全问题
1. 针对域名系统的恶意攻击：DDOS攻击造成域名解析瘫痪。
2. 域名劫持：修改注册信息、劫持解析结果。
3. 国家性质的域名系统安全事件：“.ly”域名瘫痪、“.af”域名的域名管理权变更。
4. 系统上运行的DNS服务存在漏洞，导致被黑客获取权限，从而篡改DNS信息。
5. DNS设置不当，导致泄漏一些敏感信息。提供给黑客进一步攻击提供有力信息。


## 递归查询与迭代查询

递归和迭代的区别，通俗地说：递归就是把一件事情交给别人，如果事情没有办完，哪怕已经办了很多，都不要把结果告诉我，我要的是你的最终结果，而不是中间结果；
如果你没办完，请你找别人办完；而迭代则是我交给你一件事，你能办多少就告诉我你办了多少，然后剩下的事情就由我来办。 
通常情况下，主机向本地域名服务器的查询一般都是采用递归查询，本地域名服务器向根根域名服务器的查询通常采用迭代查询。

## DNS报文格式
![DNS报文格式](https://img-blog.csdn.net/20140518100302531)

* 标志部分：
    * QR：定义报文类型，QR=0表示查询报文,1表示响应报文；

    * opcode:定义查询或响应的类型，0表示标准的，1表示反向的，2表示服务器状态请求；

    * AA(Authoritative answer)：授权回答，只用于响应报文。1表示该域名服务器是该区域的授权服务器；

    * TC(Truncated):可截断的，使用UDP服务时，若响应报文的总长度超过512B，则只返回前512B；

    * RD(Recursion Desired):期望递归，0表示DNS客户进程希望迭代查询，1表示DNS客户希望递归查询；

    * RA(Recursion Available):递归可用，只用于响应报文。若域名服务器支持递归，则该服务器便在响应报文中将 RA=1。

    * (zero)：保留字段；

    * rcode:表示在响应中的差错状态，只有权限服务器才能做出该判断。具体如下：
    
        0：无差错               1：格式差错            2：问题出在域名服务器上
        3：名字差错           4：查询类型不支持  5：在管理上被禁止           6~15：保留

* 查询问题

    ![查询问题详解](https://img-blog.csdn.net/20140518102906203)  
    * 查询名：包含域名的可变长度字段，每个域以计数开头，最后一个字符为0（零）,每个字符占1B。如：www.baidu.com可以这样记录：3www5baidu3com0；
    * 查询类型：每一个问题的查询有一个类型，响应也有一个类型。
    
   ![查询类型](https://img-blog.csdn.net/20140518103849390)  
   其中最常用的查询是A类型和PTR类型：A类型就是域名到IP地址，PTR查询就是IP地址到域名。
    * 查询类：定义了使用DNS的特定协议，1表示因特网。
    
## 高速缓存caching
DNS客户程序将查询报文交给某个DNS服务器后（通常是主机域名服务器），若被查询的域名不在该DNS服务器的映射表中，
则该DNS服务器会向另外一个DNS服务器请求 映射并在接收到响应后，把该映射信息返回给DNS客户程序之前，先要将该映射存储在自己的Caching中。
若同一个客户或另一个客户请求同样的映射时，它就检查caching，并能够快速实现解析。当然，由于该服务器不是DNS程序的所请求域名的权限服务器，
为了告知DNS客户程序这个响应是来自caching，而不是一个授权的信息源，这个服务器要把响应标记为未授权的。

Caching可以提高解析效率，当然“如何更新Caching”是一个问题。可以采取如下方案反证Caching过时：

* 权限域名服务器把TTL的信息添加到映射上，因为TTL定义了接收信息的服务器可以把映射信息放入Caching的时间长度（通常为2天），经过这段时间后，这个映射就变为无效的，因而任何查询都必须在发送给权限服务器。

* DNS要求每一个权限服务器对Caching中的每一条映射都有保持一个TTL计数器。Caching必须定期地搜索并清除那些TTL到期的映射。

## DNS请求中UDP和TCP的选择

   DNS可以使用UDP/53，也可以使用TCP/53，当响应报文的长度小于512B时就使用UDP(因为UDP的最大报文长度为512B)，若响应报文的长度超过512B，则选用TCP。DNS协议关于UDP和TCP的选择通常为以下两种情况：

 * 若DNS客户程序事先知道响应报文的长度512B，则应当使用TCP连接；

    NOTICE:主域名服务器与辅助域名服务器在进行区域传送时，通常数据量都比较大，所有DNS规定，区域传送使用TCP协议。

 * 若解析程序不知道响应报文的长度，它一般使用UDP协议发送DNS查询报文，若DNS响应报文的长度大于512B，服务器就截断响应报文，并把TC(truncated)位置1，在这种情况下，DNS客户程序通常使用TCP重发原来的查询请求，从而它将来能够从DNS服务器中收到完整的响应。

综上所述，我们可以得出结论：DNS客户程序在不知情（不知道DNS响应报文的长度是否超过512B）的情况下通常采用UDP与DNS服务器程序连接，在知情的情况下则采用TCP进行连接。






