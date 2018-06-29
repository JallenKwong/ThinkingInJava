# Concurrency #

**ThreadLocalVariableHolder** ThreadLocal运用

**Restaurant** 生产者-消费者问题

---

**线程安全**是多线程编程时的计算机程序代码中的一个概念。

在拥有共享数据的多条线程并行执行的程序中，线程安全的代码会通过同步机制保证各个线程都可以正常且正确的执行，不会出现数据污染等意外情况。

---

# Basic threading #


	Thread.yield();

This is static method is suggestion to the **thread scheduler**(the part of the Java threading mechanism that moves the CPU from one thread to the next)that says, "I've done the important parts of my cycle and this would be a good time to switch to another task for a while." It's completely optional.

---

## 创建线程 ##

**LiftOff** 实现Runnable接口，创建线程

**BasicThreads** 实现Thread类，创建线程

**MoreBasicThreads** 实现Thread类，创建多线程

## Executor 创建线程 ##

Executors **/ɪɡˈzɛkjətɚ/** are the prederred method for starting tasks in Java SE5/6

**CachedThreadPool** 创建线程池

**FixedThreadPool** 固定数量的线程池

A **CachedThreadPool** will generally create as many threads as it needs during the execution of a program and then will stop creating new threads as it recycles the old ones, so it's reasonable first choice as an Executor. Only if this approach causes problems do you need to switch to a **FixThreadPool**.

**SingleThreadExecutor** is like a FixedThreadPool with a size of one thread.只有一个线程的线程池

If more than one task is submitted to a **SingleThreadExecutor**, the tasks will be queued and each task will run to completion结束 before the next task is begun, all using the same thread。

## Producing return values from tasks --- Callable ##

**CallableDemo** 有返回值的任务，需要实现Callable而非Runable

## Sleep ##

A simple way to affect the behavior of your task is by calling sleep() to cease停止(block)the execution of that task for a given time.

**SleepingTask** sleep()阻塞线程一会儿

## Priority ##

**SimplePriorities** 设置线程优先级

线程优先级越高，运行的机会就越多，但优先级低的就 没有运行机会，否则会造成死锁的问题。

## Yield /jild/ ##

If you konw that you've accomplished what you need to during one pass through a loop in your run() method, you can give a **hint** to the thread scheduling mechanism that you've done enough and that some other task might as well have the CPU.

The hint(and it's a hint -- there's **no guarantee** your implementation will listen to it) tasks the form of the yield() method. When you call yield(), you are suggesting that other threads of same priority.

## Daemon thread ##

A "daemon" thread is intended to provide a general service in background as long as the program is running, but it's not part of the essence of program.

**SimpleDaemons** 将线程设置为守护线程，主线程运行完成，守护线程也会随之终止

**DaemonThreadFactory** 实现ThreadFactory接口的守护线程工厂

**DaemonFromFactory** Executor 通过**DaemonThreadFactory** 工厂借口创建守护线程

**DaemonThreadPoolExecutor** 更高一级的 守护线程创造的方法 通过继承ThreadPoolExecutor()

**Daemons** 守护线程里执行任务中所创建的线程也是守护线程（龙生龙，凤生凤）

**DaemonsDontRunFinally** Daemon 与 finally擦不出火花

Daemon线程小结：

1. 树倒猢狲散
2. 龙生龙，凤生凤
3. 与finally的羁绊

## Coding variations 花式创建并启动线程 ##

**SimpleThread** 继承Thread类创建线程后，构造方法立即启动

**SelfManaged** 类似上例子，用Runnable接口

**ThreadVariations** 内部类与Runable或Thread碰撞

## Terminology术语 -- 对线程误解 ##

Thread 与 Task 

**正解**：

**You create tasks and somehow attach a thread to your tasks so that the thread will drive that task.**

In Java, the **Thread** class by itself does nothing.It drives the task that it's given.

误解：

Thread 与 Task  傻傻分不清楚

Yet threading literature invariably uses language like "the thread performs this or that action". The impression that you get is that **the thread is the task(wrong)**, and when I first encountered Java threads, this impression was so strong that I saw a clear "is-a" relationship, which said to me that I should obviously inherit a task from a **Thread**. 

建议：

Add to this the poor choice of name for the **Runnable** interface， which I think would have beem much better named **Task**.

If the interface is clearly nothing more than a generic encapsulation of its methods, then the "it-does-this-thing-able" naming approach is appropriate, but if it intends to express a higher concept, like **Task**, then the concept name is more helpful.

## Joining a thread ##

One thread may call **join()** on another thread to wait for the second thread to comnplete before proceeding.

主要用途

If a thread call **t.join()** on another thread **t**, then calling thread is suspended until the target thread **t** finishes(when **t.is Alive()** is **false**)

**Joining** join()用途,等其他线程跑完,自己接着跑

**Attention**:

When another thread calls **interrupt()** on this thread, a flag is set to indicate that the thread calls **interrupt()** on this thread, a flag is cleared when the exception is caught, so result will always be **false** inside the **catch** clase.

## Create responsive user interface ##

**ResponsiveUI** 一个创建守护线程的用户界面例子

## Thread Groups ##

The value of thread groups can be summed up by quote from Joshua Bloch:

> Thread groups are best viewed as an unsuccessful experiment, and you may simply ignore their existence.

## Catching exceptions ##

Because of the nature of thread, you **can't** catch an exception that has escaped from a thread. 

Once an exception gets outside of a task's **run()** method, it will propagate out to the console unless you take **special steps** to capture such errant exceptions.

**ExceptionThread** 一个简单线程抛异常的例子

**NaiveExceptionHandling** try-catch并不能捕获从线程抛出的异常

**CaptureUncaughtException** 通过实现Thread.UncaughtExceptionHandler接口方法来捕获线程抛出来的异常

**SettingDefaultHandler** 全局设置UncaughtExceptionHandler

# Sharing resources #

太公分猪肉，人人有份

## Improperly accessing resources 不当访问资源 ##

线程不安全的事例

**IntGenerator**

**EvenChecker**

**EvenGenerator**

得不出奇数的结果

术语

**atomic原子性**，which means that simple operations like assignment and value return happen without the possibility.

**volatile易变的** 修饰成员变量，确保可见性，不使用缓存机制

**race condition竞争条件** where two or more tasks race to respond回应 to a condition and thus collide冲突 or otherwise produce inconsistent不一致 results.

**Java的 自增并不是原子操作**

It's important to note that the increment operation itself requires multiple steps, and the task can be suspended 悬挂 by the threading mechanism in the midst of an increment-that is, **increment is not an atomic operation in Java**. So even a single increment isn't safe to do without protecting the task.

## Resolving shared resource contention竞争 ##

To solve the problem of thread collision, virtually 实际上 all concurrency schemes 计划 **serialize** 连载，序列化 access to shared resources.

This means that only one task at a time is allowed to access the shared resources.

处理法
This is ordinary accomplished by putting a clause around a piece of code that only allows one task at a time to pass through that piece of code.

Because this clause produces **mutual exclusion相互排斥**, a common name for such a mechanism is **mutex互斥**

**synchronized**

To prevent collisions over resources, Java has built-in support in the form of the **synchronized** keyword.

When a task wishes to execute a piece of code guarded by the **sychronized** keyword, it checks to see if the lock is available, then acquires it, executes the code, and releases it.

大概用法思路 **一个萝卜一个坑**

To control access to a shared resource, you first put it inside an object. Then any method that uses the resource can be made **synchronized**.

If a task is in a call to one of the **synchronized** methods, all other tasks are blocks from entering any of the synchronized methods of that object until the first task return from its call.

更详细例子

1. In production code, you're already seen that you should make the data elements of a class **private** and access that memory only through methods.

2. You can prevent collision by declaring those methods **synchronized**, :

---

	synchronized void f(){...}
	synchronized void g(){...}

All objects automatically contain a single **lock** (also referred to as a **monitor**).

When you call any **synchronized** method, that object is locked and no other **synchronized** method of that object can be called until the first one finishes and releases the lock.

For the preceding methods, if **f()** is called for an object by one task , a different task cannot call **f()** or **g()** for the same object until **f()** is completed and release the lock. 

Thus, there is a single lock that is shared by all the **synchronized** methods of a particular object, and this lock can be used to prevent object memory from being written by more than one task at a time.

关于静态synchronized方法

There's also a single lock per class(as part of the Class Object for the class), so that **synchronized static** methods can lock each other out from simultaneous同时发生的 access of **static** data on a class-wide basis.

**什么时候用synchronized?**

*Java concurrency in practice* 的作者作出的解释

> If you are **writing** a variable that might next be **read** by another thread, or **reading** a variable that might have last been **written** by another thread, you must use synchronization, and further, **both the reader and the writer must synchronize using the same monitor lock.**


All in all, **Every method that accesses a critical shared resource must be synchronized or it's won't right.**












