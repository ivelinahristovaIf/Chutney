package lyutenica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

public class Kitchen {
	private static final int MAX_VEGETABLES_OF_TYPE = 30;
	private static Kitchen instance = null;

	private Map<VegetableType, ArrayBlockingQueue<Vegetable>> pans;

	private Kitchen() {
		this.pans = new HashMap<>();
		for (VegetableType type : VegetableType.values()) {
			ArrayBlockingQueue<Vegetable> vegetables = new ArrayBlockingQueue<Vegetable>(MAX_VEGETABLES_OF_TYPE);
			this.pans.put(type, vegetables);
		}
	}

	public synchronized static Kitchen getInstance() {
		if (instance == null) {
			instance = new Kitchen();
		}
		return instance;
	}

	public void putInPan(Vegetable vegetable) {
		if (vegetable != null) {
			try {
				this.pans.get(vegetable.getType()).put(vegetable);
				System.out.println("[" + Thread.currentThread().getName() + "] Put " + vegetable + " in pan "
						+ vegetable.getType() + " ,left " + this.pans.get(vegetable.getType()).size());
			} catch (InterruptedException e) {
				System.out.println("[" + Thread.currentThread().getName() + "] was interrupted while putting  "
						+ vegetable + " in pan " + vegetable.getType());
				return;
			}
		}
	}

	public List<Vegetable> takeFromPan() {
		final int HOW_MUCH_TO_TAKE = 5;
		List<Vegetable> batch = new ArrayList<Vegetable>(HOW_MUCH_TO_TAKE * VegetableType.values().length);
		for (VegetableType type : this.pans.keySet()) {
			Vegetable vegetable;

			for (int index = 0; index < HOW_MUCH_TO_TAKE; index++) {
				try {
					vegetable = this.pans.get(type).take();
					System.out.println("[" + Thread.currentThread().getName() + "] Took from pan " + vegetable);
					batch.add(vegetable);
					System.out.println(
							"[" + Thread.currentThread().getName() + "] Added in batch " + vegetable);
				} catch (InterruptedException e) {
					System.out.println(Thread.currentThread().getName() + " was interrupted while putting into batch");
					return null;
				}
			}
		}
		return batch;
	}
}
