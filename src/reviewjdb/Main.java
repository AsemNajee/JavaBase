 /**
  *   >> Al-Reecha .~
  *   << BY : Asem Najee >>
  */

package reviewjdb;

import java.sql.SQLException;
import reviewjdb.model.Student;

/**
 * @Coder Asem Najee
 * @author Al-Reecha
 */
public class Main {
    public static void main(String[] args) throws SQLException, Exception {
        Student st = new Student();
        st.find(0);
        System.out.println(fetchStudent(st));
        
        System.out.println("---------------------");
        st.getAll().forEach(System.out::println);
    }
    
    public static String fetchStudent(Student st){
        return st.getId() + ": " + st.getName() + "[" + st.getAge() + "]";
    }
}