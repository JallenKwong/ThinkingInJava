package com.lun.concurrency.newcomponents;

//: concurrency/CountDownLatchDemo.java
import java.util.concurrent.*;
import java.util.*;

// Performs some portion of a task:
class TaskPortion implements Runnable {
	private static int counter = 0;
	private final int id = counter++;
	private static Random rand = new Random(47);
	private final CountDownLatch latch;

	TaskPortion(CountDownLatch latch) {
		this.latch = latch;
	}

	public void run() {
		try {
			doWork();
			latch.countDown();
		} catch (InterruptedException ex) {
			// Acceptable way to exit
		}
	}

	public void doWork() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(rand.nextInt(2000));
		System.out.println(this + "completed");
	}

	public String toString() {
		return String.format("%1$-3d ", id);
	}
}

// Waits on the CountDownLatch:
class WaitingTask implements Runnable {
	private static int counter = 0;
	private final int id = counter++;
	private final CountDownLatch latch;

	WaitingTask(CountDownLatch latch) {
		this.latch = latch;
	}

	public void run() {
		try {
			latch.await();
			System.out.println("Latch barrier passed for " + this);
		} catch (InterruptedException ex) {
			System.out.println(this + " interrupted");
		}
	}

	public String toString() {
		return String.format("WaitingTask %1$-3d ", id);
	}
}
