
package javabaseproject.database.models;

import javabaseproject.javabase.core.annotations.Default;
import javabaseproject.javabase.core.annotations.NotNull;
import javabaseproject.javabase.core.collections.ModelsCollection;
import javabaseproject.javabase.core.annotations.PrimaryKey;
import javabaseproject.javabase.core.annotations.Unique;
import javabaseproject.javabase.core.database.models.Model;
import javabaseproject.javabase.core.database.models.Relations;

@PrimaryKey("name")
public class Person extends Model<Person>{

    @Unique @NotNull
    protected int id;
    protected String name;
    @Default("male")
    protected String gender;

    // Don't delete this constructor please (: it will cause a problem
    public Person(){}

    public Person(int id, String name, String gender){
        this.id = id;
        this.name = name;
        this.gender = gender;
    }
    public Person(int id, String name){
        this.id = id;
        this.name = name;
    }

    public Person(String name) {
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

    /**
     * this Person class is related with the Book class by relation
     * many to many, so this relation is need to middleware table and that
     * is BookPerson class and that called 'pivot' . </br>
     * this method is get all books of this person using pivot
     *
     * @return collection of books related with this person
     */
    public ModelsCollection<Book> books() throws Exception {
        return BookPerson.belongsToMany(this);
    }

}