package lyutenica;

public enum ZelenchykType {
	DOMAT(3), CHUSHKA(6), PATLADJAN(9);
	private final int time;

	private ZelenchykType(int time) {
		this.time = time;
	}

	public int getTime() {
		return time;
	}

	public static ZelenchykType getRandomType() {
		return ZelenchykType.values()[(int) (ZelenchykType.values().length * Math.random())];
	}
}
