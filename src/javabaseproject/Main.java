package javabaseproject;

import javabaseproject.database.models.Person;
import javabaseproject.database.models.User;
import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.framework.commandline.Command;

/**
 *
 * @author AsemNajee
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Command.println("... Hello From b{Buzog} ^_^");
        User user = new User(1, "Asem");
        Command.println(user.toJson()); // print the user
    }
}