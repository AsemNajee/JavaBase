package javabaseproject;

import java.lang.reflect.AccessFlag;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import javabaseproject.javabase.core.annotations.NotNull;
import javabaseproject.javabase.core.annotations.Unique;
import javabaseproject.model.Student;

public class ReviewJDB {
    
    /**
     * package => 0
     * public => 1
     * private => 2
     * protected => 4
     * static => 8
     * 
     * 
     */

    @NotNull
    @Unique
    String name = "asem";
    private String email;
    public int age;
    protected float[] degrees;
    String[] frinds;
    static int x ;
    ArrayList<String> names;
    

    public static void main(String[] args) throws SQLException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, Exception {
        
        System.out.println(Arrays.toString(Student.class.getSuperclass().getDeclaredFields()));
//        System.out.println(Arrays.asList(Student.class.getDeclaredFields());
//        System.out.println(new Controller(Student.class));
//        System.out.println(Student.class.desiredAssertionStatus());
//        System.out.println(Student.class.get);
        
        
        
        
        System.out.println("====================");
        ReviewJDB r = new ReviewJDB();
        System.out.println(new Controller(ReviewJDB.class));
        String splits[] = ReviewJDB.class.getName().split("\\.");
        System.out.println(splits[splits.length -1]);
        Arrays.stream(ReviewJDB.class.getNestHost().getDeclaredFields()).forEach(
                item -> {
                    boolean isAcceptable = item.getAnnotatedType().toString().matches("^[A-Za-z\\.]*(String|int)$")
                            && item.accessFlags().stream().filter(m -> m == AccessFlag.PRIVATE).count() > 0;
                    try {
                        System.out.println(
                                "getName: " + item.getName() + "\n"
                                + "value: " + item.get(new ReviewJDB()) + "\n"
                                + "toGenericString: " + item.toGenericString() + "\n"
                                + "getAnnotatedType: " + item.getAnnotatedType() + "\n"
                                + "getDeclaredAnnotations: " + Arrays.stream(item.getDeclaredAnnotations()).map(a -> a).collect(Collectors.toList()).toString() + "\n"
                                + "getAnnotations: " + Arrays.stream(item.getAnnotations()).map(a -> a).collect(Collectors.toList()).toString() + "\n"
                                + "getModifiers: " + item.getModifiers() + "\n"
                                + "accessFlags: " + item.accessFlags() + "\n"
                                + "isAcceptable: " + isAcceptable + "\n"
                                + "getType: " + item.getType() + "\n"
                        );
                    } catch (Exception e) {

                    }
                }
        );
    }

    void one() {

    }

    void tow() {

    }

    
    void three() {

    }

    @Override
    public String toString() {
        return "ReviewJDB{" + "name=" + name + ", email=" + email + '}';
    }

}

class Controller {

    String[] fields, methods;

    Controller(Class clazz) throws Exception {
        fields = new String[clazz.getDeclaredFields().length];
        methods = new String[clazz.getDeclaredMethods().length];
        fields = (String[]) Arrays.stream(clazz.getDeclaredFields()).map(f -> f.getName()).collect(Collectors.toList()).toArray(fields);
        methods = (String[]) Arrays.stream(clazz.getDeclaredMethods()).map(m -> m.getName()).collect(Collectors.toList()).toArray(methods);
    }

    @Override
    public String toString() {
        return "Controller{" + "fields=" + Arrays.toString(fields) + ", methods=" + Arrays.toString(methods) + '}';
    }
}
