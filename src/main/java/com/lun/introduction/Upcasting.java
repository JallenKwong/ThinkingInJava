package com.lun.introduction;

import java.util.ArrayList;
import java.util.List;

class Shape{}

class Circle extends Shape{}

class Square extends Shape{}

class Triangle extends Shape{}


public class Upcasting {
	
	public static void main(String[] args) {
		
		
		//Can't compile.
		//List<? extends Shape> list = new ArrayList<? extends Shape>();
		List<? extends Shape> list = new ArrayList<Shape>();
		list = new ArrayList<Circle>();
		list = new ArrayList<Square>();
		list = new ArrayList<Triangle>();
		
		//Can't compile.
		//list.add(new Circle());
		//list.add(new Triangle());
		
		//Can't compile, either.
		//list.add(new Shape());		
		
		//Can't compile, either.
		//list.add(new Object());
		
		Shape s = list.get(0);
		
		//Can't compile.
		//Circle s = list.get(0);
		
		//---
		//Can't compile.
		//List<? super Shape> list2 = new ArrayList<? super Shape>();
		List<? super Shape> list2 = new ArrayList<Shape>();
		
		list2 = new ArrayList<Object>();
		
		//Can't compile.
//		list2 = new ArrayList<Circle>();
//		list2 = new ArrayList<Square>();
//		list2 = new ArrayList<Triangle>();
		
		//能加子类
		list2.add(new Circle());
		
		list2.add(new Shape());
		
		//Can't compile.
		//list2.add(new Object());
		
		//Can't compile.
		//Shape s = list2.get(0);
		Object s2 = list2.get(0);
		
		Circle s3 = (Circle)list2.get(0);
		
		
		//总结
		//? extends Shape 不能 add
		//? super Shape 可get可强制转换,
		
	}
	
}
