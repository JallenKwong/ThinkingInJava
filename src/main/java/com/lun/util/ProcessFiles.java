//: net/mindview/util/ProcessFiles.java
package com.lun.util;

import java.io.*;

public class ProcessFiles {
	public interface Strategy {
		void process(File file);
	}

	private Strategy strategy;
	private String ext;

	public ProcessFiles(Strategy strategy, String ext) {
		this.strategy = strategy;
		this.ext = ext;
	}

	public void start(String[] args) {
		try {
			if (args.length == 0)
				processDirectoryTree(new File("."));
			else
				for (String arg : args) {
					File fileArg = new File(arg);
					if (fileArg.isDirectory())
						processDirectoryTree(fileArg);
					else {
						// Allow user to leave off extension:
						if (!arg.endsWith("." + ext))
							arg += "." + ext;
						strategy.process(new File(arg).getCanonicalFile());
					}
				}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void processDirectoryTree(File root) throws IOException {
		for (File file : Directory.walk(root.getAbsolutePath(), ".*\\." + ext))
			strategy.process(file.getCanonicalFile());
	}

	// Demonstration of how to use it:
	public static void main(String[] args) {
		new ProcessFiles(new ProcessFiles.Strategy() {
			public void process(File file) {
				System.out.println(file);
			}
		}, "java").start(args);
	}
} 

/*
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\basic\BasicThreads.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\basic\CachedThreadPool.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\basic\CallableDemo.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\basic\CaptureUncaughtException.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\basic\DaemonFromFactory.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\basic\Daemons.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\basic\DaemonsDontRunFinally.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\basic\ExceptionThread.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\basic\FixedThreadPool.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\basic\Joining.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\basic\LiftOff.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\basic\MoreBasicThreads.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\basic\NaiveExceptionHandling.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\basic\ResponsiveUI.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\basic\SelfManaged.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\basic\SettingDefaultHandler.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\basic\SimpleDaemons.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\basic\SimplePriorities.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\basic\SimpleThread.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\basic\SingleThreadExecutor.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\basic\SleepingTask.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\basic\ThreadVariations.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\cooperation\NotifyVsNotifyAll.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\cooperation\PipedIO.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\cooperation\Restaurant.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\cooperation\TestBlockingQueues.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\cooperation\ToastOMatic.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\cooperation\WaxOMatic.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\cooperation\WaxOMatic2.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\deadlock\Chopstick.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\deadlock\DeadlockingDiningPhilosophers.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\deadlock\FixedDiningPhilosophers.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\deadlock\Philosopher.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\newcomponents\CountDownLatchDemo.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\newcomponents\DelayQueueDemo.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\newcomponents\ExchangerDemo.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\newcomponents\GreenhouseScheduler.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\newcomponents\HorseRace.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\newcomponents\PriorityBlockingQueueDemo.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\performance\ActiveObjectDemo.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\performance\FastSimulation.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\performance\ListComparisons.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\performance\MapComparisons.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\performance\ReaderWriterList.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\performance\SimpleMicroBenchmark.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\performance\SynchronizationComparisons.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\performance\Tester.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\semaphore\Fat.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\semaphore\Pool.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\semaphore\SemaphoreDemo.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\share\AtomicEvenGenerator.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\share\AtomicIntegerTest.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\share\AtomicityTest.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\share\AttemptLocking.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\share\CriticalSection.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\share\EvenChecker.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\share\EvenGenerator.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\share\ExplicitCriticalSection.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\share\IntGenerator.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\share\MutexEvenGenerator.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\share\SerialNumberChecker.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\share\SerialNumberGenerator.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\share\SynchronizedEvenGenerator.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\share\SyncObject.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\share\ThreadLocalVariableHolder.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\simulation\BankTellerSimulation.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\simulation\CarBuilder.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\simulation\RestaurantWithQueues.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\terminate\CloseResource.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\terminate\Interrupting.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\terminate\Interrupting2.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\terminate\InterruptingIdiom.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\terminate\MultiLock.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\concurrency\terminate\NIOInterruption.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\enumerated\AlarmPoints.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\enumerated\BigEnumSet.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\enumerated\Burrito.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\enumerated\CarWash.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\enumerated\Competitor.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\enumerated\ConstantSpecificMethod.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\enumerated\EnumClass.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\enumerated\EnumImplementation.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\enumerated\EnumMaps.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\enumerated\EnumSets.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\enumerated\Input.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\enumerated\menu\Course.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\enumerated\menu\Food.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\enumerated\menu\Meal.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\enumerated\menu\Meal2.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\enumerated\menu\TypeOfFood.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\enumerated\NonEnum.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\enumerated\NotClasses.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\enumerated\Outcome.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\enumerated\OverrideConstantSpecific.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\enumerated\OzWitch.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\enumerated\PostOffice.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\enumerated\Reflection.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\enumerated\RoShamBo.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\enumerated\RoShamBo1.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\enumerated\RoShamBo2.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\enumerated\RoShamBo3.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\enumerated\RoShamBo4.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\enumerated\SecurityCategory.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\enumerated\SpaceShip.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\enumerated\Spiciness.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\enumerated\TrafficLight.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\enumerated\UpcastEnum.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\enumerated\VendingMachine.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\exception\AlwaysFinally.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\exception\ExceptionSilencer.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\exception\FinallyWorks.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\exception\LostMessage.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\exception\MultipleReturns.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\exception\OnOffException1.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\exception\OnOffException2.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\exception\OnOffSwitch.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\exception\Switch.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\exception\WithFinally.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\initnclean\TerminationCondition.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\io\DirectoryDemo.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\io\DirList.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\io\DirList2.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\io\DirList3.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\reusingclass\BlankFinal.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\reusingclass\FinalArguments.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\reusingclass\FinalData.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\reusingclass\FinalOverridingIllusion.java
C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\reusingclass\Jurassic.java

*/
