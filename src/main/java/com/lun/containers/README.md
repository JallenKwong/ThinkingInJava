# Containers in Depth #

## Full container taxonomy ##

![](image/full-container-taxonomy.png)

Java SE5新添加的：

- Queue接口及其实现PriorityQueue和各种风格BlockingQueue
- ConcurrentMap接口及其实现ConcurrentHashMap
- CopyOnWriteArrayList、CopyOnWriteArraySet用于并发
- EnumSet、EnumMap

## Filling containers ##

Collections.fill()只能针对List，且复制单个对象的引用

[FillingLists](FillingLists.java)

	Object.toString() <=> getClass().getName() + '@' + Integer.toHexString(hashCode())















