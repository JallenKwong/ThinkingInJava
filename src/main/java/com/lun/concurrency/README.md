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


All in all, **Every method that accesses a critical shared resource must be synchronized or it's won't right线程不安全.**


**SynchronizedEvenGenerator** synchronized 上面的EvenGenerator，使线程安全

**MutexEvenGenerator** 通过使用明确的互斥机制Java SE5的java.util.concurrent的**ReentrantLock**/rɪ'entrənt/重入锁实现线程安全


synchronized 与 Lock 对象的比较

1. 使用Lock对象在try-catch有机会finally
2. synchronized更简洁
3. Lock对象提供更细粒度的操作

**AttemptLocking** ReentrantLock的运用示例，tryLock()尝试加锁的方法运用（限时等待锁，超时了就不等）

## Atomicity and Volatility ##

**错误的认识**

**Atomic operations do not need to synchronized.**

An atomic operation is one that cannot be interrupted by thread scheduler; 

if the operation begins, then it will run to completion结束 before the possibility of a context switch.

Relying on atomicity is tricky and dangerous-**you should try to use atomicity instead of synchronization if you are a concurrency expert, or you have help from such an expert**

Trying to remove synchronization with atomicity is usually a sign of premature optimization, and will cause you a lot of trouble, probably without gaining much, anything.

---

**valitile**/ˈvɑ:lətl/关键字 作用

