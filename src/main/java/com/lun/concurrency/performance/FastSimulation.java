package com.lun.concurrency.performance;

//: concurrency/FastSimulation.java
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.*;

public class FastSimulation {
	static final int N_ELEMENTS = 100000;
	static final int N_GENES = 30;
	static final int N_EVOLVERS = 50;
	static final AtomicInteger[][] GRID = new AtomicInteger[N_ELEMENTS][N_GENES];
	static Random rand = new Random(47);

	static class Evolver implements Runnable {
		public void run() {
			while (!Thread.interrupted()) {
				// Randomly select an element to work on:
				int element = rand.nextInt(N_ELEMENTS);
				for (int i = 0; i < N_GENES; i++) {
					int previous = element - 1;
					if (previous < 0)
						previous = N_ELEMENTS - 1;
					int next = element + 1;
					if (next >= N_ELEMENTS)
						next = 0;
					int oldvalue = GRID[element][i].get();
					// Perform some kind of modeling calculation:
					int newvalue = oldvalue + GRID[previous][i].get() + GRID[next][i].get();
					newvalue /= 3; // Average the three values
					if (!GRID[element][i].compareAndSet(oldvalue, newvalue)) {
						// Policy here to deal with failure. Here, we
						// just report it and ignore it; our model
						// will eventually deal with it.
						System.out.println("Old value changed from " + oldvalue);
					}
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < N_ELEMENTS; i++)
			for (int j = 0; j < N_GENES; j++)
				GRID[i][j] = new AtomicInteger(rand.nextInt(1000));
		for (int i = 0; i < N_EVOLVERS; i++)
			exec.execute(new Evolver());
		TimeUnit.SECONDS.sleep(5);
		exec.shutdownNow();
	}
}

/*
Old value changed from 82
Old value changed from 783
Old value changed from 385
Old value changed from 400
Old value changed from 446
Old value changed from 541
Old value changed from 659
Old value changed from 561
Old value changed from 390
Old value changed from 708
Old value changed from 610
Old value changed from 598
Old value changed from 416
Old value changed from 584
Old value changed from 495
Old value changed from 698
Old value changed from 477
Old value changed from 110
Old value changed from 341
Old value changed from 587
Old value changed from 772
Old value changed from 390
Old value changed from 426
Old value changed from 503
Old value changed from 569
Old value changed from 543
Old value changed from 496
Old value changed from 623
Old value changed from 459
Old value changed from 492
Old value changed from 594
Old value changed from 425
Old value changed from 459
Old value changed from 468
Old value changed from 412
Old value changed from 481
Old value changed from 321
Old value changed from 647
Old value changed from 491
Old value changed from 603
Old value changed from 677
Old value changed from 505
Old value changed from 581
Old value changed from 452
Old value changed from 529
Old value changed from 371
Old value changed from 265
Old value changed from 427
Old value changed from 446
Old value changed from 494
Old value changed from 569
Old value changed from 429
Old value changed from 580
Old value changed from 398
Old value changed from 483
Old value changed from 569
Old value changed from 536
Old value changed from 530
Old value changed from 471
Old value changed from 399
Old value changed from 560
Old value changed from 542
Old value changed from 511
Old value changed from 469
Old value changed from 428
Old value changed from 463
Old value changed from 440
Old value changed from 686
Old value changed from 456
Old value changed from 326
Old value changed from 412
Old value changed from 459
Old value changed from 638
Old value changed from 434
Old value changed from 583
*/

