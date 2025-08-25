package javabaseproject.javabase.core.database;

import javabaseproject.javabase.framework.exceptions.ExceptionHandler;

public abstract class Seeder {
    /**
     * run the seeder
     */
    public abstract void run() throws Exception;

    /**
     * seeding one Seeder
     * @param seeder instance of the seeder class
     */
    public static void seed(Seeder seeder) {
        ExceptionHandler.handle(seeder::run);
    }

    /**
     * seeding one Seeder
     * @param seeder a class of the seeder like UserSeeder.class
     */
    public static void seed(Class<? extends Seeder> seeder) {
        ExceptionHandler.handle(() -> seeder.getDeclaredConstructor().newInstance().run());
    }
}
