 /**
  *   >> Al-Reecha .~
  *   << BY : Asem Najee >>
  */

package reviewjdb.model;

/**
 * @Coder Asem Najee
 * @author Al-Reecha
 */
public class Student extends Person {
    protected int id;
    
    public Student(){
        super(Student.class);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Student{" + "id=" + id + ", " + super.toString() + '}';
    }
    
    
}
