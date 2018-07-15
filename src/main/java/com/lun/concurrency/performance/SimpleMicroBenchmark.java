package com.lun.concurrency.performance;

//: concurrency/SimpleMicroBenchmark.java
// The dangers of microbenchmarking.
import java.util.concurrent.locks.*;

abstract class Incrementable {
	protected long counter = 0;

	public abstract void increment();
}

class SynchronizingTest extends Incrementable {
	public synchronized void increment() {
		++counter;
	}
}

class LockingTest extends Incrementable {
	private Lock lock = new ReentrantLock();

	public void increment() {
		lock.lock();
		try {
			++counter;
		} finally {
			lock.unlock();
		}
	}
}

public class SimpleMicroBenchmark {
	static long test(Incrementable incr) {
		long start = System.nanoTime();
		for (long i = 0; i < 10000000L; i++)
			incr.increment();
		return System.nanoTime() - start;
	}

	public static void main(String[] args) {
		long synchTime = test(new SynchronizingTest());
		long lockTime = test(new LockingTest());
		System.out.printf("synchronized: %1$10d\n", synchTime);
		System.out.printf("Lock:         %1$10d\n", lockTime);
		System.out.printf("Lock/synchronized = %1$.3f", (double) lockTime / (double) synchTime);
	}
} 
/*
第一次跑
synchronized:  519866257
Lock:          390732383
Lock/synchronized = 0.752

第二次跑
synchronized:  473815614
Lock:          383441561
Lock/synchronized = 0.809

第三次跑
synchronized:  526007813
Lock:          471752173
Lock/synchronized = 0.897

并没有达到书上那种跑出的结果L/S > 1


*/
