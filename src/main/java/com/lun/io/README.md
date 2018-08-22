# IO #

## The File Class ##

The **File** class has a deceiving name; you might think it refers to a file, but it doesn't. In fact, "FilePath" would have been a better name for the class. It can be represent the name of particular file or the name of a set of files in a directory.

### A directory lister ###

[DirList](DirList.java) FilenameFilter 及 new File().list() 的运用 （涉及到策略模式）

[DirList2](DirList2.java)

[DirList3](DirList3.java) **DirList** 使用匿名类方式实现的两个例子

### 目录实用工具 ###

程序设计中一项常见的任务就是在文件集上执行操作，这些文件要么在本地目录中，要么遍布整个目录树中。

[Directory](../util/Directory.java) 一种工具为你产生基于你提供的正则表达式而被选中的文件集

[PPrint](../util/PPrint.java) 一个可以打印时添加新行并缩排所有元素的工具

[DirectoryDemo](DirectoryDemo.java) Directory的运用示例

[ProcessFiles](../util/ProcessFiles.java) 用到策略模式，执行了查找具有特定扩展名的文件所需的全部工作，并且当它找到匹配的文件时，将直接把文件传递给Strategy对象，（打算用这些文件干什么东东）（这策略模式写得太纠缠了）

### 目录的检查及创建 ###

File类不仅仅只代表存在的文件或目录。也可以用File对象来创建的目录或尚不存在的整个目录路径。可以查看文件的特性（如：大小，最后修改时间，读/写），检查某个File对象代表的是一个文件还是一个目录，并可删除文件。

[MakeDirectories](MakeDirectories.java) File类的一些其他方法

## 输入和输出 ##














