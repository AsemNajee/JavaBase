package javabaseproject.javabase.core.database;

import javabaseproject.javabase.core.interfaces.CheckedRunnable;

public abstract class Seeder {
    public abstract void run();

    public static void seed(Seeder seeder){
        seeder.run();
    }
}
