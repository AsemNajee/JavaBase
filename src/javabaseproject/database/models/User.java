

package javabaseproject.database.models;

import javabaseproject.javabase.core.annotations.ForeignKey;
import javabaseproject.javabase.core.collections.ModelsCollection;
import javabaseproject.javabase.core.annotations.PrimaryKey;
import javabaseproject.javabase.core.annotations.Unique;
import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.core.database.models.Relations;

@PrimaryKey("id")
public class User extends Model<User>{
	public User(String name, int id){
        this.name = name;
        this.id = id;
    }

    protected int id;
    @Unique
    protected String name;
    @ForeignKey(value = Person.class, key = "id")
    protected int personId;

    public Person person() throws Exception {
        return Relations.hasOne(this, Person.class);
    }

    // Don't delete this constructor please (: it will cause a problem
    public User(){}

    public User(int id, String name){
        this.id = id;
        this.name = name;
    }

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public ModelsCollection<Person> persons() throws Exception {
        return UserPerson.belongsToMany(this);
    }

}