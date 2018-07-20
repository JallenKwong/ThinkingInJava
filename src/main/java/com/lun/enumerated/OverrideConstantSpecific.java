package com.lun.enumerated;

//: enumerated/OverrideConstantSpecific.java

public enum OverrideConstantSpecific {
	NUT, BOLT, WASHER {
		void f() {
			System.out.println("Overridden method");
		}
	};
	void f() {
		System.out.println("default behavior");
	}

	public static void main(String[] args) {
		for (OverrideConstantSpecific ocs : values()) {
			System.out.println(ocs + ": ");
			ocs.f();
		}
	}
} 
/*
NUT: 
default behavior
BOLT: 
default behavior
WASHER: 
Overridden method
*/
