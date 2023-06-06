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
	public Order dequeueOrder(long timeout) throws InterruptedException {
		synchronized (orders){
			while(orders.isEmpty())
				orders.wait(timeout);

			var removedOrder = orders.pollFirst();
			System.out.println("ORDER " + removedOrder.getMealName() + " removed");

			return orders.pollFirst();

		}
	}

	@Override
	public int getOrderCount() {
		synchronized (orders){
			return orders.size();
		}
	}

	@Override
	public Dish dequeueDish(long timeout) throws InterruptedException {
		synchronized (dishes){
			while(dishes.isEmpty())
				dishes.wait(timeout);

			var removedDish = dishes.pollFirst();
			System.out.println("Dish " + removedDish.getMealName() + " removed");
			dishes.notifyAll();
			return removedDish;
		}
	}

	@Override
	public void enqueueDish(Dish m) throws InterruptedException {
		synchronized (dishes){
			while(dishes.size() >= getMaxDishes())
				dishes.wait();

			dishes.addLast(m);
			System.out.println("Dish " + m.getMealName() + " added");
			dishes.notifyAll();
		}
	}

	@Override
	public int getDishesCount() {
		synchronized (dishes){
			return dishes.size();
		}
	}
}
