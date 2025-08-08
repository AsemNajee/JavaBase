package javabaseproject.javabase.core.database.faker;

public class Fake {
    public static String name(){
        String[] names = {
                "Mohammed", "Ali", "Ahmed", "Said", "Khalid",
                "Omar", "Osama", "Naji", "Asem", "Abdullah", "Bashar",
                "Qasim", "Tareq", "Hadi", "Marwan", "Maged", "Saber"
        };
        return names[randomNumber(0, names.length)];
    }

    public static int randomNumber(int min, int max){
        return (int)((Math.random() * max) - min);
    }
}
