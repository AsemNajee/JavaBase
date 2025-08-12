package javabaseproject.javabase.framework;

import javabaseproject.javabase.framework.commandline.Command;
import javabaseproject.javabase.framework.commandline.output.Colors;

import java.io.*;

public class FileHandler {
    private final File file;
    public FileHandler(String filePath) {
        this(new File(filePath));
    }

    public FileHandler(File file){
        this.file = file;
    }

    public static FileHandler of(String filePath) {
        return new FileHandler(filePath);
    }
    public static FileHandler of(File file) {
        return new FileHandler(file);
    }

    public void write(String content) throws IOException {
        FileWriter fw = new FileWriter(file);
        fw.write(content);
        fw.close();
    }

    public String read() throws IOException {
        String readed = "";
        BufferedReader fr = new BufferedReader(new FileReader(file));
        String temp;
        while((temp = fr.readLine()) != null){
            readed += temp;
        }
        return readed;
    }

    public boolean delete(){
        return file.delete();
    }

    public boolean exists(){
        return file.exists();
    }
}
