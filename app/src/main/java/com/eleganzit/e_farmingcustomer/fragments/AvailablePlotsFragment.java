package com.eleganzit.e_farmingcustomer.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eleganzit.e_farmingcustomer.NavHomeActivity;
import com.eleganzit.e_farmingcustomer.R;
import com.eleganzit.e_farmingcustomer.adapters.AvailablePlotsAdapter;
import com.eleganzit.e_farmingcustomer.model.AvailablePlotsData;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AvailablePlotsFragment extends Fragment {

    RecyclerView rc_plots;

    public AvailablePlotsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v=inflater.inflate(R.layout.fragment_available_plots, container, false);

        NavHomeActivity.home_title.setText("Available Plots");

        rc_plots=v.findViewById(R.id.rc_plots);

        AvailablePlotsData availablePlotsData=new AvailablePlotsData("","");

        ArrayList<AvailablePlotsData> arrayList=new ArrayList<>();

        arrayList.add(availablePlotsData);
        arrayList.add(availablePlotsData);
        arrayList.add(availablePlotsData);
        arrayList.add(availablePlotsData);
        arrayList.add(availablePlotsData);
        arrayList.add(availablePlotsData);
        arrayList.add(availablePlotsData);
        arrayList.add(availablePlotsData);
        arrayList.add(availablePlotsData);

        rc_plots.setAdapter(new AvailablePlotsAdapter(arrayList,getActivity()));

        return v;
    }

}
