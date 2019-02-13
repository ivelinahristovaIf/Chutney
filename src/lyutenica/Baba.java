package lyutenica;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Baba extends Person {

	private static final int MIN_KG = 3;
	private static final int MAX_KG = 12;
	private Pisar pisar;

	private List<Lyutenica> skladirani;

	public Baba(String name, int age, Pisar pisar) throws PersonException {
		super(name, age);
		this.skladirani = new ArrayList<Lyutenica>();
		this.pisar = pisar;
	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			List<Zelenchyk> partida = new ArrayList<Zelenchyk>(Kyhnya.getInstance().vzemiOtTava());
			System.out.println("[" + this.getName() + "] Vzeh si nujnite zelenchyci");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				return;
			}
			int kg = this.variPartida();
			this.skladiraiPartida(partida, kg);
			this.pisar.zapishiBaba(this, this.skladirani);
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

	public void skladiraiPartida(List<Zelenchyk> partida, int kg) {
		LocalDateTime dateTime = LocalDateTime.now();
		Lyutenica l = new Lyutenica(partida, dateTime, kg);
		this.skladirani.add(l);
		System.out.println("[" + this.getName() + "] skladirah si lyutenicata");
	}

	public List<Lyutenica> getSkladirani() {
		return Collections.unmodifiableList(skladirani);
	}
}
