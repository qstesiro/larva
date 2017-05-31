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

特别提示：如果你喜欢在命令行下工作或是调试程序，但是主要的工作平台是window系统的话，我个人强烈建议你使用以下软件
         Online documentation: https://conemu.github.io/en/TableOfContents.html
         这个软件我用了有几年了，现在的功能已经比较稳定了，平时我在工作与学习时都使用它，强烈推荐：）

编译程序
第一步：编译Larva需要提前配制好Java环境（需要1.8版本）与Maven环境（3.0版本）
第二步：安装tools.jar到Maven，安装这个主要是为了后续打成可执行Jar的Lib目录中会拷贝tools.jar
mvn install:install-file -DgroupId=com.sun -DartifactId=tools -Dversion=1.8 -Dfile=%JAVA_HOME%\lib\tools.jar -Dpackaging=jar
其中%JAVA_HOME%替换成具体的安装路径
第三步：进入Larva目录下，使用Maven编译、打包程序，生成可执行Jar包；
mvn clean compile package 
成功后会在target目录下生成lib目录（包含所有依赖的Jar包）与larva-1.0.1.jar

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

脚本语言 （暂无说明，后续完善）
具体代码参见以下两个包中的源码
com.runbox.script
com.runbox.debug.script

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
thread.query [flags]
说明：列出当前所有线程
参数：flags 一个组合标志位，必须是一整形
样例：
thread.switch id
说明：切换线程
参数：id 线程ID，必须是整形
样例：thread.switch 10;
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
breakpoint.method [package.]className.method([argument[, argument]]) {block}
说明：通过方法设置断点
参数：package 包路径，这是一个可选的部分，如果在执行此条命令之前已经通过import.class命令导入了类，就可能只使用类名称；
      className 类名称，这是必须给出，如果是内嵌类需要使用外部分类加内嵌类，例如：Demo$Inner形式；
      method 方法名称
      argument 是方法的参数类型，参数个数根据method来确定，参数类型如果之前已经通过import.class命令导入了类，也可以只使用
               类名称，例如：method(Map, List);
      以上四部分必须是一个字符串类型
      block 是命令尾块，这个块中的脚本会在断点被命中后执行；
样例：import.class "com.runbox.demo.Demo";
      import.class "com.runbox.demo.Demo$Inner";
      breakpoint.method "Demo.method(String, Integer, Map)" {
            print.value @id; 
            print.field @thread; 
            print.value "this is a method breakpoint"; 
            execute.run;
      };
      breakpoint.method "Demo$Inner.method()" {execute.run;};
      @method = "Demo.method()"; breakpoint.method @method {print.value "hello debugger."};
breakpoint.line [package.]className:lineNumber {block}
说明：通过行号设置断点
参数：package 包路径，这是一个可选的部分，如果在执行此条命令之前已经通过import.class命令导入了类，就可能只使用类名称；
      className 类名称，这是必须给出，如果是内嵌类需要使用外部分类加内嵌类，例如：Demo$Inner形式；
      lineNumber 是在源代码中行号
      以上三部分必须是一个字符串类型，也可以是一个自定义的auto变量（具体参数）；
      block 是命令尾块，这个块中的脚本会在断点被命中后执行；
样例：import.class "com.runbox.demo.Demo";
      breakpoint.line "Demo.line:61";
      @line = "Demo.line:61"; breakpoint.line @line;
breakpoint.access [package.]className.fieldName {block}
说明：设置一个字段的访问断点，当字段值被读取进触发
参数：package 包路径，这是一个可选的部分，如果在执行此条命令之前已经通过import.class命令导入了类，就可能只使用类名称；
      className 类名称，这是必须给出，如果是内嵌类需要使用外部分类加内嵌类，例如：Demo$Inner形式；
      fieldName 字段名称
      以上三部分必须是一个字符串类型，也可以是一个自定义的auto变量（具体参数）；
      block 是命令尾块，这个块中的脚本会在断点被命中后执行；
