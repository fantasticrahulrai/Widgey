package com.widgets.widgey.widgets.weather;

import static android.app.job.JobInfo.NETWORK_TYPE_ANY;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.work.Configuration;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.kwabenaberko.openweathermaplib.constant.Units;
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;
import com.kwabenaberko.openweathermaplib.implementation.callback.CurrentWeatherCallback;
import com.kwabenaberko.openweathermaplib.model.currentweather.CurrentWeather;

import org.apache.commons.text.WordUtils;

import java.util.concurrent.TimeUnit;

import com.widgets.widgey.R;
import com.widgets.widgey.helpers.Utils;
import com.widgets.widgey.helpers.WeatherWorker;

/**
 * Implementation of App Widget functionality.
 */
public class Weather2Widget extends AppWidgetProvider {

    static SharedPreferences mUserDetails;
    static SharedPreferences.Editor mEditor;
    static String tempString, city, weather;
    static int imgsrc;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_weather2);
        mUserDetails = context.getSharedPreferences("Details", 0);
        city = mUserDetails.getString("city", "London");
        weather = mUserDetails.getString("weather", "Clear Sky");
        tempString = mUserDetails.getString("temp", "30 C");
        imgsrc = mUserDetails.getInt("weatherIcon", R.drawable.weather_03);

        views.setTextViewText(R.id.city, city);
        views.setTextViewText(R.id.weather, weather);
        views.setTextViewText(R.id.temp, tempString);
        views.setImageViewResource(R.id.weatherIcon, imgsrc);
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
       /* GPSTracker gpsTracker = new GPSTracker(context);

        mEditor = mUserDetails.edit();
        Utils.putDouble(mEditor, "lat", gpsTracker.getLatitude()).apply();
        Utils.putDouble(mEditor, "long", gpsTracker.getLongitude()).apply();*/

        workerOn(context);

    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        WorkManager.getInstance(context).cancelUniqueWork("weather2");
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
        workManager.enqueueUniquePeriodicWork("weather2", ExistingPeriodicWorkPolicy.KEEP,periodicRefreshRequest);

        Log.d("Worker", "Worker On called"+ periodicRefreshRequest.getId());

    }

    public static class WeatherScheduler extends JobService {

        SharedPreferences mUserDetails, sharedPrefs;
        double latitude, longitude;
        OpenWeatherMapHelper helper;
        String tempString, weathericon, city, weather;

        public WeatherScheduler() {

            Configuration.Builder builder  = new Configuration.Builder();
            builder.setJobSchedulerJobIdRange(0,1000);

        }

        @Override
        public boolean onStartJob(JobParameters params) {

            Context context = this;
            mUserDetails = context.getSharedPreferences("Details", 0);
            sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);

            latitude = Utils.getDouble(mUserDetails, "lat", 51.509865);
            longitude = Utils.getDouble(mUserDetails, "long", -0.118092);

            helper = new OpenWeatherMapHelper("20c8e6c83321eeaf71bd5c675d5b1057");
            helper.setUnits(Units.METRIC);

            helper.getCurrentWeatherByGeoCoordinates(latitude, longitude, new CurrentWeatherCallback() {
                @Override
                public void onSuccess(CurrentWeather cw) {
                    city = cw.getName();
                    weather = WordUtils.capitalizeFully(cw.getWeather().get(0).getDescription());
                    tempString = (int) cw.getMain().getTemp() + "°";
                    weathericon = cw.getWeather().get(0).getIcon();

                    switch (weathericon) {
                        case "01d":
                            imgsrc = R.drawable.weather_01;
                            break;
                        case "01n":
                            imgsrc = R.drawable.weather_01n;
                            break;
                        case "02d":
                        case "02n":
                            imgsrc = R.drawable.weather_02;
                            break;
                        case "03d":
                            imgsrc = R.drawable.weather_03;
                            break;
                        case "03n":
                            imgsrc = R.drawable.weather_03n;
                            break;
                        case "04d":
                            imgsrc = R.drawable.weather_04;
                            break;
                        case "04n":
                            imgsrc = R.drawable.weather_04n;
                            break;
                        case "09d":
                        case "09n":
                            imgsrc = R.drawable.weather_09;
                            break;
                        case "10d":
                            imgsrc = R.drawable.weather_10;
                            break;
                        case "10n":
                            imgsrc = R.drawable.weather_10n;
                            break;
                        case "11d":
                        case "11n":
                            imgsrc = R.drawable.weather_11;
                            break;
                        case "13d":
                        case "13n":
                            imgsrc = R.drawable.weather_13;
                            break;
                        case "50d":
                        case "50n":
                            imgsrc = R.drawable.weather_50;
                            break;
                        default:
                            imgsrc = R.drawable.weather_none_available;
                            break;
                    }


                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_weather2);
                    ComponentName thisWidget = new ComponentName(context, Weather2Widget.class);

                    views.setTextViewText(R.id.city, city);
                    views.setTextViewText(R.id.weather, weather);
                    views.setTextViewText(R.id.temp, tempString);
                    views.setImageViewResource(R.id.weatherIcon, imgsrc);
                    appWidgetManager.updateAppWidget(thisWidget, views);

                    Toast.makeText(context, "Weather fetching success "+ city, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Throwable throwable) {

                    Toast.makeText(context, "Weather fetching failed ", Toast.LENGTH_SHORT).show();

                }

            });

            return false;


        }

        @Override
        public boolean onStopJob(JobParameters params) {


            return false;
        }

    }

    public static void turnOnJobScheduler(Context context) {

        long ONE_HOUR = 60 * 60 * 1000L; // 1 hour

        ComponentName componentName = new ComponentName(context, WeatherScheduler.class);
        JobInfo jobInfo = new JobInfo.Builder(001, componentName)
                //.setRequiresCharging(true)
                .setPersisted(true)
                .setPeriodic(ONE_HOUR)
                .setRequiredNetworkType(NETWORK_TYPE_ANY)
                .build();


        JobScheduler mJobScheduler = (JobScheduler)context.getSystemService(context.JOB_SCHEDULER_SERVICE);
        int resultCode = mJobScheduler.schedule(jobInfo);
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Toast.makeText(context, "Weather Scheduled", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Error Scheduling Weather", Toast.LENGTH_SHORT).show();
        }
    }

    public static void turnOffJobScheduler(Context context) {

        JobScheduler mJobScheduler = (JobScheduler)context.getSystemService(context.JOB_SCHEDULER_SERVICE);
        mJobScheduler.cancel(001);

    }

    public static boolean isJobServiceOn( Context context ) {
        JobScheduler scheduler = (JobScheduler) context.getSystemService( Context.JOB_SCHEDULER_SERVICE ) ;

        boolean hasBeenScheduled = false ;

        for ( JobInfo jobInfo : scheduler.getAllPendingJobs() ) {
            if ( jobInfo.getId() == 001 ) {
                hasBeenScheduled = true ;
                break ;
            }
        }

        return hasBeenScheduled ;
    }
}