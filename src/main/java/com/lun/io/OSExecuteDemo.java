package com.lun.io;

import com.lun.util.OSExecute;

//: io/OSExecuteDemo.java
// Demonstrates standard I/O redirection.

public class OSExecuteDemo {
	public static void main(String[] args) {
		OSExecute.command("dir");
	}
} 
/*
 驱动器 C 中的卷没有标签。
 卷的序列号是 CE2F-63AD

 C:\eclipse-workspace\ThinkingInJava 的目录

2018/08/25  02:52    <DIR>          .
2018/08/25  02:52    <DIR>          ..
2018/06/11  14:55             1,433 .classpath
2018/06/26  15:31                43 .gitignore
2018/06/11  14:55               566 .project
2018/06/11  14:55    <DIR>          .settings
2018/06/11  14:55               689 pom.xml
2018/08/23  23:13               273 README.md
2018/06/11  14:55    <DIR>          src
2018/06/11  14:55    <DIR>          target
               5 个文件          3,004 字节
               5 个目录 10,914,353,152 可用字节
*/