样例：import.class "com.runbox.demo.Demo";
      breakpoint.access "Demo.count";
breakpoint.modify [package.]className.fieldName {block}
说明：设置一个字段的访问断点，当字段值被修改时触发
参数：package 包路径，这是一个可选的部分，如果在执行此条命令之前已经通过import.class命令导入了类，就可能只使用类名称；
      className 类名称，这是必须给出，如果是内嵌类需要使用外部分类加内嵌类，例如：Demo$Inner形式；
      fieldName 字段名称
      以上三部分必须是一个字符串类型，也可以是一个自定义的auto变量（具体参数）；
      block 是命令尾块，这个块中的脚本会在断点被命中后执行；
样例：import.class "com.runbox.demo.Demo";
      breakpoint.modify "Demo$Inner.count" {execute.run;};
breakpoint.query 
说明：列出当前所有断点
参数：无
样例：breakpoint.query;
breakpoint.delete [id[, id]]
说明：删除某个或某些断点
参数：[id[, id]] 一个或多个断点的ID，多个ID用逗号分隔，如果无参数则删除所有断点，所有ID必须为整形类型；
样例：@id = 4; breakpoint.delete 2，0x3, @id;
breakpoint.enable [id[, id]]
说明：启动某个或某些断点
参数：[id[, id]] 一个或多个断点的ID，多个ID用逗号分隔，如果无参数则启用所有处于禁用状态断点，所有ID必须为整形类型；
样例：@id = 4; breakpoint.enable 2，0x3, @id;
breakpoint.disable [id[, id]]
说明：禁用某个或某些断点
参数：[id[, id]] 一个或多个断点的ID，多个ID用逗号分隔，如果无参数则启用所有处于启用状态断点，所有ID必须为整形类型；
样例：@id = 4; breakpoint.enable 2，0x3, @id;

执行
execute.run
说明：继续运行当前被调试的目标
参数：无
样例：execute.run;
execute.next.over [count]
说明：以源码为单位运行，遇到方法调用不进入
参数：count 代码运行几行，默认为一行，必须是整形数
样例：execute.next.over;
      execute.next.over 2;
      @count = 0x3; execute.next.over @count;
execute.next.into [count]
说明：以源码为单位运行，遇到方法调用则进入
参数：count 代码运行几行，默认为一行，必须是整形数
样例：execute.next.into；
      execute.next.into 2；
      @count = 0x3; execute.next.into @count;
execute.step.over [count]
说明：以虚拟指令为单位运行，遇到方法调用不进入
参数：count 运行几条虚拟指令，默认为一条，必须是整形数
样例：execute.step.over；
      execute.step.over 2；
      @count = 0x3; execute.step.over @count;
execute.step.into [count]
说明：以虚拟指令为单位运行，遇到方法调用则进入
参数：count 运行几条虚拟指令，默认为一条，必须是整形数
样例：execute.step.into；
      execute.step.into 2；
      @count = 0x3; execute.step.into @count;
execute.file file --- 此功能当前未完成
说明：运行一个外部的文件，文件内容是larva脚本；
参数：文件全路径，必须是字符串类型；
样例：execute.file ".\debug.jdb";
      @file = ".\debug.jdb"; execute.file @file;

显示变量
print.value expression[, flags]
说明：计算一个表达式并显示其结果
参数：expression 标准的Larva脚本表达式（具体参见脚本说明部分）
      flags 是一个标志组合
      0x00 不显示任何类型（默认值）；
      0x01 显示变量类型；
      0x02 显示变量值类型；
      对于原始类型来说变量类型与值类型一致，但是对于引用变量则不同，例如：引用类型为Object，但是某值可能为Object任何子类；
样例： @var = "hello"; print.value @var;
      print.value "dog, come on";
      @var = 10; print.value @var, 0x1;
      @var = 10; print.value @var, 0x2;
      @var = 10; @flags = 0x1 | 0x3; print.value @var, @flags;
      print.value this.inner.count;
