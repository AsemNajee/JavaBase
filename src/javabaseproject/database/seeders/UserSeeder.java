package javabaseproject.database.seeders;

import javabaseproject.javabase.core.database.Seeder;
import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.database.models.User;

public class UserSeeder extends Seeder {
    public void run() throws Exception{
        Model.of(User.class).factory().create(10);
    }
}
