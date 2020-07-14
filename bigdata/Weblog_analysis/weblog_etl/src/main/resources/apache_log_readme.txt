1    Tomcat 日志概述

Tomcat 日志信息分 为 两 类 ：

一是运行中的日志，它主要 记录 运行的一些信息，尤其是一些异常 错误 日志信息 。
二是 访问 日志信息，它 记录 的 访问 的 时间 ， IP ， 访问 的 资 料等相 关 信息。

2    Tomcat 日志配置
2.1  访问日志的配置

默认 tomcat 不记录访问日志，如下方法可以使 tomcat 记录访问日志

编辑 ${catalina}/conf/server.xml 文件 . 注 :${catalina} 是 tomcat 的安装目录

把以下的注释 (<!-- -->) 去掉即可。
 <!--
        <Valve className="org.apache.catalina.valves.AccessLogValve"
         directory="logs"  prefix="localhost_access_log." suffix=".txt"
         pattern="common" resolveHosts="false"/>
  -->
2.2  配置 tomcat 写出更详细的日志

通过对 2.1 示例中 pattern 项的修改，可以改变日志输出的内容。

该项值可以为： common 与 combined ，这两个 预 先 设 置好的 格式对应的日志输出内容如下：

common 的值： %h %l %u %t %r %s %b
combined 的值： %h %l %u %t %r %s %b %{Referer}i %{User-Agent}i

pattern 也可以根据需要自由 组 合 , 例如 :
        <Valve className="org.apache.catalina.valves.AccessLogValve" directory="logs"
               prefix="localhost_access_log." suffix=".txt"
               pattern="%h %l %u %t &quot;%r&quot; %s %b &quot;%{Referer}i&quot; &quot;%{User-Agent}i&quot; &quot;%{Cookie}i&quot; %I %O %{X-Forwarded-For}i %a %D" />
               
现在我们主要来看一下pattern配置段，它用于指定日志的输出格式。有效的日志格式模式可以参见下面内容，如下字符串，其对应的信息由指定的响应内容取代：
 1．Apache日志参数说明：

Apache日志格式字符串的含义
%% 百分号(Apache2.0.44或更高的版本)
%a 远端IP地址
%A 本机IP地址
%B 除HTTP头以外传送的字节数
%b 以CLF格式显示的除HTTP头以外传送的字节数，也就是当没有字节传送时显示’-‘而不是0。
%{Foobar}C 在请求中传送给服务端的cookieFoobar的内容。
%D 服务器处理本请求所用时间，以微为单位。
%{FOOBAR}e 环境变量FOOBAR的值
%f 文件名
%h 远端主机
%H 请求使用的协议
%{Foobar}i 发送到服务器的请求头Foobar:的内容。
%l 远端登录名(由identd而来，如果支持的话)，除非IdentityCheck设为”On“，否则将得到一个”-”。
%m 请求的方法
%{Foobar}n 来自另一个模块的注解Foobar的内容。
%{Foobar}o 应答头Foobar:的内容。
%p 服务器服务于该请求的标准端口。
%P 为本请求提供服务的子进程的PID。
%{format}P 服务于该请求的PID或TID(线程ID)，format的取值范围为：pid和tid(2.0.46及以后版本)以及hextid(需要APR1.2.0及以上版本)
%q 查询字符串(若存在则由一个”?“引导，否则返回空串)
%r 请求的第一行
%s 状态。对于内部重定向的请求，这个状态指的是原始请求的状态，—%>s则指的是最后请求的状态。
%t 时间，用普通日志时间格式(标准英语格式)
%{format}t 时间，用strftime(3)指定的格式表示的时间。(默认情况下按本地化格式)
%T 处理完请求所花时间，以秒为单位。
%u 远程用户名(根据验证信息而来；如果返回status(%s)为401，可能是假的)
%U 请求的URL路径，不包含查询字符串。
%v 对该请求提供服务的标准ServerName。
%V 根据UseCanonicalName指令设定的服务器名称。
%X 请求完成时的连接状态：
X= 连接在应答完成前中断。
+= 应答传送完后继续保持连接。
-= 应答传送完后关闭连接。
(在1.3以后的版本中，这个指令是%c，但这样就和过去的SSL语法：%{var}c冲突了)
%I 接收的字节数，包括请求头的数据，并且不能为零。要使用这个指令你必须启用mod_logio模块。
%O 发送的字节数，包括请求头的数据，并且不能为零。要使用这个指令你必须启用mod_logio模块。

2．自定义Apache日志格式：

一些常见的格式串：
通用日志格式(CLF)：
“%h %l %u %t \”%r\” %>s %b”
带虚拟主机的通用日志格式：
“%v %h %l %u %t \”%r\” %>s %b”
NCSA扩展/组合日志格式：
“%h %l %u %t \”%r\” %>s %b \”%{Referer}i\” \”%{User-agent}i\”"
Referer日志格式：
“%{Referer}i -> %U”
Agent(Browser)日志格式：
“%{User-agent}i”

自定义Apache日志，则在http.conf中加入如下字段：

LogFormat "%h %l %u %t %T \"%r\" %>s %b \"%{Referer}i\" \"%{User-Agent}i\"" common

说明：一般Apache默认的格式为“LogFormat "%h %l %u %t %T \"%r\" %>s %b”，上面字段中“%｛Referer｝i”代表访问网站时，自己所处的地址，“%{User-Agent}i”代表用户使用什么浏览器访问的网站，以及用户所使用的系统是什么操作系统。后面“common”字段很重要，它代表是自己定义的，如果不加些字段，则添加的自定义日志不起作用。