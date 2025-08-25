package javabaseproject.database.models;

import javabaseproject.javabase.core.annotations.PrimaryKey;
import javabaseproject.javabase.core.annotations.Unique;
import javabaseproject.javabase.core.collections.ModelsCollection;
import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.core.database.models.Relations;

@PrimaryKey("id")
public class Book extends Model<Book>{

    protected int id;
    @Unique
    protected String name;

    // Don't delete this constructor please (: it will cause a problem
    public Book(){}

    public Book(int id, String name){
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
        return BookPerson.belongsToMany(this);
    }

// ... add more fields with protected access modifier
}
