package lyutenica;

public class Zelenchyk {
	private final ZelenchykType type;
	private final int id;
	private static int nextId = 1;

	public Zelenchyk(ZelenchykType type) {
		this.type = type;
		this.id = Zelenchyk.nextId++;
	}

	public ZelenchykType getType() {
		return type;
	}

	@Override
	public String toString() {
		return "Zelenchyk " + type + " " + id;
	}

}
