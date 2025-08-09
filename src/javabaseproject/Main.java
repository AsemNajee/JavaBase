package javabaseproject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author AsemNajee
 */
public class Main {
    public static void main() throws Exception {
        System.out.println(System.getProperty("user.dir"));
        String model = "TestModel";
        String fileContent = """
                package javabaseproject.javabase;
                \s
                import java.util.HashMap;
                import javabaseproject.javabase.core.RecordedClass;
                import javabaseproject.javabase.core.Recorder;
                import javabaseproject.model.Book;
                import javabaseproject.model.Person;
                import javabaseproject.model.Student;
                import javabaseproject.model.Test;
                import javabaseproject.model.TestModel;
                //{NEW_IMPORT_HERE}//
                public class MyModels {   \s
                    public static HashMap<String, RecordedClass> registerAll(){
                        Recorder.add(Book.class);
                        Recorder.add(TestModel.class);
                        //{NEW_MODEL_HERE}//
                        return Recorder.getModels();   \s
                    }\s
                    public static HashMap<String, RecordedClass> getRegisteredModels(){       \s
                        return registerAll();
                    }
                }
                                
                """.replaceAll("(.*)" + model + "(;|.class)(.*)\n", "");
        System.out.println(fileContent);
        Pattern p = Pattern.compile("(.*)" + model + "(;|.class)(.*)") ;
        Matcher m = p.matcher(fileContent);
        while(m.find()){
            System.out.println("find in : " + m.group());
        }


//        Student st = new Student();
//        st.find(0);
//        System.out.println(fetchStudent(st));
//        
//        System.out.println("---------------------");
//        st.getAll().forEach(System.out::println);
//        
//        System.out.println("---------------------");
//        st.save();

//        Book b = new Book();
//        b.setName("Book Name");
//        b.setAuthor("Asem");
//        b.setPageCount(10);
//        b.save();
        
    }
}