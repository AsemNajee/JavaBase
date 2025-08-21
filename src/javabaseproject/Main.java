package javabaseproject;

import javabaseproject.database.models.User;
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
     *    Person person = new Person("Asem", 1);
     *    person.save();
     * </code>
     * now you have a database and table called person with name and id
     * and, you have one row in the database with id=1, name=Asem in person table
     */
    public static void main(String[] args) throws Exception {
        Test.main(null);
//        Command.println("... Hello From b{Buzog} ^_^");
//        User user = new User("Asem", 1);
////        user.save(); // save user in database
//        Command.print(Model.of(User.class).find(1)); // get user from database
    }
}