package javabaseproject.javabase.lang;

import javabaseproject.ENV;
import javabaseproject.javabase.lang.en.EnglishFakeData;

public class FakeData {
    public static String[] names(){
        return switch (ENV.LANGUAGE){
            case EN -> EnglishFakeData.names();
            case AR -> FakeData.names(); // change this to the class that contains the data in arabic language
        };
    }
}
