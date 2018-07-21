# Enumerated Type #

[Basic enum features](#basic-enum-features)

[Adding methods to an enum](#adding-methods-to-an-enum)

[Enums in switch statements](#enums-in-switch-statements)

[The mystery of values](#the-mystery-of-values)

[Implements, not inherits](#implements,-not-inherits)

[Random selection](#random-selection)

[Using interfaces for organization](#using-interfaces-for-organization)

[Using EnumSet instead of flags](#using-enumSet-instead-of-flags)

[Using EnumMap](#using-enumMap)

[Constant-specific methods](#constant-specific-methods)

[Multiple dispatch](#multiple-dispatch)

## Basic enum features ##

调用enum的values()方法，可以遍历enum实例。

创建enum时，编译器会为你生成一个相关的类，这个类继承自java.lang.Enum

[EnumClass](EnumClass.java) 样式Enum的基本使用方法

[Spiciness](Spiciness.java)

[Burrito](Burrito.java) 静态导入enum 的Spiciness 到 Burrito

## Adding methods to an enum ##

除了不能继承自一个enum之外，我们基本上可以将enum看作一个常规类。也就是说，我们可以先enum中添加方法。enum甚至可以有main()方法。

[OzWitch](OzWitch.java) 像class那样实现enum

[SpaceShip](SpaceShip.java) 覆盖toString()

## Enums in switch statements ##

[TrafficLight](TrafficLight.java) switch中用enum

## The mystery of values ##

编译器为你创建的enum类都**继承**自Enum类。

然而 Enum 并没有 values()方法。

[Reflection](Reflection.java) Enum类与自己实现的enum通过反射及反编译，揭示Enum 并没有 values()方法。

values()是由编译器添加的static方法

若向上转型upcast则用不了values()，可以通过使用Class中getEnumConstants()获取enum实例

[UpcastEnum](UpcastEnum.java) 向上转型获取Enum实例

[NonEnum](NonEnum.java) getEnumConstants()是Class中的方法，可以对不是每句的类来调用此方法(会抛异常)


## Implement, not inherits ##

自己实现的enum不能再派生其他类（不能继承enum）

[EnumImplementation](EnumImplementation.java) 创建一个新的enum时，可以同时实现一个或多个接口

## Random selection ##

随机选择

[Enums](Enums.java) 里面的<T extends Enum<T\>>表示T是一个enum实例

[RandomTest](RandomTest.java) Enumsc测试用例

## Using interfaces for organization ##

无法从enum继承子类。

有时源自我们希望扩展enum中的元素，有时是因为我们希望使用子类将一个enum中的元素进行分类

[Food](menu/Food.java) 在一个借口的内部，创建实现该接口的枚举，以此将元素进行分组，可以达到将枚举元素分类组织的目的。

[TypeOfFood](menu/TypeOfFood.java) Food的运用

[Meal](menu/Meal.java) 枚举的枚举

[SecurityCategory](SecurityCategory.java) 更简洁的枚举的枚举

[Meal2](menu/Meal2.java) Food中的 枚举的枚举 的精简版

## Using EnumSet instead of flags ##

Set 是一种集合，只能向其中添加不重复的对象。当然，enum也要求其成员都是唯一，所以enum看起来具有集合的行为。

不过由于不能从enum中添加删除或添加元素，所以它只能算是不太有用的集合。

Java SE5引入EnumSet，是为了通过enum创建一种替代品，以替代传统的基于int的“位标志”。这种标志可以用来表示某种“开/关”信息，不过，使用这种标志，我们最终操作的只是一些bit.

EnumSet的设计充分考虑到了速度因素，就其内部而言，它可能就是将一个long值作为比特向量。

[AlarmPoints](AlarmPoints.java)

[EnumSets](EnumSets.java) EnumSet的运用示例

long有64位，若enum的成员变量超过64位，就弄多一个long来记录

[BigEnumSet](BigEnumSet.java) 超64个成员变量的enum

## Using EnumMap ##

[EnumMap](EnumMap.java) EnumMap的用例

## Constant-specific methods ##

可以为enum实例编写方法，从而为每个enum实例赋予各自不同的行为

[ConstantSpecificMethod](ConstantSpecificMethod.java) 常量相关的方法

上面的例子通常称为**表驱动的代码table-dirven code**(联想二维坐标x,y确定一个点)

enum实例与类有点相似

[NotClasses](NotClasses.java) enum实例与类的不同之处

[CarWash](CarWash.java) 常量相关的方法 及 EnumSet中的应用

[OverrideConstantSpecific](OverrideConstantSpecific.java) 复写常量方法


### 使用enum的责任链 ###

责任链**Chain of Responsibility** 设计模式

以多种不同的方式来解决一个问题，然后将它们链接在一起。当一个请求到来时，它遍历这个链，直到链中的某个方法能够处理该请求。

[PostOffice](PostOffice.java) 责任链设计模式的用例

### 使用enum的状态机 ###

枚举类型非常适合用来创建状态机。

一个状态机可以具有有限个特定的状态，它通常根据输入，从一个状态转移到下一个状态，不过也可能存在瞬时状态(transient states)，而一旦任务执行结束，状态机就会立刻离开瞬时状态。

[Input](Input.java)

[VendingMachine](VendingMachine.java) 自动售货机——一个很好的状态机例子

## Multiple dispatch ##

问题：当你要处理多种交互类型，程序可能会变得相当杂乱。

举例来说，如果一个系统要分析和执行数学表达式。我们可能会声明Number.plus(Number)、Number.multiple(Number)等等，其中Number是各种数字对象的超类。

然而，当你声明a.plus(b)、你并不知道a或b的确切类型，**如何能让它们正确地交互？**

---

Java只支持**单路分发**。也就是说，如果要执行的操作包含了不止一个类型未知的对象时，那么**Java的动态绑定机制只能处理其中一个类型**。这就无法解决我们上面提到的问题。所以，你必须自己来判定其他类型，从而实现自己的动态绑定行为。

---

解决上面问题的方法就是**多路分发**。多态只能发生在方法调用时，所以，**若果你想使用两路分发，那么就必须有两个方法的调用：第一个方法调用决定第一个未知类型，第二个方法调用决定第二个未知的类型**。要利用多路分发，程序员**必须为每一个类型提供一个实际的方法调用**，如果你要处理两个不同的类型体系，就需要为每个类型体系执行一个方法调用。

一般而言，程序员需要有设定好的某种配置，以便一个方法调用能够引出更多的方法调用，从而能够在这个过程中处理多种类型。

为了达到这种效果，我们需要与多个方法一同工作：因为每个分发都需要一个方法调用。

[Outcome](Outcome.java)

[RoShamBo1](RoShamBo1.java)多路分发示例

### 使用enum分发 ###

[Competitor](Competitor.java)

[RoShamBo](RoShamBo.java)

[RoShamBo2](RoShamBo2.java)

### 使用常量相关的方法 ###

[RoShamBo3](RoShamBo3.java)

[RoShamBo4](RoShamBo4.java)

### 使用EnumMap分发 ###

[RoShamBo5](RoShamBo5.java)

### 使用二维数组分发 ###

多个分发程序中，这代码最简洁

[RoShamBo6](RoShamBo6.java)

感悟：多路分发感觉像策略模式 

