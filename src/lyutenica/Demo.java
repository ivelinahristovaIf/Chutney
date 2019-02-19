package lyutenica;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Demo {

	public static void main(String[] args) throws PersonException, IOException {
		Writer writer = new Writer();
		List<Person> people = new ArrayList<Person>();
		people.add(new Girl("Spaska", 16, writer));
		people.add(new Girl("Irena", 18, writer));
		people.add(new Girl("Darina", 17, writer));
		people.add(new Boy("Misho", 21));
		people.add(new Boy("Tisho", 24));
		people.add(new Boy("Gosho", 24));
		people.add(new Granny("Genka", 54, writer));
		people.add(new Granny("Cona", 67, writer));
		people.add(new Granny("Fidanka", 70, writer));

		writer.start();
		for (Person person : people) {
			person.start();
		}

	}

}
