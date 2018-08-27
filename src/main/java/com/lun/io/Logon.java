package com.lun.io;

//: io/Logon.java
// Demonstrates the "transient" keyword.
import java.util.concurrent.*;
import java.io.*;
import java.util.*;

public class Logon implements Serializable {
	private Date date = new Date();
	private String username;
	private transient String password;

	public Logon(String name, String pwd) {
		username = name;
		password = pwd;
	}

	public String toString() {
		return "logon info: \n   username: " + username + "\n   date: " + date + "\n   password: " + password;
	}

	public static void main(String[] args) throws Exception {
		Logon a = new Logon("Hulk", "myLittlePony");
		System.out.println("logon a = " + a);
		ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("Logon.out"));
		o.writeObject(a);
		o.close();
		
		TimeUnit.SECONDS.sleep(1); // Delay
		
		// Now get them back:
		ObjectInputStream in = new ObjectInputStream(new FileInputStream("Logon.out"));
		System.out.println("Recovering object at " + new Date());
		a = (Logon) in.readObject();
		System.out.println("logon a = " + a);
		
		new File("Logon.out").delete();
	}
}
/*
logon a = logon info: 
   username: Hulk
   date: Mon Aug 27 23:53:36 CST 2018
   password: myLittlePony
Recovering object at Mon Aug 27 23:53:37 CST 2018
logon a = logon info: 
   username: Hulk
   date: Mon Aug 27 23:53:36 CST 2018
   password: null <--------------------------------------transient
 */
