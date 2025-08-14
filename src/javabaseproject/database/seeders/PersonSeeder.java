package javabaseproject.database.seeders;

import javabaseproject.javabase.core.database.Seeder;
import javabaseproject.javabase.core.interfaces.CheckedRunnable;
import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.database.models.Person;
import javabaseproject.javabase.framework.exceptions.ExceptionHandler;

public class PersonSeeder extends Seeder {
    public void run() {
        ExceptionHandler.handle(() -> {
            Model.of(Person.class).factory().create(10);
        });
    }
}
