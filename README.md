# Larva (Java Debug)
I used to debug C or C++ program by console, like cdb, kd when I worked in windows system. About one year ago, I began to learn Java. But I did not find a suitable debug tool in console (JDB is a good chose for someone else but I did not used to it.) I began trying to find a debugger working in console like cdb or kd, but I did not find a suitable tool, so I decided to make a debug tool of my own, called Larva (I like this name which from an interesting cartoon)

Larva, in present, has several function belowing:
breakpoints (method/line/access/modify points, create, delete, enable, disable, etc.)
sources code manage (append, delete, etc.)
threads (query, switch, suspend, resume, etc.)
stack (query frame, switch frame, etc.)
exeutes (source code debug, step over, step into, etc.) 
class (query, query field, query method, etc.)
variant (print, field, locals, etc.)
template (support list, map, vector, queue, etc.)
monitor (wait chain, wait object, etc.)
virtual machin information
script called Larva script (support standard java expression excepting new object and invoking method)

Of course, there are a lot of debug in Larva and it do need to be perfected. 
There is a reference about Larva, you can read it if you are interested in it.
I`ll thank for your advices or debug report (runbox@163.com)  
