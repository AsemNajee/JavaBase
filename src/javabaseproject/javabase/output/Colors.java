package javabaseproject.javabase.output;

public enum Colors {
    BLACK(0), 
    RED(1), 
    GREEN(2),
    YELLOW(3),
    BLUE(4),
    PURPLE(5),
    SKY(6),
    WHITE(7);

    private int code;
    Colors(int code){
        this.code = code;
    }
    
    public String textColor(){
        return "\u001B[3" + code + "m";
    }
    public String bgColor(){
        return "\u001B[4" + code + "m";
    }
    public String reset(){
        return "\u001B[0m";
    }
}
