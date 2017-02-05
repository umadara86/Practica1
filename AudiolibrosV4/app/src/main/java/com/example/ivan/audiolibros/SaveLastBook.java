package com.example.ivan.audiolibros;

/**
 * Created by Ivan on 4/2/17.
 */

public class SaveLastBook {
    private final LibroStorage librosStorage;

    public SaveLastBook(LibroStorage librosStorage) {
        this.librosStorage = librosStorage;
    }
    public void execute(int id) {
        librosStorage.saveLastBook(id);
    }
}
