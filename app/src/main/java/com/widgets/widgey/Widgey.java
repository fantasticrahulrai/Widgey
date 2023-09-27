package com.widgets.widgey;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatDelegate;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.onesignal.OneSignal;
import com.revenuecat.purchases.LogLevel;
import com.revenuecat.purchases.Purchases;
import com.revenuecat.purchases.PurchasesConfiguration;


public class Widgey extends Application {

    private Context context;
    SharedPreferences sharedPrefs;
    String themePref;

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        themePref = sharedPrefs.getString("themePref", "Set by System");


        //RevenueCat Initialization
        Purchases.setLogLevel(LogLevel.DEBUG);
        Purchases.configure(new PurchasesConfiguration.Builder(this, "goog_HflgnTaOqAOekVEZeeHuxXCKgET").build());

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId("4fd8e206-3cbb-48e1-a8f0-fe20c621706c");
        OneSignal.unsubscribeWhenNotificationsAreDisabled(true);
        OneSignal.pauseInAppMessages(false);
        OneSignal.setLocationShared(false);


        //Firebase & crashlytics
        FirebaseAnalytics.getInstance(this);

        checkMode();

    }

    private void checkMode() {

        switch (themePref) {
            case "Disabled":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;

            case "Enabled":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;

            case "Set By System":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
        }


    }
}
