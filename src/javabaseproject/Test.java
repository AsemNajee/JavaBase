package javabaseproject;

import javabaseproject.database.models.Book;
import javabaseproject.database.models.BookPerson;
import javabaseproject.database.models.Person;
import javabaseproject.database.models.User;
import javabaseproject.javabase.core.database.Connector;
import javabaseproject.javabase.core.database.faker.Fake;
import javabaseproject.javabase.core.database.io.Fetcher;
import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.core.database.models.Relations;
import javabaseproject.javabase.core.database.querybuilders.MYSQLBuilder;
import javabaseproject.javabase.core.database.querybuilders.query.Condition;
import javabaseproject.javabase.core.database.querybuilders.query.DB;
import javabaseproject.javabase.core.recorder.Recorder;
import javabaseproject.javabase.framework.commandline.Command;
import javabaseproject.javabase.framework.commandline.controllers.PivotController;

import java.util.function.Supplier;
import java.util.regex.Pattern;


public class Test {
    public static void main(String[] args) throws Exception {
        Command.println(MYSQLBuilder.addingForeignKeys(Recorder.getRecordedClass(BookPerson.class)));


//        Command.println("------------");
//        Command.println(DB.from(Book.class).all());

//        Command.println(DB.from(Person.class).all("id"));
//        Command.println(Condition.where("Asem", DB.from(Person.class).all("name")));
//        String file = """
//                    public void setId(int id){
//                        this.id = id;
//                    }
//
//                    public int getId(){
//                        return id;
//                    }
//                """;
//        var p = Pattern.compile("^.*(?<space>(\\}.*\\{)).*").matcher(file);
//        while(p.find()){
//            Command.println(p.group("space"));
//        }

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

        /*
        User user = new User(1, "Asem");
        User user1 = new User(2, "Abdullah");
        User user2 = new User(3, "Saeed");
        user.save();
        user1.save();
        user2.save();

        Command.print(
                DB.from(User.class).whereIn("id", 1, 2, 3).all()
        );

        DB.from(User.class).all(); // all users

        User asem;
        asem = DB.from(User.class).where("id", 1).get();
        // OR
        asem = Model.of(User.class).find(1);

        Book book = new Book();
         */
    }
}

