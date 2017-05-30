软件介绍
Larva是一个基于命令行调试器，使用Java语言编写，它可以调试所有基于JavaVM运行的程序，理论上支持所有符合Java虚拟机规范的所有虚拟机与具体的语言无关，
例如：Java、Scala、JPython、JRuby等，当前作者只针对HotSpot与Dalvik两种虚拟机实现做过基本的测试，它的基本运行方式类似于Linux上的GDB与Windows中的CDB、WinDBG，除了最基本的命令功能外还内嵌了一个自定义的脚本语言，是专门为调试程序定制的可以用于编写自动化脚本，当前程序支持以以下一些基本的功能：
断点操作：方法断点、行断点、访问断点、修改断点;
单步操作：行单步（进入、跳过）、虚拟指令单步（进入、跳过）;
虚拟机信息：虚拟机基本信息如名称、版本、以及支持的基本能力;
变量查看：查看变量，包括基本类型、字符串，列表所有局部变量、字段等;
线程操作：列出所有线程、线程组，查看线程栈，挂起、恢复等;
模板变量显示：查看常见一些模板，例如：List、Map、Queue等;
类信息查询：查看类的基本信息，例如：访问权限、所有字段、所有方法、常量池等;
方法查询：查看方法的基本信息，例如：访问权限、参数、局部变量、虚拟指令等;
编写这个调试的完全是出于个人爱好，程序中难免会存在一些Bug，只限于交流学习使用，如果在使用的过程中发现有任何的问题或有任何的意见、想法都可以联系：runbox@163.com

编译程序
第一步：编译Larva需要提前配制好Java环境与Maven环境（具体配制请参见官方文档）
第二步：安装tools.jar到Maven，安装这个主要是为了后续打成可执行Jar的Lib目录中会拷贝tools.jar
mvn install:install-file -DgroupId=com.sun -DartifactId=tools -Dversion=1.8 -Dfile=%JAVA_HOME%\lib\tools.jar -Dpackaging=jar
其中%JAVA_HOME%替换成具体的安装路径
第三步：进入Larva目录下，使用Maven编译、打包程序，生成可执行Jar包；
mvn clean compile package 
成功后会在target目录下生成lib目录（所有依赖的Jar包）与larva-1.0.1.jar

启动调试
当前调试器只支持通过网络附着目标程序，首先启动被调试的程序，再启动调试器
启动目标程序（参数详细意义可以参见Java官方文档）
%JAVA_HOME%\java.exe -agentlib:jdwp=transport=dt_socket,address=地址:端口,server=y,suspend=y "程序正常运行的需其它参数"
样例：
%JAVA_HOME%\java.exe -agentlib:jdwp=transport=dt_socket,address=localhost:1025,server=y,suspend=y -classpath ".\target\classes\" com.runbox.demo.Demo
%JAVA_HOME%\java.exe -agentlib:jdwp=transport=dt_socket,address=192.168.1.123:1025,server=y,suspend=y -classpath ".\target\classes\" com.runbox.demo.Demo
启动调试器 
%JAVA_HOME%\java.exe -jar larva.jar -address 地址:端口 -script "Larva脚本文件"
样例：
%JAVA_HOME%\java.exe -jar larva.jar -address localhost:1025 -script D:\demo\debug.jdb
%JAVA_HOME%\java.exe -jar larva.jar -address 192.168.1.123:1025
参数说明：
-address 被调试目标的监听地址包括IP与Port（必须）
-script 自定义的调试脚本（可选）

脚本语言

命令列表

别名
alias.define command, alias
说明：为某条命令定义一个别名，这个别名可以在后续使用效果如同命令本身一样，不能对某个别名再定义别名;
参数：command某个已经定义的命令不能是某个别名，必须是字符串类型;
      alias自定义的别名，必须是字符串类型;
样例：alias.define "breakpoint.line", "b.l";
      alias.define "execute.next.over", "e.n.o";
alias.delete alias[, alias]
说明：删除一个或多个已经定义的别名;
参数：alias[, alias] 一个或多个别名以逗号间隔, 每一个别名必须是字符串类型，如果没有参数则删除所有已定义的别名；
样例：alias.delete "b.l", "e.n.o";
alias.query
说明：查询当前已经定义的所有命令别名;
参数：无
样例：alias.query

导入类
import.class classPath
说明：为了避免在过长的类路径, 可以将某个类以全路径的方式导入后续直接使用类名称就可以了, 这个命令可以导入内部类具体参见样例
参数：类的全路径
样例：import.class "java.lang.String";
      import.class "java.lang.Integer";
      import.class "java.util.Map";
      import.class "java.util.LinkedList";
      @clazz = "com.runbox.demo.Demo"; import.class @clazz;
      import.class "com.runbox.demo.Demo$Inner";

