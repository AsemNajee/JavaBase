package javabaseproject.database.models;

import javabaseproject.javabase.core.annotations.PrimaryKey;
import javabaseproject.javabase.core.annotations.Unique;
import javabaseproject.javabase.core.database.models.Model;

@PrimaryKey("id")
public class Son extends User{

    // Don't delete this constructor please (: it will cause a problem
    public Son(){}

    public Son(int id, String name){
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

// ... add more fields with protected access modifier
}
