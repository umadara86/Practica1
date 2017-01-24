package com.example.ivan.audiolibros;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

/**
 * Created by Ivan on 21/1/17.
 */

public class MiAppWidgetProvider extends AppWidgetProvider {

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] widgetIds) {
        for (int widgetId : widgetIds) {
            actualizaWidget(context, widgetId);
        }
    }

    public static void actualizaWidget(Context context, int widgetId) {
        int cont = incrementaContador(context, widgetId);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        remoteViews.setTextViewText(R.id.textView1, "Contador: " + cont);


        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.analogClock1, pendingIntent);


        AppWidgetManager.getInstance(context).updateAppWidget(widgetId, remoteViews);
    }

    private static int incrementaContador(Context context, int widgetId) {
        SharedPreferences prefs = context.getSharedPreferences("contadores", Context.MODE_PRIVATE);
        int cont = prefs.getInt("cont_" + widgetId, 0);
        cont++;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("cont_" + widgetId, cont);
        editor.commit();
        return cont;
    }
}
