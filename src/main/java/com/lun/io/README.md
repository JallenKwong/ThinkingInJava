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

InputStream或Reader 的read()，读取单个字节或者字节数组

OutputStream或Writer的write()，写单个字节或者字节数组

### InputStream ###

![](image/inputstream.png)

### OutputStream ###

![](image/outputstream.png)

### Reader ####

![](image/reader.png)

### Writer ###

![](image/writer.png)

InputStream和OutputStream在以面向**字节**形式的IO中仍可以提供极有价值的功能，Reader和Writer则提供兼容Unicode与面向**字符**IO功能

### 数据的来源和去处 ###

InputStream 与 Reader 关系

![](image/relation.png)

### 更改流行为 ###

![](image/filter.png)

### 未发生变化的类 ###

![](image/unchange.png)

## RandomAccessFile ##

适用于由大小已知的记录组成的文件，所以可以使用**seek()**将记录从一处转移到另一处，然后读取或者修改记录。

在JDK 1.4 中，RandomAccessFile的大多数功能（但不是全部）由nio存储映射文件所取代。

## IO流的典型使用方式 ##

### 缓冲输入文件 ###

[BufferedInputFile](BufferedInputFile.java)

### 从内存输入 ###

[MemoryInput](MemoryInput.java)

### 格式化的内存输入 ###

[FormattedMemoryInput](FormattedMemoryInput.java) 一个字节一个字节地读取

[TestEOF](TestEOF.java) 使用available()方法查看还有多少可供存取的字符，然后，一次一个字节地读取文件

注意，available()的工作方式会随着所读取的媒介类型的不同而有所不同；字面意思就是“在没有阻塞的情况下所能读取的字节数”。对于文件，这意味着整个文件；但是对于不同类型的流，可能就不是这样，因此要谨慎使用。

### 基本的文件输出 ###

FileWriter对象可以先文件写入数据

[BasicFileOutput](BasicFileOutput.java)

LineNumberInputStream没卵用

**文本文件输出的快捷方式**

Java SE5在PrintWriter中添加了一个辅助构造器，使得你**不必**在每次希望创建文本并向其中写入时，都去执行所有的装饰工作。

[FileOutputShortcut](FileOutputShortcut.java)

### 存储和恢复数据 ###

[StoringAndRecoveringData](StoringAndRecoveringData.java)DataOutputStream 和 DataInputStream 存取或读取基本类型

### 读取随机访问文件 ###

利用**seek()**可以到处移动

[UsingRandomAccessFile](UsingRandomAccessFile.java)

### 管道流 ###

PipedInputStream

PipedOutStream

PipedReader

PipedWriter

用在多线程问题上



