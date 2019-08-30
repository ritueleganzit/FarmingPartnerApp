package com.eleganzit.farmingpartner.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eleganzit.farmingpartner.NavHomeActivity;
import com.eleganzit.farmingpartner.R;
import com.eleganzit.farmingpartner.adapters.viewPagerAdapter;
import com.eleganzit.farmingpartner.subFragments.PaymentPendingFragment;
import com.eleganzit.farmingpartner.subFragments.PaymentSuccessfulFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentHistoryFragment extends Fragment implements TabLayout.OnTabSelectedListener {

    TabLayout tabLayout;
    ViewPager viewPager;

    public PaymentHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment\
        View v=inflater.inflate(R.layout.fragment_payment_history, container, false);

        NavHomeActivity.home_title.setText("Payment History");

        tabLayout = v.findViewById(R.id.payment_tabs);
        viewPager = v.findViewById(R.id.payment_viewpager);

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("Successful"));
        tabLayout.addTab(tabLayout.newTab().setText("Pending"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing viewPager

        //Creating our pager adapter
        /*adapter = new viewPagerAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);
*/
        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);

        return v;

    }

    private void setupViewPager(ViewPager viewPager) {
        viewPagerAdapter adapter = new viewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new PaymentSuccessfulFragment(), "Successful");

        adapter.addFragment(new PaymentPendingFragment(), "Pending");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tabLayout.setOnTabSelectedListener(this);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
