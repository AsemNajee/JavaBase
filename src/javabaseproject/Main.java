package javabaseproject;

import javabaseproject.database.models.Book;
import javabaseproject.database.models.BookPerson;
import javabaseproject.database.models.Person;
import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.framework.commandline.Command;

/**
 * @author AsemNajee <br/>
 * GitHub : <a href="https://github.com/AsemNajee">My Github</a><br/>
 * Telegram : <a href="https://t.me/AsemNajee">Telegram Channel</a><br/>
 * Site : <a href="https://asem.buzog.com">Portfolio</a>
 */
public class Main {
    /**
     * Run DevHandler and command these lines below
     *         1- make:model Person
     *         2- db:init
     *         3- db:migrate
     * then here uncomment this line
     * <code>
     *    Person person = new Person(1, "Asem");
     *    person.save();
     * </code>
     * now you have a database and table called person with name and id
     * and, you have one row in the database with id=1, name=Asem in person table
     */
    public static void main(String[] args) throws Exception {
        Person person = new Person(1, "Asem");
        // save user in database
        person.save();
        // get user from database with primary key (name is primary key)
        Command.println("Person Info:");
        Command.print(Model.of(Person.class).find("Asem"));

        // create books
        Book book1 = new Book(1, "Java");
        Book book2 = new Book(2, "PHP");
        Book book3 = new Book(3, "JS");
        // save books in database
        book1.save();
        book2.save();
        book3.save();
//        DB.insertAll(book1, book2, book3);
        Command.println("All Books:");
        Command.print(Model.of(Book.class).getAll());

        // make a relation (many to many) between books and person
        BookPerson bp1 = new BookPerson(1, book1, person);
        BookPerson bp2 = new BookPerson(2, book2, person);
        BookPerson bp3 = new BookPerson(3, book2, person);
        // save a relation in database
        bp1.save();
        bp2.save();
        bp3.save();
        // print person books
        Command.printf("Books of Person(%s)", person.getName());
        Command.println(person.books());
    }
}