package lyutenica;

public enum VegetableType {
	DOMAT(3), CHUSHKA(6), PATLADJAN(9);
	private final int time;

	private VegetableType(int time) {
		this.time = time;
	}

	public int getTime() {
		return time;
	}

	public static VegetableType getRandomType() {
		return VegetableType.values()[(int) (VegetableType.values().length * Math.random())];
	}
}
