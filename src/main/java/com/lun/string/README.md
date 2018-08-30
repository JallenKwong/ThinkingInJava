# String #

## 不可变String ##

String对象是不可改变的。查看JDK文档你就会发现，String类中每一个看起来会修改String值的方法，实际上都是创建了一个全新的String对象，以包含字符串内容。而最初的String丝毫未动。

[Immutable](Immutable.java)

## 重载+与StringBuilder ##

不可改变带来一定效率问题。为String对象重载的+操作符就是一个例子。重载的意思是，一个操作符在应用于特定的类时，被赋予特殊的意义（用于String的 + 与 += 是Java仅有的两个重载过的操作符，而Java并不允许程序员重载任何操作符）

[Concatenation](Concatenation.java)

![](image/string01.png)
![](image/string02.png)

反汇编后需注意的是：编译器自动引入java.lang.StringBuilder类。虽然在源码中未使用StringBuilder，但是编译器却自作主张使用它，因为它高效。


随意用String，编译器会为你优化？

[WhitherStringBuilder](WhitherStringBuilder.java) 反汇编后看看for循环内 拼接String对象与StringBuilder的对比

![](image/string03.png)

注意重点是：StringBuilder是在循环内构造，这意味着每经过循环一次，就会创建一个新的StringBuilder对象。

![](image/string04.png)

可以看到，不仅循环部分简短，而且它只生成了一个StringBuilder对象。显示地创建StringBuilder还允许你预先为其指定大小。若你已知最终字符串大概有多长，那预先指定StringBuilder的大小可以避免多次重新分配缓冲。

[UsingStringBuilder](UsingStringBuilder.java) 若需要在toString()方法中使用循环，最好创建一个StringBuilder对象，用它来构造最终的结果。

## 无意识的递归 ##

[ArrayListDisplay](ArrayListDisplay.java) ArrayList.toString()，他会遍历ArrayList中包含的所有对象，调用每个元素上的toString()方法

若你希望toString()方法打印对象的内存地址，也许你会考虑使用this关键字：

[InfiniteRecursion](InfiniteRecursion.java)

会得到

	Exception in thread "main" java.lang.StackOverflowError

原因在于

	public String toString() {
	    return " InfiniteRecursion address: " + this + "\n";
	 }

this前面是字符串，+ 重载为字符串的拼接符，于是this就调用toString()，于是发生递归调用

解决方法：将this换成super.toString()

## String上的操作 ##

![](image/string05.png)
![](image/string06.png)

## 格式化输出 ##

Java SE5推出C语言中printf()风格的格式化输出这一功能，它简单，强大。

### printf() ###


















