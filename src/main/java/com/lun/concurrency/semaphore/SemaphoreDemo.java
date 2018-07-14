package com.lun.concurrency.semaphore;

//: concurrency/SemaphoreDemo.java
// Testing the Pool class
import java.util.concurrent.*;
import java.util.*;

// A task to check a resource out of a pool:
class CheckoutTask<T> implements Runnable {
	private static int counter = 0;
	private final int id = counter++;
	private Pool<T> pool;

	public CheckoutTask(Pool<T> pool) {
		this.pool = pool;
	}

	public void run() {
		try {
			T item = pool.checkOut();
			System.out.println(this + "checked out " + item);
			TimeUnit.SECONDS.sleep(1);
			System.out.println(this + "checking in " + item);
			pool.checkIn(item);
		} catch (InterruptedException e) {
			// Acceptable way to terminate
		}
	}

	public String toString() {
		return "CheckoutTask " + id + " ";
	}
}

public class SemaphoreDemo {
	final static int SIZE = 25;

	public static void main(String[] args) throws Exception {
		//初始化对象池
		final Pool<Fat> pool = new Pool<Fat>(Fat.class, SIZE);
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < SIZE; i++)
			exec.execute(new CheckoutTask<Fat>(pool));
		System.out.println("All CheckoutTasks created");
		System.out.println("=========================");
		
		List<Fat> list = new ArrayList<Fat>();
		for (int i = 0; i < SIZE; i++) {
			Fat f = pool.checkOut();
			System.out.print(i + ": main() thread checked out ");
			f.operation();
			list.add(f);
		}
		Future<?> blocked = exec.submit(new Runnable() {
			public void run() {
				try {
					// Semaphore prevents additional checkout,
					// so call is blocked:
					pool.checkOut();
				} catch (InterruptedException e) {
					System.out.println("checkOut() Interrupted");
				}
			}
		});
		TimeUnit.SECONDS.sleep(2);
		blocked.cancel(true); // Break out of blocked call
		
		System.out.println("=========================");
		System.out.println("Checking in objects in " + list);
		for (Fat f : list)
			pool.checkIn(f);
		for (Fat f : list)
			pool.checkIn(f); // Second checkIn ignored
		exec.shutdown();
	}
}

/*
CheckoutTask 0 checked out Fat id: 0
CheckoutTask 1 checked out Fat id: 1
CheckoutTask 3 checked out Fat id: 2
CheckoutTask 5 checked out Fat id: 3
CheckoutTask 7 checked out Fat id: 4
CheckoutTask 9 checked out Fat id: 5
CheckoutTask 11 checked out Fat id: 6
CheckoutTask 13 checked out Fat id: 7
CheckoutTask 15 checked out Fat id: 8
CheckoutTask 2 checked out Fat id: 9
CheckoutTask 17 checked out Fat id: 10
CheckoutTask 19 checked out Fat id: 11
All CheckoutTasks created
=========================
0: main() thread checked out CheckoutTask 21 checked out Fat id: 13
CheckoutTask 23 checked out Fat id: 14
CheckoutTask 4 checked out Fat id: 15
Fat id: 12
1: main() thread checked out Fat id: 16
2: main() thread checked out Fat id: 17
3: main() thread checked out Fat id: 18
4: main() thread checked out Fat id: 19
5: main() thread checked out Fat id: 20
6: main() thread checked out Fat id: 21
7: main() thread checked out Fat id: 23
8: main() thread checked out Fat id: 24
CheckoutTask 6 checked out Fat id: 22
CheckoutTask 0 checking in Fat id: 0
9: main() thread checked out Fat id: 0
CheckoutTask 1 checking in Fat id: 1
CheckoutTask 8 checked out Fat id: 1
CheckoutTask 7 checking in Fat id: 4
CheckoutTask 3 checking in Fat id: 2
CheckoutTask 5 checking in Fat id: 3
CheckoutTask 12 checked out Fat id: 3
CheckoutTask 10 checked out Fat id: 2
CheckoutTask 14 checked out Fat id: 4
CheckoutTask 13 checking in Fat id: 7
CheckoutTask 9 checking in Fat id: 5
CheckoutTask 16 checked out Fat id: 5
CheckoutTask 11 checking in Fat id: 6
CheckoutTask 15 checking in Fat id: 8
CheckoutTask 18 checked out Fat id: 6
CheckoutTask 19 checking in Fat id: 11
CheckoutTask 17 checking in Fat id: 10
CheckoutTask 20 checked out Fat id: 7
CheckoutTask 22 checked out Fat id: 8
CheckoutTask 24 checked out Fat id: 10
10: main() thread checked out Fat id: 11
CheckoutTask 23 checking in Fat id: 14
CheckoutTask 21 checking in Fat id: 13
11: main() thread checked out Fat id: 13
12: main() thread checked out Fat id: 14
CheckoutTask 4 checking in Fat id: 15
CheckoutTask 2 checking in Fat id: 9
13: main() thread checked out Fat id: 15
14: main() thread checked out Fat id: 9
CheckoutTask 6 checking in Fat id: 22
15: main() thread checked out Fat id: 22
CheckoutTask 8 checking in Fat id: 1
16: main() thread checked out Fat id: 1
CheckoutTask 12 checking in Fat id: 3
17: main() thread checked out CheckoutTask 14 checking in Fat id: 4
CheckoutTask 10 checking in Fat id: 2
Fat id: 3
18: main() thread checked out Fat id: 2
19: main() thread checked out Fat id: 4
CheckoutTask 16 checking in Fat id: 5
20: main() thread checked out Fat id: 5
CheckoutTask 22 checking in Fat id: 8
CheckoutTask 24 checking in Fat id: 10
21: main() thread checked out Fat id: 8
22: main() thread checked out Fat id: 10
CheckoutTask 20 checking in Fat id: 7
CheckoutTask 18 checking in Fat id: 6
23: main() thread checked out Fat id: 6
24: main() thread checked out Fat id: 7
checkOut() Interrupted
=========================
Checking in objects in [Fat id: 12, Fat id: 16, Fat id: 17, Fat id: 18, Fat id: 19, Fat id: 20, Fat id: 21, Fat id: 23, Fat id: 24, Fat id: 0, Fat id: 11, Fat id: 13, Fat id: 14, Fat id: 15, Fat id: 9, Fat id: 22, Fat id: 1, Fat id: 3, Fat id: 2, Fat id: 4, Fat id: 5, Fat id: 8, Fat id: 10, Fat id: 6, Fat id: 7]

*/