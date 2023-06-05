package ohm.softa.a10.kitchen;

import ohm.softa.a10.model.Dish;
import ohm.softa.a10.model.Order;

import java.util.Deque;
import java.util.LinkedList;

public class KitchenHatchImpl implements KitchenHatch{
	private int maxMeals;
	private Deque<Order> orders;
	private Deque<Dish> dishes;


	public KitchenHatchImpl(int maxMeals, Deque<Order> orders){
		this.maxMeals = maxMeals;
		this.orders = orders;
		this.dishes = new LinkedList<Dish>();
	}

	@Override
	public int getMaxDishes() {
		return maxMeals;
	}

	@Override
	synchronized public Order dequeueOrder(long timeout) throws InterruptedException {
		while(orders.isEmpty())
				wait(timeout);

		return orders.pollFirst();
	}

	@Override
	synchronized public int getOrderCount() {
		return orders.size();
	}

	@Override
	synchronized public Dish dequeueDish(long timeout) throws InterruptedException {
		while(dishes.isEmpty())
			wait(timeout);

		var removedDish = dishes.pollFirst();
		notifyAll();
		return removedDish;
	}

	@Override
	synchronized public void enqueueDish(Dish m) throws InterruptedException {
		while(dishes.size() >= getMaxDishes())
			wait();

		dishes.addLast(m);
		notifyAll();
	}

	@Override
	synchronized public int getDishesCount() {
		return dishes.size();
	}
}
