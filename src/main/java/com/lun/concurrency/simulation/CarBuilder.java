package com.lun.concurrency.simulation;

//: concurrency/CarBuilder.java
// A complex example of tasks working together.
import java.util.concurrent.*;
import java.util.*;

class Car {
	private final int id;
	private boolean engine = false, driveTrain = false, wheels = false;

	public Car(int idn) {
		id = idn;
	}

	// Empty Car object:
	public Car() {
		id = -1;
	}

	public synchronized int getId() {
		return id;
	}

	public synchronized void addEngine() {
		engine = true;
	}

	public synchronized void addDriveTrain() {
		driveTrain = true;
	}

	public synchronized void addWheels() {
		wheels = true;
	}

	public synchronized String toString() {
		return "Car " + id + " [" + " engine: " + engine + " driveTrain: " + driveTrain + " wheels: " + wheels + " ]";
	}
}

class CarQueue extends LinkedBlockingQueue<Car> {
}

class ChassisBuilder implements Runnable {
	private CarQueue carQueue;
	private int counter = 0;

	public ChassisBuilder(CarQueue cq) {
		carQueue = cq;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				TimeUnit.MILLISECONDS.sleep(500);
				// Make chassis:
				Car c = new Car(counter++);
				System.out.println("ChassisBuilder created " + c);
				// Insert into queue
				carQueue.put(c);
			}
		} catch (InterruptedException e) {
			System.out.println("Interrupted: ChassisBuilder");
		}
		System.out.println("ChassisBuilder off");
	}
}

class Assembler implements Runnable {
	private CarQueue chassisQueue, finishingQueue;
	private Car car;
	private CyclicBarrier barrier = new CyclicBarrier(4);
	private RobotPool robotPool;

	public Assembler(CarQueue cq, CarQueue fq, RobotPool rp) {
		chassisQueue = cq;
		finishingQueue = fq;
		robotPool = rp;
	}

	public Car car() {
		return car;
	}

	public CyclicBarrier barrier() {
		return barrier;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				// Blocks until chassis is available:
				car = chassisQueue.take();
				// Hire robots to perform work:
				robotPool.hire(EngineRobot.class, this);
				robotPool.hire(DriveTrainRobot.class, this);
				robotPool.hire(WheelRobot.class, this);
				barrier.await(); // Until the robots finish
				// Put car into finishingQueue for further work
				finishingQueue.put(car);
			}
		} catch (InterruptedException e) {
			System.out.println("Exiting Assembler via interrupt");
		} catch (BrokenBarrierException e) {
			// This one we want to know about
			throw new RuntimeException(e);
		}
		System.out.println("Assembler off");
	}
}

class Reporter implements Runnable {
	private CarQueue carQueue;

	public Reporter(CarQueue cq) {
		carQueue = cq;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				System.out.println(carQueue.take());
			}
		} catch (InterruptedException e) {
			System.out.println("Exiting Reporter via interrupt");
		}
		System.out.println("Reporter off");
	}
}

abstract class Robot implements Runnable {
	private RobotPool pool;

	public Robot(RobotPool p) {
		pool = p;
	}

	protected Assembler assembler;

	public Robot assignAssembler(Assembler assembler) {
		this.assembler = assembler;
		return this;
	}

	private boolean engage = false;

	public synchronized void engage() {
		engage = true;
		notifyAll();
	}

	// The part of run() that's different for each robot:
	abstract protected void performService();

	public void run() {
		try {
			powerDown(); // Wait until needed
			while (!Thread.interrupted()) {
				performService();
				assembler.barrier().await(); // Synchronize
				// We're done with that job...
				powerDown();
			}
		} catch (InterruptedException e) {
			System.out.println("Exiting " + this + " via interrupt");
		} catch (BrokenBarrierException e) {
			// This one we want to know about
			throw new RuntimeException(e);
		}
		System.out.println(this + " off");
	}

