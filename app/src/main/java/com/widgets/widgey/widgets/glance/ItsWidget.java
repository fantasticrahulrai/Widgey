package com.widgets.widgey.widgets.glance;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;

import org.joda.time.DateTime;

import com.widgets.widgey.R;
import com.widgets.widgey.helpers.WeatherWorker;

/**
 * Implementation of App Widget functionality.
 */
public class ItsWidget extends AppWidgetProvider {

    private static final String TAG = "ItsWidget";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_its);
        //views.setTextViewText(R.id.appwidget_text, widgetText);

        DateTime dt = new DateTime();
        String date = dt.dayOfMonth().getAsString();

        if(date.length()<2)
            date = "0"+date;

        views.setTextViewText(R.id.day, dt.dayOfWeek().getAsShortText());
        views.setTextViewText(R.id.date, date);
        views.setTextViewText(R.id.dateFull, dt.monthOfYear().getAsString()+"/"+dt.year().getAsString());

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

        Log.d(TAG, "On Update called");
    }

    @Override
    public void onEnabled(Context context) {

        Log.d(TAG, "On enabled called");

    }
    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}