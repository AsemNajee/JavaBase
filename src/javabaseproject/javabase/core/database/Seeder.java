package javabaseproject.javabase.core.database;

import javabaseproject.javabase.core.database.faker.Fake;
import javabaseproject.model.Book;

public class Seeder {
    public static void seed() throws Exception {
        new Book(Fake.name(), Fake.name(), Fake.randomNumber(10, 500)).save();
//        new Book().dropTable();
//        for (int i = 0; i < 10; i ++){
//        }
    }
}
