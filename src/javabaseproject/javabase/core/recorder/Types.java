package javabaseproject.javabase.core.recorder;

/**
 * 
 * @author AsemNajee
 */
public enum Types {
    STRING("VARCHAR(255)"), INT, FLOAT("FLOAT"), LONG, SHORT("TINYINT"), BYTE, DOUBLE, BOOLEAN;
    
    private String type;
    private String javaType;
    
    public String getType(){
        return type;
    }

    public String getJavaType(){
        return javaType;
    }
    
    Types(){
        type = this.name();
        javaType = this.name().toLowerCase();
        if(javaType.equals("string")){
            javaType = "String";
        }
    }
    
    Types(String type){
        this.type = type;
        javaType = this.name().toLowerCase();
        if(javaType.equals("string")){
            javaType = "String";
        }
    }
}
