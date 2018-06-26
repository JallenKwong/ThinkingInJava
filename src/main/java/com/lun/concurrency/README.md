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