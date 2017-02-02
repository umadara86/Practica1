package com.example.ivan.audiolibros;

/**
 * Created by Ivan on 2/2/17.
 */

public class MainController {
    LibroStorage libroStorage;
    public MainController(LibroStorage libroStorage) { this.libroStorage = libroStorage;
    }
    public void saveLastBook(int id) { libroStorage.saveLastBook(id);
    }
}
