package com.lun.concurrency.basic;

//: concurrency/NaiveExceptionHandling.java
// {ThrowsException}
import java.util.concurrent.*;

public class NaiveExceptionHandling {
	public static void main(String[] args) {
		try {
			ExecutorService exec = Executors.newCachedThreadPool();
			exec.execute(new ExceptionThread());
		} catch (RuntimeException ue) {
			// This statement will NOT execute!
			System.out.println("Exception has been handled!");
		}
	}
} /// :~
/*
Exception in thread "pool-1-thread-1" java.lang.RuntimeException
	at com.lun.concurrency.ExceptionThread.run(ExceptionThread.java:9)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
	at java.lang.Thread.run(Thread.java:748)

*/