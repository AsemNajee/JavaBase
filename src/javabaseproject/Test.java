package javabaseproject;

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
//        Command.print(DB.from(User.class).whereIn("id", 1, 2, 3, 4, 5).all());
    }
}

