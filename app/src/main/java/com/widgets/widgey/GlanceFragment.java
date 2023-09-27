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
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.microsoft.appcenter.analytics.Analytics;
import com.widgets.widgey.helpers.GPSTracker;
import com.widgets.widgey.helpers.Utils;
import com.widgets.widgey.widgets.android12.Analog1Widget;
import com.widgets.widgey.widgets.glance.GlanceQuoteWidget;
import com.widgets.widgey.widgets.glance.GlanceWidget;
import com.widgets.widgey.widgets.glance.HeyWidget;
import com.widgets.widgey.widgets.glance.ItsWidget;
import com.widgets.widgey.widgets.glance.TodaysWidget;
import com.widgets.widgey.widgets.weather.Weather1Widget;
import com.widgets.widgey.widgets.weather.Weather2Widget;

public class GlanceFragment extends Fragment {

    Context context;
    SharedPreferences mUserDetails, sharedPrefs;
    SharedPreferences.Editor mEditor;
    Boolean isPremium, isSignedIn;
    private boolean hasLoadedOnce= false;
    private View v1, v2, v3, v4, v5, v6, g4, g5, v7, v8, v9;

    private CardView headercard;
    private GoogleSignInClient mGoogleSignInClient;
    String sName, sEmail, sDp, savedName;
    private static final int RC_SIGN_IN = 9001;


    public GlanceFragment() {
        // Required empty public constructor
    }

    public static GlanceFragment newInstance() {
        GlanceFragment fragment = new GlanceFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_glance, container, false);

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

        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        headercard = v.findViewById(R.id.widget_header);
        v4 = v.findViewById(R.id.glance1);
        v5 = v.findViewById(R.id.glance2);
        v6 = v.findViewById(R.id.glance3);
        g4 = v.findViewById(R.id.glance4);
        g5 = v.findViewById(R.id.glance5);

        FlurryAgent.logEvent("WidgetsFragment");
        Analytics.trackEvent("WidgetsFragment");

        headercard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                init(Analog1Widget.class);
                // Toast.makeText(context, "Analog Clock clicked", Toast.LENGTH_LONG).show();
                FlurryAgent.logEvent("WidgetClicked");
                Analytics.trackEvent("WidgetClicked");

            }
        });

        v4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                init(GlanceWidget.class);
                FlurryAgent.logEvent("WidgetClicked");
                Analytics.trackEvent("WidgetClicked");

            }
        });


        v5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                init(ItsWidget.class);
                FlurryAgent.logEvent("WidgetClicked");
                Analytics.trackEvent("WidgetClicked");

            }
        });

        v6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                init(TodaysWidget.class);
                FlurryAgent.logEvent("WidgetClicked");
                Analytics.trackEvent("WidgetClicked");

            }
        });

        g4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isSignedIn = mUserDetails.getBoolean("signedin", false);

                if(isSignedIn)
                    init(HeyWidget.class);
                else {
                    signIn();
                    //init(HeyWidget.class);
                    Toast.makeText(context, "SignIn required for name.", Toast.LENGTH_LONG).show();
                }


                FlurryAgent.logEvent("WidgetClicked");
                Analytics.trackEvent("WidgetClicked");

            }
        });

        g5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                init(GlanceQuoteWidget.class);

                FlurryAgent.logEvent("WidgetClicked");
                Analytics.trackEvent("WidgetClicked");

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

    private void inputDialog() {

        MaterialAlertDialogBuilder b = new MaterialAlertDialogBuilder(context);
        b.setTitle("");
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
