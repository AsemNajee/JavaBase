package javabaseproject.database.factories;

import javabaseproject.database.models.Animal;
import javabaseproject.javabase.core.database.Factory;
import javabaseproject.javabase.core.database.faker.Fake;

public class AnimalFactory extends Factory<Animal>{
    public Animal item() {
        return new Animal(
                Fake.randomNumber(),
				Fake.name()

        );
    }
}
