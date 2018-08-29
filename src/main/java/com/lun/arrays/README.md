# 数组 #

[数组为什么特殊](#数组为什么特殊)

[数组是第一级对象](#数组是第一级对象)

[返回一个数组](#返回一个数组)

[多维数组](#多维数组)

[数组与泛型](#数组与泛型)

[创建测试数据](#创建测试数据)

[java.util.Arrays使用功能](#java.util.arrays使用功能)

## 数组为什么特殊 ##

数组与其他种类的容器之间的区别有三方面：

1. 效率
2. 类型检查
3. 保存基本类型的能力

数组就是一种简单线性序列，使得访问非常快速。 但是为这种速度付出的代价是数组对象的大小被固定，并且在其生命周期不可改变。

ArrayList更具有弹性

由于泛型、自动包装出现，数组仅存的优点就是效率

[ContainerComparison](ContainerComparison.java) 数组和ArrayList的比较


## 数组是第一级对象 ##

无论使用哪种类型的数组，数组标识符其实只是一个引用，指向在堆中创建的一个真实对象，这个（数组）对象用以保存其他对象引用。

可以作为数组初始化语法的一部分隐式地创建此对象，或者用new 表达式显式创建。只读成员length是数组的唯一部分，表示对象可以存储多少元素

[]语法是访问数组对象的方式。

[ArrayOptions](ArrayOptions.java) 总结了初始化数组的各种方式，以及如何对指向数组的引用赋值，使之指向另一个数组对象。此例说明，对象数组和基本类型数组在使用上是几乎相同的，唯一的区别就是对象数组保存的是引用，基本类型数值直接保存基本类型的值。


## 返回一个数组 ##

在Java中，你只是直接“返回一个数组”，而无需担心要为数组负责——只要你需要它，他就会一直存在，当你使用完后，垃圾回收器会清理掉它。

[IceCream](IceCream.java)

## 多维数组 ##

[MultidimensionalPrimitiveArray](MultidimensionalPrimitiveArray.java)

使用Arrays.deepToString()方法，可以将多维数组转换为多个String

[ThreeDWithNew](ThreeDWithNew.java)用new创建多维数组

[RaggedArray](RaggedArray.java) 数中构成的每个向量都可以具有任意长度(被称为粗糙数组)

[AutoboxingArrays](AutoboxingArrays.java) 自动包装机制对数组初始化也起作用

[AssemblingMultidimensionalArrays](AssemblingMultidimensionalArrays.java)逐个、部分构建一个非基本类型的对象数组

[MultiDimWrapperArray](MultiDimWrapperArray.java) Arrays.deepToString()对于基本类型数组和对象数组都起作用

## 数组与泛型 ##

数组与泛型不能很好地结合。你不能实例化参数化类型的数组

	Peel<Banana>[] peels = new Peel<Banana>[10];//Illegal

数组必须知道它们所持有确切类型，以保证类型安全

但是，可以参数化数组本身的类型：

[ParameterizedArrayType](ParameterizedArrayType.java) 参数化类和参数化方法参数化数组本身的类型，参数化方法比较方便

**根据上例，不能创建泛型数组这说法并不是十分准确。**

诚然，编译器确实不让实例化数组，但是，它允许创建对这种数组的引用。例如：

	List<String> ls;

[ArrayOfGenerics](ArrayOfGenerics.java) 尽管你不能创建实际的持有泛型的数组对象，但是你可以创建非泛型数组，然后将其转型。

事实上，泛型容器总是比泛型数据更好的选择

[ArrayOfGenericType](ArrayOfGenericType.java) 在类或方法的内部，擦除**通常**会使泛型变得不适用，不能创建泛型数组

总结：直接创建泛型不可取，采取迂回的方法——**创建非泛型数组，然后将其转型**

## 创建测试数据 ##

### Arrays.fill() ###

用同一个值填充各个位置，而针对对象而言，就是复制同一个引用进行填充。

### 数组生成器 ###

[CountingGenerator](CountingGenerator.java) 自增计数生成器

[GeneratorsTest](GeneratorsTest.java) CountingGenerator的示例

[RandomGenerator](RandomGenerator.java) 随机数生成器

[RandomGeneratorsTest](RandomGeneratorsTest.java) RandomGenerator的示例

### 从Generator创建数组 ###

[Generated](Generated.java) 从Generator创建数组工具

[TestGenerated](TestGenerated.java) Generated的演示用例

[ConvertTo](ConvertTo.java) 包装类数组转换成基本类型数组

[PrimitiveConversionDemonstration](PrimitiveConversionDemonstration.java) ConvertTo和Generated演示用例

[TestArrayGeneration](TestArrayGeneration.java) RandomGenerator、ConvertTo和ConvertTo演示用例

## java.util.Arrays使用功能 ##

### 复制数组 ###

[CopyingArrays](CopyingArrays.java) 使用System.arraycopy()进行复制

如果复制对象数组，那么只是复制了对象的引用——而不是对象本身的拷贝。这称作为**浅复制**(shallow copy)

### 数组的比较 ###

数组相等的条件是元素个数必须相等，并且对应的位置元素也相等，这可以通过对每个元素使用equals()作比较来判断。

[ComparingArrays](ComparingArrays.java)

### 数组元素的比较 ###

排序必须根据对象的实际类型执行比较操作。

程序设计的基本目标是：“将保持不变的事物与会发生改变的事物相分离”

通过使用**策略模式**，可以将“会发生变化的代码”封装在单独的类中(策略对象)，你可以将策略对象传给总是相同的代码，这些代码将使用策略来完成其算法。

通过这种方式，你能够用不同的对象来表示不同的比较方式，然后将它们传递给相同的排序代码。

[CompType](CompType.java) 实现了java.util.Comparable接口的compareTo方法，在进行排序

另一个方法就是，实现了Comparator接口

[Reverse](Reverse.java) Arrays.sort(a, Collections.reverseOrder());实现反序

### 数组排序 ###

[StringSorting](StringSorting.java) 对String进行排序

Java标准类库中的排序算法针对正排序的特殊类型进行了优化——针对基本类型设计的“快速排序”(Quicksort)，以及针对对象设计的“稳定归并排序”。所以无须担心排序的性能，除非你可以证明排序部分的确是程序效率的瓶颈

### 已排序的数组中查找 ###

Arrays.binarySearch(a, r)

[ArraySearching](ArraySearching.java)

[AlphabeticSearch](AlphabeticSearch.java) 







