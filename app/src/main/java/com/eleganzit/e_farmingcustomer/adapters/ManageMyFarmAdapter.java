package com.eleganzit.e_farmingcustomer.adapters;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eleganzit.e_farmingcustomer.ManageMyFarmActivity;
import com.eleganzit.e_farmingcustomer.R;
import com.eleganzit.e_farmingcustomer.ViewFarmActivity;
import com.eleganzit.e_farmingcustomer.fragments.AvailablePlotsFragment;
import com.eleganzit.e_farmingcustomer.fragments.ViewAvailablePlotsFragment;
import com.eleganzit.e_farmingcustomer.model.AvailablePlotsData;
import com.eleganzit.e_farmingcustomer.model.FarmSlotsData;
import com.eleganzit.e_farmingcustomer.model.FarmSlotsResponse;
import com.eleganzit.e_farmingcustomer.model.ManageFarmData;

import java.util.ArrayList;

public class ManageMyFarmAdapter extends RecyclerView.Adapter<ManageMyFarmAdapter.MyViewHolder> {

    ArrayList<FarmSlotsData> arrayList;
    Context context;
    String farm_id;

    public ManageMyFarmAdapter(ArrayList<FarmSlotsData> arrayList, Context context,String farm_id) {
        this.arrayList = arrayList;
        this.context = context;
        this.farm_id = farm_id;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.manage_my_farm_layout,viewGroup,false);

        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        final FarmSlotsData farmSlotsData=arrayList.get(i);

        myViewHolder.cardviewdashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //((FragmentActivity)context).getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                context.startActivity(new Intent(context, ViewFarmActivity.class)
                .putExtra("farm_id",farm_id)
                .putExtra("slot_number",farmSlotsData.getSlot())
                .putExtra("veg_name",farmSlotsData.getVegetableName())
                .putExtra("vegetable_id",farmSlotsData.getVegetable_id()));
                ((FragmentActivity)context).overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        if(farmSlotsData.getSlot()!=null)
        if(farmSlotsData.getSlot().equalsIgnoreCase(""))
        {
            myViewHolder.txt_slot_number.setText("Not Provided");
        }
        else
        {
            myViewHolder.txt_slot_number.setText(farmSlotsData.getSlot());
        }
        else
            myViewHolder.txt_slot_number.setText("Not Provided");

        if(farmSlotsData.getVegetableName()!=null)
        if(farmSlotsData.getVegetableName().equalsIgnoreCase(""))
        {
            myViewHolder.txt_cultivated.setText("Not Provided");
        }
        else {
            myViewHolder.txt_cultivated.setText(farmSlotsData.getVegetableName());
        }
        else
            myViewHolder.txt_cultivated.setText("Not Provided");
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout cardviewdashboard;
        TextView txt_slot_number,txt_cultivated,txt_last_event;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            cardviewdashboard=itemView.findViewById(R.id.cardviewdashboard);
            txt_slot_number=itemView.findViewById(R.id.txt_slot_number);
            txt_cultivated=itemView.findViewById(R.id.txt_cultivated);
            txt_last_event=itemView.findViewById(R.id.txt_last_event);

        }
    }

}

