package lyutenica;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Granny extends Person {

	private static final int MIN_KG = 3;
	private static final int MAX_KG = 12;
	private Writer pisar;

	private List<Lyutenitsa> skladirani;

	public Granny(String name, int age, Writer pisar) throws PersonException {
		super(name, age);
		this.skladirani = new ArrayList<Lyutenitsa>();
		this.pisar = pisar;
	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			List<Vegetable> partida = new ArrayList<Vegetable>(Kitchen.getInstance().takeFromPan());
			System.out.println("[" + this.getName() + "] Vzeh si nujnite zelenchyci");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				return;
			}
			int kg = this.variPartida();
			this.skladiraiPartida(partida, kg);
			this.pisar.writeGranny(this, this.skladirani);
			synchronized (this.pisar) {
				this.pisar.notifyAll();
			}
		}
	}

	public int variPartida() {
		int kg = new Random().nextInt(MAX_KG - MIN_KG + 1) + MIN_KG;
		System.out.println(this.getName() + " svarih " + kg + "kg lyutenica!");
		return kg;
	}

	public void skladiraiPartida(List<Vegetable> partida, int kg) {
		LocalDateTime dateTime = LocalDateTime.now();
		Lyutenitsa l = new Lyutenitsa(partida, dateTime, kg);
		this.skladirani.add(l);
		System.out.println("[" + this.getName() + "] skladirah si lyutenicata");
	}

	public List<Lyutenitsa> getSkladirani() {
		return Collections.unmodifiableList(skladirani);
	}
}
