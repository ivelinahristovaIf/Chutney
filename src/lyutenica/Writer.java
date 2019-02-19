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

public class Writer extends Thread {
	// Girl->VegetableType->quantity
	private Map<Girl, Map<VegetableType, Integer>> harvested = new ConcurrentHashMap<>();
	private Map<Granny, List<Lyutenitsa>> storage = new ConcurrentHashMap<>();
	private File file;

	public Writer() throws IOException {
		this.setDaemon(true);
		this.file = new File("stats.txt");
		if(!file.exists()) {
			new File("stats.txt").createNewFile();
		}
	}

	public void writeAGirl(Girl girl, VegetableType type, int quantity) {
		// No girl
		if (!this.harvested.containsKey(girl)) {
			this.harvested.put(girl, new HashMap<VegetableType, Integer>());
			for (VegetableType vegetableType : VegetableType.values()) {
				this.harvested.get(girl).put(vegetableType, 0);
			}
		}
		// empty quantity
		if (this.harvested.get(girl).get(type) == 0) {
			this.harvested.get(girl).put(type, quantity);
		}
		int oldQuantity = this.harvested.get(girl).get(type);
		this.harvested.get(girl).put(type, oldQuantity + quantity);
	}

	public void writeGranny(Granny b, List<Lyutenitsa> stored) {
		if (!this.storage.containsKey(b)) {
			this.storage.put(b, new ArrayList<>());
		}
		this.storage.get(b).addAll(stored);
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
					if (!this.harvested.isEmpty()) {
						pw.println(new Gson().toJson(this.harvested));
					}
					if (!this.storage.isEmpty()) {
						for (Granny b : this.storage.keySet()) {
							pw.print(new Gson().toJson("  Baba " + b.getName() + ": " + this.storage.get(b)));
							for (Lyutenitsa l : this.storage.get(b)) {
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
