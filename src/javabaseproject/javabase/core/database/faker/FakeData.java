package javabaseproject.javabase.core.database.faker;

import javabaseproject.ENV;
import javabaseproject.javabase.lang.ar.ArabicFakeData;
import javabaseproject.javabase.lang.en.EnglishFakeData;

public class FakeData {
    public static String[] names(){
        return switch (ENV.LANGUAGE){
            case EN -> EnglishFakeData.names();
            case AR -> ArabicFakeData.names();
        };
    }
    public static String[] emails(){
        return EnglishFakeData.names();
    }
    public static String[] sentences(){
        return switch (ENV.LANGUAGE){
            case EN -> EnglishFakeData.sentences();
            case AR -> ArabicFakeData.sentences();
        };
    }
    public static String[] articles(){
        return switch (ENV.LANGUAGE){
            case EN -> EnglishFakeData.articles();
            case AR -> ArabicFakeData.articles();
        };
    }
}
