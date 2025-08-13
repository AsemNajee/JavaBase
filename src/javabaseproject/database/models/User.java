
package javabaseproject.database.models;

import javabaseproject.javabase.core.annotations.PrimaryKey;
import javabaseproject.javabase.core.annotations.Unique;
import javabaseproject.javabase.core.database.models.Model;

@PrimaryKey("id")
public class User extends Model<User>{
    public User(String password, String name, int id){
        this.password = password;
        this.name = name;
        this.id = id;
    }

    protected int id;
    @Unique
    protected String name;
    protected String password;

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

    @Override
    public String[] hidden(){
        return new String[]{
                "password"
        };
    }

// ... add more fields with protected access modifier
}