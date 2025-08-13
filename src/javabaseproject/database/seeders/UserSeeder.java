package javabaseproject.database.seeders;

import javabaseproject.javabase.core.database.Seeder;
import javabaseproject.javabase.core.interfaces.CheckedRunnable;
import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.database.models.User;
import javabaseproject.javabase.framework.exceptions.ExceptionHandler;

public class UserSeeder extends Seeder {
    public CheckedRunnable run() {
        ExceptionHandler.handle(() -> {
            Model.of(User.class).factory().create(2);
        });
        return null;
    }
}
