package javabaseproject.database.seeders;

import javabaseproject.javabase.core.database.Seeder;
import javabaseproject.javabase.core.interfaces.CheckedRunnable;
import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.database.models.Book;
import javabaseproject.javabase.framework.exceptions.ExceptionHandler;

public class BookSeeder extends Seeder {
    public CheckedRunnable run() {
        ExceptionHandler.handle(() -> {
            Model.of(Book.class).factory().create(10);
        });
        return null;
    }
}
