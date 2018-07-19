# Enumerated Type #

[Basic enum features](#basic-enum-features)

[Adding methods to an enum](#adding-methods-to-an-enum)

[Enums in switch statements](#enums-in-switch-statements)

[The mystery of values](#the-mystery-of-values)

[Implements, not inherits](#implements, not inherits)

[Random selection](#random-selection)

[](#)

[](#)

[](#)

[](#)

[](#)

[](#)

[](#)



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

[RandomTest](RandomTest.java)













