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
import android.widget.TextView;

import com.eleganzit.e_farmingcustomer.R;
import com.eleganzit.e_farmingcustomer.ViewFarmActivity;
import com.eleganzit.e_farmingcustomer.model.PaymentsData;

import java.util.ArrayList;

public class PaymentSuccessfulAdapter extends RecyclerView.Adapter<PaymentSuccessfulAdapter.MyViewHolder> {

    ArrayList<PaymentsData> arrayList;
    Context context;

    public PaymentSuccessfulAdapter(ArrayList<PaymentsData> arrayList, Context context) {
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
        TextView txt_price,txt_received,txt_plot_name;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            cardviewdashboard=itemView.findViewById(R.id.cardviewdashboard);
            txt_price=itemView.findViewById(R.id.txt_price);
            txt_received=itemView.findViewById(R.id.txt_received);
            txt_plot_name=itemView.findViewById(R.id.txt_plot_name);

        }
    }

}
