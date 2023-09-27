package com.widgets.widgey.widgets.glance;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import org.joda.time.DateTime;

import com.widgets.widgey.R;
import com.widgets.widgey.helpers.WeatherWorker;

/**
 * Implementation of App Widget functionality.
 */
public class TodaysWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_glance3);

        DateTime dt = new DateTime();
        String date = dt.dayOfMonth().getAsString() + " " +
                dt.monthOfYear().getAsText();

        views.setTextViewText(R.id.day, dt.dayOfWeek().getAsText());
        views.setTextViewText(R.id.date, date);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}