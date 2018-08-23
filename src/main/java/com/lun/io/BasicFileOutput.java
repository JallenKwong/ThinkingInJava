package com.lun.io;

//: io/BasicFileOutput.java
import java.io.*;

public class BasicFileOutput {
	static String file = "out.txt";

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new StringReader(BufferedInputFile.read("pom.xml")));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
		int lineCount = 1;
		String s;
		while ((s = in.readLine()) != null)
			out.println(lineCount++ + ": " + s);
		out.close();
		// Show the stored file:
		System.out.println(BufferedInputFile.read(file));
		new File("out.txt").delete();
	}
} /* (Execute to see output) */// :~
