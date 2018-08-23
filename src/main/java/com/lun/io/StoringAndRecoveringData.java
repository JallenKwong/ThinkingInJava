package com.lun.io;

//: io/StoringAndRecoveringData.java
import java.io.*;

public class StoringAndRecoveringData {
	public static void main(String[] args) throws IOException {
		String fileName = "Data.txt";
		
		DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
		out.writeDouble(3.14159);
		out.writeUTF("That was pi");
		out.writeDouble(1.41413);
		out.writeUTF("Square root of 2");
		out.close();
		DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(fileName)));
		System.out.println(in.readDouble());
		// Only readUTF() will recover the
		// Java-UTF String properly:
		System.out.println(in.readUTF());
		System.out.println(in.readDouble());
		System.out.println(in.readUTF());
		new File(fileName).delete();
		
	}
} /*
	 * Output: 3.14159 That was pi 1.41413 Square root of 2
	 */// :~
