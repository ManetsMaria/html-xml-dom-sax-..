package main;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NotePad.by on 21.05.2017.
 */
public class SerializationManager {
    public void serializeToFile(List<?> list, String fileName) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(fileName);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        for (Object object : list) {
            out.writeObject(object);
        }
    }

    public <T> List<T> deserializeFromFile(String fileName) throws IOException, ClassNotFoundException {
        List<T> list = new ArrayList<>();
        FileInputStream fileIn = new FileInputStream(fileName);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        while (fileIn.available() != 0) {
            list.add((T) in.readObject());
        }
        return list;
    }
}
