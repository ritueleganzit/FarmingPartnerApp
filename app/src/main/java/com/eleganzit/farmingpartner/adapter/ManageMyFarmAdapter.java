package com.eleganzit.farmingpartner.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.eleganzit.farmingpartner.R;
import com.eleganzit.farmingpartner.activity.ManageMyFarm;
import com.eleganzit.farmingpartner.model.AdminVegetablelist;
import com.eleganzit.farmingpartner.model.FarmSlotsData;

import java.util.ArrayList;

public class ManageMyFarmAdapter extends RecyclerView.Adapter<ManageMyFarmAdapter.MyViewHolder> {

    ArrayList<AdminVegetablelist> arrayList;
    Context context;
    String farm_id;

    public ManageMyFarmAdapter(ArrayList<AdminVegetablelist> arrayList, Context context, String farm_id) {
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
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {

        final AdminVegetablelist adminVegetablelist=arrayList.get(i);
        String upperString = adminVegetablelist.getVegName().substring(0,1).toUpperCase() + adminVegetablelist.getVegName().substring(1);
        myViewHolder.txt_cultivated.setText(""+upperString);

        Log.d("sasda",""+adminVegetablelist.getVegName());
        Glide
                .with(context)
                .load(""+adminVegetablelist.getVegImage())
                .into(myViewHolder.vegetableimage);
        myViewHolder.cardviewdashboard.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        context.startActivity(new Intent(context, ManageMyFarm.class)
                .putExtra("vegetable_id",""+adminVegetablelist.getVegetableId())
                .putExtra("farm_id",""+farm_id)


        );

        ((FragmentActivity)context).overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

    }
});

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

LinearLayout cardviewdashboard;
ImageView vegetableimage;
TextView txt_cultivated;


        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            vegetableimage=itemView.findViewById(R.id.vegetableimage);
            cardviewdashboard=itemView.findViewById(R.id.cardviewdashboard);
            txt_cultivated=itemView.findViewById(R.id.txt_cultivated);

        }
    }

}

