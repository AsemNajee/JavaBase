package javabaseproject.database.models;

import javabaseproject.javabase.core.annotations.PrimaryKey;
import javabaseproject.javabase.core.annotations.Unique;
import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.core.database.models.Relations;

@PrimaryKey("id")
public class Person extends Model<Person>{

    protected int id;
    @Unique
    protected String name;
    protected int user_id;

    // Don't delete this constructor please (: it will cause a problem
    public Person(){}

    public Person(int id, String name){
        this.id = id;
        this.name = name;
    }

    public User user() throws Exception {
        return Relations.belongsTo(this, User.class);
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

// ... add more fields with protected access modifier
}
