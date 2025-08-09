package javabaseproject.factories;

import javabaseproject.models.Book;

public class BookFactory extends Factory{
    public static Book make(int count){
        return new Book();
    }
}
