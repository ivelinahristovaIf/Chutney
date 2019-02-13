package lyutenica;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Moma extends Person {
	private static final int MIN_AGE = 14;
	private static final int MAX_AGE = 19;
	private static final int MIN_QUANTITY = 3;
	private static final int MAX_QUANTITY = 6;
	private Pisar pisar;

	public Moma(String name, int age, Pisar pisar) throws PersonException {
		super(name, age);
		this.pisar = pisar;
	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				System.out.println(this.getName() + " nqkoi me prekysna dokato bera");
			}
			List<Zelenchyk> nabrani = this.naberi();
			ZelenchykType type = null;
			for (Zelenchyk zelenchyk : nabrani) {
				Baraka.getInstance().slojiVkoshnica(zelenchyk);
				type = zelenchyk.getType();
			}
			System.out.println("[" + this.getName() + "]: Slojih gi vsichkite v koshnica");
			this.pisar.zapishiMoma(this, type, nabrani.size());
			synchronized (this.pisar) {
				this.pisar.notifyAll();
			}
		}
	}

	public List<Zelenchyk> naberi() {
		ZelenchykType randomType = ZelenchykType.getRandomType();
		System.out.println("[" + this.getName() + "] Pochvam da bera!");
		int randomQuantity = new Random().nextInt(MAX_QUANTITY - MIN_QUANTITY + 1) + MIN_QUANTITY;
		List<Zelenchyk> nabraniOtMoma = new ArrayList<Zelenchyk>(randomQuantity);
		for (int i = 0; i < randomQuantity; i++) {
			nabraniOtMoma.add(new Zelenchyk(randomType));
		}
		System.out.println("[" + this.getName() + "] nabrah " + nabraniOtMoma.size() + " i shte gi slagam v koshnica");
		return nabraniOtMoma;
	}

	@Override
	public void setAge(int age) throws PersonException {
		if (age >= MIN_AGE && age <= MAX_AGE) {
			super.setAge(age);
		} else {
			throw new PersonException("Invalid moma age!");
		}
	}

}
