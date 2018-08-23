package com.lun.io;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * 看看Unicode 有多少个字符 ? 1114112
 * 
 * @author JK
 *
 *	18-8-24
 */
public class CharAndUnicode {
	public static void main(String[] args) throws FileNotFoundException {
		PrintWriter out = new PrintWriter("unicode.txt");
		int i = 0;
		while(true) {
			out.print(Integer.toHexString(i));
			out.print(", ");
			out.print((char)i);
//			if(i != 0 && i % 10 ==0)
				out.print('\n');
			if(++i > Character.MAX_CODE_POINT) {
				System.out.println(i);
				break;
			}
		}
		out.print('\n');
		out.print(i);
	}
	
}
