package com.widgets.widgey;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.flurry.android.FlurryAgent;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.microsoft.appcenter.analytics.Analytics;
import com.widgets.widgey.helpers.GPSTracker;
import com.widgets.widgey.helpers.Utils;
import com.widgets.widgey.widgets.android12.Analog1Widget;
import com.widgets.widgey.widgets.android12.Analog2Widget;
import com.widgets.widgey.widgets.android12.Analog3Widget;
import com.widgets.widgey.widgets.android12.Analog4Widget;
import com.widgets.widgey.widgets.android12.Analog5Widget;
import com.widgets.widgey.widgets.android12.Analog6Widget;
import com.widgets.widgey.widgets.android12.Analog7Widget;
import com.widgets.widgey.widgets.android12.Analog8Widget;
import com.widgets.widgey.widgets.calander.Calander1Widget;
import com.widgets.widgey.widgets.calander.Calander2Widget;
import com.widgets.widgey.widgets.ios15.IosClockWidget;
import com.widgets.widgey.widgets.ios15.IosWeatherWidget;
import com.widgets.widgey.widgets.weather.Weather1Widget;
import com.widgets.widgey.widgets.weather.Weather2Widget;
import com.widgets.widgey.widgets.weather.Weather3Widget;

public class WidgetsFragment extends Fragment {

    Context context;
    SharedPreferences mUserDetails, sharedPrefs;
    SharedPreferences.Editor mEditor;
    Boolean isPremium, isSignedIn;
    private boolean hasLoadedOnce= false;
    private View v1, v2, v3, v4, v5, v6, v7, v8, v9, w1, w2, w3, c1, c2, c3, t1, t2, t3;
    private CardView headercard;
    String sName, sEmail, sDp, savedName;
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;


    public WidgetsFragment() {
        // Required empty public constructor
    }

