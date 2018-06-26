package com.lun.concurrency;

//: concurrency/FixedThreadPool.java
import java.util.concurrent.*;

public class FixedThreadPool {
	public static void main(String[] args) {
		// Constructor argument is number of threads:
		ExecutorService exec = Executors.newFixedThreadPool(5);
		for (int i = 0; i < 10; i++)
			exec.execute(new LiftOff());
		exec.shutdown();
	}
} 
/*
#1(9), #1(8), #1(7), #1(6), #1(5), 
#1(4), #1(3), #1(2), #1(1), #1(Liftoff!), 
#5(9), #2(9), #2(8), #5(8), #2(7), #5(7), 
#2(6), #3(9), #3(8), #2(5), #3(7), #4(9), 
#3(6), #3(5), #3(4), #3(3), #3(2), #3(1), 
#3(Liftoff!), #6(9), #6(8), #6(7), #6(6), 
#6(5), #6(4), #6(3), #6(2), #0(9), #2(4), 
#5(6), #5(5), #5(4), #5(3), #5(2), #5(1), 
#5(Liftoff!), #7(9), #7(8), #7(7), #4(8), 
#4(7), #6(1), #7(6), #4(6), #2(3), #0(8), 
#6(Liftoff!), #8(9), #8(8), #8(7), #8(6), 
#8(5), #8(4), #8(3), #8(2), #8(1), #8(Liftoff!), 
#9(9), #9(8), #9(7), #9(6), #9(5), #9(4), #0(7), 
#2(2), #2(1), #2(Liftoff!), #4(5), #0(6), #9(3), 
#7(5), #7(4), #9(2), #7(3), #0(5), #4(4), #0(4), 
#4(3), #7(2), #0(3), #4(2), #7(1), #0(2), #9(1), 
#9(Liftoff!), #0(1), #4(1), #4(Liftoff!), #0(Liftoff!), 
#7(Liftoff!), 	 
*/// :~
