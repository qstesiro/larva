# 软件介绍
Larva是一个基于命令行调试器，使用Java语言编写，它可以调试所有基于JavaVM运行的程序，理论上支持所有符合Java虚拟机规范的所有虚拟机与具体的语言无关，
例如：Java、Scala、JPython、JRuby等，当前作者只针对HotSpot与Dalvik两种虚拟机实现做过基本的测试，它的基本运行方式类似于Linux上的GDB与Windows中的CDB、WinDBG，除了最基本的命令功能外还内嵌了一个自定义的脚本语言，是专门为调试程序定制的可以用于编写自动化脚本，当前程序支持以以下一些基本的功能：<br>
断点操作：方法断点、行断点、访问断点、修改断点;<br>
单步操作：行单步（进入、跳过）、虚拟指令单步（进入、跳过）;<br>
虚拟机信息：虚拟机基本信息如名称、版本、以及支持的基本能力;<br>
变量查看：查看变量，包括基本类型、字符串，列表所有局部变量、字段等;<br>
线程操作：列出所有线程、线程组，查看线程栈，挂起、恢复等;<br>
模板变量显示：查看常见一些模板，例如：List、Map、Queue等;<br>
类信息查询：查看类的基本信息，例如：访问权限、所有字段、所有方法、常量池等;<br>
方法查询：查看方法的基本信息，例如：访问权限、参数、局部变量、虚拟指令等;<br>
编写这个调试的完全是出于个人爱好，程序中难免会存在一些Bug，只限于交流学习使用，如果在使用的过程中发现有任何的问题或有任何的意见、想法都可以联系：runbox@163.com

## 特别提示：
如果你喜欢在命令行下工作或是调试程序，但是主要的工作平台是windows系统的话，我个人强烈建议你使用以下软件<br>
Online documentation: https://conemu.github.io/en/TableOfContents.html<br>
这个软件我用了有几年了，现在的功能已经比较稳定了，平时我在工作与学习时都使用它，强烈推荐：）<br>

## 编译程序
第一步：编译Larva需要提前配制好Java环境（需要1.8版本）与Maven环境（3.0版本）<br>
第二步：安装tools.jar到Maven，安装这个主要是为了后续打成可执行Jar的Lib目录中会拷贝tools.jar<br>
mvn install:install-file -DgroupId=com.sun -DartifactId=tools -Dversion=1.8 -Dfile=%JAVA_HOME%\lib\tools.jar -Dpackaging=jar<br>
其中%JAVA_HOME%替换成具体的安装路径<br>
第三步：进入Larva目录下，使用Maven编译、打包程序，生成可执行Jar包；<br>
mvn clean compile package <br>
成功后会在target目录下生成lib目录（包含所有依赖的Jar包）与larva-1.0.1.jar

## 启动调试
当前调试器只支持通过网络附着目标程序，首先启动被调试的程序，再启动调试器<br>
启动目标程序（参数详细意义可以参见Java官方文档）<br>
%JAVA_HOME%\java.exe -agentlib:jdwp=transport=dt_socket,address=地址:端口,server=y,suspend=y "程序正常运行的需其它参数"<br>
样例：%JAVA_HOME%\java.exe -agentlib:jdwp=transport=dt_socket,address=localhost:1025,server=y,suspend=y -classpath ".\target\classes\" com.runbox.demo.Demo<br>
&emsp;&emsp;&emsp;%JAVA_HOME%\java.exe -agentlib:jdwp=transport=dt_socket,address=192.168.1.123:1025,server=y,suspend=y -classpath ".\target\classes\" com.runbox.demo.Demo<br>
启动调试器 <br>
%JAVA_HOME%\java.exe -jar larva.jar -address 地址:端口 -script "Larva脚本文件"<br>
样例：%JAVA_HOME%\java.exe -jar larva.jar -address localhost:1025 -script D:\demo\debug.jdb<br>
&emsp;&emsp;&emsp;%JAVA_HOME%\java.exe -jar larva.jar -address 192.168.1.123:1025<br>
参数说明：-address 被调试目标的监听地址包括IP与Port（必须）<br>
&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;-script 自定义的调试脚本（可选）

## 脚本语言（请参见SCRIPT.md）

## 命令列表（请参见COMMAND.md）

