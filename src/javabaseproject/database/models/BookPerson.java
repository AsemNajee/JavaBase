package javabaseproject.database.models;

import javabaseproject.javabase.core.database.models.Pivot;

public class PersonBook extends Pivot<Book, Person> {
    public PersonBook(Book book, Person person){
        super(book, person);
    }
}
