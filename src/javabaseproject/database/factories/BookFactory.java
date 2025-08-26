package javabaseproject.database.factories;

import javabaseproject.database.models.Book;
import javabaseproject.javabase.core.database.Factory;
import javabaseproject.javabase.core.database.faker.Fake;

public class BookFactory extends Factory<Book>{
    public Book item() {
        return new Book(

                Fake.randomNumber(),
                Fake.name()
        );
    }
}
