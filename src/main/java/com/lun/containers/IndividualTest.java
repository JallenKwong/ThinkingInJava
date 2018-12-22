package com.lun.containers;

//: containers/IndividualTest.java
import java.util.*;

import com.lun.holding.MapOfList;
import com.lun.typeinformation.pets.Individual;
import com.lun.typeinformation.pets.Pet;

public class IndividualTest {
	public static void main(String[] args) {
		Set<Individual> pets = new TreeSet<Individual>();
		for (List<? extends Pet> lp : MapOfList.petPeople.values())
			for (Pet p : lp)
				pets.add(p);
		System.out.println(pets);
	}
} /*

*/// :~
