package org.example.eventos;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static org.example.eventos.EventosAplicacion.guardarIdRegistro;

/**
 * Created by Ivan on 26/2/17.
 */

public class EventosFCMInstanceIDService extends FirebaseInstanceIdService {
    @Override

    public void onTokenRefresh() {
        String idPush;
        idPush = FirebaseInstanceId.getInstance().getToken();
        guardarIdRegistro(getApplicationContext(), idPush);
        }
}