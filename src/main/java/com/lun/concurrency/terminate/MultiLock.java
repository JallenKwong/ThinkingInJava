package com.lun.concurrency.terminate;

//: concurrency/MultiLock.java
// One thread can reacquire the same lock.

public class MultiLock {
	public synchronized void f1(int count) {
		if (count-- > 0) {
			System.out.println("f1() calling f2() with count " + count);
			f2(count);
			System.out.println("end");
		}
	}

	public synchronized void f2(int count) {
		if (count-- > 0) {
			System.out.println("f2() calling f1() with count " + count);
			f1(count);
			System.out.println("end");
		}
	}

	public static void main(String[] args) throws Exception {
		final MultiLock multiLock = new MultiLock();
		new Thread() {
			public void run() {
				multiLock.f1(10);
			}
		}.start();
	}
} 
/*

*/
