package javabaseproject.database.models;

import javabaseproject.javabase.core.annotations.ForeignKey;
import javabaseproject.javabase.core.annotations.PrimaryKey;
import javabaseproject.javabase.core.collections.ModelsCollection;
import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.core.database.models.Pivot;
import javabaseproject.javabase.core.database.models.Relations;
import javabaseproject.javabase.core.database.querybuilders.query.DB;
import javabaseproject.javabase.core.recorder.Recorder;

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

    public static <M extends Model<M>, M2 extends Model<M2>, P extends Pivot<M, M2>> ModelsCollection<M> related(Class<P> pivot, Class<M> returnedItem, M2 model) throws Exception {
        var relationKeys = Model.of(BookPerson.class).query().where("personId", model.getKey()).all();
        var keys = relationKeys.stream().map(Model::getKey);
        return DB.from(returnedItem)
                .whereIn(
                        Recorder.getRecordedClass(returnedItem).getPrimaryKey().getName(),
                        keys.toArray())
                .all();
    }
}
