package javabaseproject.model;

import javabaseproject.javabase.core.annotations.ForeignKey;
import javabaseproject.javabase.core.annotations.NotNull;
import javabaseproject.javabase.core.annotations.PrimaryKey;
import javabaseproject.javabase.core.annotations.Unique;

@PrimaryKey("id")
public class Person extends Model<Person> {
    protected String name;
    protected int age;
    
    @NotNull 
    @Unique
    @ForeignKey(Person.class)
    protected int personId;
    
    public Person(){
        super(Person.class);
    }
    
    public Person(Class clazz){
        super(clazz);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" + "name=" + name + ", age=" + age + '}';
    }
    
    
}
