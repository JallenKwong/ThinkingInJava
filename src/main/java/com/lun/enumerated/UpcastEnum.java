package com.lun.enumerated;

//: enumerated/UpcastEnum.java
// No values() method if you upcast an enum

enum Search {
	HITHER, YON
}

public class UpcastEnum {
	public static void main(String[] args) {
		Search[] vals = Search.values();
		Enum e = Search.HITHER; // 
		// e.values(); // No values() in Enum
		for (Enum en : e.getClass().getEnumConstants())
			System.out.println(en);
	}
}
/*
HITHER
YON
*/
