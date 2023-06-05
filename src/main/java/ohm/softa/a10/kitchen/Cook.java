package ohm.softa.a10.kitchen;

import ohm.softa.a10.internals.displaying.ProgressReporter;
import ohm.softa.a10.model.Dish;
import ohm.softa.a10.model.Order;

public class Cook implements Runnable{

	KitchenHatch kitchenHatch;
	ProgressReporter progressReporter;
	String name;

	public Cook(String name, KitchenHatch kitchenHatch, ProgressReporter progressReporter){
		this.kitchenHatch = kitchenHatch;
		this.progressReporter = progressReporter;
		this.name = name;
	}

	@Override
	public void run() {
		Order order = null;
		try {
			order = kitchenHatch.dequeueOrder();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		while(!order.equals(null)){
			Dish dish = new Dish(order.getMealName());
			try {
				Thread.sleep(dish.getCookingTime());
				kitchenHatch.enqueueDish(dish);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			progressReporter.updateProgress();
		}

		progressReporter.notifyCookLeaving();
	}
}
