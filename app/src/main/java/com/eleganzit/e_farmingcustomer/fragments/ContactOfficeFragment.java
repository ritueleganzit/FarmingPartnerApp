package com.eleganzit.e_farmingcustomer.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eleganzit.e_farmingcustomer.NavHomeActivity;
import com.eleganzit.e_farmingcustomer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactOfficeFragment extends Fragment {


    public ContactOfficeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_contact_office, container, false);

        NavHomeActivity.home_title.setText("Contact Office");


        return v;
    }

}
