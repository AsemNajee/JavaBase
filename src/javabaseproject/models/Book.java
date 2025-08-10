package javabaseproject.models;

import javabaseproject.javabase.core.annotations.PrimaryKey;
import javabaseproject.javabase.core.annotations.Unique;
import javabaseproject.javabase.core.interfaces.HasFactory;

import java.util.ArrayList;

@PrimaryKey("id")
public class Book extends Model<Book> implements HasFactory<Book> {

    protected int id;
    @Unique
    protected String name;

    public Book(){}

    public Book(int id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public ArrayList<Book> make(int count) {
        return null;
    }

    @Override
    public Book make() {
        return null;
    }

// ... add more props with protected access modifier
}
