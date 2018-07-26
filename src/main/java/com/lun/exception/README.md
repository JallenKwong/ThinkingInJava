# Error Handling with Exceptions #

## with finally ##

对于一些代码，可能会希望无论try块中的异常是否抛出，它们都能得到执行。这通常适用于内存回收之外的情况（因为回收由垃圾回收器）。为了达到这个效果，可以在异常处理程序后面加上finally子句。

[FinallyWorks](FinallyWorks.java)

### What's finally for ###

Java有垃圾回收机制，所以内存释放不再是问题。

Java也没有析构函数可供调用，Java在什么情况下才能用到finally

**当要除内存之外的资源恢复到它们的初始状态**，要用到finally子句。

这种需要清理的资源包括：已经打开的文件或网络连接，在屏幕上画的图形，甚至可以是外部世界的某个开关

[Switch](Switch.java)

[OnOffException1](OnOffException1.java)

[OnOffException2](OnOffException2.java)

[OnOffSwitch](OnOffSwitch.java) 清理示例1

[WithFinally](WithFinally.java) 清理示例2

[AlwaysFinally](AlwaysFinally.java) 清理示例3

### Using finally during return ###

finally 子句总是会执行，所以在一个方法中，可以从多个点返回，并且可以保证重要的清理工作仍然会执行

[MultipleReturns](MultipleReturns.java)

### Pitfall:the lost exception ###

finally的缺点——消失的Exception

[LostMessage](LostMessage.java)

一种处理方式：沉默是金

[ExceptionSilencer](ExceptionSilencer.java)
