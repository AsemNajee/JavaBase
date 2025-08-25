package javabaseproject.database.models;

import javabaseproject.javabase.core.annotations.ForeignKey;
import javabaseproject.javabase.core.annotations.PrimaryKey;
import javabaseproject.javabase.core.collections.ModelsCollection;
import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.core.database.models.Pivot;
import javabaseproject.javabase.core.database.models.Relations;
import javabaseproject.javabase.core.database.querybuilders.query.DB;
import javabaseproject.javabase.core.recorder.Recorder;
import javabaseproject.javabase.framework.commandline.Command;

import java.lang.reflect.InvocationTargetException;

@PrimaryKey("id")
public class BookPerson extends Model<BookPerson> implements Pivot<Book, Person> {

    protected int id;
    @ForeignKey(Book.class)
    protected int bookId;
    @ForeignKey(Person.class)
    protected int personId;
    public BookPerson(){}
    public BookPerson(int id, Book book, Person person){
        this.bookId = book.getId();
        this.personId = person.getId();
        this.id = id;
    }

    public static ModelsCollection<Person> belongsToMany(Book book) throws Exception {
        return Relations.belongsToMany(BookPerson.class, Person.class, book);
    }
    public static ModelsCollection<Book> belongsToMany(Person person) throws Exception {
        return Relations.belongsToMany(BookPerson.class, Book.class, person);
    }
}
