package javabaseproject;

import javabaseproject.database.models.Son;
import javabaseproject.database.models.User;
import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.core.recorder.Recorder;
import javabaseproject.javabase.framework.commandline.Command;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public class Test {

    public static void main(String[] args) throws Exception {
//        Recorder.getModels().forEach((k, v) -> Command.println(v));
//        Model.of(User.class).getAll().forEach(Command::println);
//        Son s = new Son(2, "A son");
//        s.save();
        Model.of(Son.class).getAll().forEach(item -> Command.println(item.toJson()));
    }
}

