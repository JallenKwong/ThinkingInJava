package com.lun.containers;

//: containers/References.java
// Demonstrates Reference objects
import java.lang.ref.*;
import java.util.*;

class VeryBig {
	private static final int SIZE = 10000;
	private long[] la = new long[SIZE];
	private String ident;

	public VeryBig(String id) {
		ident = id;
	}

	public String toString() {
		return ident;
	}

	protected void finalize() {
		System.out.println("Finalizing " + ident);
	}
}

public class References {
	private static ReferenceQueue<VeryBig> rq = new ReferenceQueue<VeryBig>();

	public static void checkQueue() {
		Reference<? extends VeryBig> inq = rq.poll();
		if (inq != null)
			System.out.println("In queue: " + inq.get());
	}

	public static void main(String[] args) {
		int size = 10;
		// Or, choose size via the command line:
		if (args.length > 0)
			size = new Integer(args[0]);
		LinkedList<SoftReference<VeryBig>> sa = new LinkedList<SoftReference<VeryBig>>();
		for (int i = 0; i < size; i++) {
			sa.add(new SoftReference<VeryBig>(new VeryBig("Soft " + i), rq));
			System.out.println("Just created: " + sa.getLast());
			checkQueue();
		}
		
		System.out.println("---");
		
		LinkedList<WeakReference<VeryBig>> wa = new LinkedList<WeakReference<VeryBig>>();
		for (int i = 0; i < size; i++) {
			wa.add(new WeakReference<VeryBig>(new VeryBig("Weak " + i), rq));
			System.out.println("Just created: " + wa.getLast());
			checkQueue();
		}
		System.out.println("---");
		
		SoftReference<VeryBig> s = new SoftReference<VeryBig>(new VeryBig("Soft"));
		WeakReference<VeryBig> w = new WeakReference<VeryBig>(new VeryBig("Weak"));
		
		System.out.println("---");
		System.gc();
		LinkedList<PhantomReference<VeryBig>> pa = new LinkedList<PhantomReference<VeryBig>>();
		for (int i = 0; i < size; i++) {
			pa.add(new PhantomReference<VeryBig>(new VeryBig("Phantom " + i), rq));
			System.out.println("Just created: " + pa.getLast());
			checkQueue();
		}
	}
} /*
Just created: java.lang.ref.SoftReference@70dea4e
Just created: java.lang.ref.SoftReference@5c647e05
Just created: java.lang.ref.SoftReference@33909752
Just created: java.lang.ref.SoftReference@55f96302
Just created: java.lang.ref.SoftReference@3d4eac69
Just created: java.lang.ref.SoftReference@42a57993
Just created: java.lang.ref.SoftReference@75b84c92
Just created: java.lang.ref.SoftReference@6bc7c054
Just created: java.lang.ref.SoftReference@232204a1
Just created: java.lang.ref.SoftReference@4aa298b7
Just created: java.lang.ref.WeakReference@7d4991ad
Just created: java.lang.ref.WeakReference@28d93b30
Just created: java.lang.ref.WeakReference@1b6d3586
Just created: java.lang.ref.WeakReference@4554617c
Just created: java.lang.ref.WeakReference@74a14482
Just created: java.lang.ref.WeakReference@1540e19d
Just created: java.lang.ref.WeakReference@677327b6
Just created: java.lang.ref.WeakReference@14ae5a5
Just created: java.lang.ref.WeakReference@7f31245a
Just created: java.lang.ref.WeakReference@6d6f6e28
Just created: java.lang.ref.PhantomReference@135fbaa4
In queue: null
Just created: java.lang.ref.PhantomReference@45ee12a7
In queue: null
Just created: java.lang.ref.PhantomReference@330bedb4
In queue: null
Finalizing Weak
Just created: java.lang.ref.PhantomReference@2503dbd3
In queue: null
Just created: java.lang.ref.PhantomReference@4b67cf4d
In queue: null
Finalizing Weak 9
Finalizing Weak 8
Finalizing Weak 7
Finalizing Weak 6
Finalizing Weak 5
Finalizing Weak 4
Finalizing Weak 3
Finalizing Weak 2
Finalizing Weak 1
Finalizing Weak 0
Just created: java.lang.ref.PhantomReference@7ea987ac
In queue: null
Just created: java.lang.ref.PhantomReference@12a3a380
In queue: null
Just created: java.lang.ref.PhantomReference@29453f44
In queue: null
Just created: java.lang.ref.PhantomReference@5cad8086
In queue: null
Just created: java.lang.ref.PhantomReference@6e0be858
In queue: null

*/// :~
