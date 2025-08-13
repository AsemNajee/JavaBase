package javabaseproject.javabase.framework.commandline.controllers;

import javabaseproject.javabase.config.ENV;
import javabaseproject.javabase.framework.FileHandler;
import javabaseproject.javabase.framework.FilePaths;

import java.io.*;

public class RegisterController {
    public static void register(String model) throws IOException {

        addToRegisterClass(model);
    }
    public static void unregister(String model) throws IOException {
        String pattern = "(.*)" + model + "(;|.class)(.*)\n";
        String fileContent = readRegisterFile().toString();
        writeToRegisterFile(new StringBuilder(fileContent.replaceAll(pattern, "")));
    }

    private static void addToRegisterClass(String model) throws IOException {
        StringBuilder fileContent = readRegisterFile();
        int cursorOfAddNewRegisterModel = fileContent.indexOf("//{NEW_MODEL_HERE}//");
        fileContent.insert(cursorOfAddNewRegisterModel, "Recorder.add("+model+".class);\n\t");
        int cursorOfAddNewImportForModel = fileContent.indexOf("//{NEW_IMPORT_HERE}//");
        fileContent.insert(cursorOfAddNewImportForModel, "import {basePackage}.{modelsPackage}.".replace("{basePackage}", ENV.ROOT_PACKAGE).replace("{modelsPackage}", ENV.MODELS_PACKAGE) + model + ";\n");
        writeToRegisterFile(fileContent);
    }

    private static StringBuilder readRegisterFile() throws IOException {
        File file = new File(FilePaths.getRegisterFilePath());
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder fileContent = new StringBuilder();
        String temp;
        while((temp = br.readLine()) != null){
            fileContent.append(temp).append("\n");
        }
        br.close();
        return fileContent;
    }
    private static void writeToRegisterFile(StringBuilder fileContent) throws IOException {
        FileHandler.of(new File(FilePaths.getRegisterFilePath())).write(fileContent.toString());
    }
}
