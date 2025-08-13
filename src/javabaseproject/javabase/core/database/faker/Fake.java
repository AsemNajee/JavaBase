package javabaseproject.javabase.core.database.faker;

import javabaseproject.javabase.lang.FakeData;

/**
 * fake and random data for help developer in factories and where he wants :)
 */
public class Fake {
    /**
     * random name of person
     * all names are for men
     * @return random name
     */
    public static String name(){
        return random(FakeData.names());
    }

    /**
     * random email
     * this method is depending on {@code name()} method
     * and just adding the domain to the name
     * @return random email with random domain
     */
    public static String email(){
        return email(random(
                "gmail.com", "outlook.com"
        ));
    }

    /**
     * {@inheritDoc email()}
     * @param domain the specific domain to the email
     * @return random email with specific domain
     */
    public static String email(String domain){
        return name() + "@" + domain;
    }

    /**
     * get a random sentence
     * its not implemented
     * @return random sentence
     */
    public static String sentence(){
        return random(
                "", ""
        );
    }

    /**
     * return a group of sentences to make a fake paragraph
     * @return fake paragraph is group of sentences
     */
    public static String paragraph(){
        String p = "";
        p += sentence() + "\n";
        p += sentence() + "\n";
        p += sentence() + "\n";
        return p;
    }

    public static int randomNumber(){
        return randomNumber(0, Integer.MAX_VALUE);
    }
    public static int randomNumber(int min, int max){
        return (int)randomFloat(min, max);
    }

    public static short randomShort(){
        return randomShort((short) 0, Short.MAX_VALUE);
    }
    public static short randomShort(short min, short max){
        return (short)randomNumber(min, max);
    }

    public static byte randomByte(){
        return randomByte((byte) 0, Byte.MAX_VALUE);
    }
    public static byte randomByte(byte min, byte max){
        return (byte)randomNumber(min, max);
    }

    public static float randomFloat(){
        return randomFloat(0, Float.MAX_VALUE);
    }
    public static float randomFloat(float min, float max){
        return (float)((Math.random() * (max - min)) + min);
    }

    public static boolean randomBoolean(){
        return random(true, false);
    }

    /**
     * get the random value from the array
     * @param items any number of items or array to choose a random item from it
     * @return one value from the array randomly chose
     * @param <T> the array is of any type
     */
    @SafeVarargs
    public static <T> T random(T... items){
        return items[randomNumber(0, items.length)];
    }
}
