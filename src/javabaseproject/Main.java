package javabaseproject;

import javabaseproject.database.models.Book;
import javabaseproject.javabase.core.database.models.Model;

/**
 * 
 * @author AsemNajee
 */
public class Main {
    public static void main() throws Exception {
        Model.of(Book.class).getAll().forEach(book -> System.out.println(book.toJson()));
    }
}