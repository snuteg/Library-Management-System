package com.natig.library.service;

import com.natig.library.storage.DatabaseData;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class StorageService {
    public static void save(DatabaseData data, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(data);
        } catch (IOException e) {
            System.out.println("Save error: " + e.getMessage());
        }
    }

    public static DatabaseData load(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (DatabaseData) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new DatabaseData();
        }
    }
}
