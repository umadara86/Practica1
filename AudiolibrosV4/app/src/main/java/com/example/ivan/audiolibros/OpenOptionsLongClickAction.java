package com.example.ivan.audiolibros;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by Ivan on 31/1/17.
 */

public class OpenOptionsLongClickAction implements LongClickAction{
    private final MainActivity mainActivity;
    private LayoutInflater inflador;
    public OpenOptionsLongClickAction(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        inflador = (LayoutInflater) mainActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    @Override
    public void execute(View view) {
        View v = inflador.inflate(R.layout.elemento_selector, null);
    }
}