    public static WidgetsFragment newInstance() {
        WidgetsFragment fragment = new WidgetsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v =  inflater.inflate(R.layout.fragment_widgets, container, false);

        context = getActivity();

        mUserDetails = getActivity().getSharedPreferences("Details", 0);
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        isPremium = mUserDetails.getBoolean("premium", false);
        isSignedIn = mUserDetails.getBoolean("signedin", false);
        savedName = mUserDetails.getString("sname", null);

        GoogleSignInOptions gso = new
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        //mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        headercard = v.findViewById(R.id.widget_header);
        v1 = v.findViewById(R.id.analog_clock1);
        v2 = v.findViewById(R.id.analog_clock2);
        v3 = v.findViewById(R.id.analog_clock3);
        v4 = v.findViewById(R.id.analog_clock4);
        v5 = v.findViewById(R.id.analog_clock5);
        v6 = v.findViewById(R.id.analog_clock6);
        v7 = v.findViewById(R.id.analog_clock7);
        v8 = v.findViewById(R.id.analog_clock8);
        v9 = v.findViewById(R.id.analog_clock9);
        w1 = v.findViewById(R.id.weather1);
        w2 = v.findViewById(R.id.weather2);
        w3 = v.findViewById(R.id.weather3);
        c1 = v.findViewById(R.id.calander1);
        c2 = v.findViewById(R.id.calander2);
        c3 = v.findViewById(R.id.calander3);
        t1 = v.findViewById(R.id.textclock1);
        t2 = v.findViewById(R.id.textclock2);
        t3 = v.findViewById(R.id.textclock3);

        FlurryAgent.logEvent("WidgetsFragment");
        Analytics.trackEvent("WidgetsFragment");

         headercard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                init(Analog1Widget.class);
               // Toast.makeText(context, "Analog Clock clicked", Toast.LENGTH_LONG).show();
                FlurryAgent.logEvent("Analog1");
                Analytics.trackEvent("Analog1");

            }
        });

        v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                init(Analog2Widget.class);
                FlurryAgent.logEvent("Analog2");
                Analytics.trackEvent("Analog2");

            }
        });

        v3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                init(Analog3Widget.class);
                FlurryAgent.logEvent("Analog3");
                Analytics.trackEvent("Analog3");

            }
        });

        v4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                init(Analog4Widget.class);
                FlurryAgent.logEvent("Analog3");
                Analytics.trackEvent("Analog3");

            }
        });

        v5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                init(Analog5Widget.class);
                FlurryAgent.logEvent("Analog3");
                Analytics.trackEvent("Analog3");

            }
        });

        v6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                init(Analog6Widget.class);
                FlurryAgent.logEvent("Analog3");
                Analytics.trackEvent("Analog3");

            }
        });

        v7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                init(Analog7Widget.class);
                FlurryAgent.logEvent("Analog3");
                Analytics.trackEvent("Analog3");

            }
        });

        v8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                init(Analog8Widget.class);
                FlurryAgent.logEvent("Analog3");
                Analytics.trackEvent("Analog3");

            }
        });

        v9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                init(IosClockWidget.class);
                FlurryAgent.logEvent("Analog3");
                Analytics.trackEvent("Analog3");

            }
        });
        w1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkPermission()) {
                    GPSTracker gpsTracker = new GPSTracker(context);
                    mEditor = mUserDetails.edit();
                    Utils.putDouble(mEditor, "lat", gpsTracker.getLatitude()).commit();
                    Utils.putDouble(mEditor, "long", gpsTracker.getLongitude()).commit();

                    init(Weather3Widget.class);
                }
                else{
                    Toast.makeText(context, "Location is required for Weather", Toast.LENGTH_SHORT).show();
                    requestPermissions(1);
                }

                FlurryAgent.logEvent("Weather1");
                Analytics.trackEvent("Weather1");


            }
        });


        w2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkPermission()) {
                    GPSTracker gpsTracker = new GPSTracker(context);
                    mEditor = mUserDetails.edit();
                    Utils.putDouble(mEditor, "lat", gpsTracker.getLatitude()).commit();
                    Utils.putDouble(mEditor, "long", gpsTracker.getLongitude()).commit();

                    //Toast.makeText(context, "Lat + Long "+ gpsTracker.getLatitude()+ " "+gpsTracker.getLongitude(), Toast.LENGTH_SHORT).show();

                    init(Weather2Widget.class);
                }
                else{
                    Toast.makeText(context, "Location is required for Weather", Toast.LENGTH_SHORT).show();
                    requestPermissions(2);
                }

                FlurryAgent.logEvent("Weather2");
                Analytics.trackEvent("Weather2");

            }
        });

        w3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkPermission()) {
                    GPSTracker gpsTracker = new GPSTracker(context);
                    mEditor = mUserDetails.edit();
                    Utils.putDouble(mEditor, "lat", gpsTracker.getLatitude()).commit();
                    Utils.putDouble(mEditor, "long", gpsTracker.getLongitude()).commit();

                    init(IosWeatherWidget.class);
                }
                else{
                    Toast.makeText(context, "Location is required for Weather", Toast.LENGTH_SHORT).show();
                    requestPermissions(3);
                }

                FlurryAgent.logEvent("Weather3");
                Analytics.trackEvent("Weather3");

            }
        });

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                init(Calander1Widget.class);
                FlurryAgent.logEvent("calander1");
                Analytics.trackEvent("calander1");

            }
        });

        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                init(Calander2Widget.class);
                FlurryAgent.logEvent("calander1");
                Analytics.trackEvent("calander1");

            }
        });

        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "Coming soon", Toast.LENGTH_SHORT).show();

            }
        });

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "Coming soon", Toast.LENGTH_SHORT).show();

            }
        });

        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "Coming soon", Toast.LENGTH_SHORT).show();

            }
        });

        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "Coming soon", Toast.LENGTH_SHORT).show();

            }
        });

        return v;
    }


    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);


        if (this.isVisible()) {
            if (isVisibleToUser && !hasLoadedOnce) {

                hasLoadedOnce = true;
            }
        }

    }

    public void requestPermissions(int i){

        requestPermissions(
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,},
                i);
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);

        return result == PackageManager.PERMISSION_GRANTED ;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {


        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            GPSTracker gpsTracker = new GPSTracker(context);

            mEditor = mUserDetails.edit();
            putDouble(mEditor, "lat", gpsTracker.getLatitude()).apply();
            putDouble(mEditor, "long", gpsTracker.getLongitude()).apply();

            if(requestCode ==1) {
                init(Weather1Widget.class);
            }
            else if (requestCode ==2){
                init(Weather2Widget.class);
            }
            else if (requestCode ==3){
                init(IosWeatherWidget.class);
            }

            Toast.makeText(context, "Location granted", Toast.LENGTH_SHORT).show();


        } else {

            Toast.makeText(context, "Location is required for Weather", Toast.LENGTH_SHORT).show();
        }


    }


    SharedPreferences.Editor putDouble(final SharedPreferences.Editor edit, final String key, final double value) {
        return edit.putLong(key, Double.doubleToRawLongBits(value));
    }

    double getDouble(final SharedPreferences prefs, final String key, final double defaultValue) {
        return Double.longBitsToDouble(prefs.getLong(key, Double.doubleToLongBits(defaultValue)));
    }


    public void init(Class clas) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            AppWidgetManager mAppWidgetManager = context.getSystemService(AppWidgetManager.class);

            ComponentName myProvider = new ComponentName(context, clas);

            Bundle b = new Bundle();
            b.putString("ggg", "ggg");
            if (mAppWidgetManager.isRequestPinAppWidgetSupported()) {
                Intent pinnedWidgetCallbackIntent = new Intent(context, clas);
                PendingIntent successCallback = PendingIntent.getBroadcast(context, 0,
                        pinnedWidgetCallbackIntent, PendingIntent.FLAG_IMMUTABLE);

                mAppWidgetManager.requestPinAppWidget(myProvider, b, successCallback);
            }
        }
    }


    //**************************Google SignIn methods ******************

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount guser = completedTask.getResult(ApiException.class);

            sEmail = guser.getEmail();
            sName = guser.getDisplayName();
            if(guser.getPhotoUrl()!=null)
                sDp = guser.getPhotoUrl().toString();
            else
                sDp = Utils.Dp;


        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(context, "Google Error : " + e.getMessage(), Toast.LENGTH_LONG).show();

        }
    }
}