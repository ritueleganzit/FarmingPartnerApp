package com.eleganzit.e_farmingcustomer.adapters;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.eleganzit.e_farmingcustomer.R;
import com.eleganzit.e_farmingcustomer.ViewFarmActivity;
import com.eleganzit.e_farmingcustomer.model.PaymentsData;

import java.util.ArrayList;

public class PaymentPendingAdapter extends RecyclerView.Adapter<PaymentPendingAdapter.MyViewHolder> {

    ArrayList<PaymentsData> arrayList;
    Context context;

    public PaymentPendingAdapter(ArrayList<PaymentsData> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.payments_layout,viewGroup,false);

        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout cardviewdashboard;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            cardviewdashboard=itemView.findViewById(R.id.cardviewdashboard);

        }
    }

}
