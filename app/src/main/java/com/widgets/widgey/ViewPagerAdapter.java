package com.widgets.widgey;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {

            case 0: return GlanceFragment.newInstance();
            case 1: return WidgetsFragment.newInstance();



            default: return WidgetsFragment.newInstance();
        }
    }

    //@Override
    public int getCount() {
        return 2;
    }

    private String tabTitles[] = new String[]{ "Glance", "Widgets"};


    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

}