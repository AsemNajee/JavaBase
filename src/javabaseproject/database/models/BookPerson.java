package javabaseproject.database.models;

import javabaseproject.javabase.core.annotations.ForeignKey;
import javabaseproject.javabase.core.annotations.PrimaryKey;
import javabaseproject.javabase.core.collections.ModelsCollection;
import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.core.database.models.Relations;

@PrimaryKey("id")
public class BookPerson extends Model<BookPerson> {

    protected int id;
    @ForeignKey(Book.class)
    protected int bookId;
    @ForeignKey(value = Person.class, key = "id")
    protected int personId;
    public BookPerson(){}
    public BookPerson(int id, Book book, Person person){
        this.bookId = book.getId();
        this.personId = person.getId();
        this.id = id;
    }

    /**
     * get all persons related with the book by this pivot
     *
     * @param book the book to get its related persons
     * @return collection of persons
     */
    public static ModelsCollection<Person> belongsToMany(Book book) throws Exception {
        return Relations.belongsToMany(BookPerson.class, Person.class, book);
    }

    /**
     * get all books related with the person by this pivot
     *
     * @param person the person to get its related books
     * @return collection of books
     */
    public static ModelsCollection<Book> belongsToMany(Person person) throws Exception {
        return Relations.belongsToMany(BookPerson.class, Book.class, person);
    }
}
