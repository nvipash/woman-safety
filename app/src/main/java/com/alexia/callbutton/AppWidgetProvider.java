package com.alexia.callbutton;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

public class AppWidgetProvider extends android.appwidget.AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + 677007558));
            PendingIntent pendingIntent = PendingIntent
                    .getActivity(context, 0, callIntent, 0);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_button);
            views.setOnClickPendingIntent(R.id.widget_button, pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }
}