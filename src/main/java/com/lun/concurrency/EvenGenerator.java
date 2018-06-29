package com.lun.concurrency;

//: concurrency/EvenGenerator.java
// When threads collide.

public class EvenGenerator extends IntGenerator {
	private int currentEvenValue = 0;

	public int next() {
		++currentEvenValue; // Danger point here!
		
		//加快目标结果显示出来
//		Thread.yield();
		
		++currentEvenValue;
		return currentEvenValue;
	}

	public static void main(String[] args) {
		EvenChecker.test(new EvenGenerator(), 2);
	}
} /*
	 * Output: (Sample) Press Control-C to exit 89476993 not even! 89476993 not
	 * even!
	 */// :~
