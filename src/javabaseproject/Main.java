package javabaseproject;

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
//        new User("password", "Said", 4).save();

        Command.println(Model.of(User.class).find(1).toJson());
        Model.of(User.class).getAll().stream().forEach(item -> Command.println(item.toJson()));
    }
}