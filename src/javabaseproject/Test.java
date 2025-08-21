package javabaseproject;

import javabaseproject.database.models.User;
import javabaseproject.javabase.core.database.Json;
import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.core.recorder.FieldController;
import javabaseproject.javabase.core.recorder.Recorder;
import javabaseproject.javabase.core.recorder.Types;
import javabaseproject.javabase.framework.commandline.Command;
import javabaseproject.javabase.lang.ar.ArabicFakeData;
import javabaseproject.javabase.lang.en.EnglishFakeData;

import java.util.Arrays;
import java.util.Random;

public class Test {

    public static void main(String[] args) throws Exception {
        User user = (User) Model.of(User.class).fromJson(
                """
                {"name":"Asem","id":1}
                """);
        Command.print(user);
    }
}

