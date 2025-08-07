 /**
  *   >> Al-Reecha .~
  *   << BY : Asem Najee >>
  */

package javabaseproject;

import java.sql.SQLException;
import javabaseproject.model.Book;
import javabaseproject.model.Student;

/**
 * @Coder Asem Najee
 * @author Al-Reecha
 */
public class Main {
    public static void main(String[] args) throws SQLException, Exception {
//        Student st = new Student();
//        st.find(0);
//        System.out.println(fetchStudent(st));
//        
//        System.out.println("---------------------");
//        st.getAll().forEach(System.out::println);
//        
//        System.out.println("---------------------");
//        st.save();

        Book b = new Book();
        b.setName("Book Name");
        b.setAuthor("Asem");
        b.setPageCount(10);
        b.save();
        
    }
    
    public static String fetchStudent(Student st){
        return st.getId() + ": " + st.getName() + "[" + st.getAge() + "]";
    }
}