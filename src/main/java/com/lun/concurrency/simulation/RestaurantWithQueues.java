//: concurrency/restaurant2/RestaurantWithQueues.java
// {Args: 5}
package com.lun.concurrency.simulation;

import java.util.concurrent.*;

import com.lun.enumerated.menu.Course;
import com.lun.enumerated.menu.Food;

import java.util.*;

// This is given to the waiter, who gives it to the chef:
class Order { // (A data-transfer object)
	private static int counter = 0;
	private final int id = counter++;
	private final Customer customer;
	private final WaitPerson waitPerson;
	private final Food food;

	public Order(Customer cust, WaitPerson wp, Food f) {
		customer = cust;
		waitPerson = wp;
		food = f;
	}

	public Food item() {
		return food;
	}

	public Customer getCustomer() {
		return customer;
	}

	public WaitPerson getWaitPerson() {
		return waitPerson;
	}

	public String toString() {
		return "Order: " + id + " item: " + food + " for: " + customer + " served by: " + waitPerson;
	}
}

// This is what comes back from the chef:
class Plate {
	private final Order order;
	private final Food food;

	public Plate(Order ord, Food f) {
		order = ord;
		food = f;
	}

	public Order getOrder() {
		return order;
	}

	public Food getFood() {
		return food;
	}

	public String toString() {
		return food.toString();
	}
}

class Customer implements Runnable {
	private static int counter = 0;
	private final int id = counter++;
	private final WaitPerson waitPerson;
	// Only one course at a time can be received:
	private SynchronousQueue<Plate> placeSetting = new SynchronousQueue<Plate>();

	public Customer(WaitPerson w) {
		waitPerson = w;
	}

	public void deliver(Plate p) throws InterruptedException {
		// Only blocks if customer is still
		// eating the previous course:
		placeSetting.put(p);
	}

	public void run() {
		for (Course course : Course.values()) {
			Food food = course.randomSelection();
			try {
				waitPerson.placeOrder(this, food);
				// Blocks until course has been delivered:
				System.out.println(this + "eating " + placeSetting.take());
			} catch (InterruptedException e) {
				System.out.println(this + "waiting for " + course + " interrupted");
				break;
			}
		}
		System.out.println(this + "finished meal, leaving");
	}

	public String toString() {
		return "Customer " + id + " ";
	}
}

class WaitPerson implements Runnable {
	private static int counter = 0;
	private final int id = counter++;
	private final Restaurant restaurant;
	BlockingQueue<Plate> filledOrders = new LinkedBlockingQueue<Plate>();

	public WaitPerson(Restaurant rest) {
		restaurant = rest;
	}

	public void placeOrder(Customer cust, Food food) {
		try {
			// Shouldn't actually block because this is
			// a LinkedBlockingQueue with no size limit:
			restaurant.orders.put(new Order(cust, this, food));
		} catch (InterruptedException e) {
			System.out.println(this + " placeOrder interrupted");
		}
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				// Blocks until a course is ready
				Plate plate = filledOrders.take();
				System.out.println(this + "received " + plate + " delivering to " + plate.getOrder().getCustomer());
				plate.getOrder().getCustomer().deliver(plate);
			}
		} catch (InterruptedException e) {
			System.out.println(this + " interrupted");
		}
		System.out.println(this + " off duty");
	}

	public String toString() {
		return "WaitPerson " + id + " ";
	}
}

class Chef implements Runnable {
	private static int counter = 0;
	private final int id = counter++;
	private final Restaurant restaurant;
	private static Random rand = new Random(47);

	public Chef(Restaurant rest) {
		restaurant = rest;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				// Blocks until an order appears:
				Order order = restaurant.orders.take();
				Food requestedItem = order.item();
				// Time to prepare order:
				TimeUnit.MILLISECONDS.sleep(rand.nextInt(500));
				Plate plate = new Plate(order, requestedItem);
				order.getWaitPerson().filledOrders.put(plate);
			}
		} catch (InterruptedException e) {
			System.out.println(this + " interrupted");
		}
		System.out.println(this + " off duty");
	}

	public String toString() {
		return "Chef " + id + " ";
	}
}

class Restaurant implements Runnable {
	private List<WaitPerson> waitPersons = new ArrayList<WaitPerson>();
	private List<Chef> chefs = new ArrayList<Chef>();
	private ExecutorService exec;
	private static Random rand = new Random(47);
	BlockingQueue<Order> orders = new LinkedBlockingQueue<Order>();

