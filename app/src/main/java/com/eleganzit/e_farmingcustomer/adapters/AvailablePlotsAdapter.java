package com.eleganzit.e_farmingcustomer.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eleganzit.e_farmingcustomer.R;
import com.eleganzit.e_farmingcustomer.model.AvailablePlotsData;

import java.util.ArrayList;

public class AvailablePlotsAdapter extends RecyclerView.Adapter<AvailablePlotsAdapter.MyViewHolder> {

    ArrayList<AvailablePlotsData> arrayList;
    Context context;

    public AvailablePlotsAdapter(ArrayList<AvailablePlotsData> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.available_plots_layout,viewGroup,false);

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

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
        }
    }

}
