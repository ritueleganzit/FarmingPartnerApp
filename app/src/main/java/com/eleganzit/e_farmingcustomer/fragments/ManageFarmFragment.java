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
import com.eleganzit.e_farmingcustomer.adapters.ManageFarmAdapter;
import com.eleganzit.e_farmingcustomer.model.AvailablePlotsData;
import com.eleganzit.e_farmingcustomer.model.ManageFarmData;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManageFarmFragment extends Fragment {


    RecyclerView rc_farms;

    public ManageFarmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v=inflater.inflate(R.layout.fragment_manage_farm, container, false);

        NavHomeActivity.home_title.setText("Manage Farm");

        rc_farms=v.findViewById(R.id.rc_farms);

        ManageFarmData manageFarmData=new ManageFarmData("","");

        ArrayList<ManageFarmData> arrayList=new ArrayList<>();

        arrayList.add(manageFarmData);
        arrayList.add(manageFarmData);
        arrayList.add(manageFarmData);
        arrayList.add(manageFarmData);
        arrayList.add(manageFarmData);
        arrayList.add(manageFarmData);
        arrayList.add(manageFarmData);
        arrayList.add(manageFarmData);
        arrayList.add(manageFarmData);

        rc_farms.setAdapter(new ManageFarmAdapter(arrayList,getActivity()));

        return v;
    }

}
