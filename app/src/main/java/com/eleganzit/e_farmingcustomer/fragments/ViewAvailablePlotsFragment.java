package com.eleganzit.e_farmingcustomer.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eleganzit.e_farmingcustomer.MainActivity;
import com.eleganzit.e_farmingcustomer.NavHomeActivity;
import com.eleganzit.e_farmingcustomer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewAvailablePlotsFragment extends Fragment {

    Button btn_continue;
    ImageView img_farm;
    TextView txt_owner_name,txt_plot_number,txt_plot_price,txt_contact,txt_email,txt_farm_name,txt_allowed_capacity,txt_address,txt_description;
    LinearLayout lin_view_map;

    public ViewAvailablePlotsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_view_available_plots, container, false);

        NavHomeActivity.home_title.setText("View Available Plots");

        btn_continue=v.findViewById(R.id.btn_continue);
        img_farm=v.findViewById(R.id.img_farm);
        txt_owner_name=v.findViewById(R.id.txt_owner_name);
        txt_plot_number=v.findViewById(R.id.txt_plot_number);
        txt_plot_price=v.findViewById(R.id.txt_plot_price);
        txt_contact=v.findViewById(R.id.txt_contact);
        txt_email=v.findViewById(R.id.txt_email);
        txt_farm_name=v.findViewById(R.id.txt_farm_name);
        txt_allowed_capacity=v.findViewById(R.id.txt_allowed_capacity);
        txt_address=v.findViewById(R.id.txt_address);
        txt_description=v.findViewById(R.id.txt_description);
        lin_view_map=v.findViewById(R.id.lin_view_map);

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        return v;
    }

}
