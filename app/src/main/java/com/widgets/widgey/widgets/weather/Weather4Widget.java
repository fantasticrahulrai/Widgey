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
public class Weather4Widget extends AppWidgetProvider {


    Context context;
    static SharedPreferences mUserDetails, sharedPrefs;
    static int imgsrc, imgbg;
    static String tempString, city, weather, minmax;
    long ONE_HOUR = 60 * 60 * 1000L; // 1 hour
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_weather4);
        mUserDetails = context.getSharedPreferences("Details", 0);
        city = mUserDetails.getString("city", "London");
        weather = mUserDetails.getString("weather", "Clear Sky");
        tempString = mUserDetails.getString("temp", "30 C");
        imgsrc = mUserDetails.getInt("weatherIconMin", R.drawable.ic_minimalist_cloudy);
        imgbg = mUserDetails.getInt("weatherBgMin", R.drawable.img_bg_minimalist_night_clear_small);
        minmax = mUserDetails.getString("minmax", "25~35");

        views.setTextViewText(R.id.city, city);
        views.setTextViewText(R.id.weather, weather);
        views.setTextViewText(R.id.temp, tempString);
        views.setTextViewText(R.id.minmax, minmax);
        views.setImageViewResource(R.id.weatherIcon, imgsrc);
        views.setImageViewResource(R.id.weatherBg, imgbg);
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
        workerOn(context);
    }

    @Override
    public void onDisabled(Context context) {
        WorkManager.getInstance(context).cancelUniqueWork("weather4");
    }

    public static void workerOn(Context context) {


        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        PeriodicWorkRequest periodicRefreshRequest =
                new PeriodicWorkRequest.Builder(WeatherWorker.class, 1, TimeUnit.HOURS)
                        .setConstraints(constraints)
                        .addTag("WeatherTag")
                        .build();


        WorkManager workManager = WorkManager.getInstance(context);
        workManager.enqueueUniquePeriodicWork("weather4", ExistingPeriodicWorkPolicy.KEEP,periodicRefreshRequest);

        Log.d("Worker", "Worker On called 4 "+ periodicRefreshRequest.getId());

    }
}