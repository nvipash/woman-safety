package com.alexia.callbutton;

import android.Manifest;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.widget.RemoteViews;

import com.alexia.callbutton.MainActivity;
import com.alexia.callbutton.R;
import com.alexia.callbutton.fragments.ButtonFragment;

public class AppWidgetProvider extends android.appwidget.AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {

            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+677007558));
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, callIntent,
            0);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_button);
            views.setOnClickPendingIntent(R.id.widget_button, pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent){
    super.onReceive(context, intent);
    }

}
