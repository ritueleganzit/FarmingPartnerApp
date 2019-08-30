package com.eleganzit.farmingpartner.subFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eleganzit.farmingpartner.R;
import com.eleganzit.farmingpartner.adapters.PaymentPendingAdapter;
import com.eleganzit.farmingpartner.model.PaymentsData;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentPendingFragment extends Fragment {


    RecyclerView rc_pending;

    public PaymentPendingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_payment_pending, container, false);

        rc_pending=v.findViewById(R.id.rc_pending);

        PaymentsData paymentsData =new PaymentsData("","");

        ArrayList<PaymentsData> arrayList=new ArrayList<>();

        arrayList.add(paymentsData);
        arrayList.add(paymentsData);
        arrayList.add(paymentsData);

        rc_pending.setAdapter(new PaymentPendingAdapter(arrayList,getActivity()));

        return v;
    }

}
