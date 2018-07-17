# Concurrency #

[Basic threading](#basic-threading)

[Sharing resources](#sharing-resources)

[Terminating tasks](#terminating-tasks)

[Cooperation between tasks](#cooperation-between-tasks)

[Deadlock](#deadlock)

[New Library components](#new-library-components)

[Simulation](#simulation)

[Performance tuning](#performance-tuning)

[Active objects](#active-objects)

[Summary](#summary)

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

# Cooperation between tasks #

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

另一个缘由：消失的信号

	T1:
	synchronized(sharedMonitor){
		<setup condition for T2>
		sharedMonitor.notify();
	}

	T2:
	while(someCondition){
		//Point 1
		synchronized(sharedMonitor){
			sharedMonitor.wait();
		} 
	}

问题出现过程：

T2 someCondition is true 

然后 Java调度器调到T1
	condition设为false
	sharedMonitor.notify()

然后 调到 T2
	sharedMonitor.wait()

然后就死锁了

解决之道

	synchronized(sharedMonitor){
		while(someCondition)
			sharedMonitor.wait();
	}

## notify() & notifyAll() ##

notifyAll()比notify()更安全，notify()是notifyAll()另一种优化，但要确定唤醒有且仅有一个特定的任务

**NotifyVsNotifyAll** notify()和notifyAll()


## 生产者与消费者 ##

**Restaurant** 生产者和消费者的实例

### 使用显式的Lock和Condition对象 ###

**WaxOMatic2** 使用显式的Lock和Condition对象修改先前的示例WaxOMatic2 (又线程间的合作)

synchronized - Lock

Object.wait() - Condition.await()

Object.notify() - Condition.signal()

Object.notifyAll() - Condition.signalAll()

这样映射可以让线程同步 更加符合 面向对象编程

## 生产者-消费者 与 队列 ##

wait() 和 notifyAll()方法以一种非常低级的方式解决了任务互相操作问题，即每次交互都需要握手。

在许多情况下，你使用高级别的**同步队列**java.util.concurrency.BlockingQueue解决协作任务，这队列每时每刻都只允许一个任务插入或移除元素。

阻塞队列比notifyAll()、wait()比，简单高效

**TestBlockingQueues** 阻塞队列们的基本使用

**ToastOMatic** 阻塞队列来弄成生产者-消费者，还有使用Enum的优秀示例

## 任务间使用管道进行输入、输出 ##

**PipedIO** PipedReader允许任务向管道写/PipedWriter允许不同任务从同一个管道中读取

# DeadLock #

什么是死锁？

某种任务在等另一个任务，而后者又等待别的任务，这样一直下去，直到这个**链条**上的任务又在等待第一个释放锁。 这得到了一个任务之间相互等待的连续循环，没有哪一个线程能继续。这称之为 **死锁**。

由Edsger Dijkstra提出的**哲学家就餐**问题是一个经典的死锁例证。

**Chopstick**

**Philosopher**

**DeadlockingDiningPhilosophers** 哲学家就餐(死锁示例)

**要解决死锁问题，要必须明白以下四个条件同时满足，就会发生死锁**： 

1. **互斥条件**。任务中使用的资源至少有一个是不能共享。这里，一根**Chopstick**一次只能被一个**Philosopher**使用。

2. 至少有一个任务它必须持有一个资源且正在等待获取一个当前被别的任务持有的资源，这里**Philosopher**必须拿着一根**Chopstick**并且等待另一根。

3. 资源不能被任务抢占，任务必须把资源释放当作普通事件。**Philosopher**是绅士，不抢东西

4. 必须有**循环等待**，这时，一个任务等待其他任务所持有的资源，后者又在等待另一个任务所持有的资源，这样一直下去，直到有一个任务在等待第一个任务所持有的资源，使得大家被锁住。在**DeadlockingDiningPhilosophers**中，因为每个**Philosopher**都试图先得到右边的**Chopstick**,然后得到左边的，发生循环等待。

**FixedDiningPhilosophers** 解决哲学家就餐(死锁示例)卡死问题，破坏第四条，最后一位哲学家先左后右拿**Chopstick**

# java.util.concurrency的新构件 #

## CountDownLatch倒数闩 ##

This is used to synchronize one or more tasks by forcing them to wait for the completion of set of operations being performed by other tasks

- CountDownLatch设置初始构造数字initNum，如100

- latch.await()的任务将会阻塞，initNum为0开始后启动
- latch.countDown() initNum--;

---

CountDownLatch的典型用法：

将任务分成n份，创建n值的CountDownLatch

当一个子任务完成，调用countDown() 闩 的 initNum--

主任务调用await()进行阻塞，当子任务都完成了，主任务将继续


**CountDownLatchDemo** CountDownLatch的用例

---

例子中 线程频繁使用 random.nextInt()引出**类库是否线性安全**的问题

JDK文档并没有指出random.nextInt()是线性安全的

只能通过源码阅读，搜索引擎进行查证

这里了碰巧random.nextInt()是线性安全的

## CyclicBarrier循环栅 ##

CyclicBarrier适用于的情况：

**你希望创建一组任务，它并行地执行工作，然后在进行下一个步骤之前等待，直至所有任务都完成（看起来有点像join()）。**

它使得所有的并行任务都将在栅栏处队列，因此可以一致地向前移动。

这非常像CountDownLatch，只是CountDownLatch是只触发一次的事件，而CyclicBarrier可以多次使用。

**HorseRace** CyclicBarrier简单示例


	T1---->|-------->|
	       |         |。。。。。。
	T2-->  |  ------>|
	       CB        CB

## DelayQueue延迟队列 ##

DelayQueue是一个无界的BlockingQueue，用于放置实现了Delayed接口的对象，其中的**对象只能在其到期时才能从队列中取走**。

这种队列是有序的，即队头对象的延迟到期时间最长。如果没有任何延期到期，那么就不会有任何头元素，并且poll()将返回null(null不能放置队列)

**DelayQueueDemo**  DelayQueue演示示例

## PriorityBlockingQueue ##

它是一个很基础的优先队列，它具有可阻塞的读取的操作。

**PriorityBlockingQueueDemo** 优先阻塞队列 的 生产者-消费者

## 使用ScheduledExecutor的温室控制器 ##

**ScheduledThreadPoolExecutor**提供了使用schedule()(运行一次任务)运行一次任务或者scheduleAtFixedRate()(每隔规则的事件重复执行任务)，**你可以将Runnable对象设置为在将来的某个时刻执行**。

**GreenhouseScheduler** ScheduledThreadPoolExecutor的运用示例


## Semaphore 信号量 ##

正常的锁(concurrency.locks或synchronized锁)在任何时刻都**只允许一个任务访问一项资源**，而 **计数信号量** 允许**n个任务**同时访问这个资源。

**Fat** 费时操作

**Pool** 对象池 它管理着数量有限的对象，当要使用对象时可以签出它们，而用户使用时，可以将它们签回 Semaphore.acquire()，Semaphore.release()是关键

**SemaphoreDemo** 


## Exchanger ##

**Exchanger**是在两个任务之间交换对象的栅栏。

当这些任务进入栅栏时，它们各自拥有一个对象，当它们离开时，它们都拥有之前有对象持有的对象。

**Exchanger**的典型应用场景是：一个任务在创建对象，这些对象的生产代价和高昂，而另一个任务在消费这些对象。通过这种方式，可以有更多的对象在被创建的同时被消费。

**ExchangerDemo** 用Exchanger实现消费者-生产者

# Simulation #

**并发最有趣也是最令人兴奋的用法就是创建仿真**

通过使用并发，仿真的每个构建都可以成为其自身的任务，这使得仿真更容易编程。

**BankTellerSimulation** 银行仿真

**RestaurantWithQueues** 饭店仿真

**CarBuilder** 汽车组装

# Performance tuning #

**SimpleMicroBenchmark** 简单的Lock和synchronized的性能测试

上面的例子有两个主要问题

1. 我们只有在这些互斥存在竞争的情况下，才能看到真正的性能差异，因此必须有多个任务尝试着访问互斥代码区。而在上面的示例中，每个互斥都是由单个main()线程在隔离的情况下测试。

2. 编译器看到synchronized关键字，有可能会执行特殊的优化。


目标测试：1.多任务，2.计算足够的复杂且不可预测

**SynchronizationComparisons** 更完美的 Java同步功能测试

**记住**，这个程序只给出了各种互斥方式之间的差异的趋势，而上面的输出也仅仅表示这些差异在我的特定环境下的特定机器上的表现。

Lock，Atomic比synchronized高效许多

synchronized根据可读性

建议，synchronized关键字入手，只有性能方面调优时才替换为Lock对象。

Atomic对象只有在非常简单的情况下才有用

更安全的做法是：以更加传统的互斥方式入手，只有在性能方面的需求能够明确指示时，再替换为Atomic


## 免锁容器 ##

容器是所有编程中的基础工具

Vector和Hashtable这类早期容器具有许多synchronized方法，用于线程环境下会造成不必要的开销。


Java 1.2中，新的容器类库是不同步的，并且Collections类提供了各种static的同步的装饰方法，从而来同步不同类型的容器。

尽管这是一种改进，因为它使你可以选择在你的容器中是否使用同步，但是这种开销仍旧基于synchronized加锁机制的。

Java SE5特别添加了新的容器，通过使用更灵巧的计数消除加锁，从而提高线程的性能。

**这些免锁容器背后的通用策略是**：对容器的修改可以与读取操作同时发生，只要读取者只能看到完成修改的结果即可。修改是在容器数据结构的某个部分的一个单独的副本（有时是整个数据结构的副本）上执行，并且这个副本在修改过程中是不可视的。只有当修改完成时，被修改的结构才会自动地与主数据结构进行交换，之后读取者就可以看到这个修改了。

在**CopyOnWriteArrayList**中，写入将导致创建整个底层数组的副本，而源数组将保存在原地，使得复制的数组在被修改时，读取操作可安全执行。

当修改完成时，一个原子性的操作将把新的数组换入，使得新的读取操作可以看到新的修改。

**CopyOnWriteArrayList**的好处之一当多个迭代器同时遍历和修改这个列表时，不会抛出**ConcurrentModificationException**，因此没必要编写特殊的代码去防范这种异常，就像你以前必须作的那样。

**CopyOnWriteArraySet**将使用**CopyOnWriteArrayList**来实现其免锁行为

**ConcurrentHashMap**和**ConcurrentLinkedQueue**使用了类似的技术，允许并发的读取和写入，但是容器中只有部分内容而不是整个容器可以被复制和修改。

然而，任何修改在完成之前，读取者仍旧不能看到它们。**ConcurrentHashMap**不会**ConcurrentModificationException**。

## 乐观锁 ##

只要你主要是从免锁容器中读取，那么它就会比其synchronized对应物快许多，因为获取和释放锁的开销被省掉了。如果需要免锁容器中执行少量写入，那么情况仍旧如此。 多少才算 “少量”。

**Tester** 测试框架

**ListComparisons** CopyOnWriteArray和Collections.synchronizedList的读写性能比

从输出中可以看到，synchronized ArrayList无论读取者和写入者相同。但是，CopyOnWriteArrayList在没有写入者时，速度会快许多，并且在有5个写入者，速度仍旧明显地快。

看起来你应该尽量使用**CopyOnWriteArrayList**，对列表写入的影响并没有超过短期同步整个列表的影响。

当然，你必须在你的具体应用中尝试这两种不同的方式，以了解到底哪个更好一些。

**MapComparisons** synchronizedHashMap和ConcurrentHashMap在性能方面的比较结果。

## 乐观加锁 Atomic 代替 synchronized 或 Lock##

尽管Atomic对象将执行像decrementAndGet这样的原子性操作，但是某些Atomic还允许你执行所谓的“**乐观加锁**”

这意味着当你执行某项计算时，**实际上没有使用互斥**，但是在这项计算完成，并且你准备更新这个Atomic对象时，你需要使用一个称谓compareAndSet()方法。

你将旧值和新值一起提交给这个方法，**如果旧值与它在Atomic对象中发现的值不一致，那么这个操作就失败**——这意味着某个其他的任务已经于此操作执行期间修改了这个对象。

记住，我在正常情况下将使用互斥（synchronized或Lock）来防止多个任务同时修改一个对象，但是这里我们是“**乐观的**”，因为我们保持数据未锁定状态，并希望没有任何其他任务插入修改它。

所有这些又都是以性能的名义执行的——通过使用Atomic来代替synchronized或Lock，可以获得性能上的好处。

---

如果compareAndSet()操作失败会发生什么？

这正是棘手的地方，也是你在应用这项技术时的受限之处，即只能对能够吻合这些需求的问题。

如果compareAndSet()失败，那么就必须决定做些什么，这是一个非常重要的问题，因为如果不能执行某些恢复操作，那么你就不能使用这项技术，从而必须使用传统的互斥。**你可能会重试这个操作，如果第二次成功，那么万事大吉；或者可能会忽略这次失败，直接结束——在某些仿真中**，如果数据点丢失，在重要的框架中，这就是最终需要做的事情（当然，**你必须很好地理解你的模型，以了解情况是否确定如此**）。

**FastSimulation** 遗传算法，乐观加锁示例，Atomic代替synchronized或Lock

## ReadWriteLock ##

**ReadWirteLock**对向数据结构相对不频繁地写入，但是有多个任务要经常读取这个数据结构的这类情况进行优化。

**ReadWriteLock**使得你可以同时有多个读取者，只要它们都不试图写入即可。如果写锁已经被其它任务持有，那么任何读取者都能访问，直至这和写锁被释放为止。

**ReadWirteLock**是否能够提高程序的性能是完全不可确定的。

**ReadWirteLock**是否能够给你的程序带来好处的方式就用试验来证明。

**ReaderWriterList** ReadWirteLock的读写示例


# Active objects #

上述的线程较复杂和难以使用

另一种 替代方案 **活动对象或行动者** 。

之所以称这些对象时“活动的”，是因为每个对象都维护着它自己的工作器下线程和消息队列，并且所有对这种对象的请求都将进入队列排队，任何时候都只能运行其中的一个。

因此，有了活动对象，我们就**可以串行化消息而不是方法，这意味着不再需要防备一个任务在其循环的中间被中断这种问题**。

当你向一个对象发送消息时，这个消息会转变为一个任务，该任务会被插入到这个对象的队列，等待以后的某个时刻运行。Java SE5的**Future**将会派上用场

**ActiveObjectDemo** 用Future的功能来实现活动对象

上面例子编写较麻烦，希望以后能有编译器直接支持。

# Summary #

使用Java线程进行并发程序设计的基础设计：

1. 可以运行多个独立的任务。
2. 必须考虑当这些任务关闭时，可能出现的所有问题。
3. 任务可能会在共享资源上彼此干涉。互斥（锁）是用来防止这种冲突的基本工具。
4. 若任务设计得不够仔细，就可能会死锁。

使用Java线程原因：

1. 要处理很多任务，它们交织在一起，应用并发能够更有效地使用计算机（包括多个CPU上透明地分配任务的能力）
2. 能够更好地组织代码
3. 更方便用户使用

线程间的切换比进程间的切换更高效

多线程的主要缺陷：

1. 等待共享资源的时候的性能降低
2. 需要处理线程的额外CPU花费
3. 糟糕的程序设计导致不必要的复杂度。
4. 有可能产生一些病态行为，如饿死、竞争、死锁、活锁（多个运行各自任务的线程使得整体无法完成）
5. 不同平台导致的不一致性。比如，竞争条件在某些机器上很快出现，但在别的机器上根本不出现。如果你在后一种机器上做开发，那么当你发布程序的时候就会大吃一惊

因为多个线程可能共享一个资源，比如一个对象的内存，而且你必须确定多个线程不会同时读取和改变这个资源，这就是线程产生的最大难题。

这需要明智地使用可用的加锁机制（例如synchronized关键字），它仅仅是个工具，同时它们会引入潜在的死锁条件，所以要



注意一个机器能跑多少个线程，不能跑超多

# 下一个 目标 #

《Java Concurrency in Practice》











