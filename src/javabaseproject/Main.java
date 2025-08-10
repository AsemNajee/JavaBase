package javabaseproject;

import javabaseproject.models.Book;
import javabaseproject.models.Model;

/**
 * 
 * @author AsemNajee
 */
public class Main {
    public static void main() throws Exception {
        Model.getAll(Book.class).forEach(book -> System.out.println(book.toJson()));
        new Book(3, "Third Book").save();
        Model.getAll(Book.class).forEach(book -> System.out.println(book.toJson()));
        System.out.println("-----------");
        Model.find(Book.class, 2).delete();
        Model.getAll(Book.class).forEach(book -> System.out.println(book.toJson()));
        System.out.println("-----------");
        System.out.println(Model.find(Book.class, 1).toJson());
    }
}