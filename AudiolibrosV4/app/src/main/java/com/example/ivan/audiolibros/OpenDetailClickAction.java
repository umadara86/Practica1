package com.example.ivan.audiolibros;

/**
 * Created by Ivan on 28/1/17.
 */

public class OpenDetailClickAction implements ClickAction {
    private final MainActivity mainActivity;
    public OpenDetailClickAction(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void execute(int position) {
        mainActivity.mostrarDetalle(position);
    }
}
