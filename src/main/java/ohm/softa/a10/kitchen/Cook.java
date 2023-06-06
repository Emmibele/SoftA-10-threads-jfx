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

		while (kitchenHatch.getOrderCount() > 0){
			try{
				var order = kitchenHatch.dequeueOrder();
				var dish = new Dish((order.getMealName()));
				if(dish.equals(null))
					break;
				Thread.sleep(dish.getCookingTime());
				kitchenHatch.enqueueDish(dish);
				progressReporter.updateProgress();
			}catch(Exception e){
				e.printStackTrace(System.out);
			}
		}
		progressReporter.notifyCookLeaving();
	}
}
