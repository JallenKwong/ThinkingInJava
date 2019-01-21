# Everything Is an Object #

[用引用操纵对象](#用引用操纵对象)

[必须由你创建所有对象](#必须由你创建所有对象)

[永远不需要销毁对象](#永远不需要销毁对象)

[创建新的数据类型类](#创建新的数据类型：类)

[方法、参数和返回值](#方法参数和返回值)

[构建一个java程序](#构建一个java程序)

[static关键字](#static关键字)

[你第一个java程序](#你第一个java程序)

[注释和嵌入式文档](#注释和嵌入式文档)

[编码风格](#编码风格)


**Although it is based on C++, Java is more of a “pure” object-oriented language.** 

## 用引用操纵对象 ##

Although you treat everything as an object, the identifier you manipulate is actually a “**reference**” to an object.

引用——对象

遥控——电视机

You might imagine a television (the object) and a remote control (the reference). As long as you’re holding this reference, you have a connection to the television, but when someone says, “Change the channel” or “Lower the volume,” what you’re manipulating is the reference, which in turn modifies the object. 

If you want to move around the room and still control the television, you take the remote/reference with you, not the television. 

	String s;
	String s = "asdf";

## 必须由你创建所有对象 ##

When you create a reference, you want to connect it with a new object. You do so, in general, with the **new** operator. The keyword new says, “Make me a new one of these objects.” 

	String s = new String("asdf"); 

### 存储之地 ###

1. 寄存器Registers. 不能操控
2. 堆栈Stack. Java引用存储区
3. 堆Heap. Java对象存储区
4. 常量存储Constant storage. 常量值**通常**直接存放在程序代码内部。例子：**字符串池**，所有字面常量字符串和具有字符串值的常量表达式都是自动由内存限定的，并且会置于特殊的静态存储区。
5. Non-RAM storage. 如**流对象**或**持久化对象**。

### 特例：基本类型 ###

Java采取与C和C++相同的方法。也就是说，不用new来创建变量，而是创建一个并非是引用的"自动"变量。

这个变量直接存储“值”，并置于**堆栈Stack**中，因此更加高效。

Java要确定每种基本类型所占存储空间的大小。

Primitive type|Size|Minimum|Maximum|Wrapper type
---|---|---|---|---
boolean|—|—|—|Boolean
char|16 bits|Unicode 0|Unicode 2^16- 1|Character
byte|8 bits|-128|+127|Byte
short|16 bits|-2^15|+215-1|Short
int|32 bits|-2^31|+2^31-1|Integer
long|64 bits|-2^63|+2^63-1|Long
float|32 bits|IEEE754|IEEE754|Float
double|64 bits|IEEE754|IEEE754|Double
void|—|—|—|Void 

Java SE5的自动包装功能将自动地将基本类型转换为包装器类型

高精度数字**BigInteger**和**BigDecimal**

BigDecimal可以进行精确的货币计算

### Java中的数组 ###

一旦Java看到null，就知道这个引用还没有指向某个对象。

## 永远不需要销毁对象 ##

Java替我们完成所有的清理工作，从而大大地简化这个问题的。

### 作用域 ###

Scope

	{
	int x = 12;
	// Only x available
		{
			int q = 96;
			// Both x & q available
		}
	// Only x available
	// q is "out of scope"
	} 

**在作用域里定义的变量只可用于作用域结束之前**。

You cannot do the following, even though it is legal in C and C++: 

	{
		int x = 12;
		{ 
			int x = 96; // Illegal
		}
	}

### 对象的作用域 ###

**Java对象不具备和基本类型一样的生命周期**。

当用new创建一个Java对象时，可以存活于作用域之外。

	{
		String s = new String("a string");
	} // End of scope 

引用s在作用域终点就消失了。然而，s指向的String对象仍继续占据内存空间。在这段代码中，我们无法在这个作用域之后访问这个对象，因为对它唯一的引用已超出了作用域的范围。

事实证明，由new创建的对象，只要你需要，就要一直保留下来。

在C++中，你不仅必须要确保对象的保留时间与你需要这些对象的时间一样长，而且还必须在你使用完它们之后，将其销毁。

在Java中，靠什么防止对象们填满内存空间，进而阻塞你的程序？——Java有一个**垃圾回收器GC**，用来监视用**new**创建的所有对象，并辨别哪些不会再被引用得对象。随后，释放这些对象的内存空间，以便供其他新对象使用。

也就是说，你根本不必担心内存回收的问题。你只需要创建对象，一旦不再需要，它们就会自动消失。这样做就消除了这类问题（即内存泄露），这是由于程序员忘记释放内存而产生的问题。

## 创建新的数据类型：类 ##

	class ATypeName { /* Class body goes here */ } 
	ATypeName a = new ATypeName(); 

### 字段和方法 ###

字段**fields**（或称数据成员**data members**）

方法**methods**/音mie fen s/（或称成员函数 **member functions**）

**基本类型成员成员默认值**

Primitive type|Default
---|---
boolean|false
char|‘\u0000’ (null) 
byte|(byte)0
short|(short)0
int|0
long|0L
float|0.0f
double|0.0d

局部变量是不会自动初始化，直接使用会抛异常

	public static void main(String[] args) {
		int i;
		
		//Can't work
		//System.out.println(i);
	}

---

	Exception in thread "main" java.lang.Error: Unresolved compilation problem: 
		The local variable i may not have been initialized

## 方法、参数和返回值 ##

	ReturnType methodName( /* Argument list */ ) {
	 /* Method body */
	} 

方法名method name + 参数列表argument list = 方法签名 the signature of the method

---

	int x = a.f();

这种调用方法的行为通常被称为**发送消息给对象**。在上面的例子中，消息是f()，对象是a。面向对象的程序设计通常简单地归纳为“**向对象发送消息**”。

### 参数列表 ###

**按值传递**，实际上也是引用，除了8个基本类型

**程序似乎只是一系列带有方法的对象组合，这些方法以其他对象为参数，并发送消息给其他对象**。

## 构建一个Java程序 ##

### 名字可见式 ###

为了给一个类库生成不会与其他名字混淆的名字，Java设计者希望程序员反过来使用自己的Internet域名，因为这样可以保证它们肯定是独一无二的。

	com.lun.helloworld

### 运用其他构件 ###

	import java.util.ArrayList;

## static关键字 ##

考虑情景

1. 只想为某特定域分配**单一存储空间**，而不去考虑究竟要创建多少对象，甚至无需创建对象。
2. 希望某个方法不与包含它的类的任何对象关联在一起。也就是说，即使没有创建对象，也能够调用这个方法。**如：Math.abs()**

通过static关键字可以满足这两方面需要。

当声明一个事物为**static**时，就意味着这个域或方法不会与包含它的那个类的任何对象实例关联在一起。所以，即使从未创建某个类的任何对象，也可以调用其**static方法（类方法）**或**static域（类数据）**

**static域（类数据）**

	class StaticTest {
	 static int i = 47;
	} 

	StaticTest st1 = new StaticTest();
	StaticTest st2 = new StaticTest(); 

At this point, both **st1.i** and **st2.i** have the same value of **47** since they refer to the same piece of memory. 

可以通过类名直接调用

	StaticTest.i++; 

---

**static方法（类方法）**

	class Incrementable {
	 static void increment() { StaticTest.i++; }
	} 

	Incrementable sf = new Incrementable();
	sf.increment(); 

	Incrementable.increment(); 

static作用于某个字段，可定会改变数据创建的方式（因为一个**static字段**对**每个类**来说都只有一份存储空间，而**非static字段**这是对**每个对象**有一个存储空间）

但如果static作用于某个方法，差别却没有那么大。

**static方法**的一个重要用法就是在不创建任何对象的前提下就可以调用它。

和其他任何方法一样，static方法可创建或使用与其类型相同的被命名对象，因此，static方法常常拿来做“**牧羊人**”的角色，负责看护与其隶li4属同一类型的实例群


## 你第一个java程序 ##

	// HelloDate.java
	import java.util.*;
	public class HelloDate {
		public static void main(String[] args) {
			System.out.println("Hello, it’s: ");
			System.out.println(new Date());
		}
	} 


[显示从运行程序的系统中获取的所有“属性”ShowProperties](ShowProperties.java)

### 编译和运行 ###

	javac HelloDate.java

	java HelloDate

## 注释和嵌入式文档 ##

两种注释风格

1. /**/
2. //

javadoc提取注释的工具，输出HTML文件

**语法**

	/** A class comment */
	public class Documentation1 {
		/** A field comment */
		public int i;

		/** A method comment */
		public void f() {}
	} ///:~ 

**示例**

	import java.util.*;
	/** The first Thinking in Java example program.
	 * Displays a string and today’s date.
	 * @author Bruce Eckel
	 * @author www.MindView.net
	 * @version 4.0
	*/
	public class HelloDate {

		/** Entry point to class & application.
		* @param args array of string arguments
		* @throws exceptions No exceptions thrown
		*/
		public static void main(String[] args) {
			System.out.println("Hello, it’s: ");
			System.out.println(new Date());
		}
	} 

---

注释可用HTML的标签

	/**
	* You can <em>even</em> insert a list:
	* <ol>
	* <li> Item one
	* <li> Item two
	* <li> Item three
	* </ol>
	*/
	///:~ 

---

一些javadoc标签

1. @see 
	- @see classname
	- @see fully-qualified-classname
	- @see fully-qualified-classname#method-name
2. {@link package.class#member label} 
3. {@docRoot} 
4. {@inheritDoc} 
5. @version
	- @version version-information
6. @author
	- @author author-information
7. @since
8. @param
	- @param parameter-name description 
9. @return
	- @return description 
10. @throws
	- @throws fully-qualified-class-name description
11. @deprecated 

## 编码风格 ##

驼峰风格camel-casing

	class AllTheColorsOfTheRainbow {

		int anIntegerRepresentingColors;

		void changeTheHueOfTheColor(int newHue) {
		// ...
		}
		// ...
	} 
