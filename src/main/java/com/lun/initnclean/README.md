# Initialization & Cleanup #

[用构造器确保初始化](#用构造器确保初始化)

[方法重载](#方法重载)

[默认构造器](#默认构造器)

[this关键字](#this关键字)

[清理：终结处理和垃圾回收](#清理终结处理和垃圾回收)

[成员初始化](#成员初始化)

[构造器初始化](#构造器初始化)

[数组初始化](#数组初始化)

[枚举类型](#枚举类型)

## 用构造器确保初始化 ##

[SimpleConstructor](SimpleConstructor.java) 无参构造器来初始化对象

A constructor that takes no arguments is called the default constructor. The Java documents typically use the term **no-arg constructor**, but “**default constructor**” has been in use for many years before Java appeared, so I will tend to use that. 

[SimpleConstructor2](SimpleConstructor2.java)有参构造器来初始化对象

构造器是一种特殊类型的方法。

## 方法重载 ##

method overloading

[Overloading](Overloading.java)

### 区分重载方法 ###

**每个重载的方法都必须有一个独一无二的参数类型列表**。

[OverloadingOrder](OverloadingOrder.java)

### 涉及基本类型的重载 ###

[PrimitiveOverloading](PrimitiveOverloading.java)

如果传入的数据类型（实际参数类型）小于方法中声明的形式参数类型，实际数据类型就会被提升。

---

[Demotion](Demotion.java)

如果传入的实际参数较大，就得通过类型转换来执行窄化转换。如果不这样做，编译器就会报错。

### 以返回值区分重载方法 ###

**以返回值区分重载方法，行不通**

	void f(){}
	int f(){return 1;}

---

	f();//单独运行，但是Java如何判断该调用哪一个f()呢？

## 默认构造器 ##

[DefaultConstructor](DefaultConstructor.java)

[NoSynthesis](NoSynthesis.java)

创建了一个新对象，并调用其默认构造器——即使你没有明确定义它。没有它的话，就没有办法可调用，就无法创建对象。但是，如果已经定义了一个构造器（无论是否有参数），编译器就不会帮你自动创建默认构造器

## this关键字 ##

**this**关键字只能在方法内部使用，表示对“**调用方法的那个对象**”的引用。

若方法内部调用同一个类的另一个方法，就不必使用this,直接调用即可。

[Apricot](Apricot.java)

[Leaf](Leaf.java)

[PassingThis](PassingThis.java)

### 在构造器中调用构造器 ###

可能为一个类谢了多个构造器，有时可能现在一个构造器中调用另一个构造器，以避免重复代码。可用this关键字做到这一点。

[Flower](Flower.java)

### static的含义 ###

在static方法的内部不能调用非静态方法，反过来倒是可以的。

而且可以在没有创建任何对象的前提下，仅仅通过类本身来调用static方法和static域。

[更多信息](../object#static关键字)


## 清理：终结处理和垃圾回收 ##

Java有回收器负责回收无用对象占据的内存资源。

但也有**特殊情况**：假定你的对象（**并非使用new**）获得一块“特殊”的内存区域，由于垃圾回收器只知道释放那些经由new 分配的内存，所以它不知道该如何释放该对象的这块“特殊”内存。

为了应对这种情况，Java允许在类中定义一个名为finalize()的方法。（finalize()用于特定方法）

它的工作原理“假定”是这样的：一旦垃圾回收器准备好释放对象占有的存储空间，将首先调用其finalize()方法，并且在下一次**垃圾回收动作发生之时**，才会真正回收对象占用的内存。所以要是你打算用finalize(),就能垃圾回收时刻做一些重要的清理工作。

本地方法 是 一种在Java 中调用非Java代码（C++或C）的方式。

free()函数是C和C++中的函数，所以需要在finalize()中用本地方法调用它。

---

Java中

1. 对象可能不被垃圾回收
2. 垃圾回收并不等于“析构”（C++中的概念）
3. 垃圾回收只与内存有关

Java并未提供“析构函数”或相似的概念，要做类似的清理工作，必须自己动手创建一个执行清理工作的普通方法。

例如，假设某个对象在创建过程中会将自己绘制到屏幕上，如果不是明确地从屏幕上将其擦除，它可能永远得不到清理。

如果finalize()里加入某种擦除功能，当“垃圾回收”发生时（不能保证一定会发生），finalize()得到了调用，图像就会被擦除。要是“垃圾回收”没有发生，图像就会一直不留下来

---

**通常，不能指望finalize()，必须创建其他的“清理”方法，并且明确地调用它们**。

finalize()只能存在于程序员很难用到的一些晦涩用法里。

不过，finalize可用在 对象**终结条件**的验证

当对某个对象不再感兴趣——也就是它可以被清理了，这对象应该处于某种状态，使它占用的内存可以被安全地释放。

例如，要是对象代表了一个打开的文件，在对象被回收前程序员应该关闭这个文件。只要对象中存在没有被适当清理的部分，程序就存在很隐晦的缺陷。

finalize()可以用来最终发现这种情况——尽管它并不总是会被调用。如果某些finalize()的动作使得缺陷被发现，那么就可据此找出问题所在——这才是人们真正关心的。（相信这里起到安全网的作用）

[TerminationCondition](TerminationCondition.java)

---

下面来自Effective Java 2nd

>终结方法finalize通常是不可预测的，也是很危险，一般情况是不必要的。

>终结方法有两种合法用途：

>1. 当对象的所有者忘记调用前面段落中建议的显式终止方法时，终结方法可以充当“安全网（safety net）”

>2. 跟对象的本地对等体（native peer）有关。本地对等体是个本体对象（native object），普通对象通过本地方法（native method）委托给一个本地对象。它是一种特殊的对象，垃圾回收器不会知道它，所以终结方法是一种最合适的回收工具。

### 垃圾回收器GC如何工作 ###

听起来很奇怪——**存储空间的释放竟然会影响存储空间的分配**，但这确实是某些Java虚拟机的工作方式。

某Java虚拟机中，堆的实现截然不同：它更像一个传送带，每分配一个新对象，它就往前移动一格。

Java中的堆未必完全像传送带那样工作。若真，势必导致频繁的内存页面调度，影响性能。

关键在于GC的介入，当它工作时，将一面回收空间，一面使堆中的对象紧凑排列，这样“堆指针”就可以很容易移动到更靠近传送带的开始处，也就尽量避免了页面错误。通过GC对对象重新排列，实现一个高效可供分配的堆模型。

GC回收机制

1. **引用计数** 未被Java虚拟机实现 缺点：若出现循环引用，会出现“对象应该被回收，但引用计数不为0” 
2. **自适应** 依据思想：对任何“活”的对象，一定能最终追溯到其存活在堆栈或静态存储区之中的引用。由此，若从堆栈和静态存储区开始，遍历所有的引用，就能找到所有“活的”对象
3. **停止——复制** **stop-copy** 缺点：需要更多内存空间，若没有太多垃圾，就很消耗性能
4. **标记——清扫** **mark-sweep** **自适应**

---

在这里讨论的Java虚拟机中，内存分配以较大的“**块**”为单位。若对象较大，它会占用单独的块。

严格说，**停止——复制**要求在释放旧有对象前，必须先把所有存活对象从**旧堆**复制到**新堆**，这将导致大量内存复制行为。

有了块之后，垃圾回收器在回收的时候就可以往废弃的块拷贝对象了。

每个块都有相应的代数**generation count**来记录它是否存活。通常，若块在某处被引用，其代数会增加；GC将对上次回收动作之后新分配的块进行整理。这对处理**大量短命的临时对象**很有帮助。

GC会定期进行完整的清理动作——大型对象仍然不会被复制（只是代数会增加），内含小型对象的那些块则被复制并整理。

JavaVM会进行监视，若所有对象都很稳定，GC效率降低，则切换到“**标记——清扫**”方式；同样，JavaVM跟踪“**标记——清扫**”的效果，要是堆空间出现很多碎片，就会切换回“**停止——复制**”方式。

这就是“**自适应**”技术，可以称“**自适应的、分代的、停止——复制、标记——清扫**”式垃圾回收器。

---

JavaVM附加技术提升速度

**Just-In-Time** 即使编译器的技术，可把程序部分或全部翻译成本地机器码（这本来是Java虚拟机的工作），程序运行速度得以提升。

## 成员初始化 ##

局部变量未初始化就调用，会抛异常

成员字域有缺省值 [更多信息](../object#字段和方法)

[InitialValues](InitialValues.java)

### 指定初始化 ###

在定义类成员变量的地方为其赋值（C++不能这样做）

[InitialValues2](InitialValues2.java)

[Measurement](Measurement.java)

可通过调用某个方法来提供初值

[MethodInit](MethodInit.java)

[MethodInit2](MethodInit2.java)

[MethodInit3](MethodInit3.java)

## 构造器初始化 ##

[Counter](Counter.java)

### 初始化顺序 ###

在类的内部，变量定义的先后顺序决定了初始化的顺序。即使变量定义散布于方法定义之间，它们仍旧会在任何方法（包括构造器）被调用**之前**得到初始化。

[OrderOfInitialization](OrderOfInitialization.java)

### 静态数据的初始化 ###

无论创建多少个对象，静态数据都只占用一份存储区域。

static关键字**不能**应用于局部变量，因此它**只能**作用于字域。若一个字域是静态的基本类型域，且也没有对它进行初始化，那么它就会获得基本类型的标准初值；若它是一个对象引用，那么它的默认初始化值就是**null**。

[StaticInitialization](StaticInitialization.java)

初始化的顺序是静态对象（若它们尚未因前面的对象创建过程而被初始化），而后是“非静态”对象。

**总结一下对象的创建过程**，假设有个名为Dog的类：

1. 即使没有显示地使用static关键字，构造器实际上也是静态方法。因此，当首次创建类型为Dog的对象时，或者Dog类的静态方法/静态域首次被访问时，Java解释器必须查找类路径，以定位Dog.class
2. 然后载入Dog.class，有关**静态初始化**的所有动作都会执行。因此，**静态初始化**只在Class对象首次加载的时候进行一次。
3. 当用new Dog()创建**对象**的时候，首先将在堆上为Dog对象分配足够的存储空间。
4. 这块存储空间会被清零，这就自动地将Dog对象中的所有基本类型数据都设置成了默认值，而引用则被设置成了null.
5. 执行所有出现于字段定义处的初始化动作。
6. 执行构造器。

### 显示的静态初始化 ###

Java允许将多个静态初始化动作组织成一个特殊的“静态子句”或“静态块”

静态块

[Spoon](Spoon.java)

[ExplicitStatic](ExplicitStatic.java)

### 非静态实例初始化 ###

实例初始化子句与静态块类似

[Mugs](Mugs.java)

## 数组初始化 ##

[ArraysOfPrimitives](ArraysOfPrimitives.java) 基本类型数组初始化

[ArrayNew](ArrayNew.java)

[ArrayClassObj](ArrayClassObj.java) 对象类型数组初始化，包装类

[ArrayInit](ArrayInit.java) 包装类数组

[DynamicArray](DynamicArray.java) main 变参数组

[更多信息](../arrays)

### 可变参数列表 ###

[VarArgs](VarArgs.java)

[NewVarArgs](NewVarArgs.java)

[OptionalTrailingArguments](OptionalTrailingArguments.java)

[VarargType](VarargType.java)

[AutoboxingVarargs](AutoboxingVarargs.java)

[OverloadingVarargs](OverloadingVarargs.java)

[OverloadingVarargs2](OverloadingVarargs2.java)

[OverloadingVarargs3](OverloadingVarargs3.java)

## 枚举类型 ##

[Spiciness](Spiciness.java)

[SimpleEnumUse](SimpleEnumUse.java)

[EnumOrder](EnumOrder.java)

[Burrito](Burrito.java) switch使用enum枚举

[更多信息](../enumerated)

