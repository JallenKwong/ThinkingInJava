# Concurrency #

**ThreadLocalVariableHolder** ThreadLocal运用

**Restaurant** 生产者-消费者问题

---

**线程安全**是多线程编程时的计算机程序代码中的一个概念。

在拥有共享数据的多条线程并行执行的程序中，线程安全的代码会通过同步机制保证各个线程都可以正常且正确的执行，不会出现数据污染等意外情况。

---

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






