	public Restaurant(ExecutorService e, int nWaitPersons, int nChefs) {
		exec = e;
		for (int i = 0; i < nWaitPersons; i++) {
			WaitPerson waitPerson = new WaitPerson(this);
			waitPersons.add(waitPerson);
			exec.execute(waitPerson);
		}
		for (int i = 0; i < nChefs; i++) {
			Chef chef = new Chef(this);
			chefs.add(chef);
			exec.execute(chef);
		}
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				// A new customer arrives; assign a WaitPerson:
				WaitPerson wp = waitPersons.get(rand.nextInt(waitPersons.size()));
				Customer c = new Customer(wp);
				exec.execute(c);
				TimeUnit.MILLISECONDS.sleep(100);
			}
		} catch (InterruptedException e) {
			System.out.println("Restaurant interrupted");
		}
		System.out.println("Restaurant closing");
	}
}

public class RestaurantWithQueues {
	public static void main(String[] args) throws Exception {
		ExecutorService exec = Executors.newCachedThreadPool();
		Restaurant restaurant = new Restaurant(exec, 5, 2);
		exec.execute(restaurant);
		if (args.length > 0) // Optional argument
			TimeUnit.SECONDS.sleep(new Integer(args[0]));
		else {
			System.out.println("Press 'Enter' to quit");
			System.in.read();
		}
		exec.shutdownNow();
	}
} 
/*
Press 'Enter' to quit
WaitPerson 0 received SPRING_ROLLS delivering to Customer 1 
Customer 1 eating SPRING_ROLLS
WaitPerson 3 received SPRING_ROLLS delivering to Customer 0 
Customer 0 eating SPRING_ROLLS
WaitPerson 0 received BURRITO delivering to Customer 1 
Customer 1 eating BURRITO
WaitPerson 3 received SPRING_ROLLS delivering to Customer 2 
Customer 2 eating SPRING_ROLLS
WaitPerson 3 received BURRITO delivering to Customer 0 
Customer 0 eating BURRITO
WaitPerson 1 received SPRING_ROLLS delivering to Customer 3 
Customer 3 eating SPRING_ROLLS
WaitPerson 0 received FRUIT delivering to Customer 1 
Customer 1 eating FRUIT
WaitPerson 4 received SALAD delivering to Customer 5 
Customer 5 eating SALAD
WaitPerson 1 received SPRING_ROLLS delivering to Customer 4 
Customer 4 eating SPRING_ROLLS
WaitPerson 3 received SOUP delivering to Customer 6 
Customer 6 eating SOUP
WaitPerson 0 received SALAD delivering to Customer 7 
Customer 7 eating SALAD
WaitPerson 3 received LASAGNE delivering to Customer 2 
Customer 2 eating LASAGNE
WaitPerson 3 received GELATO delivering to Customer 0 
Customer 0 eating GELATO
WaitPerson 2 received SOUP delivering to Customer 8 
Customer 8 eating SOUP
WaitPerson 2 received SPRING_ROLLS delivering to Customer 9 
Customer 9 eating SPRING_ROLLS
WaitPerson 3 received SOUP delivering to Customer 10 
Customer 10 eating SOUP
WaitPerson 3 received SOUP delivering to Customer 11 
Customer 11 eating SOUP
WaitPerson 0 received BLACK_COFFEE delivering to Customer 1 
Customer 1 eating BLACK_COFFEE
Customer 1 finished meal, leaving
WaitPerson 1 received HUMMOUS delivering to Customer 3 
Customer 3 eating HUMMOUS
WaitPerson 1 received SOUP delivering to Customer 12 
Customer 12 eating SOUP
WaitPerson 1 received LASAGNE delivering to Customer 4 
Customer 4 eating LASAGNE
WaitPerson 4 received SOUP delivering to Customer 13 
Customer 13 eating SOUP
WaitPerson 4 received HUMMOUS delivering to Customer 5 
Customer 5 eating HUMMOUS
WaitPerson 3 received PAD_THAI delivering to Customer 6 
Customer 6 eating PAD_THAI
WaitPerson 4 received SALAD delivering to Customer 14 
Customer 14 eating SALAD
WaitPerson 0 received PAD_THAI delivering to Customer 7 
Customer 7 eating PAD_THAI
WaitPerson 3 received TIRAMISU delivering to Customer 2 
Customer 2 eating TIRAMISU
WaitPerson 3 received SALAD delivering to Customer 15 
Customer 15 eating SALAD
WaitPerson 3 received CAPPUCCINO delivering to Customer 0 
Customer 0 eating CAPPUCCINO
Customer 0 finished meal, leaving
WaitPerson 3 received SPRING_ROLLS delivering to Customer 16 
Customer 16 eating SPRING_ROLLS
WaitPerson 2 received LASAGNE delivering to Customer 8 
Customer 8 eating LASAGNE
WaitPerson 1 received SOUP delivering to Customer 17 
Customer 17 eating SOUP
WaitPerson 0 received SOUP delivering to Customer 18 
Customer 18 eating SOUP
WaitPerson 2 received LASAGNE delivering to Customer 9 
Customer 9 eating LASAGNE
WaitPerson 3 received SOUP delivering to Customer 19 
Customer 19 eating SOUP
WaitPerson 1 received SOUP delivering to Customer 20 
Customer 20 eating SOUP
WaitPerson 3 received LASAGNE delivering to Customer 10 
Customer 10 eating LASAGNE
WaitPerson 1 received SOUP delivering to Customer 22 
Customer 22 eating SOUP
WaitPerson 0 received SALAD delivering to Customer 21 
Customer 21 eating SALAD
WaitPerson 3 received LASAGNE delivering to Customer 11 
Customer 11 eating LASAGNE
WaitPerson 2 received SALAD delivering to Customer 23 
Customer 23 eating SALAD
WaitPerson 1 received GELATO delivering to Customer 3 
Customer 3 eating GELATO
WaitPerson 3 received SOUP delivering to Customer 25 
Customer 25 eating SOUP
WaitPerson 4 received SALAD delivering to Customer 24 
Customer 24 eating SALAD
WaitPerson 1 received SOUP delivering to Customer 26 
Customer 26 eating SOUP
WaitPerson 1 received TIRAMISU delivering to Customer 4 
Customer 4 eating TIRAMISU
WaitPerson 1 received LASAGNE delivering to Customer 12 
Customer 12 eating LASAGNE
WaitPerson 0 received SOUP delivering to Customer 27 
Customer 27 eating SOUP
WaitPerson 4 received PAD_THAI delivering to Customer 13 
Customer 13 eating PAD_THAI
WaitPerson 4 received GELATO delivering to Customer 5 
Customer 5 eating GELATO
WaitPerson 0 received SALAD delivering to Customer 28 
Customer 28 eating SALAD
WaitPerson 2 received SOUP delivering to Customer 29 
Customer 29 eating SOUP
WaitPerson 3 received CREME_CARAMEL delivering to Customer 6 
Customer 6 eating CREME_CARAMEL
WaitPerson 4 received SOUP delivering to Customer 30 
Customer 30 eating SOUP
WaitPerson 4 received VINDALOO delivering to Customer 14 
Customer 14 eating VINDALOO
WaitPerson 0 received GELATO delivering to Customer 7 
Customer 7 eating GELATO
WaitPerson 4 received SPRING_ROLLS delivering to Customer 31 
Customer 31 eating SPRING_ROLLS
WaitPerson 0 received SALAD delivering to Customer 32 
Customer 32 eating SALAD
WaitPerson 1 received SOUP delivering to Customer 33 
Customer 33 eating SOUP
WaitPerson 3 received DECAF_COFFEE delivering to Customer 2 
Customer 2 eating DECAF_COFFEE
Customer 2 finished meal, leaving
WaitPerson 3 received LENTILS delivering to Customer 15 
Customer 15 eating LENTILS
WaitPerson 2 received SPRING_ROLLS delivering to Customer 34 
Customer 34 eating SPRING_ROLLS
WaitPerson 3 received SPRING_ROLLS delivering to Customer 36 
Customer 36 eating SPRING_ROLLS
WaitPerson 1 received SALAD delivering to Customer 35 
Customer 35 eating SALAD
WaitPerson 3 received PAD_THAI delivering to Customer 16 
Customer 16 eating PAD_THAI
WaitPerson 4 received SOUP delivering to Customer 38 
Customer 38 eating SOUP
WaitPerson 4 received SALAD delivering to Customer 37 
Customer 37 eating SALAD
WaitPerson 2 received FRUIT delivering to Customer 8 
Customer 8 eating FRUIT
WaitPerson 3 received SALAD delivering to Customer 40 
Customer 40 eating SALAD
WaitPerson 0 received SOUP delivering to Customer 39 
Customer 39 eating SOUP
WaitPerson 1 received LASAGNE delivering to Customer 17 
Customer 17 eating LASAGNE
WaitPerson 1 received SPRING_ROLLS delivering to Customer 41 
Customer 41 eating SPRING_ROLLS
WaitPerson 3 received SALAD delivering to Customer 42 
Customer 42 eating SALAD
WaitPerson 0 received LASAGNE delivering to Customer 18 
Customer 18 eating LASAGNE
WaitPerson 2 received SALAD delivering to Customer 43 
Customer 43 eating SALAD
WaitPerson 2 received CREME_CARAMEL delivering to Customer 9 
Customer 9 eating CREME_CARAMEL
WaitPerson 4 received SPRING_ROLLS delivering to Customer 45 
Customer 45 eating SPRING_ROLLS
WaitPerson 1 received SOUP delivering to Customer 44 
Customer 44 eating SOUP
WaitPerson 0 received SOUP delivering to Customer 46 
Customer 46 eating SOUP
WaitPerson 3 received VINDALOO delivering to Customer 19 
Customer 19 eating VINDALOO
WaitPerson 1 received PAD_THAI delivering to Customer 20 
Customer 20 eating PAD_THAI
WaitPerson 2 received SPRING_ROLLS delivering to Customer 47 
Customer 47 eating SPRING_ROLLS
WaitPerson 3 received TIRAMISU delivering to Customer 10 
Customer 10 eating TIRAMISU
WaitPerson 1 received SALAD delivering to Customer 49 
Customer 49 eating SALAD
WaitPerson 2 received SPRING_ROLLS delivering to Customer 50 
Customer 50 eating SPRING_ROLLS
WaitPerson 1 received BURRITO delivering to Customer 22 
Customer 22 eating BURRITO
WaitPerson 3 received SALAD delivering to Customer 51 
Customer 51 eating SALAD
WaitPerson 1 received SOUP delivering to Customer 48 
Customer 48 eating SOUP
WaitPerson 0 received PAD_THAI delivering to Customer 21 
Customer 21 eating PAD_THAI
WaitPerson 4 received SALAD delivering to Customer 52 
Customer 52 eating SALAD
WaitPerson 0 received SOUP delivering to Customer 53 
Customer 53 eating SOUP
WaitPerson 3 received CREME_CARAMEL delivering to Customer 11 
Customer 11 eating CREME_CARAMEL
WaitPerson 2 received LASAGNE delivering to Customer 23 
Customer 23 eating LASAGNE
WaitPerson 0 received SALAD delivering to Customer 54 
Customer 54 eating SALAD
WaitPerson 4 received SPRING_ROLLS delivering to Customer 55 
Customer 55 eating SPRING_ROLLS
WaitPerson 1 received TEA delivering to Customer 3 
Customer 3 eating TEA
Customer 3 finished meal, leaving
WaitPerson 3 received BURRITO delivering to Customer 25 
Customer 25 eating BURRITO
WaitPerson 1 received SOUP delivering to Customer 56 
Customer 56 eating SOUP
WaitPerson 2 received SALAD delivering to Customer 57 
Customer 57 eating SALAD
WaitPerson 4 received VINDALOO delivering to Customer 24 
Customer 24 eating VINDALOO
WaitPerson 1 received SPRING_ROLLS delivering to Customer 58 
Customer 58 eating SPRING_ROLLS
WaitPerson 2 received SPRING_ROLLS delivering to Customer 59 
Customer 59 eating SPRING_ROLLS
WaitPerson 1 received LENTILS delivering to Customer 26 
Customer 26 eating LENTILS
WaitPerson 2 received SALAD delivering to Customer 60 
Customer 60 eating SALAD
WaitPerson 1 received CAPPUCCINO delivering to Customer 4 
Customer 4 eating CAPPUCCINO
Customer 4 finished meal, leaving
WaitPerson 4 received SPRING_ROLLS delivering to Customer 61 
Customer 61 eating SPRING_ROLLS
WaitPerson 1 received CREME_CARAMEL delivering to Customer 12 
Customer 12 eating CREME_CARAMEL
WaitPerson 0 received LENTILS delivering to Customer 27 
Customer 27 eating LENTILS
WaitPerson 2 received SOUP delivering to Customer 62 
Customer 62 eating SOUP
WaitPerson 2 received SALAD delivering to Customer 63 
Customer 63 eating SALAD
WaitPerson 4 received SPRING_ROLLS delivering to Customer 64 
Customer 64 eating SPRING_ROLLS
WaitPerson 4 received CREME_CARAMEL delivering to Customer 13 
Customer 13 eating CREME_CARAMEL
WaitPerson 0 received SPRING_ROLLS delivering to Customer 66 
Customer 66 eating SPRING_ROLLS
WaitPerson 2 received SPRING_ROLLS delivering to Customer 65 
Customer 65 eating SPRING_ROLLS
WaitPerson 3 received SPRING_ROLLS delivering to Customer 67 
Customer 67 eating SPRING_ROLLS
WaitPerson 4 received DECAF_COFFEE delivering to Customer 5 
Customer 5 eating DECAF_COFFEE
Customer 5 finished meal, leaving
WaitPerson 1 received SOUP delivering to Customer 68 
Customer 68 eating SOUP
WaitPerson 4 received SOUP delivering to Customer 69 
Customer 69 eating SOUP
WaitPerson 0 received BURRITO delivering to Customer 28 
Customer 28 eating BURRITO
WaitPerson 2 received HUMMOUS delivering to Customer 29 
Customer 29 eating HUMMOUS
WaitPerson 2 received SALAD delivering to Customer 70 
Customer 70 eating SALAD
WaitPerson 4 received SALAD delivering to Customer 72 
Customer 72 eating SALAD
WaitPerson 0 received SOUP delivering to Customer 71 
Customer 71 eating SOUP
WaitPerson 3 received TEA delivering to Customer 6 
Customer 6 eating TEA
Customer 6 finished meal, leaving
WaitPerson 4 received HUMMOUS delivering to Customer 30 
Customer 30 eating HUMMOUS
WaitPerson 4 received SALAD delivering to Customer 73 
Customer 73 eating SALAD
WaitPerson 4 received BLACK_FOREST_CAKE delivering to Customer 14 
Customer 14 eating BLACK_FOREST_CAKE
WaitPerson 2 received SALAD delivering to Customer 74 
Customer 74 eating SALAD
WaitPerson 0 received LATTE delivering to Customer 7 
Customer 7 eating LATTE
Customer 7 finished meal, leaving
WaitPerson 4 received SPRING_ROLLS delivering to Customer 75 
Customer 75 eating SPRING_ROLLS
WaitPerson 4 received VINDALOO delivering to Customer 31 
Customer 31 eating VINDALOO
WaitPerson 3 received SALAD delivering to Customer 77 
Customer 77 eating SALAD
WaitPerson 3 received SALAD delivering to Customer 76 
Customer 76 eating SALAD
WaitPerson 0 received PAD_THAI delivering to Customer 32 
Customer 32 eating PAD_THAI
WaitPerson 3 received SOUP delivering to Customer 78 
Customer 78 eating SOUP
WaitPerson 1 received HUMMOUS delivering to Customer 33 
Customer 33 eating HUMMOUS
WaitPerson 1 received SPRING_ROLLS delivering to Customer 80 
Customer 80 eating SPRING_ROLLS
WaitPerson 3 received SALAD delivering to Customer 79 
Customer 79 eating SALAD

Customer 12 waiting for COFFEE interrupted
Customer 12 finished meal, leaving
Customer 93 waiting for APPETIZER interrupted
Customer 93 finished meal, leaving
Customer 180 waiting for APPETIZER interrupted
Customer 180 finished meal, leaving
Customer 114 waiting for APPETIZER interrupted
Customer 114 finished meal, leaving
Customer 26 waiting for DESSERT interrupted
Customer 26 finished meal, leaving
Customer 150 waiting for APPETIZER interrupted
Customer 150 finished meal, leaving
Customer 120 waiting for APPETIZER interrupted
Customer 120 finished meal, leaving
Customer 112 waiting for APPETIZER interrupted
Customer 112 finished meal, leaving
Customer 144 waiting for APPETIZER interrupted
Customer 144 finished meal, leaving
Customer 179 waiting for APPETIZER interrupted
Customer 179 finished meal, leaving
Customer 51 waiting for MAINCOURSE interrupted
Customer 51 finished meal, leaving
Customer 55 waiting for MAINCOURSE interrupted
Customer 55 finished meal, leaving
Customer 177 waiting for APPETIZER interrupted
Customer 177 finished meal, leaving
Customer 168 waiting for APPETIZER interrupted
Customer 168 finished meal, leaving
Customer 159 waiting for APPETIZER interrupted
Customer 159 finished meal, leaving
Customer 171 waiting for APPETIZER interrupted
Customer 171 finished meal, leaving
Customer 53 waiting for MAINCOURSE interrupted
Customer 53 finished meal, leaving
Customer 24 waiting for DESSERT interrupted
Customer 24 finished meal, leaving
Customer 128 waiting for APPETIZER interrupted
Customer 128 finished meal, leaving
Customer 13 waiting for COFFEE interrupted
Customer 13 finished meal, leaving
Customer 129 waiting for APPETIZER interrupted
Customer 129 finished meal, leaving
Customer 67 waiting for MAINCOURSE interrupted
Customer 67 finished meal, leaving
Customer 69 waiting for MAINCOURSE interrupted
Customer 69 finished meal, leaving
Customer 23 waiting for DESSERT interrupted
Customer 23 finished meal, leaving
Customer 173 waiting for APPETIZER interrupted
Customer 173 finished meal, leaving
Customer 138 waiting for APPETIZER interrupted
Customer 138 finished meal, leaving
Customer 77 waiting for MAINCOURSE interrupted
Customer 77 finished meal, leaving
Customer 34 waiting for MAINCOURSE interrupted
Customer 34 finished meal, leaving
Customer 169 waiting for APPETIZER interrupted
Customer 169 finished meal, leaving
Customer 155 waiting for APPETIZER interrupted
Customer 155 finished meal, leaving
WaitPerson 1  interrupted
WaitPerson 1  off duty
Customer 57 waiting for MAINCOURSE interrupted
Customer 57 finished meal, leaving
Customer 140 waiting for APPETIZER interrupted
Customer 140 finished meal, leaving
Customer 90 waiting for APPETIZER interrupted
Customer 90 finished meal, leaving
Customer 63 waiting for MAINCOURSE interrupted
Customer 63 finished meal, leaving
Customer 28 waiting for DESSERT interrupted
Customer 28 finished meal, leaving
Customer 21 waiting for DESSERT interrupted
Customer 21 finished meal, leaving
Customer 100 waiting for APPETIZER interrupted
Customer 100 finished meal, leaving
Customer 146 waiting for APPETIZER interrupted
Customer 146 finished meal, leaving
Customer 17 waiting for DESSERT interrupted
Customer 9 waiting for COFFEE interrupted
Customer 9 finished meal, leaving
Customer 17 finished meal, leaving
Customer 157 waiting for APPETIZER interrupted
Customer 157 finished meal, leaving
Customer 108 waiting for APPETIZER interrupted
Customer 108 finished meal, leaving
Customer 45 waiting for MAINCOURSE interrupted
Customer 45 finished meal, leaving
Customer 43 waiting for MAINCOURSE interrupted
Customer 43 finished meal, leaving
Customer 164 waiting for APPETIZER interrupted
Customer 164 finished meal, leaving
Customer 88 waiting for APPETIZER interrupted
Customer 88 finished meal, leaving
Customer 161 waiting for APPETIZER interrupted
Customer 161 finished meal, leaving
Customer 92 waiting for APPETIZER interrupted
Customer 92 finished meal, leaving
Customer 106 waiting for APPETIZER interrupted
Customer 106 finished meal, leaving
Customer 104 waiting for APPETIZER interrupted
Customer 104 finished meal, leaving
Customer 73 waiting for MAINCOURSE interrupted
Customer 94 waiting for APPETIZER interrupted
Customer 94 finished meal, leaving
Customer 175 waiting for APPETIZER interrupted
Customer 175 finished meal, leaving
Customer 73 finished meal, leaving
Restaurant interrupted
Restaurant closing
Customer 61 waiting for MAINCOURSE interrupted
Customer 61 finished meal, leaving
Customer 135 waiting for APPETIZER interrupted
Customer 135 finished meal, leaving
Customer 98 waiting for APPETIZER interrupted
Customer 98 finished meal, leaving
Customer 152 waiting for APPETIZER interrupted
Customer 152 finished meal, leaving
Customer 102 waiting for APPETIZER interrupted
Customer 102 finished meal, leaving
Customer 96 waiting for APPETIZER interrupted
Customer 96 finished meal, leaving
Customer 59 waiting for MAINCOURSE interrupted
Customer 59 finished meal, leaving
Customer 166 waiting for APPETIZER interrupted
Customer 166 finished meal, leaving
Customer 133 waiting for APPETIZER interrupted
Customer 133 finished meal, leaving
Customer 131 waiting for APPETIZER interrupted
Customer 131 finished meal, leaving
Customer 124 waiting for APPETIZER interrupted
Customer 124 finished meal, leaving
Customer 153 waiting for APPETIZER interrupted
Customer 153 finished meal, leaving
Customer 79 waiting for MAINCOURSE interrupted
Customer 79 finished meal, leaving
Customer 75 waiting for MAINCOURSE interrupted
Customer 75 finished meal, leaving
Customer 84 waiting for APPETIZER interrupted
Customer 84 finished meal, leaving
Customer 86 waiting for APPETIZER interrupted
Customer 116 waiting for APPETIZER interrupted
Customer 116 finished meal, leaving
Customer 86 finished meal, leaving
Customer 49 waiting for MAINCOURSE interrupted
Customer 49 finished meal, leaving
Customer 47 waiting for MAINCOURSE interrupted
Customer 47 finished meal, leaving
Customer 36 waiting for MAINCOURSE interrupted
Customer 36 finished meal, leaving
Customer 122 waiting for APPETIZER interrupted
Customer 122 finished meal, leaving
Customer 19 waiting for DESSERT interrupted
Customer 19 finished meal, leaving
Customer 41 waiting for MAINCOURSE interrupted
Customer 41 finished meal, leaving
Customer 71 waiting for MAINCOURSE interrupted
Customer 71 finished meal, leaving
Customer 30 waiting for DESSERT interrupted
Customer 30 finished meal, leaving
WaitPerson 3  interrupted
WaitPerson 3  off duty
Chef 0  interrupted
Chef 0  off duty
Customer 148 waiting for APPETIZER interrupted
Customer 148 finished meal, leaving
Customer 11 waiting for COFFEE interrupted
Customer 142 waiting for APPETIZER interrupted
Customer 142 finished meal, leaving
Customer 11 finished meal, leaving
Customer 32 waiting for DESSERT interrupted
Customer 32 finished meal, leaving
Customer 126 waiting for APPETIZER interrupted
Customer 126 finished meal, leaving
Customer 39 waiting for MAINCOURSE interrupted
Customer 39 finished meal, leaving
Customer 82 waiting for APPETIZER interrupted
Customer 82 finished meal, leaving
Customer 110 waiting for APPETIZER interrupted
Customer 110 finished meal, leaving
Customer 65 waiting for MAINCOURSE interrupted
Customer 65 finished meal, leaving
Customer 118 waiting for APPETIZER interrupted
Customer 118 finished meal, leaving
Customer 15 waiting for DESSERT interrupted
Customer 15 finished meal, leaving
Customer 76 waiting for MAINCOURSE interrupted
Customer 76 finished meal, leaving
WaitPerson 2  interrupted
WaitPerson 2  off duty
Customer 31 waiting for DESSERT interrupted
Customer 31 finished meal, leaving
Customer 134 waiting for APPETIZER interrupted
Customer 134 finished meal, leaving
Customer 160 waiting for APPETIZER interrupted
Customer 160 finished meal, leaving
Customer 83 waiting for APPETIZER interrupted
Customer 83 finished meal, leaving
Customer 10 waiting for COFFEE interrupted
Customer 10 finished meal, leaving
Customer 40 waiting for MAINCOURSE interrupted
Customer 40 finished meal, leaving
Customer 119 waiting for APPETIZER interrupted
Customer 119 finished meal, leaving
Customer 50 waiting for MAINCOURSE interrupted
Customer 50 finished meal, leaving
Customer 95 waiting for APPETIZER interrupted
Customer 95 finished meal, leaving
Customer 145 waiting for APPETIZER interrupted
Customer 145 finished meal, leaving
Customer 109 waiting for APPETIZER interrupted
Customer 109 finished meal, leaving
Customer 91 waiting for APPETIZER interrupted
Customer 91 finished meal, leaving
Customer 170 waiting for APPETIZER interrupted
Customer 170 finished meal, leaving
Customer 37 waiting for MAINCOURSE interrupted
Customer 37 finished meal, leaving
Customer 66 waiting for MAINCOURSE interrupted
Customer 66 finished meal, leaving
Customer 89 waiting for APPETIZER interrupted
Customer 89 finished meal, leaving
Customer 136 waiting for APPETIZER interrupted
Customer 136 finished meal, leaving
Customer 33 waiting for DESSERT interrupted
Customer 33 finished meal, leaving
Customer 38 waiting for MAINCOURSE interrupted
Customer 38 finished meal, leaving
Customer 16 waiting for DESSERT interrupted
Customer 16 finished meal, leaving
Customer 127 waiting for APPETIZER interrupted
Customer 127 finished meal, leaving
Customer 18 waiting for DESSERT interrupted
Customer 18 finished meal, leaving
Customer 105 waiting for APPETIZER interrupted
Customer 105 finished meal, leaving
Customer 35 waiting for MAINCOURSE interrupted
Customer 35 finished meal, leaving
Customer 143 waiting for APPETIZER interrupted
Customer 143 finished meal, leaving
Customer 149 waiting for APPETIZER interrupted
Customer 149 finished meal, leaving
Customer 58 waiting for MAINCOURSE interrupted
Customer 58 finished meal, leaving
Customer 162 waiting for APPETIZER interrupted
Customer 162 finished meal, leaving
Customer 42 waiting for MAINCOURSE interrupted
Customer 42 finished meal, leaving
Customer 56 waiting for MAINCOURSE interrupted
Customer 56 finished meal, leaving
Customer 97 waiting for APPETIZER interrupted
Customer 97 finished meal, leaving
Customer 70 waiting for MAINCOURSE interrupted
Customer 70 finished meal, leaving
Customer 132 waiting for APPETIZER interrupted
Customer 132 finished meal, leaving
Customer 158 waiting for APPETIZER interrupted
Customer 158 finished meal, leaving
Customer 176 waiting for APPETIZER interrupted
Customer 176 finished meal, leaving
Chef 1  interrupted
Chef 1  off duty
Customer 130 waiting for APPETIZER interrupted
Customer 130 finished meal, leaving
Customer 156 waiting for APPETIZER interrupted
Customer 156 finished meal, leaving
Customer 8 waiting for COFFEE interrupted
Customer 8 finished meal, leaving
Customer 85 waiting for APPETIZER interrupted
Customer 85 finished meal, leaving
Customer 20 waiting for DESSERT interrupted
Customer 20 finished meal, leaving
Customer 115 waiting for APPETIZER interrupted
Customer 115 finished meal, leaving
Customer 178 waiting for APPETIZER interrupted
Customer 178 finished meal, leaving
Customer 103 waiting for APPETIZER interrupted
Customer 103 finished meal, leaving
Customer 117 waiting for APPETIZER interrupted
Customer 117 finished meal, leaving
Customer 68 waiting for MAINCOURSE interrupted
Customer 68 finished meal, leaving
Customer 101 waiting for APPETIZER interrupted
Customer 101 finished meal, leaving
Customer 48 waiting for MAINCOURSE interrupted
Customer 48 finished meal, leaving
Customer 99 waiting for APPETIZER interrupted
Customer 99 finished meal, leaving
Customer 139 waiting for APPETIZER interrupted
Customer 139 finished meal, leaving
Customer 60 waiting for MAINCOURSE interrupted
Customer 60 finished meal, leaving
Customer 46 waiting for MAINCOURSE interrupted
Customer 46 finished meal, leaving
Customer 121 waiting for APPETIZER interrupted
Customer 121 finished meal, leaving
Customer 80 waiting for MAINCOURSE interrupted
Customer 80 finished meal, leaving
Customer 27 waiting for DESSERT interrupted
Customer 27 finished meal, leaving
Customer 107 waiting for APPETIZER interrupted
Customer 107 finished meal, leaving
Customer 123 waiting for APPETIZER interrupted
Customer 123 finished meal, leaving
Customer 54 waiting for MAINCOURSE interrupted
Customer 54 finished meal, leaving
Customer 172 waiting for APPETIZER interrupted
Customer 172 finished meal, leaving
Customer 25 waiting for DESSERT interrupted
Customer 25 finished meal, leaving
Customer 14 waiting for COFFEE interrupted
Customer 14 finished meal, leaving
Customer 137 waiting for APPETIZER interrupted
Customer 137 finished meal, leaving
WaitPerson 0  interrupted
WaitPerson 0  off duty
Customer 78 waiting for MAINCOURSE interrupted
Customer 78 finished meal, leaving
Customer 151 waiting for APPETIZER interrupted
Customer 151 finished meal, leaving
Customer 81 waiting for APPETIZER interrupted
Customer 81 finished meal, leaving
Customer 52 waiting for MAINCOURSE interrupted
Customer 52 finished meal, leaving
Customer 111 waiting for APPETIZER interrupted
Customer 111 finished meal, leaving
Customer 113 waiting for APPETIZER interrupted
Customer 113 finished meal, leaving
Customer 87 waiting for APPETIZER interrupted
Customer 87 finished meal, leaving
Customer 125 waiting for APPETIZER interrupted
Customer 125 finished meal, leaving
Customer 44 waiting for MAINCOURSE interrupted
Customer 44 finished meal, leaving
WaitPerson 4  interrupted
WaitPerson 4  off duty
Customer 64 waiting for MAINCOURSE interrupted
Customer 64 finished meal, leaving
Customer 29 waiting for DESSERT interrupted
Customer 29 finished meal, leaving
Customer 154 waiting for APPETIZER interrupted
Customer 154 finished meal, leaving
Customer 165 waiting for APPETIZER interrupted
Customer 165 finished meal, leaving
Customer 167 waiting for APPETIZER interrupted
Customer 167 finished meal, leaving
Customer 174 waiting for APPETIZER interrupted
Customer 174 finished meal, leaving
Customer 163 waiting for APPETIZER interrupted
Customer 163 finished meal, leaving
Customer 141 waiting for APPETIZER interrupted
Customer 141 finished meal, leaving
Customer 62 waiting for MAINCOURSE interrupted
Customer 62 finished meal, leaving
Customer 147 waiting for APPETIZER interrupted
Customer 147 finished meal, leaving
Customer 74 waiting for MAINCOURSE interrupted
Customer 74 finished meal, leaving
Customer 72 waiting for MAINCOURSE interrupted
Customer 72 finished meal, leaving
Customer 22 waiting for DESSERT interrupted
Customer 22 finished meal, leaving

*/