print.field expression[, flags]
说明：列出一具对象的所有字段
参数：expression 标准larva脚本表达式，其运算结果必须为一个对象引用；
      flags 是一个标志组合
      0x00 不显示任何类型（默认值）；
      0x01 显示变量类型；
      0x02 显示变量值类型；
      对于原始类型来说变量类型与值类型一致，但是对于引用变量则不同，例如：引用类型为Object，但是某值可能为Object任何子类；
样例：print.field this;
      print.field this.map, 0x3;
      @var = this.list; @flags = 0x1 | 0x3; print.field @var, @flags;
print.local [flags]
说明：列出当前栈帧中所有局部变量
参数：flags 是一个标志组合
      0x00 不显示任何类型（默认值）；
      0x01 显示变量类型；
      0x02 显示变量值类型；
      对于原始类型来说变量类型与值类型一致，但是对于引用变量则不同，例如：引用类型为Object，但是某值可能为Object任何子类；
样例：print.local;
      print.local 0x2;
      @flags = 0x1 | 0x3; print.field @flags;
print.array expression[, flags[, index[, count]]]
说明：格式化显示数组
参数：expression 标准larva脚本表达式，其运算结果必须为一个数组引用；
      flags 是一个标志组合      
      0x01 显示基本统计信息；
      0x02 显示元素具体值；
      0x03 显示基本统计信息与元素具体值（默认）
      index 从第几个索引开始显示（默认0）
      count 显示几个元素（默认所有元素）
      注意以上flags, index, count虽然是可选的但是如果后面的参数出现则前面的参数也必须出现不能省略，
      只有后续不需要的参数是可省略（语法规则与C++中的函数默认实参类似，具体参见样例）；
样例：print.array array1, 1, 0; (count可以被省略)
      print.array array2, 2; (index, count 可以被省略)
      print.array array3; (flags, index, count 都可以被省略)
      print.array array4, 3, 0, 10; (当前命令主要想显示10个元素，但是第两个flags, index 参数不能省略)
print.string expression[, flags[, index[, count[, line]]]]
说明：格式化显示字符串
参数：expression 标准larva脚本表达式，其运算结果必须为一个字符串类型；
      flags 是一个标志组合      
      0x01 显示基本统计信息；
      0x02 显示元素具体值；
      0x03 显示基本统计信息与元素具体值（默认）
      index 从第几个索引开始显示（默认0）
      count 显示几个元素（默认的所有元素）
      line 一行显示多少个字符（默认不限制，以输出目标设备边界为依据）
      注意以上flags, index, count, line虽然是可选的但是如果后面的参数出现则前面的参数也必须出现不能省略，
      只有后续不需要的参数是可省略（语法规则与C++中的函数默认实参类似，具体参见样例）；
样例：print.string string1, 1, 0; (count, line可以被省略)
      print.string string2, 2; (index, count, line 可以被省略)
      print.string string3; (flags, index, count, line 都可以被省略)
      print.string string4, 3, 0, 100, 20; (当前命令主要想每行显示20个元素，但是第两个flags, index，count参数不能省略)

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
source.append path
说明：添加源码路径
参数：path为源代码的路径，不包含包路径部分，例如："d:\\program\\demo\\com\\runbox\\demo\\Demo.java", 只需要添加"d:\\program\\demo\\"就可以了；
样例：source.append "d:\\program\\demo"
source.delete
说明：
格式：
参数：
样例：
source.query 
说明：查询所有已经被添加的源路径；
参数：无
样例：source.query;

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
说明：结束调试并终结被调试的目标虚拟机
参数：无
样例：quit;
detach 
说明：结束调试但不结果目标虚拟机
参数：无
样例：detach;

帮助 暂未实现
help 
说明：
格式：
参数：
样例：