import.delete [className[, className]]
说明：删除已经被导入的类
参数：[className[, className]] 一个或多个已经导入的类以逗号间隔，每一个类名称必须是字符串类型，如果没有参数则删除所有被导入的类；
样例：import.delete "String";
      import.delete "Demo";
      @clazz = "Demo$Inner"; import.delete @clazz, "Map", "LinkedList";
import.query
说明：列出所有已经被导入的类
参数：无
样例：import.query

虚拟机信息
machine.name
说明：获取当前被调试的目标虚拟机名称
参数：无
样例：machine.name
machine.version 
说明：获取当前被调试的目标虚拟机版本
参数：无
样例：machine.version
machine.ability
说明：获取当前被调试目标虚拟机的某些特性
参数：
样例：
machine.suspend
说明：挂起当前被调试的目标虚拟机
参数：无
样例：machine.suspend
machine.resume
说明：恢复当前被挂起的目标虚拟机
参数：无
样例：machine.resume
machine.status
说明：获取当前被调试的目标虚拟机状态（运行或挂起）以及挂起次数
参数：无
样例：machine.status

类信息
class.query
说明：
格式：
参数：
样例：
class.field
说明：
格式：
参数：
样例：
class.method
说明：
格式：
参数：
样例：
class.monitor.query
说明：
格式：
参数：
样例：
class.monitor.prepare
说明：
格式：
参数：
样例：
class.monitor.unload
说明：
格式：
参数：
样例：
class.monitor.enable
说明：
格式：
参数：
样例：
class.monitor.disable
说明：
格式：
参数：
样例：
class.monitor.delete
说明：
格式：
参数：
样例：
class.constant
说明：
格式：
参数：
样例：

方法
method.argument
说明：
格式：
参数：
样例：
method.local
说明：
格式：
参数：
样例：
method.bytecode
说明：
格式：
参数：
样例：
method.monitor.entry
说明：
格式：
参数：
样例：
method.monitor.return
说明：
格式：
参数：
样例：

线程
thread.query
说明：
格式：
参数：
样例：
thread.switch
说明：
格式：
参数：
样例：
thread.suspend
说明：
格式：
参数：
样例：
thread.resume
说明：
格式：
参数：
样例：
thread.interrupt
说明：
格式：
参数：
样例：
thread.stack
说明：
格式：
参数：
样例：
thread.hold
说明：
格式：
参数：
样例：
thread.wait
说明：
格式：
参数：
样例：
thread.monitor.start
说明：
格式：
参数：
样例：
thread.monitor.death
说明：
格式：
参数：
样例：

断点
breakpoint.method
说明：
格式：
参数：
样例：
breakpoint.line
说明：
格式：
参数：
样例：
breakpoint.access
说明：
格式：
参数：
样例：
breakpoint.modify
说明：
格式：
参数：
样例：
breakpoint.query
说明：
格式：
参数：
样例：
breakpoint.delete
说明：
格式：
参数：
样例：
breakpoint.enable
说明：
格式：
参数：
样例：
breakpoint.disable
说明：
格式：
参数：
样例：

执行
execute.run
说明：
格式：
参数：
样例：
execute.next.over
说明：
格式：
参数：
样例：
execute.next.into
说明：
格式：
参数：
样例：
execute.step.over
说明：
格式：
参数：
样例：
execute.step.into
说明：
格式：
参数：
样例：
execute.file
说明：
格式：
参数：
样例：

显示变量
print.variable
说明：
格式：
参数：
样例：
print.field
说明：
格式：
参数：
样例：
print.local
说明：
格式：
参数：
样例：
print.array
说明：
格式：
参数：
样例：
print.string
说明：
格式：
参数：
样例：

显示模板
template.list
说明：
格式：
参数：
样例：
template.map
说明：
格式：
参数：
样例：
template.vector
说明：
格式：
参数：
样例：
template.queue
说明：
格式：
参数：
样例：
template.stack
说明：
格式：
参数：
样例：

源代码
source.append
说明：
格式：
参数：
样例：
source.delete
说明：
格式：
参数：
样例：
source.query
说明：
格式：
参数：
样例：

异常捕获
exception.monitor
说明：
格式：
参数：
样例：
exception.delete
说明：
格式：
参数：
样例：
exception.query
说明：
格式：
参数：
样例：

退出
quit
说明：
格式：
参数：
样例：
detach
说明：
格式：
参数：
样例：

帮助
help
说明：
格式：
参数：
样例：