package javabaseproject;

import javabaseproject.database.models.Person;
import javabaseproject.database.models.User;
import javabaseproject.javabase.core.database.faker.Fake;
import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.framework.commandline.Command;

/**
 * 
 * @author AsemNajee
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Person person = new Person(1, "Asem");
        person.save();

        //Person person = new Person();
        //person = person.find(1);

        person = (Person) Model.of(Person.class).find(1);
        Command.println(person.toJson());
        person = Model.find(Person.class, 1);


//        new User("password", "Said", 4).save();

//        Command.println(Model.of(User.class).find(1).toJson());
        Model.of(User.class).getAll().stream().forEach(item -> Command.println(item.toJson()));
    }
}