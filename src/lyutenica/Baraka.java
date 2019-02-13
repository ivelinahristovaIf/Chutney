package lyutenica;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

public class Baraka {
	private static final int MAX_ZELENCHYCI_OF_TYPE = 40;
	private static Baraka instance = null;

	private Map<ZelenchykType, ArrayBlockingQueue<Zelenchyk>> koshnici;

	private Baraka() {
		this.koshnici = new HashMap<>();
		for (ZelenchykType type : ZelenchykType.values()) {
			ArrayBlockingQueue<Zelenchyk> vegetables = new ArrayBlockingQueue<Zelenchyk>(MAX_ZELENCHYCI_OF_TYPE);
			this.koshnici.put(type, vegetables);
		}
	}

	public synchronized static Baraka getInstance() {
		if (instance == null) {
			instance = new Baraka();
		}
		return instance;
	}

	public void slojiVkoshnica(Zelenchyk zelenchyk) {
		if (zelenchyk != null) {
			try {
				this.koshnici.get(zelenchyk.getType()).put(zelenchyk);
				System.out.println("[" + Thread.currentThread().getName() + "] Slojih " + zelenchyk + " v koshnica "
						+ zelenchyk.getType());
			} catch (InterruptedException e) {
				System.out.println("[" + Thread.currentThread().getName() + "] Nqkoi me prekysna dokato slagam "
						+ zelenchyk + " v koshnica " + zelenchyk.getType());
				return;
			}
		}
	}

	public Zelenchyk vzemiOtKoshnica() {
		ZelenchykType randomType = ZelenchykType.getRandomType();
		Zelenchyk zelenchyk;
		try {
			zelenchyk = this.koshnici.get(randomType).take();
			System.out.println("[" + Thread.currentThread().getName() + "] Vzeh ot koshnicata " + zelenchyk);
		} catch (InterruptedException e) {
			System.out.println(Thread.currentThread().getName() + " nqkoi me prekysna dokato vzimam ot koshnicata");
			return null;
		}
		return zelenchyk;
	}

}
