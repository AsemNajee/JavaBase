 /**
  *   >> Al-Reecha .~
  *   << BY : Asem Najee >>
  */

package reviewjdb.jdbcmodel.core;

/**
 * @Coder Asem Najee
 * @author Al-Reecha
 */
public enum Types {
    STRING("VARCHAR(255)"), INT, FLOAT("FLOAT"), LONG, SHORT("TINYINT"), BYTE, DOUBLE, BOOLEAN;
    
    private String type;
    
    public String getType(){
        return type;
    }
    
    Types(){
        type = this.name();
    }
    
    Types(String type){
        this.type = type;
    }
}
