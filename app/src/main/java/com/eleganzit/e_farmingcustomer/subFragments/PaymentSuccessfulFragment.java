package com.eleganzit.e_farmingcustomer.subFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eleganzit.e_farmingcustomer.R;
import com.eleganzit.e_farmingcustomer.adapters.PaymentSuccessfulAdapter;
import com.eleganzit.e_farmingcustomer.model.PaymentsData;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentSuccessfulFragment extends Fragment {

    RecyclerView rc_successful;

    public PaymentSuccessfulFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_payment_successful, container, false);

        rc_successful=v.findViewById(R.id.rc_successful);

        PaymentsData paymentsData =new PaymentsData("","");

        ArrayList<PaymentsData> arrayList=new ArrayList<>();

        arrayList.add(paymentsData);
        arrayList.add(paymentsData);
        arrayList.add(paymentsData);

        rc_successful.setAdapter(new PaymentSuccessfulAdapter(arrayList,getActivity()));

        return v;
    }

}
