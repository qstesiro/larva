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

编译程序

启动调试
当前调试器只支持通过网络附着目标程序，首先启动被调试的程序，再启动调试器
启动目标程序（参数详细意义可以参见Java官方文档）%JAVA_HOME%/java -agentlib:jdwp=transport=dt_socket,address=localhost:1025,server=y,suspend=y "程序正常运行的需其它参数"
例：%JAVA_HOME%/java -agentlib:jdwp=transport=dt_socket,address=localhost:1025,server=y,suspend=y -classpath ".\target\classes\" com.runbox.demo.Demo
启动调试器 %JAVA_HOME%/java -jar larva.jar -address localhost:1025 -script "Larva脚本文件"
例：%JAVA_HOME%/java -jar larva.jar -address localhost:1025 -script D:\demo\debug.jdb
参数说明：
-address 被调试目标的监听地址包括IP与Port（必须）
-script 自定义的调试脚本（可选）

脚本语言

命令列表
别名
alias.define
alias.delete
alias.query
导入
import.class
import.delete
import.query
虚拟机信息
machine.name
machine.version
machine.ability
machine.suspend
machine.resume
machine.status
类信息
class.query
class.field
class.method
class.monitor.query
class.monitor.prepare
class.monitor.unload
class.monitor.enable
class.monitor.disable
class.monitor.delete
class.constant
方法
method.argument
method.local
method.bytecode
method.monitor.entry
method.monitor.return
线程
thread.query
thread.switch
thread.suspend
thread.resume
thread.interrupt
thread.stack
thread.hold
thread.wait
thread.monitor.start
thread.monitor.death
断点
breakpoint.method
breakpoint.line
breakpoint.access
breakpoint.modify
breakpoint.query
breakpoint.delete
breakpoint.enable
breakpoint.disable
执行
execute.run
execute.next.over
execute.next.into
execute.step.over
execute.step.into
execute.file
显示变量
print.variable
print.field
print.local
print.array
print.string
显示模板
template.list
template.map
template.vector
template.queue
template.stack
源代码
source.append
source.delete
source.query
异常捕获
exception.monitor
exception.delete
exception.query
退出
quit
detach
帮助
help

附录：编写这个调试的完全是出于个人爱好，程序中难免会存在一些Bug，只限于交流学习使用，如果在使用的过程中发现有任何的问题或有任何的意见、想法都可以联系：runbox@163.com