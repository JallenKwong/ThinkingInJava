package com.lun.concurrency;

//: concurrency/DaemonFromFactory.java
// Using a Thread Factory to create daemons.
import java.util.concurrent.*;

import com.lun.concurrency.util.DaemonThreadFactory;

public class DaemonFromFactory implements Runnable {
	public void run() {
		try {
			while (true) {
				TimeUnit.MILLISECONDS.sleep(100);
				System.out.println(Thread.currentThread() + " " + this);
			}
		} catch (InterruptedException e) {
			System.out.println("Interrupted");
		}
	}

	public static void main(String[] args) throws Exception {
		ExecutorService exec = 
				//实现ThreadFactory接口的类用法
				Executors.newCachedThreadPool(new DaemonThreadFactory());
		for (int i = 0; i < 10; i++)
			//创建 守护进程 
			exec.execute(new DaemonFromFactory());
		System.out.println("All daemons started");
		TimeUnit.MILLISECONDS.sleep(500); // Run for a while
		exec.shutdown();
		System.out.println("The End");
	}
} 
/* 
All daemons started
Thread[Thread-2,5,main] com.lun.concurrency.DaemonFromFactory@18e57971
Thread[Thread-4,5,main] com.lun.concurrency.DaemonFromFactory@31c115a1
Thread[Thread-6,5,main] com.lun.concurrency.DaemonFromFactory@efe5d07
Thread[Thread-8,5,main] com.lun.concurrency.DaemonFromFactory@3d1fb399
Thread[Thread-0,5,main] com.lun.concurrency.DaemonFromFactory@1f7111f1
Thread[Thread-1,5,main] com.lun.concurrency.DaemonFromFactory@3c64e78d
Thread[Thread-3,5,main] com.lun.concurrency.DaemonFromFactory@3986e8ee
Thread[Thread-5,5,main] com.lun.concurrency.DaemonFromFactory@2002c1e3
Thread[Thread-7,5,main] com.lun.concurrency.DaemonFromFactory@12fc91a2
Thread[Thread-9,5,main] com.lun.concurrency.DaemonFromFactory@29289162
Thread[Thread-1,5,main] com.lun.concurrency.DaemonFromFactory@3c64e78d
Thread[Thread-3,5,main] com.lun.concurrency.DaemonFromFactory@3986e8ee
Thread[Thread-5,5,main] com.lun.concurrency.DaemonFromFactory@2002c1e3
Thread[Thread-7,5,main] com.lun.concurrency.DaemonFromFactory@12fc91a2
Thread[Thread-9,5,main] com.lun.concurrency.DaemonFromFactory@29289162
Thread[Thread-2,5,main] com.lun.concurrency.DaemonFromFactory@18e57971
Thread[Thread-4,5,main] com.lun.concurrency.DaemonFromFactory@31c115a1
Thread[Thread-6,5,main] com.lun.concurrency.DaemonFromFactory@efe5d07
Thread[Thread-8,5,main] com.lun.concurrency.DaemonFromFactory@3d1fb399
Thread[Thread-0,5,main] com.lun.concurrency.DaemonFromFactory@1f7111f1
Thread[Thread-1,5,main] com.lun.concurrency.DaemonFromFactory@3c64e78d
Thread[Thread-3,5,main] com.lun.concurrency.DaemonFromFactory@3986e8ee
Thread[Thread-5,5,main] com.lun.concurrency.DaemonFromFactory@2002c1e3
Thread[Thread-7,5,main] com.lun.concurrency.DaemonFromFactory@12fc91a2
Thread[Thread-9,5,main] com.lun.concurrency.DaemonFromFactory@29289162
The End
*/// :~
