package org.example.eventos;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static org.example.eventos.EventosAplicacion.mostrarDialogo;

/**
 * Created by Ivan on 26/2/17.
 */

public class EventosFCMService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) { if (remoteMessage.getData().size() > 0) {
        String evento="";
        evento ="Evento: "+remoteMessage.getData().get("evento")+ "\n";
        evento = evento + "Día: "+ remoteMessage.getData().get("dia")+ "\n";
        evento = evento +"Ciudad: "+ remoteMessage.getData().get("ciudad")+"\n";
        evento = evento +"Comentario: " +remoteMessage.getData().get("comentario");
        mostrarDialogo(getApplicationContext(), evento);
    } else {
        if (remoteMessage.getNotification() != null) {
            mostrarDialogo(getApplicationContext(), remoteMessage.getNotification().getBody());
        }
    }
    }
}