1. 防止**word tearing**(JVM 允许long double 64位类型 分开32位进行操作）you do get atomicity(for simple assignments and returns)if you use the **volatile** keyword when defining  a **long** or **double** variable (not the volatile was not working properly before Java SE5)

2. Visibility**(**出现场景Changes made by one task, even if they're atomic in the sense of not being interruptible, might **not be visible** to other tasks(the changes might be temporarily stored in a local processor cache, for example),so different tasks will have a different view of the application's state**)** The **volatile** keyword ensure **visibility** across the application.If you declare a field to be **volatile**, this means that as soon as a write occurs for that field, all reads will see the change.

---

**Atomicity and volatility are distinct concept.**

没必要 用**volatile**情况

1. An atomic operation on a non-volatile field will not necessarily be flushed to main memory, and so another task that reads that field will not necessarily see the new value.

2. If multiple tasks are accessing a field, that field should be **volatile**; otherwise, the field should only be accessed via **synchronization**.
**Synchronization** ALSO causes flushing to main memory, so if a field is completely guarded by **synchronized** methods or blocks, it is not necessary to make it **volatile**

3. Any writes that a task makes will be visible to that task, so you **don't** need to make field **volatile** if it is only seen within a task.

4. **volatile** doesn't work when the value of a field depends on its previous value(such as incrementing a counter), nor does it work on fields whose values are constrained by the values of other fields, such as the **lower** and **upper** bound if a **Range** class which must obey the constraint **lower <= upper**.这条想不明白，不是自己通过业务逻辑限制吗？

---
**建议**

It's typical only safe to use **volatile** instead of **synchronized** if the class has only one mutable field.

>Again, you first choice should be to use the **synchronized** keyword-that's the safest approach, and trying to do anything else is risky

---

	i++;
	i+=3;

上述操作在Java中没有原子性atomic（C++的话就可能有，要根据不同的平台）

---

### 书籍上的示例 ###

**AtomicityTest** 原子性操作并不保证线程安全的示例

**SerialNumberGenerator**，
**SerialNumberChecker** 原子性操作并不保证线程安全的示例又一例

---

重申：

**Java increment is not atomic** and involves both a read and a write, so there's room for threading problems even in such a simple operation.

---

volatile的功能增记

**volatile** is possible for each thread to have a local stack and maintain copies of some variables there.

If you define a variable as **volatile** , it tells the compiler **not to do any optimizations** that would reads and writes that keep the field in exact synchronization with the local data in the threads.**In effect, reads and writes go directly to memory, and are not cached.**没缓存

**volatile** also restrict compiler **reordering** of accesses 重排 during optimization

However, **volatile** doesn't affect the fact that an increment isn't an **atomic** operation.

一个volatile用法 

Basically, you should make a field **volatile** if that field could be simultaneously accessed by multiple task, and at least one of those accesses is a write.

一个volatile的应用示例——**多线程终止flag**

For example, a field that is used as a flag to stop a task must be declared **volatile**; otherwise, that flag could be cached in a regiter, and when you make changes to the flag from outside the task, the cached value wouldn't be changed and the task wouldn't know it should stop.

---

### 小结 ###

1. 原子操作不可中断
2. Java中自增，自减等类操作不是原子操作
3. 不要用atomicity代替synchronized,除非你是专家或者你从专家得到帮助。

### 关键字volatile小结 ###

1. 针对long, double 的word tearing情况；
2. visiblity(no cached)(多个任务，同一变量(任务都需要的这变量情况下)(volatile修饰的情况下))(用例**多线程终止flag**)；
3. 告诉编译器，被**volatile**修饰的成员变量不作优化，如指令重排（涉及 流水线方面）；
4. **volatile**不影响“Java中自增，自减等类操作不是原子操作” 的事实。

## Atomic classes ##

有**AtomicInteger**, **AtomicLong**, **AtomicReference**

这些类的操作（如自增）能保证是原子操作，

**AtomicIntegerTest**

**AtomicEvenGenerator**

The atomic operation that are supposed to be safe are the reading and assignment of primitives. However, it's still easily **possible** to use an atomic operation that accesses your object while it's in an stable intermediate state.

## Critical section ##

Sometimes, you only want to prevent multiple thread access to part of the code inside a method instead of the entire method.

The section of code you want to isolate this way is called a **Critical section临界区** and is created using the **synchronized** keyword.

Here, **synchronized** is used to specify the object whose lock is being used to **synchronized** the enclosed code.

	synchronized(syncObject){
		//This code can be accessed 
		//by only one task at a time
	}

This is also called a **synchronized block同步块**；before it can be entered, the lock must be acquired on syncObject.If some other task already has this lock, then critical section cannot be entered until the lock is released.

**CriticalSection** synchronized 方法和synchronized block 用例

## Synchronizing on other objects ##

**SyncObject** 同步不同对象

## Thread local storage ##

**A second way** to prevent tasks form colliding over shared resources is eliminate消除 the sharing of variables.

**Thread local storage** is a mechanism that automatically create different storage for the same variable, for each different therad that use an object.

Thus, if you have five thread using an object with variable **x**, thread local storage generate five diierent pieces of storage for **x**.

**ThreadLocalVariableHolder** ThreadLocal的用法

# Terminating tasks #

通过 boolean cancle来设计线程结束标志，但是，这方法有可能不能立即终结线程

**OrnamentalGarden**通过 boolean cancle来设计线程结束标志的示例

---
**线程开发中的坑**

In real threading problems, the possibility for failure may be statistically small, so you can easily fall into the trap of believe that things are working correctly 

---
## Thread State ##


- NEW

Thread state for a thread which has not yet started.

  
- RUNNABLE

Thread state for a runnable thread. A thread in the runnable state is executing in the Java virtual machine but it may be waiting for other resources from the operating system such as processor.

  
- BLOCKED

Thread state for a thread blocked waiting for a monitor lock. A thread in the blocked state is waiting for a monitor lock to enter a synchronized block/method or reenter a synchronized block/method after calling Object.wait.

  
- WAITING

Thread state for a waiting thread. A thread is in the waiting state due to calling one of the following methods: •Object.wait with no timeout
•Thread.join with no timeout
•LockSupport.park

A thread in the waiting state is waiting for another thread to perform a particular action. For example, a thread that has called Object.wait() on an object is waiting for another thread to call Object.notify() or Object.notifyAll() on that object. A thread that has called Thread.join() is waiting for a specified thread to terminate.

---
- TIMED_WAITING

Thread state for a waiting thread with a specified waiting time. A thread is in the timed waiting state due to calling one of the following methods with a specified positive waiting time:

Thread.sleep

Object.wait with timeout

Thread.join with timeout

LockSupport.parkNanos

LockSupport.parkUntil

---
- TERMINATED

Thread state for a terminated thread. The thread has completed execution.


---
被废弃的方法

suspend(),resume()//(易死锁)

stop()//获得锁的线程用这stop(),锁将获取不得

---

The problem we need to look at now is this: **Somethings you want to terminate a task that is in a blocked state.** If you can't wait for it to get to a point in the code where it can check a state value and decide to terminate on its own, you have to force the task out of its blocked state.


## Interruption ##

**Interrupting** Executors的中断方法cancel，以及对sleep，IO，Synchronized的反应

Interrupting中**Sleep**Block is an example of **interruptible** blocking, whereas **IO**Blocked and **Synchronized**Blocked are **uninterruptible**

- 可中断:sleep 

- 不可中断:IO，Synchronized

**CloseResource** A heavy-handed but sometimes effective solution to this problem is to close the underlying resource on which the task is blocked 关闭资源，间接中断线程

**NIOInterruption** The **nio** classes for more civilized interruption of I/O. Blocked **nio** channels **automatically** respond interrupt.

NIO有文明的方法响应中断。

## Blocked by a mutex ##

**MultiLock** synchronized 方法 套用 同对象的synchronized 方法，因为同对象，锁也一致，被套用的synchronized 方法不会发生阻塞

This makes sense because one task should be able to call other synchronized methods within the same object; that task already holds the lock.

**Interrupting2** One of the features added in the Java SE5 concurrency libraries is the ability for task blocked on **ReentrantLocks** to be interrupted, unlike tasks blocked on **synchronized** methods or critical sections.

## Checking for an interrupt ##

**InterruptingIdiom** The  example shows the typical idiom that you should use in **run()** method to handle both blocked and non-blocked possibilities when the interrupted status is set

多重try-finally 且 Thread.interrupted()的应用

# Cooperation between task #

When you use threads to run more than one task at a time, you can keep one task from interfering with another task's resources by using a **lock**(**mutex**) to synchronize the behavior of the two tasks. If two tasks are stepping on each other over a shared resource(usually memory), you use **mutex** to allow only one task at a time to access that resource.


任务顺利合作的思路

The key issue when tasks are cooperating is handshaking between those tasks. To accomplish this handshaking, we use the same foundation: the **mutex**, which in this case guarantee that only one task can respond to signal.This eliminates any possible race conditions. On top of the mutex, we add a way for a task to suspend itself until **some external state** change, indicating that it's time for that task to move forward.

关键

**Object**的wait(),notifyAll()
Java SE5的**Condition**的await(),signal()

## wait() and notifyAll() ##

wait() allows you to wait for a change **in some condition** that is outside the control of the force in the current method.

Often, this condition will be changed by another task.

You don't want to idly loop while testing the condition inside your task;this is called **busy waiting**, and it's usually a use of CPU cycles.

任务合作基本思路
wait() 与 notify() 或 notifyAll()成双对

So **wait()** suspends the task while waiting for the world to change, and only when a **notify()** or **notifyAll()** occurs-suggesting that something of interesting of interest may have happened-does the task wake up and check for change.Thus, **wait()** provides a way to synchronized activities between tasks.

---


It's important to understand that **sleep()** does not release the object lock when it's called, and neither does **yield()**

sleep(),yield()并不释放锁

wait()功效
When a task enters a call to **wait()** inside a method, that thread's execution is suspended, and the lock on that object is released.

Because **wait()** release the lock,it means that the lock can be acquired by another task, so **synchronized** methods in the (unlocked) object can be called during a wait().

This is essential, because those other methods are typical what cause the change that makes it interesting for the suspended task to reawaken.

wait()拟人化意图
When you call **wait()**, you're saying, **"I've done all I can right now, so I'm going to wait right here, but I want to allow other synchronized operations to take place if they can."**

---
wait()有分 无参 和 有参（ms）

---
wait(),notify(),notifyAll 必须 放置 **synchronized** 方法 或 块内

否则，虽能通过编译，但是不能够运行，运行抛出异常**IllegalMonitorStateException**

---

**WaxOMatic** 运用wait() and notifyAll()进行任务合作的示例

---

在上面例子当中

重点:为什么wait要用while循环包围着

The previous example emphasizes that you must surround a **wait()** with a **while** loop that checks the conditions of interest.

This is important because:

1. You may have multiple tasks waiting on the same reason, and the first tasks that wakes up might change the situation(even if you don't do this someone might inherir from you class and do it). If that is the case, this task should be suspended again until its condition of interest changes.

2. By the time this task awakens from its **wait()**, it's possible that some other task will have changed things such that this task is unable to perform or is uninteresting in performing its operation at this time. Again, it should be resuspended by calling **wait()** again.

3. It's also possible that tasks could be waiting on your object's lock for different reason (in which case you must use **notifyAll()**).In this case, you need to check whether you're been woken up for the right reason, and if not, call **wait()** again.

Thus, it's essential that you check for you particular condition of interest, and go back into **wait()** if that condition is not met.This is idiomatically惯用地 written using a **while**.

























