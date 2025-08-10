package javabaseproject.factories;

import javabaseproject.models.Book;

public class BookFactory{
    public static Book make(int count){
        return new Book();
    }
}
