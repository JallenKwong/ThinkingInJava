package com.lun.containers;

//: containers/ReadOnly.java
// Using the Collections.unmodifiable methods.
import java.util.*;

import com.lun.util.Countries;

public class ReadOnly {
	static Collection<String> data = new ArrayList<String>(Countries.names(6));

	public static void main(String[] args) {
		Collection<String> c = Collections.unmodifiableCollection(new ArrayList<String>(data));
		System.out.println(c); // Reading is OK
		// ! c.add("one"); // Can't change it

		List<String> a = Collections.unmodifiableList(new ArrayList<String>(data));
		ListIterator<String> lit = a.listIterator();
		System.out.println(lit.next()); // Reading is OK
		// ! lit.add("one"); // Can't change it

		Set<String> s = Collections.unmodifiableSet(new HashSet<String>(data));
		System.out.println(s); // Reading is OK
		// ! s.add("one"); // Can't change it

		// For a SortedSet:
		Set<String> ss = Collections.unmodifiableSortedSet(new TreeSet<String>(data));

		Map<String, String> m = Collections.unmodifiableMap(new HashMap<String, String>(Countries.capitals(6)));
		System.out.println(m); // Reading is OK
		// ! m.put("Ralph", "Howdy!");

		// For a SortedMap:
		Map<String, String> sm = Collections.unmodifiableSortedMap(new TreeMap<String, String>(Countries.capitals(6)));
	}
} /*

*/// :~
