package javabaseproject.database.factories;

import javabaseproject.database.models.User;
import javabaseproject.javabase.core.database.Factory;
import javabaseproject.javabase.core.database.faker.Fake;

public class UserFactory extends Factory<User>{
    public User item() {
        return new User(
                
			Fake.name(),
			Fake.randomNumber()
        );
    }
}
