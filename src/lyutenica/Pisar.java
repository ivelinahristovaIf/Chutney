package lyutenica;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.Gson;

public class Pisar extends Thread {
	// Moma->ZelenchykType->quantity
	private Map<Moma, Map<ZelenchykType, Integer>> nabrano = new ConcurrentHashMap<>();
	private Map<Baba, List<Lyutenica>> sklad = new ConcurrentHashMap<>();
	private PrintWriter pw;

	public Pisar() throws IOException {
		this.setDaemon(true);
		new File("stats.txt").createNewFile();
		pw = new PrintWriter(new File("stats.txt"));
	}

	public void zapishiMoma(Moma m, ZelenchykType type, int kolko) {
		// No moma
		if (!this.nabrano.containsKey(m)) {
			this.nabrano.put(m, new HashMap<ZelenchykType, Integer>());
			for (ZelenchykType zelenchykType : ZelenchykType.values()) {
				this.nabrano.get(m).put(zelenchykType, 0);
			}
		}
		// empty quantity
		if (this.nabrano.get(m).get(type) == 0) {
			this.nabrano.get(m).put(type, kolko);
		}
		int oldQuantity = this.nabrano.get(m).get(type);
		this.nabrano.get(m).put(type, oldQuantity + kolko);
	}

	public void zapishiBaba(Baba b, List<Lyutenica> skladirani) {
		if (!this.sklad.containsKey(b)) {
			this.sklad.put(b, new ArrayList<>());
		}
		this.sklad.get(b).addAll(skladirani);
	}

	@Override
	public void run() {
		while (true) {
			synchronized (this) {
				try {
					this.wait();
				} catch (InterruptedException e1) {
					return;
				}
			}
			try {
				try (PrintWriter pw = new PrintWriter(new FileOutputStream(new File("stats.txt"), true))) {
					// append true
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						return;
					}
					if (!this.nabrano.isEmpty()) {
						pw.println(new Gson().toJson(this.nabrano));
					}
					if (!this.sklad.isEmpty()) {
						for (Baba b : this.sklad.keySet()) {
							pw.print(new Gson().toJson("  Baba " + b.getName() + ": " + this.sklad.get(b)));
							for (Lyutenica l : this.sklad.get(b)) {
								pw.println(new Gson().toJson(l));
							}
						}
					}
				}
			} catch (FileNotFoundException e) {
				return;
			}
		}
	}
}
