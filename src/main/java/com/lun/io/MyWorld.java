package com.lun.io;

//: io/MyWorld.java
import java.io.*;
import java.util.*;

class House implements Serializable {
}

class Animal implements Serializable {
	private String name;
	private House preferredHouse;

	Animal(String nm, House h) {
		name = nm;
		preferredHouse = h;
	}

	public String toString() {
		return name + "[" + super.toString() + "], " + preferredHouse + "\n";
	}
}

public class MyWorld {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		House house = new House();
		List<Animal> animals = new ArrayList<Animal>();
		animals.add(new Animal("Bosco the dog", house));
		animals.add(new Animal("Ralph the hamster", house));
		animals.add(new Animal("Molly the cat", house));
		System.out.println("animals: " + animals);
		
		ByteArrayOutputStream buf1 = new ByteArrayOutputStream();
		ObjectOutputStream o1 = new ObjectOutputStream(buf1);
		o1.writeObject(animals);
		o1.writeObject(animals); // Write a 2nd set
		// Write to a different stream:
		
		ByteArrayOutputStream buf2 = new ByteArrayOutputStream();
		ObjectOutputStream o2 = new ObjectOutputStream(buf2);
		o2.writeObject(animals);
		
		// Now get them back:
		ObjectInputStream in1 = new ObjectInputStream(new ByteArrayInputStream(buf1.toByteArray()));
		ObjectInputStream in2 = new ObjectInputStream(new ByteArrayInputStream(buf2.toByteArray()));
		
		List animals1 = (List) in1.readObject(), animals2 = (List) in1.readObject(), animals3 = (List) in2.readObject();
		System.out.println("animals1: " + animals1);
		System.out.println("animals2: " + animals2);
		System.out.println("animals3: " + animals3);
	}
} 
/*
animals: [Bosco the dog[com.lun.io.Animal@70dea4e], com.lun.io.House@5c647e05
, Ralph the hamster[com.lun.io.Animal@33909752], com.lun.io.House@5c647e05
, Molly the cat[com.lun.io.Animal@55f96302], com.lun.io.House@5c647e05
]
animals1: [Bosco the dog[com.lun.io.Animal@568db2f2], com.lun.io.House@378bf509
, Ralph the hamster[com.lun.io.Animal@5fd0d5ae], com.lun.io.House@378bf509
, Molly the cat[com.lun.io.Animal@2d98a335], com.lun.io.House@378bf509
]
animals2: [Bosco the dog[com.lun.io.Animal@568db2f2], com.lun.io.House@378bf509
, Ralph the hamster[com.lun.io.Animal@5fd0d5ae], com.lun.io.House@378bf509
, Molly the cat[com.lun.io.Animal@2d98a335], com.lun.io.House@378bf509
]
animals3: [Bosco the dog[com.lun.io.Animal@16b98e56], com.lun.io.House@7ef20235
, Ralph the hamster[com.lun.io.Animal@27d6c5e0], com.lun.io.House@7ef20235
, Molly the cat[com.lun.io.Animal@4f3f5b24], com.lun.io.House@7ef20235
]

*/
