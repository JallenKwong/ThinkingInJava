package com.lun.io;

/**
 * 
 * char 两个字节  1 << 16 外 是怎么的存在
 * 
 * from https://bbs.csdn.net/topics/390064041
 * 
 */
public class CharAndUnicode2 {
	public static void main(String[] args) {
        char[] chs = Character.toChars(0x10400);
        System.out.println((char)0x10400);
        System.out.printf("U+10400 高代理字符: %04x%n", (int)chs[0]);
        System.out.printf("U+10400 低代理字符: %04x%n", (int)chs[1]);        
        String str = new String(chs);
        System.out.println("代码单元长度: " + str.length());
        System.out.println("代码点数量: " + str.codePointCount(0, str.length()));
	}
}

/*
Ѐ
U+10400 高代理字符: d801
U+10400 低代理字符: dc00
代码单元长度: 2
代码点数量: 1 
*/
