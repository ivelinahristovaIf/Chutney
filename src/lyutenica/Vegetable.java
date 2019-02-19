package lyutenica;

public class Vegetable {
	private final VegetableType type;
	private final int id;
	private static int nextId = 1;

	public Vegetable(VegetableType type) {
		this.type = type;
		this.id = Vegetable.nextId++;
	}

	public VegetableType getType() {
		return type;
	}

	@Override
	public String toString() {
		return "Vegetbale " + type + " " + id;
	}

}
