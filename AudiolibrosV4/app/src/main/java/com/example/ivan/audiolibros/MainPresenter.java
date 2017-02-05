package com.example.ivan.audiolibros;

/**
 * Created by Ivan on 2/2/17.
 */

public class MainPresenter {

    private final LibroStorage libroStorage;
    private final View view;
    private final SaveLastBook saveLastBook;

    public MainPresenter(SaveLastBook saveLastBook, LibroStorage libroStorage, MainPresenter.View view) {
        this.saveLastBook= saveLastBook;
        this.libroStorage = libroStorage;
        this.view = view;
    }

    public void clickFavoriteButton() {
        if (libroStorage.hasLastBook()) {
            view.mostrarDetalle(libroStorage.getLastBook());
        } else {
            view.mostrarNoUltimaVisita();
        }
    }



    public void openDetalle(int id) {
        saveLastBook.execute(id);
        view.mostrarDetalle(id);
    }

    public interface View {
        void mostrarDetalle(int id);
        void mostrarNoUltimaVisita();
    }


}
