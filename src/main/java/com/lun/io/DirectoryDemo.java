package com.lun.io;

//: io/DirectoryDemo.java
// Sample use of Directory utilities.
import java.io.*;

import com.lun.util.Directory;
import com.lun.util.PPrint;

public class DirectoryDemo {
	public static void main(String[] args) {
		// All directories:
		PPrint.pprint(Directory.walk(".").dirs);
		// All files beginning with 'T'
		for (File file : Directory.local(".", "T.*"))
			System.out.println(file);
		System.out.println("----------------------");
		// All Java files beginning with 'T':
		for (File file : Directory.walk(".", "T.*\\.java"))
			System.out.println(file);
		System.out.println("======================");
		// Class files containing "Z" or "z":
		for (File file : Directory.walk(".", ".*[Zz].*\\.class"))
			System.out.println(file);
	}
} 
/*
[
  .\.git
  .\.git\hooks
  .\.git\info
  .\.git\logs
  .\.git\logs\refs
  .\.git\logs\refs\heads
  .\.git\logs\refs\remotes
  .\.git\logs\refs\remotes\origin
  .\.git\objects
  .\.git\objects\00
  .\.git\objects\01
  .\.git\objects\02
  .\.git\objects\03
  .\.git\objects\04
  .\.git\objects\05
  .\.git\objects\06
  .\.git\objects\07
  .\.git\objects\08
  .\.git\objects\0b
  .\.git\objects\0c
  .\.git\objects\0d
  .\.git\objects\0e
  .\.git\objects\0f
  .\.git\objects\11
  .\.git\objects\12
  .\.git\objects\14
  .\.git\objects\15
  .\.git\objects\16
  .\.git\objects\17
  .\.git\objects\18
  .\.git\objects\19
  .\.git\objects\1a
  .\.git\objects\1b
  .\.git\objects\1c
  .\.git\objects\1e
  .\.git\objects\1f
  .\.git\objects\21
  .\.git\objects\22
  .\.git\objects\23
  .\.git\objects\24
  .\.git\objects\25
  .\.git\objects\26
  .\.git\objects\28
  .\.git\objects\29
  .\.git\objects\2a
  .\.git\objects\2b
  .\.git\objects\2d
  .\.git\objects\2e
  .\.git\objects\2f
  .\.git\objects\31
  .\.git\objects\32
  .\.git\objects\33
  .\.git\objects\34
  .\.git\objects\35
  .\.git\objects\37
  .\.git\objects\38
  .\.git\objects\39
  .\.git\objects\3a
  .\.git\objects\3b
  .\.git\objects\3c
  .\.git\objects\3d
  .\.git\objects\3e
  .\.git\objects\3f
  .\.git\objects\40
  .\.git\objects\41
  .\.git\objects\42
  .\.git\objects\43
  .\.git\objects\44
  .\.git\objects\45
  .\.git\objects\46
  .\.git\objects\47
  .\.git\objects\48
  .\.git\objects\4a
  .\.git\objects\4b
  .\.git\objects\4c
  .\.git\objects\4d
  .\.git\objects\4e
  .\.git\objects\4f
  .\.git\objects\51
  .\.git\objects\52
  .\.git\objects\53
  .\.git\objects\54
  .\.git\objects\55
  .\.git\objects\56
  .\.git\objects\57
  .\.git\objects\58
  .\.git\objects\59
  .\.git\objects\5a
  .\.git\objects\5b
  .\.git\objects\5d
  .\.git\objects\5e
  .\.git\objects\5f
  .\.git\objects\61
  .\.git\objects\62
  .\.git\objects\63
  .\.git\objects\64
  .\.git\objects\65
  .\.git\objects\66
  .\.git\objects\67
  .\.git\objects\68
  .\.git\objects\69
  .\.git\objects\6a
  .\.git\objects\6b
  .\.git\objects\6c
  .\.git\objects\6d
  .\.git\objects\6f
  .\.git\objects\70
  .\.git\objects\71
  .\.git\objects\73
  .\.git\objects\74
  .\.git\objects\75
  .\.git\objects\76
  .\.git\objects\78
  .\.git\objects\79
  .\.git\objects\7a
  .\.git\objects\7c
  .\.git\objects\7d
  .\.git\objects\7e
  .\.git\objects\80
  .\.git\objects\81
  .\.git\objects\82
  .\.git\objects\83
  .\.git\objects\84
  .\.git\objects\85
  .\.git\objects\86
  .\.git\objects\87
  .\.git\objects\88
  .\.git\objects\89
  .\.git\objects\8a
  .\.git\objects\8b
  .\.git\objects\8c
  .\.git\objects\8d
  .\.git\objects\8e
  .\.git\objects\8f
  .\.git\objects\90
  .\.git\objects\91
  .\.git\objects\92
  .\.git\objects\93
  .\.git\objects\94
  .\.git\objects\95
  .\.git\objects\96
  .\.git\objects\97
  .\.git\objects\98
  .\.git\objects\99
  .\.git\objects\9a
  .\.git\objects\9b
  .\.git\objects\9d
  .\.git\objects\9f
  .\.git\objects\a0
  .\.git\objects\a1
  .\.git\objects\a2
  .\.git\objects\a3
  .\.git\objects\a5
  .\.git\objects\a6
  .\.git\objects\a8
  .\.git\objects\a9
  .\.git\objects\aa
  .\.git\objects\ab
  .\.git\objects\ad
  .\.git\objects\ae
  .\.git\objects\af
  .\.git\objects\b0
  .\.git\objects\b1
  .\.git\objects\b2
  .\.git\objects\b3
  .\.git\objects\b5
  .\.git\objects\b8
  .\.git\objects\b9
  .\.git\objects\ba
  .\.git\objects\bc
  .\.git\objects\bd
  .\.git\objects\be
  .\.git\objects\bf
  .\.git\objects\c0
  .\.git\objects\c1
  .\.git\objects\c3
  .\.git\objects\c4
  .\.git\objects\c5
  .\.git\objects\c6
  .\.git\objects\c7
  .\.git\objects\c8
  .\.git\objects\ca
  .\.git\objects\cb
  .\.git\objects\cc
  .\.git\objects\cd
  .\.git\objects\ce
  .\.git\objects\d0
  .\.git\objects\d1
  .\.git\objects\d2
  .\.git\objects\d3
  .\.git\objects\d4
  .\.git\objects\d5
  .\.git\objects\d6
  .\.git\objects\d7
  .\.git\objects\d8
  .\.git\objects\d9
  .\.git\objects\da
  .\.git\objects\dc
  .\.git\objects\de
  .\.git\objects\df
  .\.git\objects\e0
  .\.git\objects\e1
  .\.git\objects\e2
  .\.git\objects\e3
  .\.git\objects\e6
  .\.git\objects\e7
  .\.git\objects\e8
  .\.git\objects\e9
  .\.git\objects\ea
  .\.git\objects\eb
  .\.git\objects\ed
  .\.git\objects\ee
  .\.git\objects\ef
  .\.git\objects\f0
  .\.git\objects\f1
  .\.git\objects\f2
  .\.git\objects\f3
  .\.git\objects\f4
  .\.git\objects\f5
  .\.git\objects\f6
  .\.git\objects\f7
  .\.git\objects\f8
  .\.git\objects\f9
  .\.git\objects\fb
  .\.git\objects\fc
  .\.git\objects\fd
  .\.git\objects\fe
  .\.git\objects\ff
  .\.git\objects\info
  .\.git\objects\pack
  .\.git\refs
  .\.git\refs\heads
  .\.git\refs\remotes
  .\.git\refs\remotes\origin
  .\.git\refs\tags
  .\.settings
  .\src
  .\src\main
  .\src\main\java
  .\src\main\java\com
  .\src\main\java\com\lun
  .\src\main\java\com\lun\annotations
  .\src\main\java\com\lun\concurrency
  .\src\main\java\com\lun\concurrency\basic
  .\src\main\java\com\lun\concurrency\cooperation
  .\src\main\java\com\lun\concurrency\deadlock
  .\src\main\java\com\lun\concurrency\newcomponents
  .\src\main\java\com\lun\concurrency\performance
  .\src\main\java\com\lun\concurrency\semaphore
  .\src\main\java\com\lun\concurrency\share
  .\src\main\java\com\lun\concurrency\simulation
  .\src\main\java\com\lun\concurrency\terminate
  .\src\main\java\com\lun\concurrency
  .\src\main\java\com\lun\enumerated
  .\src\main\java\com\lun\enumerated\menu
  .\src\main\java\com\lun\exception
  .\src\main\java\com\lun\initnclean
  .\src\main\java\com\lun\io
  .\src\main\java\com\lun\reusingclass
  .\src\main\java\com\lun
  .\src\main\resources
  .\src\test
  .\src\test\java
  .\src\test\resources
  .\target
  .\target\classes
  .\target\classes\com
  .\target\classes\com\lun
  .\target\classes\com\lun\annotations
  .\target\classes\com\lun\concurrency
  .\target\classes\com\lun\concurrency\basic
  .\target\classes\com\lun\concurrency\cooperation
  .\target\classes\com\lun\concurrency\deadlock
  .\target\classes\com\lun\concurrency\newcomponents
  .\target\classes\com\lun\concurrency\performance
  .\target\classes\com\lun\concurrency\semaphore
  .\target\classes\com\lun\concurrency\share
  .\target\classes\com\lun\concurrency\simulation
  .\target\classes\com\lun\concurrency\terminate
  .\target\classes\com\lun\enumerated
  .\target\classes\com\lun\enumerated\menu
  .\target\classes\com\lun\exception
  .\target\classes\com\lun\initnclean
  .\target\classes\com\lun\io
  .\target\classes\com\lun\reusingclass
  .\target\classes\META-INF
  .\target\classes\META-INF\maven
  .\target\classes\META-INF\maven\com.lun
  .\target\classes\META-INF\maven\com.lun\ThinkingInJava
  .\target\test-classes
]
----------------------
.\src\main\java\com\lun\concurrency\basic\ThreadVariations.java
.\src\main\java\com\lun\concurrency\cooperation\TestBlockingQueues.java
.\src\main\java\com\lun\concurrency\cooperation\ToastOMatic.java
.\src\main\java\com\lun\concurrency\performance\Tester.java
.\src\main\java\com\lun\concurrency\share\ThreadLocalVariableHolder.java
.\src\main\java\com\lun\enumerated\menu\TypeOfFood.java
.\src\main\java\com\lun\enumerated\TrafficLight.java
.\src\main\java\com\lun\initnclean\TerminationCondition.java
======================
.\target\classes\com\lun\concurrency\newcomponents\PrioritizedTask$EndSentinel.class
.\target\classes\com\lun\concurrency\newcomponents\PrioritizedTask.class
.\target\classes\com\lun\concurrency\newcomponents\PrioritizedTaskConsumer.class
.\target\classes\com\lun\concurrency\newcomponents\PrioritizedTaskProducer.class
.\target\classes\com\lun\concurrency\performance\SynchronizationComparisons.class
.\target\classes\com\lun\concurrency\performance\SynchronizedArrayListTest.class
.\target\classes\com\lun\concurrency\performance\SynchronizedHashMapTest.class
.\target\classes\com\lun\concurrency\performance\SynchronizedTest.class
.\target\classes\com\lun\concurrency\performance\SynchronizingTest.class
.\target\classes\com\lun\concurrency\share\SynchronizedEvenGenerator.class
.\target\classes\com\lun\concurrency\terminate\SynchronizedBlocked$1.class
.\target\classes\com\lun\concurrency\terminate\SynchronizedBlocked.class
.\target\classes\com\lun\enumerated\menu\Food$Appetizer.class
.\target\classes\com\lun\enumerated\menu\Meal2$Food$Appetizer.class
.\target\classes\com\lun\enumerated\OzWitch.class
.\target\classes\com\lun\reusingclass\Gizmo.class

*/// :~