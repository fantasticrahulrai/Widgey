package com.widgets.widgey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.play.core.appupdate.AppUpdateManager;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private TabLayout tabLayout;
    ViewPagerAdapter adapterViewPager;
    ViewPager vpPager;
    Toolbar toolbar;
    SharedPreferences mUserDetails, sharedPrefs;
    SharedPreferences.Editor mEditor;
    boolean autoWallStatus, firstRun, showAd, showFlash, showSearch, isPremium, isSignedIn;

    boolean doubleBackToExitPressedOnce = false;

    private static final int REQ_CODE_VERSION_UPDATE = 530;
    private AppUpdateManager appUpdateManager;
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mUserDetails = getApplicationContext().getSharedPreferences("Details", 0); // 0 - for private mode
        autoWallStatus = mUserDetails.getBoolean("auto", false);
        firstRun = mUserDetails.getBoolean("firstrun2", true);
        showAd = mUserDetails.getBoolean("showad3", false);
        showFlash = mUserDetails.getBoolean("showflash", true);
        showSearch = mUserDetails.getBoolean("showsearch", true);
        isPremium = mUserDetails.getBoolean("premium", false);
        isSignedIn = mUserDetails.getBoolean("signedin", false);

        vpPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new ViewPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        vpPager.setCurrentItem(0);

        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(vpPager);

    }
}