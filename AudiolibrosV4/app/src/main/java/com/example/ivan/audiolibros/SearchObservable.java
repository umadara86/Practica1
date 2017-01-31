package com.example.ivan.audiolibros;

import android.support.v7.widget.SearchView;

import java.util.Observable;

/**
 * Created by Ivan on 28/1/17.
 */

public class SearchObservable extends Observable implements SearchView.OnQueryTextListener {

    @Override
    public boolean onQueryTextChange(String query) {
        setChanged();
        notifyObservers(query);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }
}
