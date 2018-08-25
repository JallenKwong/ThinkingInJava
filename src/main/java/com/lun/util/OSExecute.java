//: net/mindview/util/OSExecute.java
// Run an operating system command
// and send the output to the console.
package com.lun.util;

import java.io.*;

public class OSExecute {
	public static void command(String command) {
		boolean err = false;
		try {
			Process process = new ProcessBuilder(command.split(" ")).start();
			
			//Windows默认的编码是GBK, 不添加GBK, results会乱码
			BufferedReader results = new BufferedReader(new InputStreamReader(process.getInputStream(),"GBK"));
			String s;
			while ((s = results.readLine()) != null)
				System.out.println(s);
			BufferedReader errors = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			// Report errors and return nonzero value
			// to calling process if there are problems:
			while ((s = errors.readLine()) != null) {
				System.err.println(s);
				err = true;
			}
		} catch (Exception e) {
			// Compensate for Windows 2000, which throws an
			// exception for the default command line:
			if (!command.startsWith("CMD /C"))
				command("CMD /C " + command);
			else
				throw new RuntimeException(e);
		}
		if (err)
			throw new OSExecuteException("Errors executing " + command);
	}
	
	public static class OSExecuteException extends RuntimeException {
		public OSExecuteException(String why) { super(why); }
	}
} /// :~
