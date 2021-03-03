package Handlers;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHandler {

    public void writeObjectToFile(Object object, String file) {
        try {
            Path path = Paths.get(file);
            File filePath = path.toFile();
            if (!filePath.exists()) {
                Files.createDirectories(path);
                Files.createFile(path);
            }
            FileOutputStream fos = new FileOutputStream(filePath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("FileHandler/writeObjectToFile: " + e);
        }
    }

    public Object readObjectFromFile(String file) {
        Object result = null;
        Path path = Paths.get(file);
        File filePath = path.toFile();
        if (!filePath.exists()) {
            throw new RuntimeException("File Not Found!");
        }
        try {
            FileInputStream fis = new FileInputStream(filePath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            result = ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
            System.out.println("FileHandler/readObjectToFile: " + e);
        }
        return result;
    }
}
