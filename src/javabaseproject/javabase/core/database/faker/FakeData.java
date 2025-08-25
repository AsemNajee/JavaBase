package javabaseproject.javabase.core.database.faker;

import javabaseproject.ENV;
import javabaseproject.javabase.lang.ar.ArabicFakeData;
import javabaseproject.javabase.lang.en.EnglishFakeData;

/**
 * get fake data depending on the language from {@link ENV#LANGUAGE}
 *
 * @author AsemNajee
 * @version 1.0
 */
public class FakeData {

    /**
     * get fake names to randomly choose
     * @return array of fake names
     */
    public static String[] names(){
        return switch (ENV.LANGUAGE){
            case EN -> EnglishFakeData.names();
            case AR -> ArabicFakeData.names();
        };
    }

    /**
     * get fake emails to randomly choose
     * @return array of fake emails
     */
    public static String[] emails(){
        return EnglishFakeData.names();
    }

    /**
     * get fake sentences to randomly choose
     * @return array of fake sentences
     */
    public static String[] sentences(){
        return switch (ENV.LANGUAGE){
            case EN -> EnglishFakeData.sentences();
            case AR -> ArabicFakeData.sentences();
        };
    }

    /**
     * get fake articles to randomly choose
     * @return array of fake articles
     */
    public static String[] articles(){
        return switch (ENV.LANGUAGE){
            case EN -> EnglishFakeData.articles();
            case AR -> ArabicFakeData.articles();
        };
    }
}
