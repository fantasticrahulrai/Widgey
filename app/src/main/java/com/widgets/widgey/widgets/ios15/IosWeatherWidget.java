package com.widgets.widgey.widgets.ios15;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

import com.widgets.widgey.R;

/**
 * Implementation of App Widget functionality.
 */
public class IosWeatherWidget extends AppWidgetProvider {

    Context context;
    static SharedPreferences mUserDetails, sharedPrefs;
    static int imgsrc, imgbg;
    static String tempString, city, weather, minmax;
    long ONE_HOUR = 60 * 60 * 1000L; // 1 hour

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_weatherios);

        mUserDetails = context.getSharedPreferences("Details", 0);
        city = mUserDetails.getString("city", "London");
        weather = mUserDetails.getString("weather", "Clear Sky");
        tempString = mUserDetails.getString("temp", "30 C");
        imgsrc = mUserDetails.getInt("weatherIconPer", R.drawable.ic_per_sunny);
        imgbg = mUserDetails.getInt("weatherBg", R.drawable.weather_bg_ios_sunny);
        minmax = mUserDetails.getString("minmax", "25~35");

        views.setTextViewText(R.id.weather, weather);
        views.setTextViewText(R.id.temp, tempString);
        views.setTextViewText(R.id.minmax, minmax);
        views.setImageViewResource(R.id.weatherIcon, imgsrc);
        views.setImageViewResource(R.id.weatherBg, imgbg);
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        context = context;
        mUserDetails = context.getSharedPreferences("Details", 0);
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);

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
         WorkManager.getInstance(context).cancelUniqueWork("weatherios");
    }

    public static void workerOn(Context context) {


        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        /*PeriodicWorkRequest periodicRefreshRequest =
                new PeriodicWorkRequest.Builder(WeatherWorker.class, 2, TimeUnit.HOURS)
                        .setConstraints(constraints)
                        .addTag("WeatherTag")
                        .build();


        WorkManager workManager = WorkManager.getInstance(context);
        workManager.enqueueUniquePeriodicWork("weatherios", ExistingPeriodicWorkPolicy.KEEP,periodicRefreshRequest);

        Log.d("Worker", "Worker On called"+ periodicRefreshRequest.getId());*/

    }


}