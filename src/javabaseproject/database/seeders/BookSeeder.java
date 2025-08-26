package javabaseproject.database.seeders;

import javabaseproject.javabase.core.database.Seeder;
import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.database.models.Book;

public class BookSeeder extends Seeder {
    public void run() throws Exception{
        Model.of(Book.class).factory().create(10);
    }
}
