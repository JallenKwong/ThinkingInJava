package com.lun.concurrency;

//: concurrency/AtomicityTest.java
import java.util.concurrent.*;

public class AtomicityTest implements Runnable {
	private int i = 0;

	public int getValue() {
		return i;
	}

	private synchronized void evenIncrement() {
		i++;
		i++;
	}

	public void run() {
		while (true)
			evenIncrement();
	}

	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		AtomicityTest at = new AtomicityTest();
		exec.execute(at);
		while (true) {
			int val = at.getValue();
			if (val % 2 != 0) {
				System.out.println(val);
				System.exit(0);
			}
		}
	}
}
/*

我跑了半个小时结果还是没出来，通过修改一下（去掉synchronized, 加入Thread.yield()
，exec.excute()运行多个AtomicityTest任务的线程），奇数结果有时会出现）

要想保证线性安全的，按照书上说的，getValue()前要synchronized修饰

Output: (Sample) 191583767
*/// :~
