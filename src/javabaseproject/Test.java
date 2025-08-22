package javabaseproject;

import javabaseproject.database.models.Person;
import javabaseproject.database.models.User;
import javabaseproject.javabase.core.database.Connector;
import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.framework.commandline.Command;


public class Test {

    public static void main(String[] args) throws Exception {
        Person person = Model.of(Person.class)
                .find(2);
        Command.print(person);
        Command.print(person.user());
//        User user = Model.of(User.class)
//                .query()
//                .limit(10)
//                .orderBy("id")
//                .where("id", "1")
//                .where("id", "1")
//                .get("name", "id");
//        Command.print(user);
//        Command.println("has one");
//        Command.print(user.person());
//        Command.println("has many");
//        Command.print(user.persons());
//                .toQueryString();
//        var res = Connector.getConnection().prepareStatement(sql).executeQuery();
//        Command.println(sql);
//        res.next();
//        for (int i = 1; i <= res.getMetaData().getColumnCount(); i++) {
//            Command.println(res.getString(i));
//        }
//        var sql = Connector.getConnection().prepareStatement("select * from user join rel");
//        var res = sql.executeQuery();
//        res.next();
//        int i = 1;
//        int count = res.getMetaData().getColumnCount();
//        for (int j = 1; j <= count; j++) {
//            Command.print(res.getMetaData().getColumnClassName(j));
//            Command.print(": " + res.getMetaData().getPrecision(j));
//            Command.print(": " + res.getMetaData().getScale(j));
//            Command.print(": " + res.getMetaData().getColumnName(j));
//            Command.print(": " + res.getMetaData().getTableName(j));
//            Command.println(": " + res.getString(j));
//        }
//        User user = (User) Model.of(User.class).fromJson(
//                """
//                {"name":"Asem","id":1}
//                """);
//        Command.print(user);
    }
}

