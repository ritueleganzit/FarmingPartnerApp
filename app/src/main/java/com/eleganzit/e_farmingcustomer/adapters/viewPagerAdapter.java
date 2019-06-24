package com.eleganzit.e_farmingcustomer.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.eleganzit.e_farmingcustomer.subFragments.PaymentPendingFragment;
import com.eleganzit.e_farmingcustomer.subFragments.PaymentSuccessfulFragment;

public class viewPagerAdapter extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;
    private String[] tabTitles = new String[]{"Successful", "Pending"};
    //Constructor to the class
    public viewPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                PaymentSuccessfulFragment tab1 = new PaymentSuccessfulFragment();
                return tab1;
            case 1:
                PaymentPendingFragment tab2 = new PaymentPendingFragment();
                return tab2;

            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}