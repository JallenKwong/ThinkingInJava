package com.lun.concurrency.share;

//: concurrency/SerialNumberGenerator.java

public class SerialNumberGenerator {
	private static volatile int serialNumber = 0;

	
	/**
	 * SerialNumberChecker 中保证线程安全，
	 * 需要将nextSerialNumber方法加入synchronized修饰
	 * 
	 * @return
	 */
	public static int nextSerialNumber() {
		return serialNumber++; // Not thread-safe
	}
} /// :~
