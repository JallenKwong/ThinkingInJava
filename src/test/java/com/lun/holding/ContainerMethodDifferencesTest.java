package com.lun.holding;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

import org.junit.Test;

import com.lun.util.Sets;



public class ContainerMethodDifferencesTest {

	/**
	 * HashMap与TreeMap之间不同的方法和相同的方法
	 */
	@Test
	public void test() {
		Set<String> set1 = ContainerMethodDifferences.methodSet(TreeMap.class);
		Set<String> set2 = ContainerMethodDifferences.methodSet(HashMap.class);
		
		
		System.out.println(Sets.difference(set1, set2));
		System.out.println(Sets.intersection(set1, set2));

		/*[descendingKeySet, navigableKeySet, higherEntry, higherKey, 
			floorKey, subMap, ceilingKey, pollLastEntry, firstKey, 
			lowerKey, headMap, tailMap, lowerEntry, ceilingEntry, 
			descendingMap, pollFirstEntry, lastKey, firstEntry, 
			floorEntry, comparator, lastEntry]*/
		
		/*[getClass, getOrDefault, replace, containsValue, put, 
			compute, merge, entrySet, containsKey, size, wait, 
			computeIfAbsent, notifyAll, values, replaceAll, 
			notify, remove, hashCode, get, putAll, keySet, 
			forEach, clear, isEmpty, computeIfPresent, 
			equals, clone, toString, putIfAbsent]*/

	}
	
}
