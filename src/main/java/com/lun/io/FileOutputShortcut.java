package com.lun.io;

//: io/FileOutputShortcut.java
import java.io.*;

public class FileOutputShortcut {
	static String file = "out.txt";

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new StringReader(BufferedInputFile.read("pom.xml")));
		// Here's the shortcut:
		PrintWriter out = new PrintWriter(file);
		
		int lineCount = 1;
		String s;
		while ((s = in.readLine()) != null)
			out.println(lineCount++ + ": " + s);
		out.close();
		// Show the stored file:
		System.out.println(BufferedInputFile.read(file));
		new File(file).delete();
	}
} /* (Execute to see output) */// :~
