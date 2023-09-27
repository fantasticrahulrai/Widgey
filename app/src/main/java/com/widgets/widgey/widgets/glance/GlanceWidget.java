package com.widgets.widgey.widgets.glance;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import org.joda.time.DateTime;

import java.util.Random;

import com.widgets.widgey.R;

/**
 * Implementation of App Widget functionality.
 */
public class GlanceWidget extends AppWidgetProvider {

    private static final String TAG = "GlanceWidget";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        DateTime dt = new DateTime();
        Random ran = new Random();

        int h = dt.getHourOfDay();
        int r = ran.nextInt(3);

        String[] morning = context.getResources().getStringArray(R.array.greetings_morning);
        String[] afternoon = context.getResources().getStringArray(R.array.greetings_noon);
        String[] evening = context.getResources().getStringArray(R.array.greetings_evening);
        String[] night = context.getResources().getStringArray(R.array.greetings_night);
        String[] anytime = context.getResources().getStringArray(R.array.greetings_default);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_glance);

        views.setTextViewText(R.id.day, dt.dayOfWeek().getAsText() + ",");
        views.setTextViewText(R.id.date, dt.monthOfYear().getAsShortText() + " " + dt.dayOfMonth().getAsString());

        if (h >= 4 && h < 12)
            views.setTextViewText(R.id.grettings, morning[r]);
        else if (h >= 12 && h < 16)
            views.setTextViewText(R.id.grettings, afternoon[r]);
        else if (h >= 17 && h < 21)
            views.setTextViewText(R.id.grettings, evening[r]);
        else if (h >= 21 && h < 24)
            views.setTextViewText(R.id.grettings, night[r]);
        else
            views.setTextViewText(R.id.grettings, anytime[r]);


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


    /*@Override
    public void onReceive(Context context, Intent intent) {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisAppWidget = new ComponentName(context.getPackageName(), GlanceWidget.class.getName());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);
        onUpdate(context, appWidgetManager, appWidgetIds);

        final String action = intent.getAction()+"";
        if (action.equalsIgnoreCase("android.intent.action.TIME_SET")) {

            onUpdate(context, appWidgetManager, appWidgetIds);
            Log.d(TAG, "Time Changed");
        }

        else if (action.equalsIgnoreCase("android.intent.action.ACTION_DATE_CHANGED")) {

            onUpdate(context, appWidgetManager, appWidgetIds);
            Log.e(TAG, "Date changed");
        }

        else if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)) {
            Toast.makeText(context, "The device is charging", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Battery changed");

        }
        else if (intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)) {

            Log.e(TAG, "Battrey dis changed");
            Toast.makeText(context, "The device is not charging", Toast.LENGTH_SHORT).show();
        }


        Log.d(TAG, "On Received called "+ action);

    }*/

}