package ohm.softa.a10.kitchen;

import javafx.css.StyleableStringProperty;
import ohm.softa.a10.internals.displaying.ProgressReporter;
import ohm.softa.a10.model.Dish;

import java.util.Random;

public class Waiter implements Runnable{
	String name;
	KitchenHatch kitchenHatch;
	ProgressReporter progressReporter;

	public Waiter(String name, KitchenHatch kitchenHatch, ProgressReporter progressReporter){
		this.name = name;
		this.kitchenHatch = kitchenHatch;
		this.progressReporter = progressReporter;
	}

	@Override
	public void run() {
		var random = new Random();
		while (kitchenHatch.getDishesCount() > 0 || kitchenHatch.getOrderCount() > 0){
			try{
				var dish = kitchenHatch.dequeueDish();
				if(dish.equals(null))
					break;
				Thread.sleep(random.nextInt(10000));
				progressReporter.updateProgress();
			}catch(Exception e){
				e.printStackTrace(System.out);
			}
		}

		progressReporter.notifyWaiterLeaving();
	}
}
