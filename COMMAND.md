# 命令列表（持续完善中...）
## 配置选项
### config.query expr
说明：查询配置选项<br>
参数：expr 表达式由以几部分组成<br>
&emsp;&emsp;&emsp;[name[, name]] name 为配制选项名称，每一个都是一个表达式，运算结果必须为字符串类型，<br>
&emsp;&emsp;&emsp;如果不给出则显示全部配制选项<br>
样例：config.query;<br>
&emsp;&emsp;&emsp;config.query "ip", "port", "line", "bytecode";
### config.set expr
说明：修改配置选项<br>
参数：expr 表达式，由以下几部分组成：<br>
&emsp;&emsp;&emsp;name, value<br>
&emsp;&emsp;&emsp;name 选项名称，一个子表达式，运算结果必须是字符串，可用的选项如下：<br>
&emsp;&emsp;&emsp;ip 被调试目标的地址，启动调试时通过-address参数传递 （只读），value子表达式，运算结果必须为字符串<br>
&emsp;&emsp;&emsp;port 被调试目标的端口，启动调试时通过-address参数传递（只读），value子表达式，运算结果必须为整形<br>
&emsp;&emsp;&emsp;mode 被调试目标的地址，启动调试时通过-mode参数传递，当前只支持debug，默认为debug（只读）<br>
&emsp;&emsp;&emsp;script 启动后先运行的脚本，启动调试时通过-script参数传递（只读）<br>
&emsp;&emsp;&emsp;line 当断点事件、单步执行事件、异常事件被触发，如果有源码可以显示的情况，显示多少行源码（读写）<br>
&emsp;&emsp;&emsp;value 子表达式，运算结果必须是整形数；<br>
&emsp;&emsp;&emsp;bytecode 当断点事件、单步执行事件、异常事件被触发，如果有字节码可以显示的情况，显示多少条虚拟指令（读写）<br>
&emsp;&emsp;&emsp;value 子表达式，运算结果必须是整形数；<br>
样例：config.set "line", 9;<br>
&emsp;&emsp;&emsp;@var = "bytecode"; config.set @var, 9;<br>
## 别名定义
### alias.define expr
说明：为某条命令定义一个别名，这个别名可以在后续使用效果如同命令本身一样，不能对某个别名再定义别名;<br>
参数：expr 表达式，由以下几部分组成：<br>
&emsp;&emsp;&emsp;command, alias<br>
&emsp;&emsp;&emsp;command 子表达式，运算结果必须为字符串，某个已经定义的命令不能是某个别名;<br>
&emsp;&emsp;&emsp;alias 自定义的别名，子表达式运算结果必须是字符串类型;<br>
样例：alias.define "breakpoint.line", "b.l";<br>
&emsp;&emsp;&emsp;alias.define "execute.next.over", "e.n.o";<br>
### alias.delete expr
说明：删除一个或多个已经定义的别名;<br>
参数：expr 表达式，由以下几部分组成：<br>
&emsp;&emsp;&emsp;alias[, alias]<br>
&emsp;&emsp;&emsp;每一个别名是一个子表达式，运算结果必须是字符串类型，如果没有参数则删除所有已定义的别名；<br>
样例：alias.delete "b.l", "e.n.o";<br>
### alias.query
说明：查询当前已经定义的所有命令别名;<br>
参数：无<br>
样例：alias.query<br>
## 导入类
### import.class expr
说明：为了避免在过长的类路径, 可以将某个类以全路径的方式导入后续直接使用类名称就可以了, 这个命令可以导入内<br>
&emsp;&emsp;&emsp;调试器内部已经预导入了一些常用的类型（主要是java.lang包中的一些类），可以通过import.query<br>
&emsp;&emsp;&emsp;查看具体导入了哪些类，这样就后续就可以直接使用这类名称了；<br>
&emsp;&emsp;&emsp;部类具体参见样例<br>
参数：expr 表达式，运算结果必须为字符串，由以下几部分组成：<br>
&emsp;&emsp;&emsp;package.className<br>
&emsp;&emsp;&emsp;package 类的包名称<br>
&emsp;&emsp;&emsp;className  类名称<br>
样例：import.class "java.lang.String";<br>
&emsp;&emsp;&emsp;import.class "java.lang.Integer";<br>
&emsp;&emsp;&emsp;import.class "java.util.Map";<br>
&emsp;&emsp;&emsp;import.class "java.util.LinkedList";<br>
&emsp;&emsp;&emsp;@clazz = "com.runbox.demo.Demo"; import.class @clazz;<br>
&emsp;&emsp;&emsp;import.class "com.runbox.demo.Demo$Inner";<br>
### import.delete expr
说明：删除已经被导入的类<br>
参数：expr 表达式，由以下几部分组成：<br>
&emsp;&emsp;&emsp;[className[, className]]<br>
&emsp;&emsp;&emsp;[className[, className]] 一个或多个已经导入的类以逗号间隔，每一个类名称是一个表达式，<br>
&emsp;&emsp;&emsp;运算结果必须是字符串类型，如果没有参数则删除所有被导入的类；<br>
样例：import.delete "String";<br>
&emsp;&emsp;&emsp;import.delete "Demo";<br>
&emsp;&emsp;&emsp;@clazz = "Demo$Inner"; import.delete @clazz, "Map", "LinkedList";<br>
### import.query
说明：列出所有已经被导入的类<br>
参数：无<br>
样例：import.query<br>
## 类查询
### class.query expr
说明：获取已装载的类信息<br>
参数: expr 标准larva表达式，由以下几部分结成<br>
&emsp;&emsp;&emsp;[package.]className[, flags]<br>
&emsp;&emsp;&emsp;package 包路径，是可选的；<br>
&emsp;&emsp;&emsp;className 类名称<br>
&emsp;&emsp;&emsp;如果使用空串则显示所有当前已经被装载的类，支持正则表达式；&emsp;&emsp;&emsp;<br>
&emsp;&emsp;&emsp;flags 标准表达式，代表一个组合标志位，运算结果必须是整形，默认值显示类全路径<br>
&emsp;&emsp;&emsp;0x0001 是否包私有<br>
&emsp;&emsp;&emsp;0x0002 访问标志 <br>
&emsp;&emsp;&emsp;0x0004 修改器（直译不太明白，需要查询JVM规范）<br>
&emsp;&emsp;&emsp;0x0008 是否抽象<br>
&emsp;&emsp;&emsp;0x0010 是否不可被继承<br>
&emsp;&emsp;&emsp;0x0020 是否已被初始化<br>
&emsp;&emsp;&emsp;0x0040 是否已预装载<br>
&emsp;&emsp;&emsp;0x0080 是否静态<br>
&emsp;&emsp;&emsp;0x0100 是否已被验证<br>
&emsp;&emsp;&emsp;0x0200 编译版本<br>
&emsp;&emsp;&emsp;0x0400 源文件<br> 
&emsp;&emsp;&emsp;0x0800 实例数量<br> 
&emsp;&emsp;&emsp;0x1000 装载器<br> 
&emsp;&emsp;&emsp;0x2000 Class类型<br> 
样例：class.query "java.lang.String", 0xff;<br>
&emsp;&emsp;&emsp;class.query "com.runbox.demo.Demo.*", 0x8;<br>
&emsp;&emsp;&emsp;class.query "java.lang..*";<br>
&emsp;&emsp;&emsp;import.class "java.lang.String"; class.query "String";<br>
&emsp;&emsp;&emsp;class.query "", 0xfff;<br>
### class.field expr
说明：获取一个类的字段信息<br>
参数：expr 标准larva表达式，由以下几部分结成:<br>
&emsp;&emsp;&emsp;[package.]className.fieldName[, flags]<br>
&emsp;&emsp;&emsp;package 包路径，是可选的，必须是精确匹配不支持正则表达式；<br>
&emsp;&emsp;&emsp;className 类名称，必须是精确匹配不支持正则表达式；<br>
&emsp;&emsp;&emsp;fieldName 字段名，可以精确匹配也可以使用正则表达式的通配符，但是只能使用.***(点加星号)<br>
&emsp;&emsp;&emsp;列出所有字段；<br>
&emsp;&emsp;&emsp;以上三部分必须是字符串类型<br>
&emsp;&emsp;&emsp;flags 标准表达式，代表一个组合标志位，运算结果必须是整形<br>
&emsp;&emsp;&emsp;0x000 默认值显示类全路径<br>
&emsp;&emsp;&emsp;0x001 是否包私有<br>
&emsp;&emsp;&emsp;0x002 访问标志 <br>
&emsp;&emsp;&emsp;0x004 修改器（直译不太明白，需要查询JVM规范）<br>
&emsp;&emsp;&emsp;0x008 是否不可被继承<br>
&emsp;&emsp;&emsp;0x010 是否静态<br>
&emsp;&emsp;&emsp;0x020 是否合成（直译不太明白，需要查询JVM规范）<br>
&emsp;&emsp;&emsp;0x040 是否枚举<br>
&emsp;&emsp;&emsp;0x080 是否transient<br>
&emsp;&emsp;&emsp;0x100 是否volatile<br>
&emsp;&emsp;&emsp;0x200 声明这个字段的类<br>
&emsp;&emsp;&emsp;0x400 字段定义类型<br>
样例：class.field "com.runbox.debug.Demo..*";<br>
&emsp;&emsp;&emsp;import "java.lang.String"; class.field "String..*";<br>
&emsp;&emsp;&emsp;class.field "Demo..*", 0x1ff;<br>
### class.method expr
说明：获取一个类的方法信息<br>
参数：expr 标准larva表达式，由以下几部分结成:<br>
&emsp;&emsp;&emsp;[package.]className.methodName[, flags]<br>
&emsp;&emsp;&emsp;package 包路径，是可选的，必须是精确匹配不支持正则表达式；<br>
&emsp;&emsp;&emsp;className 类名称，必须的部分且不能使用通配符；<br>
&emsp;&emsp;&emsp;fieldName 字段名，可以使用正则表达式的通配符，但是只能使用.*(点加星号)列出所有方法；<br>
&emsp;&emsp;&emsp;以上三部分必须是字符串类型<br>
&emsp;&emsp;&emsp;flags 标准表达式，代表一个组合标志位，运算结果必须是整形<br>
&emsp;&emsp;&emsp;0x0000 默认值显示类全路径<br>
&emsp;&emsp;&emsp;0x0001 是否包私有<br>
&emsp;&emsp;&emsp;0x0002 访问标志 <br>
&emsp;&emsp;&emsp;0x0004 修改器（直译不太明白，需要查询JVM规范）<br>
&emsp;&emsp;&emsp;0x0008 是否抽象<br>
&emsp;&emsp;&emsp;0x0010 是否不可继承<br>
&emsp;&emsp;&emsp;0x0020 是否静态 <br>
&emsp;&emsp;&emsp;0x0040 是否合成（直译不太明白，需要查询JVM规范）<br>
&emsp;&emsp;&emsp;0x0080 是否桥接（直译不太明白，需要查询JVM规范）<br>
&emsp;&emsp;&emsp;0x0100 是否native<br>
&emsp;&emsp;&emsp;0x0200 是否同步<br>
&emsp;&emsp;&emsp;0x0400 是否为构造方法<br>
&emsp;&emsp;&emsp;0x0800 是为静态初始化块&emsp;&emsp;&emsp;<br>
&emsp;&emsp;&emsp;0x1000 是否接受变长参数<br>
&emsp;&emsp;&emsp;0x2000 方法的起始行号<br>
&emsp;&emsp;&emsp;0x4000 声明这个方法的类<br>
&emsp;&emsp;&emsp;0x8000 方法返回类型<br>
样例：class.method "com.runbox.debug.Demo..*";<br>
&emsp;&emsp;&emsp;import "java.lang.String"; class.method "String.noti.*", 0x7fff;<br>
### class.monitor.query expr
说明：查询当前被监控的所有类<br>
参数：expr 标准larva表达式，由以下几部分结成:<br>
&emsp;&emsp;&emsp;"prepare" | "unload"<br>
&emsp;&emsp;&emsp;prepare 被监控预装载的类<br>
&emsp;&emsp;&emsp;unload 被监控卸载的类<br>
&emsp;&emsp;&emsp;如果没有参数则显示两类监控都被显示<br>
样例：class.monitor.query "prepare";<br>
&emsp;&emsp;&emsp;class.monitor.query "unload";<br>
&emsp;&emsp;&emsp;class.monitor.query;<br>
### class.monitor.prepare expr {block}
说明：监控某一个或是某一批类的预装载<br>
参数：expr 标准larva表达式，由以下几部分结成:<br>
&emsp;&emsp;&emsp;[package.]className<br>
&emsp;&emsp;&emsp;package 包路径，是可选的；<br>
&emsp;&emsp;&emsp;className 类名称，必须的参数，<br>
&emsp;&emsp;&emsp;以上参数必须组成字符串类型，必须是精确匹配不能使用正则表达式；<br>
&emsp;&emsp;&emsp;{block} 命令尾块，这个块中的脚本会在监控事件被触发后执行；<br>
样例：import.class "com.runbox.demo.Demo"; class.monitor.prepare "Demo";<br>
&emsp;&emsp;&emsp;class.monitor.prepare "com.runbox.demo.Demo";<br>
### class.monitor.unload expr {block}
说明：监控某一个或是某一批类的卸载<br>
参数：expr 标准larva表达式，由以下几部分结成:<br>
&emsp;&emsp;&emsp;[package.]className<br>
&emsp;&emsp;&emsp;package 包路径，是可选的，如果之前通过import.class命令已经导入类可以只使用类路径；<br>
&emsp;&emsp;&emsp;className 类名称，如果之前通过import.class命令已经导入类可以只使用类路径；<br>
&emsp;&emsp;&emsp;以上参数必须组成字符串类型，必须是精确匹配不能使用正则表达式；<br>
&emsp;&emsp;&emsp;{block} 命令尾块，这个块中的脚本会在监控事件被触发后执行；
样例：import.class "com.runbox.demo.Demo"; class.monitor.unload "Demo"; <br>
&emsp;&emsp;&emsp;class.monitor.unload "com.runbox.demo.Demo";<br>
### class.monitor.enable expr
说明：启动已经被禁用某个或某些被监控预装载类或卸载类项<br>
参数：expr 标准larva表达式，由以下几部分结成:<br>
&emsp;&emsp;&emsp;[id[, id]]<br>
&emsp;&emsp;&emsp;id 某项的ID，如果没有ID则启动所有监控项<br>
样例：class.monitor.enable 2, 3, 4;<br>
&emsp;&emsp;&emsp;class.monitor.enable;<br>
### class.monitor.disable expr
说明：禁用已经被禁用某个或某些被监控预装载类或卸载类项<br>
参数：expr 标准larva表达式，由以下几部分结成:<br>
&emsp;&emsp;&emsp;[id[, id]]<br>
&emsp;&emsp;&emsp;id 某项的ID，如果没有ID则禁用所有监控项<br>
样例：class.monitor.disable 2, 3, 4;<br>
&emsp;&emsp;&emsp;class.monitor.disable;<br>
### class.monitor.delete expr
说明：删除已经被禁用某个或某些被监控预装载类或卸载类项<br>
参数：expr 标准larva表达式，由以下几部分结成:<br>
&emsp;&emsp;&emsp;[id[, id]] 每个id是一个子表达式如果没有ID则删除所有监控项<br>
样例：class.monitor.delete 2, 3, 4;<br>
&emsp;&emsp;&emsp;class.monitor.delete;<br>
### class.constant expr
说明：表出某类型的常量池信息；<br>
参数：expr 标准larva表达式，由以下几部分结成:<br>
&emsp;&emsp;&emsp;package.className<br>
&emsp;&emsp;&emsp;package 包路径<br>
&emsp;&emsp;&emsp;className 类名称<br>
&emsp;&emsp;&emsp;以上两部分组成类的全路径；<br>
样例：class.constant "com.runbox.demo.Demo";<br>
&emsp;&emsp;&emsp;class.constant "java.lang.String";<br>
## 方法查询
### method.argument expr
说明：查询方法的参数列表，如果参数名称可获取则显示名称与类型，反之则只显示类型<br>
参数：expr 标准larva表达式，由以下几部分结成:<br>
&emsp;&emsp;&emsp;[package.]class.method[(arg1, arg2, ...)]<br>
&emsp;&emsp;&emsp;package 包名称，可选参数，如果之前已经运行了import.class命令则可以只省略包路径，只给出类名称；<br>
&emsp;&emsp;&emsp;class 类名称 <br>
&emsp;&emsp;&emsp;method 方法名称<br>
&emsp;&emsp;&emsp;(arg1, arg2, ...) 参数列表，可选部分，如果给出就进行精确匹配，如果不给出参数列表则会显示所有的重载版本；<br>
样例：method.bytecode "com.runbox.demo.Demo.method";<br>
&emsp;&emsp;&emsp;method.bytecode "java.lang.String.<init>";<br>
&emsp;&emsp;&emsp;method.bytecode "java.lang.String.indexOf";<br>
### method.local expr
说明：查询方法的局部变量，如果变量名称可获取则显示名称与类型，反之则只显示类型<br>
参数：expr 标准larva表达式，由以下几部分结成:<br>
&emsp;&emsp;&emsp;[package.]class.method<br>
&emsp;&emsp;&emsp;package 包名称，可选参数，如果之前已经运行了import.class命令则可以只省略包路径，只给出类名称；<br>
&emsp;&emsp;&emsp;class 类名称 <br>
&emsp;&emsp;&emsp;method 方法名称<br>
样例：method.bytecode "com.runbox.demo.Demo.method";<br>
&emsp;&emsp;&emsp;method.bytecode "java.lang.String.<init>";<br>
&emsp;&emsp;&emsp;method.bytecode "java.lang.String.indexOf";<br>
### method.bytecode expr
说明：显示某方法的字节码<br>
参数：expr 标准larva表达式，由以下几部分结成:<br>
&emsp;&emsp;&emsp;[package.]class.method<br>
&emsp;&emsp;&emsp;package 包名称，可选参数，如果之前已经运行了import.class命令则可以只省略包路径，只给出类名称；<br>
&emsp;&emsp;&emsp;class 类名称 <br>
&emsp;&emsp;&emsp;method 方法名称，当前不支持通过参数列表区分方法，所有一次会表出多个同名的方法（如果有重载）；<br>
&emsp;&emsp;&emsp;以上三部分必须是字符串类型<br>
样例：method.bytecode "com.runbox.demo.Demo.method";<br>
&emsp;&emsp;&emsp;method.bytecode "java.lang.String.<init>";<br>
&emsp;&emsp;&emsp;method.bytecode "java.lang.String.indexOf";<br>
### method.monitor.entry (功能暂未实现)
说明：<br>
参数：<br>
样例：<br>
### method.monitor.return (功能暂未实现)
说明：<br>
参数：<br>
样例：<br>
## 设置断点
### breakpoint.method expr {block}
说明：通过方法设置断点<br>
参数：expr 标准larva表达式，运算结果必须是字符串，字符串由以下几部分组成：<br>
&emsp;&emsp;&emsp;[package.]className.method([argument[, argument]])<br>
&emsp;&emsp;&emsp;package 包路径，这是一个可选的部分，如果在执行此条命令之前已经通过import.class命令导入了类，<br>
&emsp;&emsp;&emsp;就可能只使用类名称；<br>
&emsp;&emsp;&emsp;className 类名称，这是必须给出，如果是内嵌类需要使用外部分类加内嵌类，例如：Demo$Inner形式；<br>
&emsp;&emsp;&emsp;method 方法名称<br>
&emsp;&emsp;&emsp;argument 是方法的参数类型，参数个数根据method来确定，参数类型如果之前已经通过import.class<br>
&emsp;&emsp;&emsp;命令导入了类，也可以只使用类名称，例如：method(Map, List);<br>
&emsp;&emsp;&emsp;block 是命令尾块，这个块中的脚本会在断点被命中后执行；<br>
样例：import.class "com.runbox.demo.Demo";<br>
&emsp;&emsp;&emsp;import.class "com.runbox.demo.Demo$Inner";<br>
&emsp;&emsp;&emsp;breakpoint.method "Demo.method(String, Integer, Map)" {<br>
&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;print.value @id; // 尾块的隐式参数<br>
&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;print.field @thread; // 尾块的隐式参数<br>
&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;print.value "this is a method breakpoint"; <br>
&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;execute.run;<br>
&emsp;&emsp;&emsp;};<br>
&emsp;&emsp;&emsp;breakpoint.method "Demo$Inner.method()" {execute.run;};<br>
&emsp;&emsp;&emsp;@method = "Demo.method()"; breakpoint.method @method {print.value "hello debugger."};<br>
### breakpoint.line expr {block}
说明：通过行号设置断点<br>
参数：expr 标准larva表达式，运算结果必须是字符串，字符串由以下三部分组成：<br>
&emsp;&emsp;&emsp;[package.]className:lineNumber<br>
&emsp;&emsp;&emsp;package 包路径，这是一个可选的部分，如果在执行此条命令之前已经通过import.class命令导入了类，<br>
&emsp;&emsp;&emsp;就可能只使用类名称；<br>
&emsp;&emsp;&emsp;className 类名称，这是必须给出，如果是内嵌类需要使用外部分类加内嵌类，例如：Demo$Inner形式；<br>
&emsp;&emsp;&emsp;lineNumber 是在源代码中行号<br>
&emsp;&emsp;&emsp;block 是命令尾块，这个块中的脚本会在断点被命中后执行；<br>
样例：import.class "com.runbox.demo.Demo";<br>
&emsp;&emsp;&emsp;breakpoint.line "Demo.line:61";<br>
&emsp;&emsp;&emsp;@line = "Demo.line:61"; breakpoint.line @line;<br>
### breakpoint.access expr {block}
说明：设置一个字段的访问断点，当字段值被读取进触发<br>
参数：expr 标准larva表达式，运算结果必须是字符串，字符串由以下三部分组成：<br>
&emsp;&emsp;&emsp;[package.]className.fieldName<br>
&emsp;&emsp;&emsp;package 包路径，这是一个可选的部分，如果在执行此条命令之前已经通过import.class命令导入了类，<br>
&emsp;&emsp;&emsp;就可能只使用类名称；<br>
&emsp;&emsp;&emsp;className 类名称，这是必须给出，如果是内嵌类需要使用外部分类加内嵌类，例如：Demo$Inner形式；<br>
&emsp;&emsp;&emsp;fieldName 字段名称<br>
&emsp;&emsp;&emsp;block 是命令尾块，这个块中的脚本会在断点被命中后执行；<br>
样例：import.class "com.runbox.demo.Demo";<br>
&emsp;&emsp;&emsp;breakpoint.access "Demo.count";<br>
### breakpoint.modify expr {block}
说明：设置一个字段的访问断点，当字段值被修改时触发<br>
参数：expr 标准larva表达式，运算结果必须是字符串，字符串由以下三部分组成：<br>
&emsp;&emsp;&emsp;[package.]className.fieldName<br>
&emsp;&emsp;&emsp;package 包路径，这是一个可选的部分，如果在执行此条命令之前已经通过import.class命令<br>
&emsp;&emsp;&emsp;导入了类，就可能只使用类名称；<br>
&emsp;&emsp;&emsp;className 类名称，这是必须给出，如果是内嵌类需要使用外部分类加内嵌类，例如：Demo$Inner形式；<br>
&emsp;&emsp;&emsp;fieldName 字段名称&emsp;&emsp;&emsp;<br>
&emsp;&emsp;&emsp;block 是命令尾块，这个块中的脚本会在断点被命中后执行；<br>
样例：import.class "com.runbox.demo.Demo";<br>
&emsp;&emsp;&emsp;breakpoint.modify "Demo$Inner.count" {execute.run;};<br>
### breakpoint.query 
说明：列出当前所有断点<br>
参数：无<br>
样例：breakpoint.query;<br>
### breakpoint.delete expr
说明：删除某个或某些断点<br>
参数：expr 表达式，由以下几部分组成：<br>
&emsp;&emsp;&emsp;[id[, id]]  每个id都是一个子表达式，其运算结果必须是整形，如果无参数则删除所有断点<br>
样例：@id = 4; breakpoint.delete 2，0x3, @id;<br>
### breakpoint.enable expr
说明：启动某个或某些断点<br>
参数：expr 表达式，由以下几部分组成：<br>
&emsp;&emsp;&emsp;[id[, id]] 每个id都是一个子表达式，其运算结果必须是整形数,如果无参数则启用所有处于禁用<br>
&emsp;&emsp;&emsp;状态断点<br>
样例：@id = 4; breakpoint.enable 2，0x3, @id;<br>
### breakpoint.disable expr
说明：禁用某个或某些断点<br>
参数：expr 表达式，由以下几部分组成：<br>
&emsp;&emsp;&emsp;[id[, id]] 每个id都是一个子表达式，其运算结果必须是整形数，如果无参数则启用所有处于启用状态断点<br>
样例：@id = 4; breakpoint.enable 2，0x3, @id;<br>
## 执行
### execute.run
说明：继续运行当前被调试的目标直到另一个可中断事件在目标程序中发生<br>
&emsp;&emsp;&emsp;例如一个断点或是某一监控事件等<br>
参数：无<br>
样例：execute.run;<br>
### execute.next.over [expr] {block}
说明：以源码为单位运行，遇到方法调用不进入<br>
参数：expr 表达式，代表运行几行源码，其运算结果必须是整形数，默认为一行；<br>
&emsp;&emsp;&emsp;{block} 命令尾块，这个块中的脚本会在命令执行被执行；
样例：execute.next.over;<br>
&emsp;&emsp;&emsp;execute.next.over 2;<br>
&emsp;&emsp;&emsp;@count = 0x3; execute.next.over @count;<br>
### execute.next.into [expr] {block}
说明：以源码为单位运行，遇到方法调用则进入<br>
参数：expr 表达式，代表运行几行源码，其运算结果必须是整形数，默认为一行；<br>
&emsp;&emsp;&emsp;{block} 命令尾块，这个块中的脚本会在命令执行被执行；
样例：execute.next.into；<br>
&emsp;&emsp;&emsp;execute.next.into 2;<br>
&emsp;&emsp;&emsp;@count = 0x3; execute.next.into @count;<br>
### execute.step.over [count] {block}
说明：以虚拟指令为单位运行，遇到方法调用不进入<br>
参数：count 运行几条虚拟指令，默认为一条，必须是整形数<br>
&emsp;&emsp;&emsp;{block} 命令尾块，这个块中的脚本会在命令执行被执行；
样例：execute.step.over；<br>
&emsp;&emsp;&emsp;execute.step.over 2；<br>
&emsp;&emsp;&emsp;@count = 0x3; execute.step.over @count;<br>
### execute.step.into [expr] {block}
说明：以虚拟指令为单位运行，遇到方法调用则进入<br>
参数：expr larva表达式，代表运行几条虚拟指令，其运算结果必须是整形数，默认为一条<br>
&emsp;&emsp;&emsp;{block} 命令尾块，这个块中的脚本会在命令执行被执行；
样例：execute.step.into；<br>
&emsp;&emsp;&emsp;execute.step.into 2；<br>
&emsp;&emsp;&emsp;@count = 0x3; execute.step.into @count;<br>
### execute.upon {block}
说明：执行完当前方法剩余的代码直到方法返回<br>
参数：无<br>
&emsp;&emsp;&emsp;{block} 命令尾块，这个块中的脚本会在程序执行到指定位置后被执行；<br>
样例：execute.upon;<br>
### execute.goto expr {block}
说明：执行程序到指定的位置<br>
参数：expr larva表达式，运行结果必须是字符串类型，"class:line"<br>
&emsp;&emsp;&emsp;class 类全路径，如果通过import.class，可以只给出只给出类名称<br>
&emsp;&emsp;&emsp;line 行号<br>
&emsp;&emsp;&emsp;类名称可以省略，如果不给出类名称则代表当前类，但是冒号不可以省略<br>
&emsp;&emsp;&emsp;{block} 命令尾块，这个块中的脚本会在程序执行到指定位置后被执行；<br>
样例：execute.goto "com.runbox.demo.Demo:10";<br>
&emsp;&emsp;&emsp;import.class "com.runbox.demo.Demo"; execute.goto "Demo:20";<br>
&emsp;&emsp;&emsp;execute.goto ":30";<br>
&emsp;&emsp;&emsp;@location = ":40"; execute.goto @location;<br>
### execute.file file (此功暂未完成)
说明：运行一个外部的文件，文件内容是larva脚本；<br>
参数：文件全路径，必须是字符串类型；<br>
样例：execute.file ".\\\\debug.jdb";<br>
&emsp;&emsp;&emsp;@file = ".\\\\debug.jdb"; execute.file @file;<br>
## 显示变量
### print.value expr 
说明：计算一个表达式并显示其结果<br>
参数：expr 脚本表达式（具体参见脚本说明部分）<br>
&emsp;&emsp;&emsp;expr [, flags]<br>
&emsp;&emsp;&emsp;expr 子表达式，运算结果可以为任何类型；<br>
&emsp;&emsp;&emsp;flags 是一个标志组合<br>
&emsp;&emsp;&emsp;0x00 不显示任何类型（默认值）；<br>
&emsp;&emsp;&emsp;0x01 显示变量类型；<br>
&emsp;&emsp;&emsp;0x02 显示变量值类型；<br>
&emsp;&emsp;&emsp;对于原始类型来说变量类型与值类型一致，但是对于引用变量则不同，例如：引用类型为Object，<br>
&emsp;&emsp;&emsp;但是某值可能为Object任何子类；<br>
样例： @var = "hello"; print.value @var;<br>
&emsp;&emsp;&emsp;print.value "dog, come on";<br>
&emsp;&emsp;&emsp;@var = 10; print.value @var, 0x1;<br>
&emsp;&emsp;&emsp;@var = 10; print.value @var, 0x2;<br>
&emsp;&emsp;&emsp;@var = 10; @flags = 0x1 | 0x3; print.value @var, @flags;<br>
&emsp;&emsp;&emsp;print.value this.inner.count;<br>
### print.field expr
说明：列出一具对象的所有字段<br>
参数：expr 标准表达式，由以下几部分组成：<br>
&emsp;&emsp;&emsp;expr[, flags]<br>
&emsp;&emsp;&emsp;expr 子表达式，其运算结果必须为一个对象引用；<br>
&emsp;&emsp;&emsp;flags 是一个标志组合，默认值 0x01 | 0x02<br>
&emsp;&emsp;&emsp;0x01 显示基本的信息；<br>
&emsp;&emsp;&emsp;0x02 显示字段名称与值，但不显示任何类型（默认值）；<br>
&emsp;&emsp;&emsp;0x04 显示变量类型，必须设置0x02，此标志才生效；<br>
&emsp;&emsp;&emsp;0x08 显示变量值类型，必须设置0x02，此标志才生效；<br>
&emsp;&emsp;&emsp;对于原始类型来说变量类型与值类型一致，但是对于引用变量则不同，例如：引用类型为Object，<br>
&emsp;&emsp;&emsp;但是某值可能为Object任何子类；<br>
样例：print.field this;<br>
&emsp;&emsp;&emsp;print.field this.map, 0x3;<br>
&emsp;&emsp;&emsp;@var = this.list; @flags = 0x1 | 0x3; print.field @var, @flags;<br>
### print.local
说明：列出当前栈帧中所有局部变量<br>
参数：无<br>
样例：print.local;<br>
&emsp;&emsp;&emsp;print.local 0x2;<br>
&emsp;&emsp;&emsp;@flags = 0x1 | 0x3; print.field @flags;<br>
### print.array expr
说明：格式化显示数组<br>
参数：expr 标准表达式，由以下几部分组成：<br>
&emsp;&emsp;&emsp;expr[, flags[, index[, count]]]<br>
&emsp;&emsp;&emsp;expr 标准larva脚本表达式，其运算结果必须为一个数组引用；<br>
&emsp;&emsp;&emsp;flags 是一个标志组合<br>
&emsp;&emsp;&emsp;0x01 显示基本统计信息；<br>
&emsp;&emsp;&emsp;0x02 显示元素具体值；<br>
&emsp;&emsp;&emsp;0x03 显示基本统计信息与元素具体值（默认）<br>
&emsp;&emsp;&emsp;index 从第几个索引开始显示（默认0）<br>
&emsp;&emsp;&emsp;count 显示几个元素（默认所有元素）<br>
&emsp;&emsp;&emsp;注意以上flags, index, count虽然是可选的但是如果后面的参数出现则前面的参数也必须<br>
&emsp;&emsp;&emsp;出现不能省略，只有后续不需要的参数是可省略（语法规则与C++中的函数默认实参类似，具<br>
&emsp;&emsp;&emsp;体参见样例）；<br>
样例：print.array array1, 1, 0; (count可以被省略)<br>
&emsp;&emsp;&emsp;print.array array2, 2; (index, count 可以被省略)<br>
&emsp;&emsp;&emsp;print.array array3; (flags, index, count 都可以被省略)<br>
&emsp;&emsp;&emsp;print.array array4, 3, 0, 10; (当前命令主要想显示10个元素，但是第两个flags, index 参数不能省略)<br>
### print.string expr
说明：格式化显示字符串<br>
参数：expr 标准表达式，由以下几部分组成：<br>
&emsp;&emsp;&emsp;expr[, flags[, index[, count[, line]]]]<br>
&emsp;&emsp;&emsp;expr 标准larva脚本表达式，其运算结果必须为一个字符串类型；<br>
&emsp;&emsp;&emsp;flags 是一个标志组合<br>
&emsp;&emsp;&emsp;0x01 显示基本统计信息；<br>
&emsp;&emsp;&emsp;0x02 显示元素具体值；<br>
&emsp;&emsp;&emsp;0x03 显示基本统计信息与元素具体值（默认）<br>
&emsp;&emsp;&emsp;index 从第几个索引开始显示（默认0）<br>
&emsp;&emsp;&emsp;count 显示几个元素（默认的所有元素）<br>
&emsp;&emsp;&emsp;line 一行显示多少个字符（默认不限制，以输出目标设备边界为依据）<br>
&emsp;&emsp;&emsp;注意以上flags, index, count, line虽然是可选的但是如果后面的参数出现则前面的参数也必须出现不能省略，<br>
&emsp;&emsp;&emsp;只有后续不需要的参数是可省略（语法规则与C++中的函数默认实参类似，具体参见样例）；<br>
样例：print.string string1, 1, 0; (count, line可以被省略)<br>
&emsp;&emsp;&emsp;print.string string2, 2; (index, count, line 可以被省略)<br>
&emsp;&emsp;&emsp;print.string string3; (flags, index, count, line 都可以被省略)<br>
&emsp;&emsp;&emsp;print.string string4, 3, 0, 100, 20; (当前命令主要想每行显示20个元素，但是第两个flags, index，count参数不能省略)<br>
### print.radix expr
说明：以某中进制形式显示整形数<br>
参数：expr 标准表达式，由以下两部分组成：<br>
&emsp;&emsp;&emsp;value 子表达式，运行结果必须是整形数，类型可以是byte, short, int, long<br>
&emsp;&emsp;&emsp;radix 可以是 2，8，10， 16<br>
样例：print.radix 10, 2;<br>
&emsp;&emsp;&emsp;print.radix 10, 8;<br>
&emsp;&emsp;&emsp;print.raidx 012, 16;<br>
&emsp;&emsp;&emsp;print.radix 0b1010, 10;<br>
&emsp;&emsp;&emsp;print.radix 0xa, 8;<br>
## 显示模板
### template.list expr
说明：显示某种列表类型的数据<br>
参数：expr 标准表达式，由以下几部分组成：<br>
&emsp;&emsp;&emsp;expr[, flag]<br>
&emsp;&emsp;&emsp;expr 标准的larva表达式其运算结果必须为java.util.List的某个实现类的引用<br>
&emsp;&emsp;&emsp;当前支持的子类型为：<br>
&emsp;&emsp;&emsp;java.util.List<br>
&emsp;&emsp;&emsp;java.util.ArrayList<br>
&emsp;&emsp;&emsp;java.util.LinkedList<br>
&emsp;&emsp;&emsp;java.util.Stack<br>
&emsp;&emsp;&emsp;java.util.Vector&emsp;&emsp;&emsp;<br>
&emsp;&emsp;&emsp;flags 是一个可以组合的标志位，整数类型，默认为(0x1|0x2)<br>
&emsp;&emsp;&emsp;0x1 显示基本的统计信息<br>
&emsp;&emsp;&emsp;0x2 显示容器中的元素<br>
&emsp;&emsp;&emsp;0x4 显示元素的类型<br>
样例：alias.define "template.list", "t.l"; t.l arrayList;<br>
&emsp;&emsp;&emsp;@var = arrayList; t.l @var;<br>
### template.map expr 
说明：显示某种值对类型的数据<br>
参数：expr 标准表达式，由以下几部分组成：<br>
&emsp;&emsp;&emsp;expr[, flag]<br>
&emsp;&emsp;&emsp;expr 标准的larva表达式其运算结果必须为java.util.Map的某个实现类的引用<br>
&emsp;&emsp;&emsp;当前支持的子类型为：<br>
&emsp;&emsp;&emsp;java.util.HashMap<br>
&emsp;&emsp;&emsp;java.util.Hashtable<br>
&emsp;&emsp;&emsp;java.util.IdentityHashMap<br>
&emsp;&emsp;&emsp;java.util.LinkedHashMap<br>
&emsp;&emsp;&emsp;flags 是一个可以组合的标志位，整数类型，默认为(0x1|0x2)<br>
&emsp;&emsp;&emsp;0x1 显示基本的统计信息<br>
&emsp;&emsp;&emsp;0x2 显示容器中的元素<br>
&emsp;&emsp;&emsp;0x4 显示元素的类型<br>
样例：alias.define "template.map", "t.m"; t.m hashMap;<br>
### template.queue expr
说明：显示某种队列类型的数据<br>
参数：expr 标准表达式，由以下几部分组成：<br>
&emsp;&emsp;&emsp;expr[, flag]<br>
&emsp;&emsp;&emsp;expr 标准的larva表达式其运算结果必须为java.util.Queue的某个实现类的引用<br>
&emsp;&emsp;&emsp;当前支持的子类型为：<br>
&emsp;&emsp;&emsp;java.util.ArrayDeque<br>
&emsp;&emsp;&emsp;java.util.PriorityQueue&emsp;<br>
&emsp;&emsp;&emsp;flags 是一个可以组合的标志位，整数类型，默认为(0x1|0x2)<br>
&emsp;&emsp;&emsp;0x1 显示基本的统计信息<br>
&emsp;&emsp;&emsp;0x2 显示容器中的元素<br>
&emsp;&emsp;&emsp;0x4 显示元素的类型<br>
样例：template.queue arrayQueue;<br>
&emsp;&emsp;&emsp;template.queue priorityQueue;<br>
### template.set（此功能暂未实现）
说明：显示某种集合类型的数据<br>
参数：expr 标准的larva表达式其运算结果必须为java.util.Set的某个实现类的引用<br>
&emsp;&emsp;&emsp;当前支持的子类型为：无<br>
&emsp;&emsp;&emsp;flags 是一个可以组合的标志位，整数类型，默认为(0x1|0x2)<br>
&emsp;&emsp;&emsp;0x1 显示基本的统计信息<br>
&emsp;&emsp;&emsp;0x2 显示容器中的元素<br>
&emsp;&emsp;&emsp;0x4 显示元素的类型<br>
样例：template.set hashSet;<br>
&emsp;&emsp;&emsp;template.set treeSet;<br>
### template.stack expr
说明：显示栈容器的数据<br>
参数：expr 标准的larva表达式，由以下几部分组成：<br>
&emsp;&emsp;&emsp;expr[, flag]<br>
&emsp;&emsp;&emsp;expr 标准的larva表达式其运算结果必须为java.util.Stack类型或是某子类<br>
&emsp;&emsp;&emsp;栈虽然也可以通过template.list来显示数据但是通过template.stack可以以更好的形式<br>
&emsp;&emsp;&emsp;显示（以栈的形式）；<br>
&emsp;&emsp;&emsp;flags 是一个可以组合的标志位，整数类型，默认为 0x1 | 0x2<br>
&emsp;&emsp;&emsp;0x1 显示基本的统计信息<br>
&emsp;&emsp;&emsp;0x2 显示容器中的元素<br>
&emsp;&emsp;&emsp;0x1 显示元素的类型<br>
样例：template.stack stack;<br>
## 线程查询
### thread.query [expr]
说明：列出当前所有线程<br>
参数：expr 标准表达式，代表一个组合标志位，运算结果必须是整形<br>
&emsp;&emsp;&emsp;0x01 线程组<br>
&emsp;&emsp;&emsp;0x02 栈帧数，未被挂起的线程不能获取此项，显示为n/a<br>
&emsp;&emsp;&emsp;0x04 挂起次数<br>
&emsp;&emsp;&emsp;0x08 状态（详细状态信息请参见JVM规范）<br>
&emsp;&emsp;&emsp;0x10 线程是否因断点而被挂起<br>
&emsp;&emsp;&emsp;0x20 线程名称<br>
默认为 0x08 | 0x20<br>
样例：thread.query 0x1; <br>
&emsp;&emsp;&emsp;thread.query 0xf;<br>
### thread.switch expr
说明：切换线程<br>
参数：expr 线程ID，运算结果必须是整形<br>
样例：thread.switch 10;<br>
### thread.suspend expr
说明：挂起一个或多个线程<br>
参数：expr 表达式，由以下几部分组成：<br>
&emsp;&emsp;&emsp;[id[, id]] 每个id都是一个子表达式，运算结果必须为整形，如果不给出参数则挂起所有线程<br>
样例：thread.suspend 1, 2, 3;<br>
&emsp;&emsp;&emsp;thread.suspend;<br>
### thread.resume expr
说明：恢复一个或多个线程<br>
参数：expr 表达式，由以下几部分组成：<br>
&emsp;&emsp;&emsp;[id[, id]] 每个id都是一个子表达式，运算结果必须为整形，如果不给出参数则挂起所有线程<br>
样例：thread.resume 1, 2, 3;<br>
&emsp;&emsp;&emsp;thread.resume;<br>
### thread.interrupt expr<br>
说明：中断一个线程或多个线程<br>
参数：expr 表达式，由以下几部分组成：<br>
&emsp;&emsp;&emsp;id[, id] 每个id都是一个子表达式，运算结果必须为整形<br>
&emsp;&emsp;&emsp;被中断的线程不能被挂起，如果被挂起则此操作不生效；<br>
样例：thread.interrupt 4086, 1983;<br>
&emsp;&emsp;&emsp;@id = 10; thread.interrupt @id, 4086, 1983;<br>
### thread.stack expr
说明：显示当前线程的所有栈帧（当前线程必须处于挂起状态，如果未挂起可以使用thread.suspend）<br>
参数：expr 一个表达式，运算结果必须是整形，代表标志位：<br>
&emsp;&emsp;&emsp;0x01 显示包路径<br>
&emsp;&emsp;&emsp;0x02 显示行号，如果行号不能获取则显示n/a<br>
&emsp;&emsp;&emsp;默认为 0x01 | 0x02<br>
样例：thread.stack;<br>
&emsp;&emsp;&emsp;thread.stack 0x1; <br>
&emsp;&emsp;&emsp;@var = 0x2; thread.stack @var;<br>
### thread.hold expr<br>
说明：显示指定线程当前持有的对象监控器<br>
参数：expr 标准表达式，[id[,id]] id 代表线程ID，如果不指定id则显示所有线程<br>
&emsp;&emsp;&emsp;注意此命令对以下两种情况不生效<br>
&emsp;&emsp;&emsp;一、只能处理被挂起的线程，如果线程没有被挂起（可通过thread.suspend挂起），此命令不生效；<br>
&emsp;&emsp;&emsp;二、线程状态如果是unknow或zombie，则此命令不生效；<br>
&emsp;&emsp;&emsp;holding 代表线程正持有哪些监控器对象，如果没有持有的线程显示n/a<br>
&emsp;&emsp;&emsp;waiting 代表当前正在等待监控器对象的线程列表，如果没有持有的线程显示n/a<br>
样例：thread.hold 4826, 4827<br>
&emsp;&emsp;&emsp;thread.hold<br>
### thread.wait expr <br>
说明：显示指定线程正在等待的对象监控器<br>
参数：expr 标准表达式，[id[,id]] id 代表线程ID，如果不指定id则显示所有线程<br>
&emsp;&emsp;&emsp;注意此命令对以下两种情况不生效<br>
&emsp;&emsp;&emsp;一、只能处理被挂起的线程，如果线程没有被挂起（可通过thread.suspend挂起），此命令不生效；<br>
&emsp;&emsp;&emsp;二、线程状态如果是unknow或zombie，则此命令不生效；<br>
&emsp;&emsp;&emsp;waiting 代表线程正在等待哪个对象关联的监控器，如果没有正在等待的对象显示n/a<br>
&emsp;&emsp;&emsp;holding 代表监控器正被哪个线程持有，如果没有持有的线程显示n/a<br>
样例：thread.wait<br>
&emsp;&emsp;&emsp;thread.wait 4826, 4827<br>
### thread.monitor.start {block}
说明：监控线程的启动，当目标程序中创建新的线程时调试器会收到相应的事件<br>
参数：{block} 命令尾块，这个块中的脚本会在监控的事件被触发后执行；<br>
样例：thread.monitor.start<br>
### thread.monitor.death {block}
说明：监控线程的启动，当目标程序中有线程结束时调试器会收到相应的事件<br>
参数：{block} 命令尾块，这个块中的脚本会在监控的事件被触发后执行；<br>
样例：thread.monitor.death<br>
## 源代码
### source.append expr
说明：添加源码路径<br>
参数：expr 标准表达式，运算结果必须是字符串，路径只需要给出包名称之前的路径即可<br>
&emsp;&emsp;&emsp;例如："d:\\\\program\\\\demo\\\\com\\\\runbox\\\\demo\\\\Demo.java", 只需要添加"d:\\\\program\\\\demo"<br>
样例：source.append "d:\\\\program\\demo";<br>
&emsp;&emsp;&emsp;@var = "d:\\\\program\\demo"; source.append @var;<br>
### source.delete expr
说明：删除已经增加的源码路径<br>
参数：expr 标准表达式，如果不给出参数则删除所有路径；<br>
&emsp;&emsp;&emsp;[id[, id]] 每一个ID为一个子表达式，运算结果必须为整形数<br>
样例：source.delete 1, 2, 3;<br>
&emsp;&emsp;&emsp;source.detele;<br>
### source.query 
说明：查询所有已经被添加的源路径；<br>
参数：无<br>
样例：source.query;<br>
## 异常捕获
### exception.monitor expr
说明：监控某种异常类型是否发生；
参数：expr 标准表达式，由以下几部分组成：<br>
&emsp;&emsp;&emsp;type 某中异常类型的名称，支持import.class，可以只写类名称需要注意的是这个异常类型<br>&emsp;&emsp;&emsp;在执行此命令时<br>
&emsp;&emsp;&emsp;必须是已经被目标虚拟机装载；<br>
&emsp;&emsp;&emsp;caught 是否捕获一个能被目标程序自己捕获的异常，一个子表达式，运行结果必须为布尔类型，<br>
&emsp;&emsp;&emsp;如果不省略此参数默认为true<br>
&emsp;&emsp;&emsp;uncaught 是否捕获一个不能被目标程序自己捕获的异常，一个子表达式，运行结果必须为布尔类型，<br>
&emsp;&emsp;&emsp;如果不省略此参数默认为true<br>
样例：exception.monitor "java.io.IOException", true, true;<br>
### exception.delete expr
说明：删除某种异常类型的监控<br>
参数：expr 标准表达式，如果不给出参数则删除所有异常监控；<br>
&emsp;&emsp;&emsp;[id[, id]] 每一个ID为一个子表达式，运算结果必须为整形数<br>
样例：@id = 10; exception.delete 1, 2, @id;<br>
&emsp;&emsp;&emsp;exception.delete;<br>
### exception.query
说明：查询当前所有异常监控<br>
参数：无<br>
样例：exception.query<br>
## 虚拟机
### machine.name
说明：获取当前被调试的目标虚拟机名称<br>
参数：无<br>
样例：machine.name<br>
### machine.version
说明：获取当前被调试的目标虚拟机版本<br>
参数：无<br>
样例：machine.version<br>
### machine.ability
说明：获取当前被调试目标虚拟机的某些特性<br>
参数：无<br>
样例：machine ability<br>
### machine.suspend
说明：挂起当前被调试的目标虚拟机<br>
参数：无<br>
样例：machine.suspend<br>
### machine.resume
说明：恢复当前被挂起的目标虚拟机<br>
参数：无<br>
样例：machine.resume<br>
### machine.status
说明：获取当前被调试的目标虚拟机状态（运行或挂起）以及挂起次数<br>
参数：无<br>
样例：machine.status<br>
## 信息跟踪
### trace.mode expr
说明：设置跟踪模式<br>
参数：expr 是一个表达式，运算结果必须为整形，可选的模式如下：<br>
&emsp;&emsp;&emsp;0x00 无任何跟踪（默认）<br> 
&emsp;&emsp;&emsp;0x01 跟踪JDI包发送<br>
&emsp;&emsp;&emsp;0x02 跟踪JDI包接收<br>
&emsp;&emsp;&emsp;0x04 跟踪产生的事件<br>
&emsp;&emsp;&emsp;0x08 跟踪类型引用<br>
&emsp;&emsp;&emsp;0x10 跟踪对象引用<br>
&emsp;&emsp;&emsp;以上值可以通过或运算组合0x1f用于跟踪所有事件，此功能通常用于分析调试器本身的错误或是观察<br>
&emsp;&emsp;&emsp;JDI工作原理用<br>
样例：trace.mode 0x4;<br>
&emsp;&emsp;&emsp;@mode = 0x1 | 0x2; trace.mode @mode;<br>
### trace.redirect expr
说明：此命令将跟踪到的信息通过重定向到指定的文件中<br>
参数：expr 标准表达式，运算结果必须是字符串，此字符串必须为一个文件路径，如果文件不存在<br>
&emsp;&emsp;&emsp;创建一个新文件，如果文件存在则删除再创建一个新文件；<br>
样例：@path = "D:\\\\program\\\\maven\\\\larva\\\\trace.log" trace.redirect @path;<br>
### trace.query
说明：查询当前跟踪的设置信息<br>
参数：无<br>
样例：trace.query<br>
## 退出
### quit 
说明：结束调试并终结被调试的目标虚拟机<br>
参数：无<br>
样例：quit;<br>
### detach 
说明：结束调试但不结果目标虚拟机<br>
参数：无<br>
样例：detach;<br>
## 帮助 （暂未实现）
### help 
说明：<br>
参数：<br>
样例：<br>