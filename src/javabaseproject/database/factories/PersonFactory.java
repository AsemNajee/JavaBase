package javabaseproject.database.factories;

import javabaseproject.database.models.Person;
import javabaseproject.javabase.core.database.Factory;
import javabaseproject.javabase.core.database.faker.Fake;

public class PersonFactory extends Factory<Person>{
    public Person item() {
        return new Person(
			Fake.name(),
			Fake.randomNumber(),
			Fake.email()
        );
    }
}
