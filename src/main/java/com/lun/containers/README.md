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



























