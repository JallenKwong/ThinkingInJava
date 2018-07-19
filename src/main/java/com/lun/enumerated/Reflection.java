package com.lun.enumerated;

//: enumerated/Reflection.java
// Analyzing enums using reflection.
import java.lang.reflect.*;
import java.util.*;

import com.lun.util.OSExecute;


enum Explore {
	HERE, THERE
}

public class Reflection {
	public static Set<String> analyze(Class<?> enumClass) {
		System.out.println("----- Analyzing " + enumClass + " -----");
		System.out.println("Interfaces:");
		for (Type t : enumClass.getGenericInterfaces())
			System.out.println(t);
		System.out.println("Base: " + enumClass.getSuperclass());
		System.out.println("Methods: ");
		Set<String> methods = new TreeSet<String>();
		for (Method m : enumClass.getMethods())
			methods.add(m.getName());
		System.out.println(methods);
		return methods;
	}

	public static void main(String[] args) {
		Set<String> exploreMethods = analyze(Explore.class);
		Set<String> enumMethods = analyze(Enum.class);
		System.out.println("Explore.containsAll(Enum)? " + exploreMethods.containsAll(enumMethods));
		System.out.println("Explore.removeAll(Enum): ");
		exploreMethods.removeAll(enumMethods);
		System.out.println(exploreMethods);
		// Decompile the code for the enum:
		OSExecute.command("javap -cp C:\\eclipse-workspace\\ThinkingInJava\\target\\classes\\com\\lun\\enumerated Explore");
	}
}
/*

----- Analyzing class com.lun.enumerated.Explore -----
Interfaces:
Base: class java.lang.Enum
Methods: 
[compareTo, equals, getClass, getDeclaringClass, hashCode, name, notify, notifyAll, ordinal, toString, valueOf, values, wait]
----- Analyzing class java.lang.Enum -----
Interfaces:
java.lang.Comparable<E>
interface java.io.Serializable
Base: class java.lang.Object
Methods: 
[compareTo, equals, getClass, getDeclaringClass, hashCode, name, notify, notifyAll, ordinal, toString, valueOf, wait]
Explore.containsAll(Enum)? true
Explore.removeAll(Enum): 
[values]
Compiled from "Reflection.java"
final class com.lun.enumerated.Explore extends java.lang.Enum<com.lun.enumerated.Explore> {
  public static final com.lun.enumerated.Explore HERE;
  public static final com.lun.enumerated.Explore THERE;
  static {};
  public static com.lun.enumerated.Explore[] values();
  public static com.lun.enumerated.Explore valueOf(java.lang.String);
}
*/
