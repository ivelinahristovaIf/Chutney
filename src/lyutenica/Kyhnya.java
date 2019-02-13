package lyutenica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

public class Kyhnya {
	private static final int MAX_ZELENCHYCI_OF_TYPE = 30;
	private static Kyhnya instance = null;

	private Map<ZelenchykType, ArrayBlockingQueue<Zelenchyk>> tavi;

	private Kyhnya() {
		this.tavi = new HashMap<>();
		for (ZelenchykType type : ZelenchykType.values()) {
			ArrayBlockingQueue<Zelenchyk> vegetables = new ArrayBlockingQueue<Zelenchyk>(MAX_ZELENCHYCI_OF_TYPE);
			this.tavi.put(type, vegetables);
		}
	}

	public synchronized static Kyhnya getInstance() {
		if (instance == null) {
			instance = new Kyhnya();
		}
		return instance;
	}

	public void slojiVtava(Zelenchyk zelenchyk) {
		if (zelenchyk != null) {
			try {
				this.tavi.get(zelenchyk.getType()).put(zelenchyk);
				System.out.println("[" + Thread.currentThread().getName() + "] Slojih " + zelenchyk + " v tavata "
						+ zelenchyk.getType() + " ostanaha " + this.tavi.get(zelenchyk.getType()).size());
			} catch (InterruptedException e) {
				System.out.println("[" + Thread.currentThread().getName() + "] Nqkoi me prekysna dokato slagam "
						+ zelenchyk + " v tavata " + zelenchyk.getType());
				return;
			}
		}
	}

	public List<Zelenchyk> vzemiOtTava() {
		final int KOLKO_DA_VZEMA = 5;
		List<Zelenchyk> partida = new ArrayList<Zelenchyk>(KOLKO_DA_VZEMA * ZelenchykType.values().length);
		for (ZelenchykType type : this.tavi.keySet()) {
			Zelenchyk zelenchyk;

			for (int i = 0; i < KOLKO_DA_VZEMA; i++) {
				try {
					zelenchyk = this.tavi.get(type).take();
					System.out.println("[" + Thread.currentThread().getName() + "] Vzeh ot tavata " + zelenchyk);
					partida.add(zelenchyk);
					System.out.println(
							"[" + Thread.currentThread().getName() + "] Dobavih si kym partidata " + zelenchyk);
				} catch (InterruptedException e) {
					System.out.println(Thread.currentThread().getName() + " nqkoi me prekysna dokato vzimam ot tavata");
					return null;
				}
			}
		}
		return partida;
	}
}
