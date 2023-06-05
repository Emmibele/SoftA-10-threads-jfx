package ohm.softa.a10.kitchen;

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
		Dish dish = null;
		try {
			dish = kitchenHatch.dequeueDish();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		while(!dish.equals(null)){
			try {
				Thread.sleep(new Random().nextInt(1000));
				progressReporter.updateProgress();
				dish = kitchenHatch.dequeueDish();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}

		}
		progressReporter.notifyWaiterLeaving();
	}
}
