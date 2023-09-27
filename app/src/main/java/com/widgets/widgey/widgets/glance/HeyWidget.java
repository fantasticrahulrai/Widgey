package com.widgets.widgey.widgets.glance;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import com.widgets.widgey.R;

/**
 * Implementation of App Widget functionality.
 */
public class HeyWidget extends AppWidgetProvider {

    private static SharedPreferences mUserDetails;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_glance4);
        mUserDetails = context.getSharedPreferences("Details", 0);

        DateTime dt = new DateTime();
        String daydate =  dt.dayOfWeek().getAsText() +", "+ dt.dayOfMonth().getAsString() + " " +
                dt.monthOfYear().getAsText();

        String hey = "Hey \uD83D\uDC4B ";
        String name = mUserDetails.getString("name", "Dear");
        String fname = StringUtils.substringBefore(name, " ");

        int h = dt.getHourOfDay();

        if(h>=4 && h<12)
            views.setTextViewText(R.id.grettings, "Good Morning");
        else if(h>=12 && h<16)
            views.setTextViewText(R.id.grettings, "Good Afternoon");
        else if(h>=16 && h<21)
            views.setTextViewText(R.id.grettings, "Good Evening");
        else if(h>=21 && h<24)
            views.setTextViewText(R.id.grettings, "Good Night");
        else
            views.setTextViewText(R.id.grettings, "Sleep Well");

        views.setTextViewText(R.id.hey, hey+fname+"!");
        views.setTextViewText(R.id.daydate, "Today's "+ daydate);

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