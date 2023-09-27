package com.widgets.widgey.widgets.weather;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.widgets.widgey.R;
import com.widgets.widgey.helpers.WeatherWorker;

import java.util.concurrent.TimeUnit;



/**
 * Implementation of App Widget functionality.
 */
public class Weather1Widget extends AppWidgetProvider {

    static SharedPreferences mUserDetails;
    static String tempString, city, weather;
    static int imgsrc;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_weather1);
        mUserDetails = context.getSharedPreferences("Details", 0);
        tempString = mUserDetails.getString("temp", "30 C");
        imgsrc = mUserDetails.getInt("weatherIcon", R.drawable.weather_03);

        views.setTextViewText(R.id.temp, tempString);
        views.setImageViewResource(R.id.weatherIcon, imgsrc);
        appWidgetManager.updateAppWidget(appWidgetId, views);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {


        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }


    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        workerOn(context);
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        WorkManager.getInstance(context).cancelUniqueWork("weather1");
    }


    public static void workerOn(Context context) {


        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        PeriodicWorkRequest periodicRefreshRequest =
                new PeriodicWorkRequest.Builder(WeatherWorker.class, 2, TimeUnit.HOURS)
                        .setConstraints(constraints)
                        .addTag("WeatherTag")
                        .build();


        WorkManager workManager = WorkManager.getInstance(context);
        workManager.enqueueUniquePeriodicWork("weather1", ExistingPeriodicWorkPolicy.KEEP,periodicRefreshRequest);

        Log.d("Worker", "Worker On called"+ periodicRefreshRequest.getId());

    }




}


