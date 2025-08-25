package javabaseproject;

import javabaseproject.database.models.Book;
import javabaseproject.database.models.BookPerson;
import javabaseproject.database.models.Person;
import javabaseproject.database.models.User;
import javabaseproject.javabase.core.database.Connector;
import javabaseproject.javabase.core.database.io.Fetcher;
import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.core.database.models.Relations;
import javabaseproject.javabase.core.database.querybuilders.query.Condition;
import javabaseproject.javabase.core.database.querybuilders.query.DB;
import javabaseproject.javabase.framework.commandline.Command;


public class Test {

    public static void main(String[] args) throws Exception {
        Book book = Model.of(Book.class).find(1);
        Person person = Model.of(Person.class).find(1);
        Command.print(person.books());
//        book.save();
//        person.save();
//        BookPerson bp = new BookPerson(2, book, person);
//        bp.save();
//        Command.print(DB.from(User.class).whereIn("id", 1, 2, 3, 4, 5).all());
    }
}

