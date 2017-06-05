# Larva (Java Debug)

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

I used to debug C or C++ program by console, like cdb, kd when I worked in windows system. About one year ago, I began to learn Java. But I did not find a suitable debug tool in console (JDB is a good chose for someone else but I did not used to it.) I began trying to find a debugger working in console like cdb or kd, but I did not find a suitable tool, so I decided to make a debug tool of my own, called Larva (I like this name which from an interesting cartoon)
Larva, in present, has several function belowing:
breakpoints (method/line/access/modify points, create, delete, enable, disable, etc.)
sources code manage (append, delete, etc.)
threads (query, switch, suspend, resume, etc.)
stack (query frame, switch frame, etc.)
executes (source code debug, step over, step into, etc.)
class (query, query field, query method, etc.)
variant (print, field, locals, etc.)
template (support list, map, vector, queue, etc.)
monitor (wait chain, wait object, etc.)
virtual machine information
script called Larva script (support standard java expression excepting new object and invoking method)

Of course, there are a lot of debug in Larva and it do need to be perfected. 
There is a reference about Larva, you can read it if you are interested in it.
I`ll thank for your advices or debug report (runbox@163.com)  
