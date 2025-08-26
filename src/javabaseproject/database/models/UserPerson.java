package javabaseproject.database.models;

import javabaseproject.javabase.core.annotations.ForeignKey;
import javabaseproject.javabase.core.annotations.PrimaryKey;
import javabaseproject.javabase.core.collections.ModelsCollection;
import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.core.database.models.Relations;

@PrimaryKey("id")
public class UserPerson extends Model<UserPerson> {

    protected int id;

    @ForeignKey(User.class)
    protected int userId;

    @ForeignKey(Person.class)
    protected String personName;

    public UserPerson(){}
    public UserPerson(int id, User user, Person person){
        this.userId = (int)user.getKey();
        this.personName = (String)person.getKey();
        this.id = id;
    }

    public static ModelsCollection<Person> belongsToMany(User user) throws Exception {
        return Relations.belongsToMany(UserPerson.class, Person.class, user);
    }
    public static ModelsCollection<User> belongsToMany(Person person) throws Exception {
        return Relations.belongsToMany(UserPerson.class, User.class, person);
    }
}
