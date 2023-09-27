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

            case 0: return WidgetsFragment.newInstance();
            case 1: return GlanceFragment.newInstance();


            default: return WidgetsFragment.newInstance();
        }
    }

    //@Override
    public int getCount() {
        return 2;
    }

    private String tabTitles[] = new String[]{  "Widgets", "Glance"};


    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

}