	private synchronized void powerDown() throws InterruptedException {
		engage = false;
		assembler = null; // Disconnect from the Assembler
		// Put ourselves back in the available pool:
		pool.release(this);
		while (engage == false) // Power down
			wait();
	}

	public String toString() {
		return getClass().getName();
	}
}

class EngineRobot extends Robot {
	public EngineRobot(RobotPool pool) {
		super(pool);
	}

	protected void performService() {
		System.out.println(this + " installing engine");
		assembler.car().addEngine();
	}
}

class DriveTrainRobot extends Robot {
	public DriveTrainRobot(RobotPool pool) {
		super(pool);
	}

	protected void performService() {
		System.out.println(this + " installing DriveTrain");
		assembler.car().addDriveTrain();
	}
}

class WheelRobot extends Robot {
	public WheelRobot(RobotPool pool) {
		super(pool);
	}

	protected void performService() {
		System.out.println(this + " installing Wheels");
		assembler.car().addWheels();
	}
}

class RobotPool {
	// Quietly prevents identical entries:
	private Set<Robot> pool = new HashSet<Robot>();

	public synchronized void add(Robot r) {
		pool.add(r);
		notifyAll();
	}

	public synchronized void hire(Class<? extends Robot> robotType, Assembler d) throws InterruptedException {
		for (Robot r : pool)
			if (r.getClass().equals(robotType)) {
				pool.remove(r);
				r.assignAssembler(d);
				r.engage(); // Power it up to do the task
				return;
			}
		wait(); // None available
		hire(robotType, d); // Try again, recursively
	}

	public synchronized void release(Robot r) {
		add(r);
	}
}

