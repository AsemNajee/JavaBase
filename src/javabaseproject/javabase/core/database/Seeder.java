package javabaseproject.javabase.core.database;

import javabaseproject.javabase.core.interfaces.CheckedRunnable;

public abstract class Seeder {
    public abstract CheckedRunnable run();

    public static void seed(Seeder seeder){
        seeder.run();
    }
}
