package lyutenica;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

public class Shack {
	private static final int MAX_VEGETABLES_OF_TYPE = 40;
	private static Shack instance = null;

	private Map<VegetableType, ArrayBlockingQueue<Vegetable>> baskets;

	private Shack() {
		this.baskets = new HashMap<>();
		for (VegetableType type : VegetableType.values()) {
			ArrayBlockingQueue<Vegetable> vegetables = new ArrayBlockingQueue<Vegetable>(MAX_VEGETABLES_OF_TYPE);
			this.baskets.put(type, vegetables);
		}
	}

	public synchronized static Shack getInstance() {
		if (instance == null) {
			instance = new Shack();
		}
		return instance;
	}

	public void putInBasket(Vegetable vegetable) {
		if (vegetable != null) {
			try {
				this.baskets.get(vegetable.getType()).put(vegetable);
				System.out.println("[" + Thread.currentThread().getName() + "] Put " + vegetable + " in basket "
						+ vegetable.getType());
			} catch (InterruptedException e) {
				System.out.println("[" + Thread.currentThread().getName() + "] Was interrupted while putting "
						+ vegetable + " in basket " + vegetable.getType());
				return;
			}
		}
	}

	public Vegetable takeFromBasket() {
		VegetableType randomType = VegetableType.getRandomType();
		Vegetable vegetable;
		try {
			vegetable = this.baskets.get(randomType).take();
			System.out.println("[" + Thread.currentThread().getName() + "] Took from basket " + vegetable);
		} catch (InterruptedException e) {
			System.out.println(Thread.currentThread().getName() + " was interrupted while taking from basket");
			return null;
		}
		return vegetable;
	}

}
