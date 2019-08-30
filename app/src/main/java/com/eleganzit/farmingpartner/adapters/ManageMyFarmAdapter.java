package com.eleganzit.farmingpartner.adapters;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eleganzit.farmingpartner.R;
import com.eleganzit.farmingpartner.ViewFarmActivity;
import com.eleganzit.farmingpartner.model.FarmSlotsData;

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
                .putExtra("slotimage",farmSlotsData.getVeg_image())
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
            String upperString = farmSlotsData.getVegetableName().substring(0,1).toUpperCase() + farmSlotsData.getVegetableName().substring(1);

            myViewHolder.txt_cultivated.setText(upperString);
        }
        else
            myViewHolder.txt_cultivated.setText("Not Provided");



        if(farmSlotsData.getVeg_image()!=null)

            if(farmSlotsData.getVeg_image().equalsIgnoreCase(""))
            {
            }
            else
            {
                Glide.with(context).load(farmSlotsData.getVeg_image()).into(myViewHolder.vegetableimage);
            }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout cardviewdashboard;
        TextView txt_slot_number,txt_cultivated,txt_last_event;
        ImageView vegetableimage;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            cardviewdashboard=itemView.findViewById(R.id.cardviewdashboard);
            vegetableimage=itemView.findViewById(R.id.vegetableimage);
            txt_slot_number=itemView.findViewById(R.id.txt_slot_number);
            txt_cultivated=itemView.findViewById(R.id.txt_cultivated);
            txt_last_event=itemView.findViewById(R.id.txt_last_event);

        }
    }

}

