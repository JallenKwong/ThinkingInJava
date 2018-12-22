package com.lun.containers;

//: containers/SetPerformance.java
// Demonstrates performance differences in Sets.
// {Args: 100 5000} Small to keep build testing short
import java.util.*;

public class SetPerformance {
	static List<Test<Set<Integer>>> tests = new ArrayList<Test<Set<Integer>>>();
	static {
		tests.add(new Test<Set<Integer>>("add") {
			int test(Set<Integer> set, TestParam tp) {
				int loops = tp.loops;
				int size = tp.size;
				for (int i = 0; i < loops; i++) {
					set.clear();
					for (int j = 0; j < size; j++)
						set.add(j);
				}
				return loops * size;
			}
		});
		tests.add(new Test<Set<Integer>>("contains") {
			int test(Set<Integer> set, TestParam tp) {
				int loops = tp.loops;
				int span = tp.size * 2;
				for (int i = 0; i < loops; i++)
					for (int j = 0; j < span; j++)
						set.contains(j);
				return loops * span;
			}
		});
		tests.add(new Test<Set<Integer>>("iterate") {
			int test(Set<Integer> set, TestParam tp) {
				int loops = tp.loops * 10;
				for (int i = 0; i < loops; i++) {
					Iterator<Integer> it = set.iterator();
					while (it.hasNext())
						it.next();
				}
				return loops * set.size();
			}
		});
	}

	public static void main(String[] args) {
		if (args.length > 0)
			Tester.defaultParams = TestParam.array(args);
		Tester.fieldWidth = 10;
		Tester.run(new TreeSet<Integer>(), tests);
		Tester.run(new HashSet<Integer>(), tests);
		Tester.run(new LinkedHashSet<Integer>(), tests);
	}
} /*
------------- TreeSet -------------
 size       add  contains   iterate
   10      1030       322       145
  100       208        92        16
 1000       170       108        15
10000       206       145        20
------------- HashSet -------------
 size       add  contains   iterate
   10       329       218        79
  100        76        15        30
 1000        60        18        25
10000        58        21        22
---------- LinkedHashSet ----------
 size       add  contains   iterate
   10       492        78        42
  100        73        29        14
 1000        90        30        17
10000        85        38        13

*/// :~
