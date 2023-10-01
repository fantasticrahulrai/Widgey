package com.widgets.widgey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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


    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onDestroy() {

        super.onDestroy();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;



            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);


    }

    @Override
    public void onResume(){
        super.onResume();
        // put your code here...


    }
}