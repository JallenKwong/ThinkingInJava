# Reusing classes #

## The final keyword ##

final指的是“这是无法改变的”

不改变可能出于两种理由：设计或效率

用到final的三个情况：数据、方法和类

### final data ###

向编译器告知一块数据是恒定不变的

1. 一个永不改变的编译常量
2. 一个在运行时被初始化的值，而你不希望它被改变

---
static final 只是占据一段不能改变的存储空间

---
**final基本类型和数据对象**

对于基本类型，final使数值恒定不变

而用于对象引用，final使引用恒定不变的。一旦引用被初始化指向一个对象，就无法再把它改为指向另一个对象。**然而**，对象其自身却是可以被修改的，Java并未提供使任何对象恒定不变的途径（对象成员变量值可以修改）（但可以自己编写类以取得使对象恒定不变的效果）。这一限制同样适用数组，他也是对象。

[FinalData](FinalData.java) final 用在数据上示例

### 空白final ###

Java允许生成“空白final”，所谓空白final是指被声明为final但又未给定初值的域

[BlankFinal](BlankFinal.java)

### final参数 ###

Java允许在参数列表中以声明的方式将参数指明为final。这意味着你无法在方法中更改参数引用所指向的对象

[FinalArguments](FinalArguments.java) 


### final方法 ###

用这主要有两个原因

1. 将方法锁定，防止继承类修改它的含义
2. 效率，早期的Java实现，方法指明final会提高运行效率，但是现在这优化还是留给JVM去做

**final与private**

类中所有的private方法都隐式地指定为final的。由于无法取用private方法，所以也就是无法覆盖它。可以对private添加final修饰词，**但这并不能该方法增加任何额外的意义**

混淆之处，若试图覆盖一个private方法（隐含是final）,似乎是奏效的，而且编译器也不会给出错误的信息

[FinalOverridingIllusion](FinalOverridingIllusion.java)

### final类 ###

起阉割作用

当将某个类的整体定义为final时（通过将关键字final置于它的定义之前），它表明了你不打算继承该类，而且也不允许别人这样做。

换句话说，处于某种原因，你对该类的设计永不需要做任何变动，或者出于安全的考虑，你不希望它有子类。

[Jurassic](Jurassic.java) 

final禁止继承，所以final类中所有的方法都隐式指定为final。final类添加final方法，多余的

### 有关final的忠告 ###

在设计类时，将方法指明是final，应该说是明智的。你可能会觉得，没人会想要覆盖力的方法。有时这是对的。

但请留意你所做的假设。要预见类时如何被复用的一般是很困难的，特别是对于一个通用类而言更是如此。如果将一个方法指定为final，可能妨碍其他程序员在项目中通过继承来复用你的类，而这只是因为你没有想到他会以那种方式被运用