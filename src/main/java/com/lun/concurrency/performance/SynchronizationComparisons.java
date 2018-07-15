package com.lun.concurrency.performance;

//: concurrency/SynchronizationComparisons.java
// Comparing the performance of explicit Locks
// and Atomics versus the synchronized keyword.
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.*;

abstract class Accumulator {
	public static long cycles = 50000L;
	// Number of Modifiers and Readers during each test:
	private static final int N = 4;
	public static ExecutorService exec = Executors.newFixedThreadPool(N * 2);
	private static CyclicBarrier barrier = new CyclicBarrier(N * 2 + 1);
	protected volatile int index = 0;
	protected volatile long value = 0;
	protected long duration = 0;
	protected String id = "error";
	protected final static int SIZE = 100000;
	protected static int[] preLoaded = new int[SIZE];
	static {
		// Load the array of random numbers:
		Random rand = new Random(47);
		for (int i = 0; i < SIZE; i++)
			preLoaded[i] = rand.nextInt();
	}

	public abstract void accumulate();

	public abstract long read();

	private class Modifier implements Runnable {
		public void run() {
			for (long i = 0; i < cycles; i++)
				accumulate();
			try {
				barrier.await();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	private class Reader implements Runnable {
		private volatile long value;

		public void run() {
			for (long i = 0; i < cycles; i++)
				value = read();
			try {
				barrier.await();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	public void timedTest() {
		long start = System.nanoTime();
		for (int i = 0; i < N; i++) {
			exec.execute(new Modifier());
			exec.execute(new Reader());
		}
		try {
			barrier.await();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		duration = System.nanoTime() - start;
		System.out.printf("%-13s: %13d\n", id, duration);
	}

	public static void report(Accumulator acc1, Accumulator acc2) {
		System.out.printf("%-22s: %.2f\n", acc1.id + "/" + acc2.id, (double) acc1.duration / (double) acc2.duration);
	}
}

class BaseLine extends Accumulator {
	{
		id = "BaseLine";
	}

	public void accumulate() {
		value += preLoaded[index >= SIZE ? 0 : index];
		if (index++ >= SIZE)
			index = 0;
	}

	public long read() {
		return value;
	}
}

class SynchronizedTest extends Accumulator {
	{
		id = "synchronized";
	}

	public synchronized void accumulate() {
		value += preLoaded[index++];
		if (index >= SIZE)
			index = 0;
	}

	public synchronized long read() {
		return value;
	}
}

class LockTest extends Accumulator {
	{
		id = "Lock";
	}
	private Lock lock = new ReentrantLock();

	public void accumulate() {
		lock.lock();
		try {
			value += preLoaded[index++];
			if (index >= SIZE)
				index = 0;
		} finally {
			lock.unlock();
		}
	}

	public long read() {
		lock.lock();
		try {
			return value;
		} finally {
			lock.unlock();
		}
	}
}

class AtomicTest extends Accumulator {
	{
		id = "Atomic";
	}
	private AtomicInteger index = new AtomicInteger(0);
	private AtomicLong value = new AtomicLong(0);

	public void accumulate() {
		// Oops! Relying on more than one Atomic at
		// a time doesn't work. But it still gives us
		// a performance indicator:
		int i = index.getAndIncrement();
		//value.getAndAdd(preLoaded[i]);
		value.getAndAdd(preLoaded[i >= SIZE ? 0 : i]);
		if (++i >= SIZE)
			index.set(0);
	}

	public long read() {
		return value.get();
	}
}

public class SynchronizationComparisons {
	static BaseLine baseLine = new BaseLine();
	static SynchronizedTest synch = new SynchronizedTest();
	static LockTest lock = new LockTest();
	static AtomicTest atomic = new AtomicTest();

	static void test() {
		System.out.println("============================");
		System.out.printf("%-12s : %13d\n", "Cycles", Accumulator.cycles);
		baseLine.timedTest();
		synch.timedTest();
		lock.timedTest();
		atomic.timedTest();
		Accumulator.report(synch, baseLine);
		Accumulator.report(lock, baseLine);
		Accumulator.report(atomic, baseLine);
		Accumulator.report(synch, lock);
		Accumulator.report(synch, atomic);
		Accumulator.report(lock, atomic);
	}

	public static void main(String[] args) {
		int iterations = 8; // Default
		if (args.length > 0) // Optionally change iterations
			iterations = new Integer(args[0]);
		// The first time fills the thread pool:
		System.out.println("Warmup");
		baseLine.timedTest();
		// Now the initial test doesn't include the cost
		// of starting the threads for the first time.
		// Produce multiple data points:
		for (int i = 0; i < iterations; i++) {
			test();
			Accumulator.cycles *= 2;
		}
		Accumulator.exec.shutdown();
	}
} 
/*
Warmup
BaseLine     :      70791904
============================
Cycles       :         50000
BaseLine     :      17479698
synchronized :      72139813
Lock         :      67379529
Atomic       :      22604934
synchronized/BaseLine : 4.13
Lock/BaseLine         : 3.85
Atomic/BaseLine       : 1.29
synchronized/Lock     : 1.07
synchronized/Atomic   : 3.19
Lock/Atomic           : 2.98
============================
Cycles       :        100000
BaseLine     :      29779751
synchronized :     208393068
Lock         :      75228813
Atomic       :      27048002
synchronized/BaseLine : 7.00
Lock/BaseLine         : 2.53
Atomic/BaseLine       : 0.91
synchronized/Lock     : 2.77
synchronized/Atomic   : 7.70
Lock/Atomic           : 2.78
============================
Cycles       :        200000
BaseLine     :      60960793
synchronized :     450071169
Lock         :     159319639
Atomic       :      62891290
synchronized/BaseLine : 7.38
Lock/BaseLine         : 2.61
Atomic/BaseLine       : 1.03
synchronized/Lock     : 2.82
synchronized/Atomic   : 7.16
Lock/Atomic           : 2.53
============================
Cycles       :        400000
BaseLine     :     142831625
synchronized :     849054231
Lock         :     338990341
Atomic       :     131817886
synchronized/BaseLine : 5.94
Lock/BaseLine         : 2.37
Atomic/BaseLine       : 0.92
synchronized/Lock     : 2.50
synchronized/Atomic   : 6.44
Lock/Atomic           : 2.57
============================
Cycles       :        800000
BaseLine     :     252797578
synchronized :    1667347294
Lock         :     673363608
Atomic       :     231848016
synchronized/BaseLine : 6.60
Lock/BaseLine         : 2.66
Atomic/BaseLine       : 0.92
synchronized/Lock     : 2.48
synchronized/Atomic   : 7.19
Lock/Atomic           : 2.90
============================
Cycles       :       1600000
BaseLine     :     491819385
synchronized :    3192793051
Lock         :    1291986513
Atomic       :     428213589
synchronized/BaseLine : 6.49
Lock/BaseLine         : 2.63
Atomic/BaseLine       : 0.87
synchronized/Lock     : 2.47
synchronized/Atomic   : 7.46
Lock/Atomic           : 3.02
============================
Cycles       :       3200000
BaseLine     :     928990973
synchronized :    6172775117
Lock         :    2606831526
Atomic       :     921193017
synchronized/BaseLine : 6.64
Lock/BaseLine         : 2.81
Atomic/BaseLine       : 0.99
synchronized/Lock     : 2.37
synchronized/Atomic   : 6.70
Lock/Atomic           : 2.83
============================
Cycles       :       6400000
BaseLine     :    1868705161
synchronized :   12336392049
Lock         :    5399846422
Atomic       :    1842292102
synchronized/BaseLine : 6.60
Lock/BaseLine         : 2.89
Atomic/BaseLine       : 0.99
synchronized/Lock     : 2.28
synchronized/Atomic   : 6.70
Lock/Atomic           : 2.93
*/
