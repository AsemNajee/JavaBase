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
    protected int userId;
    protected int personId;
    public BookPerson(){}

    public static ModelsCollection<Person> related(Book book) throws Exception {
        var relationKeys = Model.of(BookPerson.class).query().where("bookId", book.getId()).all();
        return DB.from(Person.class).where("id", "").all();
//        return Relations.hasMany(book, Person.class, "userId", "id");
    }
    public static ModelsCollection<User> related(Person person) throws Exception {
        return Relations.hasMany(person, User.class, "personId", "id");
    }
}
