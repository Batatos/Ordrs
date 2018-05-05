package com.example.jorjborj.ordrs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by jorjborj on 10/11/2017.
 */

public class HomePagerAdapter extends FragmentStatePagerAdapter {

    int numOfTabs;

    public HomePagerAdapter(FragmentManager fm, int numberOfTabs){
        super(fm);
        this.numOfTabs=numberOfTabs;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                CashFragment cashFragment = new CashFragment();
                return cashFragment;
            case 1:
                CardFragment cardFragment = new CardFragment();
                return cardFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
