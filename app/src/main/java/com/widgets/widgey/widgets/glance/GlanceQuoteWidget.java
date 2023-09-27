package com.widgets.widgey.widgets.glance;

import static com.widgets.widgey.helpers.Utils.loadJSONFromAsset;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.widgets.widgey.R;
import com.widgets.widgey.helpers.Quotes;
import com.widgets.widgey.helpers.QuotesSqlite;
import com.widgets.widgey.helpers.WeatherWorker;

/**
 * Implementation of App Widget functionality.
 */
public class GlanceQuoteWidget extends AppWidgetProvider {

    private static SharedPreferences mUserDetails;
    private static boolean quotesTableCreated;
    SharedPreferences.Editor mEditor;
    private final static String TAG = "QuotesWidget";


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_glancequote);
        mUserDetails = context.getSharedPreferences("Details", 0);
        quotesTableCreated = mUserDetails.getBoolean("quotecreated", false);

        if(quotesTableCreated){
            QuotesSqlite sqdb = new QuotesSqlite(context);
            Quotes q = sqdb.getRandomQuote();
            views.setTextViewText(R.id.quotes, q.getQuote());

            Log.d(TAG, "quotes table created"+ q.getQuote()+ q.getAuthor());

        }
        else {
            views.setTextViewText(R.id.quotes, "Nothing is impossible.");
            Log.d(TAG, "quotes table not created");
        }


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

        mUserDetails = context.getSharedPreferences("Details", 0);
        quotesTableCreated = mUserDetails.getBoolean("quotecreated", false);
        Log.d(TAG, "On enabled called");

        if(!quotesTableCreated){
            QuotesSqlite sqdb = new QuotesSqlite(context);
            final List<Quotes> mListSqlite = new ArrayList<>();

            try {
                JSONObject mainObj = new JSONObject(loadJSONFromAsset(context, "QuotesParse.json"));
                JSONArray jsonArray = mainObj.getJSONArray("results");
                JSONObject obj;

                for (int i = 0; i < jsonArray.length(); ++i) {

                    obj = jsonArray.getJSONObject(i);

                    Gson gson = new Gson();
                    Quotes q  = gson.fromJson(obj.toString(), Quotes.class);
                    mListSqlite.add(q);


                }

                sqdb.batchInsterstion(mListSqlite);

                mEditor = mUserDetails.edit();
                mEditor.putBoolean("quotecreated", true);
                mEditor.commit();

                Log.d(TAG, "quotes inserted");

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d(TAG, "quotes not inserted");
            }
        }
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}