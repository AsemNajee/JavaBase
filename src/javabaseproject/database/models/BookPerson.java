package javabaseproject.database.models;

import javabaseproject.javabase.core.annotations.PrimaryKey;
import javabaseproject.javabase.core.collections.ModelsCollection;
import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.core.database.models.Pivot;
import javabaseproject.javabase.core.database.models.Relations;
import javabaseproject.javabase.core.database.querybuilders.query.DB;

@PrimaryKey("id")
public class BookPerson extends Model<BookPerson> implements Pivot<Book, Person> {

    protected int id;
    protected int bookId;
    protected int personId;
    public BookPerson(){}
    public BookPerson(int id, Book book, Person person){
        this.bookId = book.getId();
        this.personId = person.getId();
        this.id = id;
    }

    public static ModelsCollection<Person> related(Book book) throws Exception {
        var relationKeys = Model.of(BookPerson.class).query().where("bookId", book.getId()).all();
        var keys = relationKeys.stream().map(item -> item.id);
        return DB.from(Person.class).whereIn("id", keys.toArray()).all();
    }
    public static ModelsCollection<Book> related(Person person) throws Exception {
        var relationKeys = Model.of(BookPerson.class).query().where("personId", person.getId()).all();
        var keys = relationKeys.stream().map(item -> item.id);
        return DB.from(Book.class).whereIn("id", keys.toArray()).all();
    }
}
