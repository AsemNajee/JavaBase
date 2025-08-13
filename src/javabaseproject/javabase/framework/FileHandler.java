package javabaseproject.javabase.framework;

import java.io.*;

public class FileHandler {
    private static boolean dirsCreated;
    private final File file;
    public FileHandler(String filePath) {
        this(new File(filePath));
    }

    public FileHandler(File file){
        this(file, true);
    }

    private FileHandler(File file, boolean makeDefaultsDirs){
        this.file = file;
        if(makeDefaultsDirs)
            makeDefaultsDirs();
    }

    private static FileHandler of(String file, boolean makeDefaultDirs){
        return new FileHandler(new File(file), makeDefaultDirs);
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
        StringBuilder readed = new StringBuilder();
        BufferedReader fr = new BufferedReader(new FileReader(file));
        String temp;
        while((temp = fr.readLine()) != null){
            readed.append("\n").append(temp);
        }
        return readed.toString();
    }

    public boolean delete(){
        if(file.exists()){
            return file.delete();
        }else{
            return false;
        }
    }

    public boolean exists(){
        return file.exists();
    }

    public boolean makeDir(){
        return file.mkdirs();
    }

    private void makeDefaultsDirs(){
        if(dirsCreated)
            return;
        FileHandler fh ;
        if(!(fh = FileHandler.of(FilePaths.getModelsPath(), false)).exists()){
            fh.makeDir();
        }
        if(!(fh = FileHandler.of(FilePaths.getSeedersPath(), false)).exists()){
            fh.makeDir();
        }
        if(!(fh = FileHandler.of(FilePaths.getFactoriesPath(), false)).exists()){
            fh.makeDir();
        }
        dirsCreated = true;
    }
}
