package lyutenica;

public class Boy extends Person {

	private static final int TIME_UNIT = 1000;

	public Boy(String name, int age) throws PersonException {
		super(name, age);

	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			// CONSUME
			Vegetable vegetable = Shack.getInstance().takeFromBasket();
			try {
				// PRODUCE
				Thread.sleep(vegetable.getType().getTime() * TIME_UNIT);
			} catch (InterruptedException e) {
				System.out.println(this.getName() + " was interrupted while producing");
				return;
			}
			Kitchen.getInstance().putInPan(vegetable);
		}
	}
}
