# Operators #

[更简单的打印语句](#更简单的打印语句)

[使用Java操作符](#使用java操作符)

[优先级](#优先级)

[赋值](#赋值)

[算术操作符](#算术操作符)

[自动递增和递减](#自动递增和递减)

[关系操作符](#关系操作符)

[逻辑操作符](#逻辑操作符)

[直接常量](#直接常量)

[按位操作](#按位操作)

[移位操作符](#移位操作符)

[三元操作符](#三元操作符)

[字符串操作符+和+=](#字符串操作符和)

[使用操作符时常犯的错误](#使用操作符时常犯的错误)

[类型转换操作符](#类型转换操作符)

[Java没有sizeof](#java没有sizeof)

[操作符小结](#操作符小结)

## 更简单的打印语句 ##

	System.out.println("Rather a lot to type");

## 使用Java操作符 ##

	+ - * / = 

	== = != //不单只基本类型，可用于对象类型

	+ += //可用于String

## 优先级 ##

当一个表达式中存在多个操作符时，操作符的优先级就决定各部分的计算顺序。不确定优先级可用括号明确

[Precedence](Precedence.java)

## 赋值 ##

=

取右值，把它复制给左值

**基本类型**存储了实际的数值，而并非指向一个对象的引用，所以在为其赋值的时候，是**直接**将一个地方的内容**复制**到了另一个地方。

对一个**对象**进行赋值操作时，我们真正操作的是对对象的**引用**。所以倘若“将一个对象赋值给另一个对象”，实际是将“**引用**”从一个地方复制到另一个地方。

[Assignment](Assignment.java) 别名现象

### 方法调用中的别名问题 ###

[PassObject](PassObject.java)

Java是按值传递的编程语言。

## 算术操作符 ##

	+ - * / %

[MathOps](MathOps.java)

### 一元加、减操作符 ###

	x = -a;
	x = a * -b;

## 自动递增和递减 ##

	++ --

	前缀式、后缀式

[AutoInc](AutoInc.java)

## 关系操作符 ##

关系操作符生成一个boolean结果

	== < > >= <=

### 测试对象的等价性 ###

==、!= 能直接用于 基础类型，对象类型则是比较是否来自同一引用，

对象比较要则要重新覆盖equals()

equals()默认是用==比较两对象

    public boolean equals(Object obj) {
        return (this == obj);
    }

[Equivalence](Equivalence.java)

[EqualsMethod](EqualsMethod.java)

[EqualsMethod2](EqualsMethod2.java)

## 逻辑操作符 ##

	&& || ！

[Bool](Bool.java)

### 短路 ###

一旦能够明确无误地确定整个表达式的值，就不再计算表达式余下部分。

[ShortCircuit](ShortCircuit.java)

## 直接常量 ##

	0x89dd 067 0L 0F 3.14D

[Literals](Literals.java)

### 指数计数法 ###

	1e-43f

[Exponents](Exponents.java)

## 按位操作 ##

	& | ~ ^

## 移位操作符 ##

	<< >>> 零扩展
	>>

如果对char/byte或者short类型的数字进行移位处理，那么再移位进行之前，它们会被转换为int类型，并且得到的结果也是一个int类型的值。

[URShift](URShift.java)

## 三元操作符 ##

	boolean-exp ? value0 : value1

[TernaryIfElse](TernaryIfElse.java)

## 字符串操作符+和+= ##

[StringOperators](StringOperators.java)

## 使用操作符时常犯的错误 ##

C/C++的常见错误

	while(x = y){
	
	}

## 类型转换操作符 ##

[Casting](Casting.java)

### 截尾和舍入 ###

[CastingNumbers](CastingNumbers.java)

[RoundingNumbers](RoundingNumbers.java)

### 提升Promotion ###

You’ll discover that if you perform any mathematical or bitwise operations on primitive data types that are smaller than an **int** (that is, **char**, **byte**, or **short**), those values will be promoted to **int** before performing the operations, and the resulting value will be of type **int**.

So if you want to assign back into the smaller type, you must use a cast. (And, since you’re assigning back into a smaller type, you might be losing information.) In general, the largest data type in an expression is the one that determines the size of the result of that expression; if you multiply a **float** and a **double**, the result will be **double**; if you add an **int** and a
**long**, the result will be **long**. 

## Java没有sizeof ##

## 操作符小结 ##

[AllOps](AllOps.java)

在char、byte和short中，我们可看到使用算术操作符中数据类型提升的效果。对这些类型的任何一个进行算术运算，都会得到int结果，必须将其显示地类型转换回原来的类型（窄化转换可能会造成信息的丢失），以将值赋给原本的类型。

小心计算出现**溢出**

[Overflow](Overflow.java)

对于char、byte或者short复合赋值并不需要类型转换。






