package javabaseproject.seeders;

import javabaseproject.factories.BookFactory;
import javabaseproject.javabase.core.database.faker.Fake;
import javabaseproject.models.Book;

public class Seeder {
    public static void seed() throws Exception {
        BookFactory.make(10);
    }
}
