package com.lun.concurrency;

//: concurrency/OrnamentalGarden.java
import java.util.concurrent.*;
import java.util.*;

class Count {
	private int count = 0;
	private Random rand = new Random(47);

	// Remove the synchronized keyword to see counting fail:
	public synchronized int increment() {
		int temp = count;
		if (rand.nextBoolean()) // Yield half the time
			Thread.yield();//潜行异常
		return (count = ++temp);
	}

	public synchronized int value() {
		return count;
	}
}

class Entrance implements Runnable {
	private static Count count = new Count();
	private static List<Entrance> entrances = new ArrayList<Entrance>();
	private int number = 0;
	// Doesn't need synchronization to read:
	private final int id;
	private static volatile boolean canceled = false;

	// Atomic operation on a volatile field:
	public static void cancel() {
		canceled = true;
	}

	public Entrance(int id) {
		this.id = id;
		// Keep this task in a list. Also prevents
		// garbage collection of dead tasks:
		entrances.add(this);
	}

	public void run() {
		while (!canceled) {
			synchronized (this) {
				++number;
			}
			System.out.println(this + " Total: " + count.increment());
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				System.out.println("sleep interrupted");
			}
		}
		System.out.println("Stopping " + this);
	}

	public synchronized int getValue() {
		return number;
	}

	public String toString() {
		return "Entrance " + id + ": " + getValue();
	}

	public static int getTotalCount() {
		return count.value();
	}

	public static int sumEntrances() {
		int sum = 0;
		for (Entrance entrance : entrances)
			sum += entrance.getValue();
		return sum;
	}
}

public class OrnamentalGarden {
	public static void main(String[] args) throws Exception {
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < 5; i++)
			exec.execute(new Entrance(i));
		// Run for a while, then stop and collect the data:
		TimeUnit.SECONDS.sleep(3);
		Entrance.cancel();
		exec.shutdown();
		if (!exec.awaitTermination(250, TimeUnit.MILLISECONDS))
			System.out.println("Some tasks were not terminated!");
		System.out.println("Total: " + Entrance.getTotalCount());
		System.out.println("Sum of Entrances: " + Entrance.sumEntrances());
	}
} 
/*
Entrance 1: 1 Total: 1
Entrance 2: 1 Total: 3
Entrance 4: 1 Total: 4
Entrance 0: 1 Total: 2
Entrance 3: 1 Total: 5
Entrance 0: 2 Total: 7
Entrance 4: 2 Total: 8
Entrance 2: 2 Total: 6
Entrance 1: 2 Total: 9
Entrance 3: 2 Total: 10
Entrance 0: 3 Total: 11
Entrance 2: 3 Total: 12
Entrance 4: 3 Total: 13
Entrance 0: 4 Total: 14
Entrance 2: 4 Total: 15
Entrance 4: 4 Total: 16
Entrance 0: 5 Total: 17
Entrance 1: 3 Total: 18
Entrance 3: 3 Total: 19
Entrance 2: 5 Total: 20
Entrance 2: 6 Total: 22
Entrance 0: 6 Total: 21
Entrance 4: 5 Total: 23
Entrance 1: 4 Total: 24
Entrance 3: 4 Total: 25
Entrance 4: 6 Total: 26
Entrance 2: 7 Total: 28
Entrance 0: 7 Total: 27
Entrance 1: 5 Total: 29
Entrance 3: 5 Total: 30
Entrance 4: 7 Total: 32
Entrance 0: 8 Total: 31
Entrance 2: 8 Total: 33
Entrance 1: 6 Total: 34
Entrance 3: 6 Total: 35
Entrance 2: 9 Total: 37
Entrance 0: 9 Total: 38
Entrance 4: 8 Total: 36
Entrance 1: 7 Total: 39
Entrance 3: 7 Total: 40
Entrance 4: 9 Total: 41
Entrance 0: 10 Total: 42
Entrance 2: 10 Total: 43
Entrance 3: 8 Total: 44
Entrance 1: 8 Total: 45
Entrance 4: 10 Total: 46
Entrance 2: 11 Total: 48
Entrance 0: 11 Total: 47
Entrance 1: 9 Total: 49
Entrance 3: 9 Total: 50
Entrance 4: 11 Total: 51
Entrance 0: 12 Total: 52
Entrance 2: 12 Total: 53
Entrance 1: 10 Total: 54
Entrance 3: 10 Total: 55
Entrance 2: 13 Total: 56
Entrance 4: 12 Total: 58
Entrance 0: 13 Total: 57
Entrance 1: 11 Total: 59
Entrance 3: 11 Total: 60
Entrance 0: 14 Total: 62
Entrance 2: 14 Total: 61
Entrance 4: 13 Total: 63
Entrance 1: 12 Total: 64
Entrance 3: 12 Total: 65
Entrance 0: 15 Total: 67
Entrance 2: 15 Total: 68
Entrance 4: 14 Total: 66
Entrance 1: 13 Total: 69
Entrance 3: 13 Total: 70
Entrance 0: 16 Total: 71
Entrance 2: 16 Total: 72
Entrance 4: 15 Total: 73
Entrance 1: 14 Total: 74
Entrance 3: 14 Total: 75
Entrance 0: 17 Total: 76
Entrance 2: 17 Total: 77
Entrance 4: 16 Total: 78
Entrance 1: 15 Total: 79
Entrance 3: 15 Total: 80
Entrance 0: 18 Total: 81
Entrance 4: 17 Total: 83
Entrance 2: 18 Total: 82
Entrance 1: 16 Total: 84
Entrance 3: 16 Total: 85
Entrance 0: 19 Total: 87
Entrance 4: 18 Total: 86
Entrance 2: 19 Total: 88
Entrance 1: 17 Total: 89
Entrance 3: 17 Total: 90
Entrance 0: 20 Total: 91
Entrance 4: 19 Total: 92
Entrance 2: 20 Total: 93
Entrance 1: 18 Total: 94
Entrance 3: 18 Total: 95
Entrance 4: 20 Total: 96
Entrance 2: 21 Total: 98
Entrance 0: 21 Total: 97
Entrance 3: 19 Total: 100
Entrance 1: 19 Total: 99
Entrance 2: 22 Total: 101
Entrance 4: 21 Total: 103
Entrance 0: 22 Total: 102
Entrance 1: 20 Total: 104
Entrance 3: 20 Total: 105
Entrance 2: 23 Total: 106
Entrance 4: 22 Total: 107
Entrance 0: 23 Total: 108
Entrance 1: 21 Total: 109
Entrance 3: 21 Total: 110
Entrance 2: 24 Total: 111
Entrance 4: 23 Total: 112
Entrance 0: 24 Total: 113
Stopping Entrance 1: 21
Stopping Entrance 3: 21
Stopping Entrance 0: 24
Stopping Entrance 4: 23
Stopping Entrance 2: 24
Total: 113
Sum of Entrances: 113
*/// :~
