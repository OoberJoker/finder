package com.example.swara.finder;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by swara on 2/25/2016.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {
    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int pos) {
        switch(pos) {
            case 0:
                return new PageSelectionFragment();
            case 1:
                return new PostedServicesListFragment();
                /*case 2:
                    return new FragmentOne();
                case 3:
                    return new FragmentTwo();*/
            default:
                return new PageSelectionFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}