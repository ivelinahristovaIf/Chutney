package lyutenica;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Girl extends Person {
	private static final int MIN_AGE = 14;
	private static final int MAX_AGE = 19;
	private static final int MIN_QUANTITY = 3;
	private static final int MAX_QUANTITY = 6;
	private Writer writer;

	public Girl(String name, int age, Writer writer) throws PersonException {
		super(name, age);
		this.writer = writer;
	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				System.out.println(this.getName() + " someone interrupted the thread while producing vegetables");
			}
			List<Vegetable> nabrani = this.naberi();
			VegetableType type = null;
			for (Vegetable zelenchyk : nabrani) {
				Shack.getInstance().putInBasket(zelenchyk);
				type = zelenchyk.getType();
			}
			System.out.println("[" + this.getName() + "]: Put them all in a basket");
			this.writer.writeAGirl(this, type, nabrani.size());
			synchronized (this.writer) {
				this.writer.notifyAll();
			}
		}
	}

	public List<Vegetable> naberi() {
		VegetableType randomType = VegetableType.getRandomType();
		System.out.println("[" + this.getName() + "] Start producing vegetables!");
		int randomQuantity = new Random().nextInt(MAX_QUANTITY - MIN_QUANTITY + 1) + MIN_QUANTITY;
		List<Vegetable> producedByGirl = new ArrayList<Vegetable>(randomQuantity);
		for (int i = 0; i < randomQuantity; i++) {
			producedByGirl.add(new Vegetable(randomType));
		}
		System.out.println("[" + this.getName() + "] produced " + producedByGirl.size() + " vegetables and put them in the basket");
		return producedByGirl;
	}

	@Override
	public void setAge(int age) throws PersonException {
		if (age >= MIN_AGE && age <= MAX_AGE) {
			super.setAge(age);
		} else {
			throw new PersonException("Invalid girl age!");
		}
	}

}
