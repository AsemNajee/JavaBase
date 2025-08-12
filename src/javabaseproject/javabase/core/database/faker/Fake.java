package javabaseproject.javabase.core.database.faker;

import javabaseproject.javabase.lang.FakeData;

public class Fake {
    public static String name(){
        return random(FakeData.names());
    }

    public static String email(){
        return email(random(
                "gmail.com", "outlook.com"
        ));
    }
    public static String email(String domain){
        return name() + "@" + domain;
    }

    public static String sentence(){
        return random(
                "", ""
        );
    }

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
    @SafeVarargs
    public static <T> T random(T... items){
        return items[randomNumber(0, items.length)];
    }
}
