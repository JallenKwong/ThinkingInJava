package com.lun.concurrency;

//: concurrency/MoreBasicThreads.java
// Adding more threads.

public class MoreBasicThreads {
	public static void main(String[] args) {
		for (int i = 0; i < 5; i++)
			new Thread(new LiftOff()).start();
		System.out.println("Waiting for LiftOff");
	}
} 

/*
Waiting for LiftOff
他们顺序每次都不一定相同
#0(9), #2(9), #4(9), #0(8), #2(8), 
#4(8), #0(7), #2(7), #4(7), #0(6), 
#2(6), #4(6), #0(5), #2(5), #4(5), 
#0(4), #2(4), #4(4), #0(3), #2(3), 
#4(3), #0(2), #2(2), #4(2), #0(1), 
#2(1), #4(1), #0(Liftoff!), #2(Liftoff!),
#4(Liftoff!), #1(9), #3(9), #1(8), #3(8),
#1(7), #3(7), #1(6), #3(6), #1(5), #3(5),
#1(4), #3(4), #1(3), #3(3), #1(2),
#3(2), #1(1), #3(1), #1(Liftoff!), #3(Liftoff!), 
 */// :~