public class CarBuilder {
	public static void main(String[] args) throws Exception {
		CarQueue chassisQueue = new CarQueue(), finishingQueue = new CarQueue();
		ExecutorService exec = Executors.newCachedThreadPool();
		RobotPool robotPool = new RobotPool();
		exec.execute(new EngineRobot(robotPool));
		exec.execute(new DriveTrainRobot(robotPool));
		exec.execute(new WheelRobot(robotPool));
		exec.execute(new Assembler(chassisQueue, finishingQueue, robotPool));
		exec.execute(new Reporter(finishingQueue));
		// Start everything running by producing chassis:
		exec.execute(new ChassisBuilder(chassisQueue));
		TimeUnit.SECONDS.sleep(7);
		exec.shutdownNow();
	}
}
/*
ChassisBuilder created Car 0 [ engine: false driveTrain: false wheels: false ]
com.lun.concurrency.simulation.DriveTrainRobot installing DriveTrain
com.lun.concurrency.simulation.EngineRobot installing engine
com.lun.concurrency.simulation.WheelRobot installing Wheels
Car 0 [ engine: true driveTrain: true wheels: true ]
ChassisBuilder created Car 1 [ engine: false driveTrain: false wheels: false ]
com.lun.concurrency.simulation.DriveTrainRobot installing DriveTrain
com.lun.concurrency.simulation.EngineRobot installing engine
com.lun.concurrency.simulation.WheelRobot installing Wheels
Car 1 [ engine: true driveTrain: true wheels: true ]
ChassisBuilder created Car 2 [ engine: false driveTrain: false wheels: false ]
com.lun.concurrency.simulation.DriveTrainRobot installing DriveTrain
com.lun.concurrency.simulation.EngineRobot installing engine
com.lun.concurrency.simulation.WheelRobot installing Wheels
Car 2 [ engine: true driveTrain: true wheels: true ]
ChassisBuilder created Car 3 [ engine: false driveTrain: false wheels: false ]
com.lun.concurrency.simulation.DriveTrainRobot installing DriveTrain
com.lun.concurrency.simulation.EngineRobot installing engine
com.lun.concurrency.simulation.WheelRobot installing Wheels
Car 3 [ engine: true driveTrain: true wheels: true ]
ChassisBuilder created Car 4 [ engine: false driveTrain: false wheels: false ]
com.lun.concurrency.simulation.DriveTrainRobot installing DriveTrain
com.lun.concurrency.simulation.EngineRobot installing engine
com.lun.concurrency.simulation.WheelRobot installing Wheels
Car 4 [ engine: true driveTrain: true wheels: true ]
ChassisBuilder created Car 5 [ engine: false driveTrain: false wheels: false ]
com.lun.concurrency.simulation.DriveTrainRobot installing DriveTrain
com.lun.concurrency.simulation.EngineRobot installing engine
com.lun.concurrency.simulation.WheelRobot installing Wheels
Car 5 [ engine: true driveTrain: true wheels: true ]
ChassisBuilder created Car 6 [ engine: false driveTrain: false wheels: false ]
com.lun.concurrency.simulation.DriveTrainRobot installing DriveTrain
com.lun.concurrency.simulation.EngineRobot installing engine
com.lun.concurrency.simulation.WheelRobot installing Wheels
Car 6 [ engine: true driveTrain: true wheels: true ]
ChassisBuilder created Car 7 [ engine: false driveTrain: false wheels: false ]
com.lun.concurrency.simulation.EngineRobot installing engine
com.lun.concurrency.simulation.WheelRobot installing Wheels
com.lun.concurrency.simulation.DriveTrainRobot installing DriveTrain
Car 7 [ engine: true driveTrain: true wheels: true ]
ChassisBuilder created Car 8 [ engine: false driveTrain: false wheels: false ]
com.lun.concurrency.simulation.DriveTrainRobot installing DriveTrain
com.lun.concurrency.simulation.WheelRobot installing Wheels
com.lun.concurrency.simulation.EngineRobot installing engine
Car 8 [ engine: true driveTrain: true wheels: true ]
ChassisBuilder created Car 9 [ engine: false driveTrain: false wheels: false ]
com.lun.concurrency.simulation.DriveTrainRobot installing DriveTrain
com.lun.concurrency.simulation.EngineRobot installing engine
com.lun.concurrency.simulation.WheelRobot installing Wheels
Car 9 [ engine: true driveTrain: true wheels: true ]
ChassisBuilder created Car 10 [ engine: false driveTrain: false wheels: false ]
com.lun.concurrency.simulation.DriveTrainRobot installing DriveTrain
com.lun.concurrency.simulation.WheelRobot installing Wheels
com.lun.concurrency.simulation.EngineRobot installing engine
Car 10 [ engine: true driveTrain: true wheels: true ]
ChassisBuilder created Car 11 [ engine: false driveTrain: false wheels: false ]
com.lun.concurrency.simulation.DriveTrainRobot installing DriveTrain
com.lun.concurrency.simulation.WheelRobot installing Wheels
com.lun.concurrency.simulation.EngineRobot installing engine
Car 11 [ engine: true driveTrain: true wheels: true ]
ChassisBuilder created Car 12 [ engine: false driveTrain: false wheels: false ]
com.lun.concurrency.simulation.DriveTrainRobot installing DriveTrain
com.lun.concurrency.simulation.EngineRobot installing engine
com.lun.concurrency.simulation.WheelRobot installing Wheels
Car 12 [ engine: true driveTrain: true wheels: true ]
Exiting com.lun.concurrency.simulation.DriveTrainRobot via interrupt
com.lun.concurrency.simulation.DriveTrainRobot off
Exiting com.lun.concurrency.simulation.WheelRobot via interrupt
com.lun.concurrency.simulation.WheelRobot off
Interrupted: ChassisBuilder
ChassisBuilder off
Exiting Assembler via interrupt
Assembler off
Exiting com.lun.concurrency.simulation.EngineRobot via interrupt
com.lun.concurrency.simulation.EngineRobot off
Exiting Reporter via interrupt
Reporter off
*/
