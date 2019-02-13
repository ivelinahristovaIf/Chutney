package lyutenica;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Demo {

	public static void main(String[] args) throws PersonException, IOException {
		Pisar pisar = new Pisar();
		List<Person> people = new ArrayList<Person>();
		people.add(new Moma("Spaska", 16, pisar));
		people.add(new Moma("Irena", 18, pisar));
		people.add(new Moma("Darina", 17, pisar));
		people.add(new Momyk("Misho", 21));
		people.add(new Momyk("Tisho", 24));
		people.add(new Momyk("Gosho", 24));
		people.add(new Baba("Genka", 54, pisar));
		people.add(new Baba("Cona", 67, pisar));
		people.add(new Baba("Fidanka", 70, pisar));

		pisar.start();
		for (Person person : people) {
			person.start();
		}

	}

}
