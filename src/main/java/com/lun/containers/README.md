# Containers in Depth #

[Full container taxonomy](#)

[Filling containers](#)

[Collection functionality](#)

[Optional operations](#)

[List functionality](#)

[Sets and storage order](#)

[Queues](#)

[Understanding Maps](#)

[](#)


## Full container taxonomy ##

![](image/full-container-taxonomy.png)

Java SE5新添加的：

- Queue接口及其实现PriorityQueue和各种风格BlockingQueue
- ConcurrentMap接口及其实现ConcurrentHashMap
- CopyOnWriteArrayList、CopyOnWriteArraySet用于并发
- EnumSet、EnumMap

## Filling containers ##

Collections.fill()只能针对List，且**复制单个对象的引用**

Collections.nCopies()类似

[FillingLists](FillingLists.java)

	Object.toString() <=> getClass().getName() + '@' + Integer.toHexString(hashCode())

### 一种Generator解决方案 ###

为了更加容易地创建数据

[CollectionData](../util/CollectionData.java) **适配器Adapter**设计模式例子

[CollectionDataTest](CollectionDataTest.java)CollectionData用例

LinkedHashSet维护的是保持了插入顺序的链接列表

[CollectionDataGeneration](CollectionDataGeneration.java)

### Map生成器 ###

[Pair](../util/Pair.java)

[MapData](../util/MapData.java)

[MapDataTest](MapDataTest.java)

### 使用Abstract类 ###

#### AbstractMap ####

Flyweight 享元模式，蝇量模式

You use a **flyweight** when the ordinary solution requires too many objects, or when producing normal objects takes up too much space.

The **Flyweight** pattern externalizes使具体化 part of the object so that, instead of everything in the object being contained within the object, some or all of the object is looked up in a more efficient external table (or produced through some other calculation that saves space). 

为了创建只读Map，可继承AbstractMap并实现entrySet()。

为了创建只读Set，可继承AbstractSet并实现iterator()和size()

[Countries](../util/Countries.java)

[CountingMapData](../util/CountingMapData.java)

#### AbstractList ####

[CountingIntegerList](../util/CountingIntegerList.java)

## Collection functionality ##


method|description
---|---
boolean add(T)|Ensures that the container holds the argument which is of generic type T.Returns false if it doesn’t add the argument. (This is an "optional" method, described in the next section.)
boolean addAll(Collection<? extends T>)|Adds all the elements in the argument.Returns true if any elements were added. ("Optional.")
void clear( )|Removes all the elements in the container. ("Optional.")
boolean contains (T)|true if the container holds the argument which is of generic type T.
Boolean containsAll(Collection<?>)|true if the container holds all the elements in the argument.
boolean isEmpty( )|true if the container has no elements.
Iterator<T> iterator( )|Returns an Iterator<T> that you can use to move through the elements in the container.
Boolean remove(Object)|If the argument is in the container, one instance of that element is removed.Returns true if a removal occurred.("Optional")
boolean removeAll(Collection<?>)|Removes all the elements that are contained in the argument. Returns true if any removals occurred. ("Optional.")
Boolean retainAll(Collection<?>)|Retains only elements that are contained in the argument (an "intersection交集," from set theory). Returns true if any changes occurred. ("Optional.")
int size( )|Returns the number of elements in the container.
Object[] toArray( )|Returns an array containing all the elements in the container.
<T>T[] toArray(T[] a)|Returns an array containing all the elements in the container. The runtime type of the result is that of the argument array a rather than plain Object.

使用ArrayList，以说明各种Collection子类的“最基本的共同的特性”。

[CollectionMethods](CollectionMethods.java)

## Optional operations ##

执行各种不同的添加和移除的方法在Collection接口中都是**可选操作**。这意味着实现类并不需要为这些方法提供功能定义。

**为什么将方法定义为可选的呢？**因为这可防止在设计中出现接口爆炸的情况。

容器类库中的其他设计看起来总是为了描述每个主题的各种变体，而最终患上了令人困惑的接口过剩症。

甚至这么做仍不能捕捉接口的各种特例，因为总是有人会发明新的接口。

“未获支持的操作”这种方式可实现Java容器类库的一个重要目标：容器易学易用。未获支持的操作是一种特例，可以延迟到需要时再实现。

为了让这种方式工作：

1. UnsupportedOperationException必须是一种罕见事件。即，对于多数类来说，所有操作都应该可以工作，只有在特例中才会有未获支持的操作。在Java容器类库中确实如此，因为你在99%时间内使用的容器类，如ArrayList，LinkedList，HashSet，HashMap以及其他的具体实现，都支持所有的操作。这种设计留下一个“后门”，如果想创建新的Collection，但是没有为Collection接口总的所有方法都提供有意义的意义，那么它仍旧适合现在的类库。
2. 若一操作是未获支持的，那么再实现接口的时候可能就会导致UnsupportOperationException异常，而不是将产品程序交个客户以后才出现此异常，这种情况是有道理的。毕竟，它表示编程上有错误：使用不正确的接口实现。

### 未获支持的操作 ###

Arrays.asList()

Collections.unmodifiableList()

通过[Unsupported](Unsupported.java)打印出会抛出UnsupportedOperationException的方法。

## List functionality ##

ArrayList、LinkedList

[Lists](Lists.java)

## Sets and storage order ##

Class|Description
---|---
Set (interface)|Each element that you add to the Set must be unique; otherwise, the Set doesn’t add the duplicate element. Elements added to a Set must at least define equals( ) to establish object uniqueness. Set has exactly the same interface as Collection. The Set interface does not guarantee that it will maintain its elements in any particular order.
HashSet|For Sets where fast lookup time is important.**Elements must also define hashCode()**.
TreeSet|An ordered Set backed by a tree. This way, you can extract an ordered sequence from a Set. **Elements must also implement the Comparable interface**.
LinkedHashSet|Has the lookup speed of a HashSet, but internally maintains the order in which you add the elements (the insertion order) using a linked list. Thus, when you iterate through the Set, the results appear in insertion order. **Elements must also define hashCode()**. 

**对于良好的编程风格而言，覆盖equals()同时覆盖hashCode()**

[TypesForSets](TypesForSets.java)

### SortedSet ###

SortedSet的元素可以保证处于排序状态

SortedSet提供的附加接口


Method|Description
---|---
Comparator comparator( )|Produces the Comparator used for this Set, or null for natural ordering.
Object first( )|Produces the lowest element.
Object last( )|Produces the highest element.
SortedSet subSet(fromElement, toElement)|Produces a view of this Set with elements from fromElement, inclusive, to toElement, exclusive.
SortedSet headSet(toElement)|Produces a view of this Set with elements less than toElement.
SortedSet tailSet(fromElement)|Produces a view of this Set with elements greater than or equal to fromElement. 

[SortedSetDemo](SortedSetDemo.java)

## Queue ##

[QueueBehavior](QueueBehavior.java)

### 优先级队列 ###

[ToDoList](ToDoList.java)

### 双向队列 ###

[Deque](Deque.java)

[DequeTest](DequeTest.java)

## Understanding Maps ##

**映射表**（也称为**关联数组**）的基本思想是它维护的是键-值对关联，因此你可以使用键查找值。

[AssociativeArray](AssociativeArray.java) 一个简单的关联数组实现。

### 性能 ###

Map|Description
---|---
HashMap|Implementation based on a hash table. (Use this class instead of **Hashtable**.) Provides constant-time performance for inserting and locating pairs. Performance can be adjusted via constructors that allow you to set the capacity and load factor of the hash table.
LinkedHashMap|Like a **HashMap**, but when you iterate through it, you get the pairs in insertion order, or in least-recently-used (LRU) order. Only slightly slower than a **HashMap**, except when iterating, where it is faster due to the linked list used to maintain the internal ordering.
TreeMap|Implementation based on a red-black tree. When you view the keys or the pairs, they will be in sorted order (determined by **Comparable** or **Comparator**). The point of a TreeMap is that you get the results in sorted order. TreeMap is the only Map with the subMap( ) method, which allows you to return a portion of the tree.
WeakHashMap|A map of weak keys that allow objects referred to by the map to be released; designed to solve certain types of problems. If no references to a particular key are held outside the map, that key may be garbage collected.
ConcurrentHashMap|A thread-safe **Map** which does not involve synchronization locking. This is discussed in the Concurrency chapter.
IdentityHashMap|A hash map that uses == instead of **equals()** to compare keys. Only for solving special types of problems; not for general use. 

[Maps](Maps.java)

### SortedMap ###

SortedMap提供的接口

Method|Description
---|---
Comparator comparator( )|Produces the comparator used for this Map, or null for natural ordering.
T firstKey( )|Produces the lowest key.
T lastKey( )|Produces the highest key.
SortedMap subMap(fromKey, toKey)|Produces a view of this Map with keys from fromKey, inclusive, to toKey, exclusive. SortedMap headMap(toKey): Produces a view of this Map with keys less than toKey.
SortedMap tailMap(fromKey)|Produces a view of this Map with keys greater than or equal to fromKey. 

[SortedMapDemo](SortedMapDemo.java)

### LinkedHashMap ###

特殊用法：

可在构造器中设定LinkedHashMap，使之采用基于访问的**最近最少使用**(LRU)算法，于是没有被访问过的元素就会出现在队列的前面。

[LinkedHashMapDemo](LinkedHashMapDemo.java)

## Hashing and hash codes ##

[SpringDetector](SpringDetector.java)没有覆盖hashCode()和equals()，HashMap并没有按预期工作

未被覆盖Object.equals()的源码

    public boolean equals(Object obj) {
        return (this == obj);
    }

默认的是 **比较是否引用对象的地址**。

**覆盖hashCode()同时也要覆盖equals()**

正确的equals()方法必须满足下列5个条件：

1. Reflexive自反性: For any **x**, **x.equals(x)** should return **true**.
2. Symmetric对称性: For any **x** and **y**, **x.equals(y)** should return **true** if and only if **y.equals(x)** returns **true**.
3. Transitive传递性: For any **x**, **y**, and **z**, if **x.equals(y)** returns true and **y.equals(z)** returns true, then **x.equals(z)** should return true.
4. Consistent一致性: For any **x** and **y**, multiple invocations of **x.equals(y)** consistently return **true** or consistently return **false**, provided no information used in equals comparisons on the object is modified.
5. 否空性，For any non-**null** **x**, **x.equals(null)** should return false. 

[SpringDetector2](SpringDetector2.java)覆盖hashCode()和equals()后，HashMap能按预期工作

当在HashSet中使用自己的类作为键时，必须注意这个问题。

### 理解hashCode() ###

[SlowMap](SlowMap.java) 使用ArrayList实现Map

[MapEntry](MapEntry.java) 保存键值

### 为速度而散列 ###

上例使用简单的线性查询，而**线性查询是最慢的查询方式**

散列使用数组，使用它来表示键的信息（不是键的本身）

数组并不保存键本身。而是通过键对象生成一个数字(散列码)，将其作为数组的下标。散列码由Object.hashCode()方法散列函数生成。

为解决数组容量被固定的问题，不同的键可产生相同的下标。也就是说，可能会有**冲突**。

于是查询一个值的过程**首先**计算散列码，然后使用散列码查询数组。冲突由**外部链接**处理：数组并不直接保存值，而是保存值的list。

然后对list中的值使用equals()方法进行线性查询。

这部分查询自然会比较慢，但是如果散列函数好的话，数组的每个位置就只有较少的值。因此，不是查询整个list，而是快速地跳到数组的某个位置，只对很少的元素进行比较。这便是HashMap会如此快的原因。

[SimpleHashMap](SimpleHashMap.java)

散列表中的“槽位”slot通常称为桶位“bucket”

### 覆盖hashCode() ###

无论何时，对同一个对象调用hashCode()都调用hashCode()都应该生成同样的值。

不应该使hashCode()依赖于具有**唯一性**的对象信息，尤其是使用**this**的值，这只能产生很糟糕的hashCode()

[StringHashCode](StringHashCode.java)String的hashCode明显基于String的内容。

hashCode()必须基于对象的内容生成散列码。

hashCode()和equals()，必须能够完全确定对象的身份


1. 均匀性
2. 高效性
3. 一致性

---

**Jushua Bloch教你怎样写出一份像样的hashCode()给出了基本的指导**

- 给**int**变量result赋予某个非零值常量，例如17
- 为对象内每个有意义的域**f**(即每个可以做equals()操作的域)计算出一个**int**散列码c：

Field type|Calculation
---|---
boolean|c = ( f ? 0 : 1)
byte, char, short, or int|c = (int)f
long|c = (int)(f ^ (f>>>32))
float|c = Float.floatToIntBits(f);
double|long l = Double.doubleToLongBits(f);<br>c = (int)(1 ^ (l>>>32))
Object, where equals( ) calls equals( ) for this field|c = f.hashCode( )
Array|Apply above rules to each element

- 合并计算得到的散列值
result = 37 * result + c
- 返回result
- 检查hashCode()最后生成的结果，确保相同的对象有相同的散列表。

[CountedString](CountedString.java) hashCode编写实例

[Individual](../typeinformation/pets/Individual.java) hashCode编写另一实例

[IndividualTest](IndividualTest.java)

## 选择接口的不同的实现 ##

ArrayList底层由数组支持，LinkedList是由双向链表实现。

### 性能测试框架 ###

[Test](Test.java)

[TestParam](TestParam.java)

[Tester](Tester.java)

### 对List的选择 ###

[ListPerformance](ListPerformance.java)

ArrayList**随机访问**比LinkedList比快，但是在**插入**和**删除**方面就稍逊一色。

Vector是古董，现在就不要用了

固定数量元素就用**数组**或**Arrays.asList()**

Queue先进先出。

CopyOnWriteArrayList专门用并发编程。

### 微基准测试 ###

Microbenchmarking

**微基准测试**，你务必小心，不能做太多的假设，并且要将你的测试窄化，以使得它们尽可能地只在感兴趣的事项上花费精力。

还必须仔细确保你的测试运行足够长的时间，以产生有意义的数据

### 对Set的选择 ###

[SetPerformance](SetPerformance.java)

HashSet性能基本上总是比TreeSet好，特别是**添加**和**查询**

TreeSet存在的唯一原因是它可以维持元素的排序状态，因此，迭代比HashSet的快

### 对Map的选择 ###




