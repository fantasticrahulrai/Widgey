package com.widgets.widgey.helpers;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.kwabenaberko.openweathermaplib.constant.Units;
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;
import com.kwabenaberko.openweathermaplib.implementation.callback.CurrentWeatherCallback;
import com.kwabenaberko.openweathermaplib.model.currentweather.CurrentWeather;
import com.widgets.widgey.R;
import com.widgets.widgey.widgets.ios15.IosWeatherWidget;
import com.widgets.widgey.widgets.weather.Weather1Widget;
import com.widgets.widgey.widgets.weather.Weather2Widget;
import com.widgets.widgey.widgets.weather.Weather3Widget;
import com.widgets.widgey.widgets.weather.Weather4Widget;

import org.apache.commons.text.WordUtils;


public class WeatherWorker extends Worker {


    SharedPreferences mUserDetails, sharedPrefs;
    SharedPreferences.Editor mEditor;
    private static final String WORK_RESULT = "work_result";
    private Context mContext;
    double latitude, longitude;
    OpenWeatherMapHelper helper;
    String tempString, weathericon, city, weather, minmax;
    private int imgsrc, imgbg, imgios, imgmin, imgminbg, imgper;

    public WeatherWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

        mContext = context;
        mUserDetails = context.getSharedPreferences("Details", 0);
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);

        latitude = Utils.getDouble(mUserDetails, "lat", 51.509865);
        longitude = Utils.getDouble(mUserDetails, "long", -0.118092);

        helper = new OpenWeatherMapHelper("3e0135ec8149a56f107e91d7385f8cca");
        helper.setUnits(Units.METRIC);

    }

    @NonNull
    @Override
    public Result doWork() {
        Data taskData = getInputData();
        // String taskDataString = taskData.getString(MainActivity.MESSAGE_STATUS);

        helper.getCurrentWeatherByGeoCoordinates(latitude, longitude, new CurrentWeatherCallback() {
            @Override
            public void onSuccess(CurrentWeather cw) {
                city = cw.getName();
                weather = WordUtils.capitalizeFully(cw.getWeather().get(0).getDescription());
                tempString = (int) cw.getMain().getTemp() + "°";
                weathericon = cw.getWeather().get(0).getIcon();
                minmax = (int) cw.getMain().getTempMin()-3+"°~"+(int)(cw.getMain().getTempMax()+3)+"°";

                switch (weathericon) {
                    case "01d":
                        imgsrc = R.drawable.weather_01;
                        imgios = R.drawable.ic_ios_sunny;
                        imgper = R.drawable.ic_per_sunny;
                        imgmin = R.drawable.ic_minimalist_sunny;
                        imgminbg = R.drawable.img_bg_minimalist_sunny_small;
                        imgbg = R.drawable.weather_bg_ios_sunny;
                        break;
                    case "01n":
                        imgsrc = R.drawable.weather_01n;
                        imgios = R.drawable.ic_ios_night_clear;
                        imgper = R.drawable.ic_per_night_clear;
                        imgmin = R.drawable.ic_minimalist_night_clear;
                        imgminbg = R.drawable.img_bg_minimalist_night_clear_small;
                        imgbg = R.drawable.weather_bg_ios_night;
                        break;
                    case "02d":
                        imgsrc = R.drawable.weather_02;
                        imgios = R.drawable.ic_ios_sunny;
                        imgper = R.drawable.ic_per_sunny;
                        imgmin = R.drawable.ic_minimalist_sunny;
                        imgminbg = R.drawable.img_bg_minimalist_sunny_small;
                        imgbg = R.drawable.weather_bg_ios_sunny;
                        break;
                    case "02n":
                        imgsrc = R.drawable.weather_02n;
                        imgios = R.drawable.ic_ios_night_clear;
                        imgper = R.drawable.ic_per_night_clear;
                        imgmin = R.drawable.ic_minimalist_night_clear;
                        imgminbg = R.drawable.img_bg_minimalist_night_clear_small;
                        imgbg = R.drawable.weather_bg_ios_night;
                        break;
                    case "03d":
                        imgsrc = R.drawable.weather_03;
                        imgios = R.drawable.ic_ios_cloudy;
                        imgper = R.drawable.ic_per_cloudy;
                        imgmin = R.drawable.ic_minimalist_cloudy;
                        imgminbg = R.drawable.img_bg_minimalist_cloudy_small;
                        imgbg = R.drawable.weather_bg_ios_cloudy;
                        break;
                    case "03n":
                        imgsrc = R.drawable.weather_03n;
                        imgios = R.drawable.ic_ios_night_cloudy;
                        imgper = R.drawable.ic_per_night_cloudy;
                        imgmin = R.drawable.ic_minimalist_night_cloudy;
                        imgminbg = R.drawable.img_bg_minimalist_night_cloudy_small;
                        imgbg = R.drawable.weather_bg_ios_cloudy;
                        break;
                    case "04d":
                        imgsrc = R.drawable.weather_04;
                        imgios = R.drawable.ic_ios_mostcloudy;
                        imgper = R.drawable.ic_per_mostcloudy;
                        imgmin = R.drawable.ic_minimalist_mostcloudy;
                        imgminbg = R.drawable.img_bg_minimalist_mostcloudy_small;
                        imgbg = R.drawable.weather_bg_ios_cloudy;
                        break;
                    case "04n":
                        imgsrc = R.drawable.weather_04n;
                        imgios = R.drawable.ic_ios_mostcloudy;
                        imgper = R.drawable.ic_per_mostcloudy;
                        imgmin = R.drawable.ic_minimalist_mostcloudy;
                        imgminbg = R.drawable.img_bg_minimalist_night_mostcloudy_small;
                        imgbg = R.drawable.weather_bg_ios_cloudy;
                        break;
                    case "09d":
                        imgsrc = R.drawable.weather_09;
                        imgios = R.drawable.ic_ios_rain;
                        imgper = R.drawable.ic_per_rain;
                        imgmin = R.drawable.ic_minimalist_rain;
                        imgminbg = R.drawable.img_bg_minimalist_rain_small;
                        imgbg = R.drawable.weather_bg_ios_rain;
                        break;
                    case "09n":
                        imgsrc = R.drawable.weather_09;
                        imgios = R.drawable.ic_ios_night_rain;
                        imgper = R.drawable.ic_per_night_rain;
                        imgmin = R.drawable.ic_minimalist_night_rain;
                        imgminbg = R.drawable.img_bg_minimalist_night_rain_small;
                        imgbg = R.drawable.weather_bg_ios_rain;
                        break;
                    case "10d":
                        imgsrc = R.drawable.weather_10;
                        imgios = R.drawable.ic_ios_rain;
                        imgper = R.drawable.ic_per_rain;
                        imgmin = R.drawable.ic_minimalist_rain;
                        imgminbg = R.drawable.img_bg_minimalist_rain_small;
                        imgbg = R.drawable.weather_bg_ios_rain;
                        break;
                    case "10n":
                        imgsrc = R.drawable.weather_10n;
                        imgios = R.drawable.ic_ios_night_rain;
                        imgper = R.drawable.ic_per_night_rain;
                        imgmin = R.drawable.ic_minimalist_night_rain;
                        imgminbg = R.drawable.img_bg_minimalist_night_rain_small;
                        imgbg = R.drawable.weather_bg_ios_rain;
                        break;
                    case "11d":
                    case "11n":
                        imgsrc = R.drawable.weather_11;
                        imgios = R.drawable.ic_ios_rainstorm;
                        imgper = R.drawable.ic_per_rainstorm;
                        imgmin = R.drawable.ic_minimalist_rainstorm;
                        imgminbg = R.drawable.img_bg_minimalist_rainstorm_small;
                        imgbg = R.drawable.weather_bg_ios_rainstorm;
                        break;
                    case "13d":
                    case "13n":
                        imgsrc = R.drawable.weather_13;
                        imgios = R.drawable.ic_ios_snow;
                        imgper = R.drawable.ic_per_snow;
                        imgmin = R.drawable.ic_minimalist_snow;
                        imgminbg = R.drawable.img_bg_minimalist_night_rainstorm_small;
                        imgbg = R.drawable.weather_bg_ios_snow;
                        break;
                    case "50d":
                        imgsrc = R.drawable.weather_50;
                        imgios = R.drawable.ic_ios_fog;
                        imgper = R.drawable.ic_per_fog;
                        imgmin = R.drawable.ic_minimalist_fog;
                        imgminbg = R.drawable.img_bg_minimalist_fog_small;
                        imgbg = R.drawable.weather_bg_ios_fog;
                        break;
                    case "50n":
                        imgsrc = R.drawable.weather_50;
                        imgios = R.drawable.ic_ios_fog;
                        imgper = R.drawable.ic_per_fog;
                        imgmin = R.drawable.ic_minimalist_fog;
                        imgminbg = R.drawable.img_bg_minimalist_night_fog_small;
                        imgbg = R.drawable.weather_bg_ios_fog;
                        break;
                    default:
                        imgsrc = R.drawable.weather_none_available;
                        imgios = R.drawable.ic_ios_sunny;
                        imgper = R.drawable.ic_per_sunny;
                        imgmin = R.drawable.ic_minimalist_sunny;
                        imgminbg = R.drawable.img_bg_minimalist_sunny_small;
                        imgbg = R.drawable.weather_bg_ios_sunny;
                        break;
                }

                // Toast.makeText(mContext, "Worker fetching success " + city, Toast.LENGTH_SHORT).show();

                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mContext);


                //********* first widget **************
                RemoteViews views1 = new RemoteViews(mContext.getPackageName(), R.layout.widget_weather1);
                ComponentName widget1 = new ComponentName(mContext, Weather1Widget.class);
                views1.setTextViewText(R.id.temp, tempString);
                views1.setImageViewResource(R.id.weatherIcon, imgsrc);
                appWidgetManager.updateAppWidget(widget1, views1);

                //********* second widget **************
                RemoteViews views2 = new RemoteViews(mContext.getPackageName(), R.layout.widget_weather2);
                ComponentName widget2 = new ComponentName(mContext, Weather2Widget.class);
                views2.setTextViewText(R.id.city, city);
                views2.setTextViewText(R.id.weather, weather);
                views2.setTextViewText(R.id.temp, tempString);
                views2.setImageViewResource(R.id.weatherIcon, imgsrc);
                appWidgetManager.updateAppWidget(widget2, views2);

                //********* third widget **************
                RemoteViews views3 = new RemoteViews(mContext.getPackageName(), R.layout.widget_weather3);
                ComponentName widget3 = new ComponentName(mContext, Weather3Widget.class);
                views3.setTextViewText(R.id.city, city);
                views3.setTextViewText(R.id.weather, weather);
                views3.setTextViewText(R.id.temp, tempString);
                views3.setTextViewText(R.id.minmax, minmax);
                views3.setImageViewResource(R.id.weatherIcon, imgios);
                views3.setImageViewResource(R.id.weatherBg, imgbg);
                appWidgetManager.updateAppWidget(widget3, views3);

                //********* fourth widget **************
                RemoteViews views4 = new RemoteViews(mContext.getPackageName(), R.layout.widget_weather4);
                ComponentName widget4 = new ComponentName(mContext, Weather4Widget.class);
                views4.setTextViewText(R.id.city, city);
                views4.setTextViewText(R.id.weather, weather);
                views4.setTextViewText(R.id.temp, tempString);
                views4.setTextViewText(R.id.minmax, minmax);
                views4.setImageViewResource(R.id.weatherIcon, imgmin);
                views4.setImageViewResource(R.id.weatherBg, imgminbg);
                appWidgetManager.updateAppWidget(widget4, views4);


                //********* ios widget **************
                RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_weatherios);
                ComponentName thisWidget = new ComponentName(mContext, IosWeatherWidget.class);
                views.setTextViewText(R.id.weather, weather);
                views.setTextViewText(R.id.temp, tempString);
                views.setTextViewText(R.id.minmax, minmax);
                views.setImageViewResource(R.id.weatherIcon, imgper);
                views.setImageViewResource(R.id.weatherBg, imgbg);
                appWidgetManager.updateAppWidget(thisWidget, views);



                mEditor = mUserDetails.edit();
                mEditor.putString("city", city);
                mEditor.putString("weather", weather);
                mEditor.putString("temp", tempString);
                mEditor.putString("minmax", minmax);
                mEditor.putInt("weatherIcon", imgsrc);
                mEditor.putInt("weatherIconPer", imgper);
                mEditor.putInt("weatherIconMin", imgmin);
                mEditor.putInt("weatherBgMin", imgminbg);
                mEditor.putInt("weatherIconIos", imgios);
                mEditor.putInt("weatherBgIos", imgbg);
                mEditor.apply();
            }

            @Override
            public void onFailure(Throwable throwable) {

                Toast.makeText(mContext, "Worker fetching failed ", Toast.LENGTH_SHORT).show();

            }

        });


        Data outputData = new Data.Builder()
                .putString("city", city)
                .putString("weather", weather)
                .putString("temp", tempString)
                .putInt("img", imgsrc)
                .build();

        return Result.success(outputData);
    }

}
