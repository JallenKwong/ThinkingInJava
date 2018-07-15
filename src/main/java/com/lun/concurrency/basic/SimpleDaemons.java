package com.lun.concurrency.basic;

//: concurrency/SimpleDaemons.java
// Daemon threads don't prevent the program from ending.
import java.util.concurrent.*;

public class SimpleDaemons implements Runnable {
	public void run() {
		try {
			while (true) {
				TimeUnit.MILLISECONDS.sleep(100);
				System.out.println(Thread.currentThread() + " " + this);
			}
		} catch (InterruptedException e) {
			System.out.println("sleep() interrupted");
		}
	}

	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 10; i++) {
			Thread daemon = new Thread(new SimpleDaemons());
			daemon.setDaemon(true); // Must call before start()
			daemon.start();
		}
		System.out.println("All daemons started");
		TimeUnit.MILLISECONDS.sleep(1000);
		System.out.println("The End");
		
	}
} 
/*
All daemons started
Thread[Thread-4,5,main] com.lun.concurrency.SimpleDaemons@4c9e4b62
Thread[Thread-0,5,main] com.lun.concurrency.SimpleDaemons@5cc1a465
Thread[Thread-2,5,main] com.lun.concurrency.SimpleDaemons@24295fbf
Thread[Thread-6,5,main] com.lun.concurrency.SimpleDaemons@5de5e084
Thread[Thread-8,5,main] com.lun.concurrency.SimpleDaemons@536bce95
Thread[Thread-5,5,main] com.lun.concurrency.SimpleDaemons@7af9bc61
Thread[Thread-3,5,main] com.lun.concurrency.SimpleDaemons@4b8efc00
Thread[Thread-7,5,main] com.lun.concurrency.SimpleDaemons@11b86924
Thread[Thread-9,5,main] com.lun.concurrency.SimpleDaemons@560a5268
Thread[Thread-1,5,main] com.lun.concurrency.SimpleDaemons@7f5d871d
Thread[Thread-4,5,main] com.lun.concurrency.SimpleDaemons@4c9e4b62
Thread[Thread-0,5,main] com.lun.concurrency.SimpleDaemons@5cc1a465
Thread[Thread-2,5,main] com.lun.concurrency.SimpleDaemons@24295fbf
Thread[Thread-6,5,main] com.lun.concurrency.SimpleDaemons@5de5e084
Thread[Thread-8,5,main] com.lun.concurrency.SimpleDaemons@536bce95
Thread[Thread-5,5,main] com.lun.concurrency.SimpleDaemons@7af9bc61
Thread[Thread-3,5,main] com.lun.concurrency.SimpleDaemons@4b8efc00
Thread[Thread-7,5,main] com.lun.concurrency.SimpleDaemons@11b86924
Thread[Thread-9,5,main] com.lun.concurrency.SimpleDaemons@560a5268
Thread[Thread-1,5,main] com.lun.concurrency.SimpleDaemons@7f5d871d
Thread[Thread-5,5,main] com.lun.concurrency.SimpleDaemons@7af9bc61
Thread[Thread-3,5,main] com.lun.concurrency.SimpleDaemons@4b8efc00
Thread[Thread-7,5,main] com.lun.concurrency.SimpleDaemons@11b86924
Thread[Thread-9,5,main] com.lun.concurrency.SimpleDaemons@560a5268
Thread[Thread-1,5,main] com.lun.concurrency.SimpleDaemons@7f5d871d
Thread[Thread-4,5,main] com.lun.concurrency.SimpleDaemons@4c9e4b62
Thread[Thread-0,5,main] com.lun.concurrency.SimpleDaemons@5cc1a465
Thread[Thread-2,5,main] com.lun.concurrency.SimpleDaemons@24295fbf
Thread[Thread-6,5,main] com.lun.concurrency.SimpleDaemons@5de5e084
Thread[Thread-8,5,main] com.lun.concurrency.SimpleDaemons@536bce95
Thread[Thread-7,5,main] com.lun.concurrency.SimpleDaemons@11b86924
Thread[Thread-3,5,main] com.lun.concurrency.SimpleDaemons@4b8efc00
Thread[Thread-5,5,main] com.lun.concurrency.SimpleDaemons@7af9bc61
Thread[Thread-1,5,main] com.lun.concurrency.SimpleDaemons@7f5d871d
Thread[Thread-9,5,main] com.lun.concurrency.SimpleDaemons@560a5268
Thread[Thread-5,5,main] com.lun.concurrency.SimpleDaemons@7af9bc61
Thread[Thread-3,5,main] com.lun.concurrency.SimpleDaemons@4b8efc00
Thread[Thread-7,5,main] com.lun.concurrency.SimpleDaemons@11b86924
Thread[Thread-9,5,main] com.lun.concurrency.SimpleDaemons@560a5268
Thread[Thread-1,5,main] com.lun.concurrency.SimpleDaemons@7f5d871d
Thread[Thread-4,5,main] com.lun.concurrency.SimpleDaemons@4c9e4b62
Thread[Thread-0,5,main] com.lun.concurrency.SimpleDaemons@5cc1a465
Thread[Thread-2,5,main] com.lun.concurrency.SimpleDaemons@24295fbf
Thread[Thread-6,5,main] com.lun.concurrency.SimpleDaemons@5de5e084
Thread[Thread-8,5,main] com.lun.concurrency.SimpleDaemons@536bce95
Thread[Thread-5,5,main] com.lun.concurrency.SimpleDaemons@7af9bc61
Thread[Thread-3,5,main] com.lun.concurrency.SimpleDaemons@4b8efc00
Thread[Thread-7,5,main] com.lun.concurrency.SimpleDaemons@11b86924
Thread[Thread-9,5,main] com.lun.concurrency.SimpleDaemons@560a5268
Thread[Thread-1,5,main] com.lun.concurrency.SimpleDaemons@7f5d871d
Thread[Thread-5,5,main] com.lun.concurrency.SimpleDaemons@7af9bc61
Thread[Thread-3,5,main] com.lun.concurrency.SimpleDaemons@4b8efc00
Thread[Thread-7,5,main] com.lun.concurrency.SimpleDaemons@11b86924
Thread[Thread-9,5,main] com.lun.concurrency.SimpleDaemons@560a5268
Thread[Thread-1,5,main] com.lun.concurrency.SimpleDaemons@7f5d871d
Thread[Thread-5,5,main] com.lun.concurrency.SimpleDaemons@7af9bc61
Thread[Thread-3,5,main] com.lun.concurrency.SimpleDaemons@4b8efc00
Thread[Thread-7,5,main] com.lun.concurrency.SimpleDaemons@11b86924
Thread[Thread-9,5,main] com.lun.concurrency.SimpleDaemons@560a5268
Thread[Thread-1,5,main] com.lun.concurrency.SimpleDaemons@7f5d871d
Thread[Thread-4,5,main] com.lun.concurrency.SimpleDaemons@4c9e4b62
Thread[Thread-0,5,main] com.lun.concurrency.SimpleDaemons@5cc1a465
Thread[Thread-2,5,main] com.lun.concurrency.SimpleDaemons@24295fbf
Thread[Thread-6,5,main] com.lun.concurrency.SimpleDaemons@5de5e084
Thread[Thread-8,5,main] com.lun.concurrency.SimpleDaemons@536bce95
The End 
*/// :~
