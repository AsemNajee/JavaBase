package javabaseproject;

/**
 * DON'T TRY HERE
 *
 *
 *
 *
 *
 *
 *
 *
 *
 * TESTING THE FRAMEWORK FUNCTIONS DURING DEVELOPMENT
 */

import com.mysql.cj.xdevapi.JsonString;
import javabaseproject.database.models.Book;
import javabaseproject.database.models.BookPerson;
import javabaseproject.database.models.Person;
import javabaseproject.database.models.User;
import javabaseproject.javabase.core.database.Connector;
import javabaseproject.javabase.core.database.faker.Fake;
import javabaseproject.javabase.core.database.io.Fetcher;
import javabaseproject.javabase.core.database.io.Json;
import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.core.database.models.Relations;
import javabaseproject.javabase.core.database.querybuilders.MYSQLBuilder;
import javabaseproject.javabase.core.database.querybuilders.SQLITEBuilder;
import javabaseproject.javabase.core.database.querybuilders.query.Condition;
import javabaseproject.javabase.core.database.querybuilders.query.DB;
import javabaseproject.javabase.core.recorder.Recorder;
import javabaseproject.javabase.framework.commandline.Command;
import javabaseproject.javabase.framework.commandline.controllers.PivotController;
import javabaseproject.javabase.framework.commandline.output.Dialog;
import javabaseproject.javabase.framework.commandline.output.OutputTemplate;

import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Test {
    public static void main(String[] args) throws Exception {
        Person person = new Person(1, "Asem");
        Command.println(person.books());

        OutputTemplate dialog = OutputTemplate.format("Hello, World", """
                Hello sorry you cant use this car Hello sorry you cant use this car Hello sorry you cant use this car
                
                you are not user really
                """);


        Command.printf(dialog.getMessage(), dialog.getParams().toArray());
        Command.printf(dialog.getWarning(), dialog.getParams().toArray());
        Command.printf(dialog.getError(), dialog.getParams().toArray());
//        var b = Model.of(Person.class).find("Bashar");
//        Command.println(b);
//        b.setId(11);
//        b.save();

//        Command.println(DB.from(Person.class).all());
//        Person person = new Person(2, "NotAliSalih", "male");
//        DB.from(Person.class).where("Abdulhamid").update(person);
//        Command.println(DB.from(Person.class).all());
//        Command.println(DB.from(Person.class).whereIn("id", 94108, 70660).delete());
//        Command.println("--------------------------");
//        Command.println(Model.of(Person.class).getAll());
//        Person p = new Person(Fake.randomNumber(1, 100000), Fake.name());
//        p.save();
//        Command.println(p);
//        Command.println(DB.from(Person.class).delete());
//        Command.println(DB.insert(new Person(Fake.randomNumber(10000, 1000000), Fake.name(), Fake.random("male", "female"))));
//        Command.println(DB.insertAll(
//                new Person(Fake.randomNumber(1, 100000), Fake.name()),
//                new Person(Fake.randomNumber(1, 100000), Fake.name()),
//                new Person(Fake.randomNumber(1, 100000), Fake.name()),
//                new Person(Fake.randomNumber(1, 100000), Fake.name()),
//                new Person(Fake.randomNumber(1, 100000), Fake.name())
//        ));

//        Command.println(DB.from(Person.class).all());
//        Command.println(MYSQLBuilder.addingForeignKeys(Recorder.getRecordedClass(BookPerson.class)));
//        Person person = Model.of(Person.class).find("Tariq");
//        Command.println(person);
//        Command.printJson(new Json<>(person).toJson(0));
//        person.setId(10);
//        person.save();
//        Command.println(person);

//        Command.println("------------");
//        Command.println(DB.from(Book.class).all());

//        Command.println(DB.from(Person.class).all("id"));
//        Command.println(Condition.where("Asem", DB.from(Person.class).all("name")));

//        Person person = Model.of(Person.class).find(1);
//        Command.print(person.books());
//        Person person = new Person(1, "Asem");
//        Book book1 = new Book(1, "Arabic");
//        Book book = new Book(2, "English");
//        book.save()
//        person.save();
//        BookPerson bp = new BookPerson(1, book, person);
//        BookPerson bp1 = new BookPerson(2, book1, person);
//        bp.save();
//        bp1.save();


//        User user = new User(1, "Asem");
//        User user1 = new User(2, "Abdullah");
//        User user2 = new User(3, "Saeed");
//        user.save();
//        Command.println(user);
//        user1.save();
//        Command.println(user1);
//        user2.save();
//        Command.println(user2);
//        Person p = new Person(Fake.randomNumber(1, 10000), Fake.name());
//        p.save();
//        Command.println(p);
//
//        Command.print(
//                DB.from(Person.class).whereIn("id", 1, 2, 3).all()
//        );
/*
        DB.from(User.class).all(); // all users

        User asem;
        asem = DB.from(User.class).where("id", 1).get();
        // OR
        asem = Model.of(User.class).find(1);

        Book book = new Book();
         */
    }
}

