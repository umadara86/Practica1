package com.example.ivan.audiolibros;

/**
 * Created by Ivan on 18/1/17.
 */

public interface onConectarListener {
    void onConectar(int valor);
    void onConectado(String ip, int puerto);
    void onDesconectado();
    void onError(String mensage);
}
