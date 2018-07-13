package com.lun.concurrency.deadlock;

//: concurrency/FixedDiningPhilosophers.java
// Dining philosophers without deadlock.
// {Args: 5 5 timeout}
import java.util.concurrent.*;

public class FixedDiningPhilosophers {
	public static void main(String[] args) throws Exception {

		// args = "0 5 timeout".split(" ");
		args = "3 5".split(" ");

		int ponder = 5;
		if (args.length > 0)
			ponder = Integer.parseInt(args[0]);
		int size = 5;
		if (args.length > 1)
			size = Integer.parseInt(args[1]);
		ExecutorService exec = Executors.newCachedThreadPool();
		Chopstick[] sticks = new Chopstick[size];
		for (int i = 0; i < size; i++)
			sticks[i] = new Chopstick();
		for (int i = 0; i < size; i++)//这你是解决哲学家就餐死锁的关键
			if (i < (size - 1))
				exec.execute(new Philosopher(sticks[i], sticks[i + 1], i, ponder));
			else
				exec.execute(new Philosopher(sticks[0], sticks[i], i, ponder));
		if (args.length == 3 && args[2].equals("timeout"))
			TimeUnit.SECONDS.sleep(5);
		else {
			System.out.println("Press 'Enter' to quit");
			System.in.read();
		}
		exec.shutdownNow();
	}
} /* (Execute to see output) */// :~
