package com.lun.concurrency.performance;

//: concurrency/MapComparisons.java
// {Args: 1 10 10} (Fast verification check during build)
// Rough comparison of thread-safe Map performance.
import java.util.concurrent.*;

import com.lun.util.CountingGenerator;
import com.lun.util.MapData;

import java.util.*;

abstract class MapTest extends Tester<Map<Integer, Integer>> {
	MapTest(String testId, int nReaders, int nWriters) {
		super(testId, nReaders, nWriters);
	}

	class Reader extends TestTask {
		long result = 0;

		void test() {
			for (long i = 0; i < testCycles; i++)
				for (int index = 0; index < containerSize; index++)
					result += testContainer.get(index);
		}

		void putResults() {
			readResult += result;
			readTime += duration;
		}
	}

	class Writer extends TestTask {
		void test() {
			for (long i = 0; i < testCycles; i++)
				for (int index = 0; index < containerSize; index++)
					testContainer.put(index, writeData[index]);
		}

		void putResults() {
			writeTime += duration;
		}
	}

	void startReadersAndWriters() {
		for (int i = 0; i < nReaders; i++)
			exec.execute(new Reader());
		for (int i = 0; i < nWriters; i++)
			exec.execute(new Writer());
	}
}

class SynchronizedHashMapTest extends MapTest {
	Map<Integer, Integer> containerInitializer() {
		return Collections.synchronizedMap(new HashMap<Integer, Integer>(
				MapData.map(new CountingGenerator.Integer(), new CountingGenerator.Integer(), containerSize)));
	}

	SynchronizedHashMapTest(int nReaders, int nWriters) {
		super("Synched HashMap", nReaders, nWriters);
	}
}

class ConcurrentHashMapTest extends MapTest {
	Map<Integer, Integer> containerInitializer() {
		return new ConcurrentHashMap<Integer, Integer>(
				MapData.map(new CountingGenerator.Integer(), new CountingGenerator.Integer(), containerSize));
	}

	ConcurrentHashMapTest(int nReaders, int nWriters) {
		super("ConcurrentHashMap", nReaders, nWriters);
	}
}

public class MapComparisons {
	public static void main(String[] args) {
		Tester.initMain(args);
		new SynchronizedHashMapTest(10, 0);
		new SynchronizedHashMapTest(9, 1);
		new SynchronizedHashMapTest(5, 5);
		new ConcurrentHashMapTest(10, 0);
		new ConcurrentHashMapTest(9, 1);
		new ConcurrentHashMapTest(5, 5);
		Tester.exec.shutdown();
	}
} 
/*
Type                             Read time     Write time
Synched HashMap 10r 0w          6531944216              0
Synched HashMap 10r 0w          6212543113              0
Synched HashMap 10r 0w          6169421611              0
Synched HashMap 10r 0w          4619488929              0
Synched HashMap 10r 0w          5413938457              0
Synched HashMap 10r 0w          4209391985              0
Synched HashMap 10r 0w          4802440006              0
Synched HashMap 10r 0w          4608478218              0
Synched HashMap 10r 0w          5837710123              0
Synched HashMap 10r 0w          4402904639              0
Synched HashMap 9r 1w           4319726022      728734782
readTime + writeTime =          5048460804
Synched HashMap 9r 1w           4501950781      667152587
readTime + writeTime =          5169103368
Synched HashMap 9r 1w           4080744053      689986567
readTime + writeTime =          4770730620
Synched HashMap 9r 1w           3952624817      545697475
readTime + writeTime =          4498322292
Synched HashMap 9r 1w           3521419080      587328360
readTime + writeTime =          4108747440
Synched HashMap 9r 1w           4475409779      544046201
readTime + writeTime =          5019455980
Synched HashMap 9r 1w           4653191447      509426428
readTime + writeTime =          5162617875
Synched HashMap 9r 1w           4024919502      640807148
readTime + writeTime =          4665726650
Synched HashMap 9r 1w           4322652325      645957562
readTime + writeTime =          4968609887
Synched HashMap 9r 1w           3968639144      262186549
readTime + writeTime =          4230825693
Synched HashMap 5r 5w           2362034973     3087816762
readTime + writeTime =          5449851735
Synched HashMap 5r 5w           2904425794     2738657824
readTime + writeTime =          5643083618
Synched HashMap 5r 5w           2041550813     2934054274
readTime + writeTime =          4975605087
Synched HashMap 5r 5w           2117819437     3408629946
readTime + writeTime =          5526449383
Synched HashMap 5r 5w           3486554978     2674767848
readTime + writeTime =          6161322826
Synched HashMap 5r 5w           2536025948     2836719976
readTime + writeTime =          5372745924
Synched HashMap 5r 5w           2665186149     3033311895
readTime + writeTime =          5698498044
Synched HashMap 5r 5w           2787364496     3253476426
readTime + writeTime =          6040840922
Synched HashMap 5r 5w           2183882193     3274906029
readTime + writeTime =          5458788222
Synched HashMap 5r 5w           1807270636     3092996434
readTime + writeTime =          4900267070
ConcurrentHashMap 10r 0w         711336095              0
ConcurrentHashMap 10r 0w         276889406              0
ConcurrentHashMap 10r 0w         407927245              0
ConcurrentHashMap 10r 0w         335063310              0
ConcurrentHashMap 10r 0w         400909457              0
ConcurrentHashMap 10r 0w         294983607              0
ConcurrentHashMap 10r 0w         320583228              0
ConcurrentHashMap 10r 0w         503036402              0
ConcurrentHashMap 10r 0w         327163685              0
ConcurrentHashMap 10r 0w         625342049              0
ConcurrentHashMap 9r 1w          394181172      104781713
readTime + writeTime =           498962885
ConcurrentHashMap 9r 1w          484631142       45956432
readTime + writeTime =           530587574
ConcurrentHashMap 9r 1w          318262615       45933848
readTime + writeTime =           364196463
ConcurrentHashMap 9r 1w          271686635       44996569
readTime + writeTime =           316683204
ConcurrentHashMap 9r 1w          196924533      202280780
readTime + writeTime =           399205313
ConcurrentHashMap 9r 1w          278530414       49389870
readTime + writeTime =           327920284
ConcurrentHashMap 9r 1w          420905937       95140985
readTime + writeTime =           516046922
ConcurrentHashMap 9r 1w          331074488       44966284
readTime + writeTime =           376040772
ConcurrentHashMap 9r 1w          223967543       97431825
readTime + writeTime =           321399368
ConcurrentHashMap 9r 1w          339229225       61176691
readTime + writeTime =           400405916
ConcurrentHashMap 5r 5w          191597546     1239498345
readTime + writeTime =          1431095891
ConcurrentHashMap 5r 5w          551632718     1629207682
readTime + writeTime =          2180840400
ConcurrentHashMap 5r 5w          396174299     1084522941
readTime + writeTime =          1480697240
ConcurrentHashMap 5r 5w          137287733      960102002
readTime + writeTime =          1097389735
ConcurrentHashMap 5r 5w          113600140     1269290568
readTime + writeTime =          1382890708
ConcurrentHashMap 5r 5w          199297503     1723005369
readTime + writeTime =          1922302872
ConcurrentHashMap 5r 5w          115251929     1479854922
readTime + writeTime =          1595106851
ConcurrentHashMap 5r 5w          113501587     1258060674
readTime + writeTime =          1371562261
ConcurrentHashMap 5r 5w          110056857     1444699268
readTime + writeTime =          1554756125
ConcurrentHashMap 5r 5w          233257690     1245830368
readTime + writeTime =          1479088058

*/
