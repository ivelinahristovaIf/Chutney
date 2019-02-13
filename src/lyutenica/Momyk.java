package lyutenica;

public class Momyk extends Person {

	private static final int TIME_UNIT = 1000;

	public Momyk(String name, int age) throws PersonException {
		super(name, age);

	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			// VZEMI
			Zelenchyk z = Baraka.getInstance().vzemiOtKoshnica();
			try {
				// OBRABOTI
				Thread.sleep(z.getType().getTime() * TIME_UNIT);
			} catch (InterruptedException e) {
				System.out.println(this.getName() + " nqkoi me prekysna dokato obrabotvam zelenchyk");
				return;
			}
			Kyhnya.getInstance().slojiVtava(z);
		}
	}
}
