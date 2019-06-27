package com.eleganzit.e_farmingcustomer.adapters;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.eleganzit.e_farmingcustomer.ManageMyFarmActivity;
import com.eleganzit.e_farmingcustomer.R;
import com.eleganzit.e_farmingcustomer.fragments.AvailablePlotsFragment;
import com.eleganzit.e_farmingcustomer.fragments.ViewAvailablePlotsFragment;
import com.eleganzit.e_farmingcustomer.model.ManageFarmData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ManageFarmAdapter extends RecyclerView.Adapter<ManageFarmAdapter.MyViewHolder> {

    ArrayList<ManageFarmData> arrayList;
    Context context;

    public ManageFarmAdapter(ArrayList<ManageFarmData> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.manage_farm_layout,viewGroup,false);

        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        ManageFarmData manageFarmData=arrayList.get(i);
        
        Glide
                .with(context)
                .asBitmap()
                .apply(new RequestOptions().transform(new RoundedCorners(25)).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL))
                .load(manageFarmData.getFarmPhoto())
                .thumbnail(.1f)
                .into(myViewHolder.img_farm);

        if(manageFarmData.getFarmName().equalsIgnoreCase(""))
        {
            myViewHolder.txt_farm_name.setText("Not Provided");
        }
        else
        {
            myViewHolder.txt_farm_name.setText(manageFarmData.getFarmName());
        }
        //myViewHolder.txt_price.setText("");
        if(manageFarmData.getFarmAddress().equalsIgnoreCase(""))
        {
            myViewHolder.txt_address.setText("Not Provided");
        }
        else {
            myViewHolder.txt_address.setText(manageFarmData.getFarmAddress());
        }
        //myViewHolder.txt_plot_capacity.setText(manageFarmData.getFarmName());
        /*if(manageFarmData.getPlotCapacity().equalsIgnoreCase(""))
        {
            myViewHolder.txt_plot_capacity.setText("Not Provided");
        }
        else {
            myViewHolder.txt_plot_capacity.setText(manageFarmData.getPlotCapacity());
        }*/
        if(manageFarmData.getFarmingPartnerName().equalsIgnoreCase(""))
        {
            myViewHolder.txt_owner_name.setText("Not Provided");
        }
        else {
            myViewHolder.txt_owner_name.setText(manageFarmData.getFarmingPartnerName());
        }

        if(manageFarmData.getRegisterDate().equalsIgnoreCase(""))
        {
            myViewHolder.txt_purchased_on.setText("Not Provided");
        }
        else {
            myViewHolder.txt_purchased_on.setText(parseDateToddMMyyyy(manageFarmData.getRegisterDate()));
        }

        myViewHolder.cardviewdashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //((FragmentActivity)context).getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                context.startActivity(new Intent(context, ManageMyFarmActivity.class));
                ((FragmentActivity)context).overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout cardviewdashboard,lin_view_map;
        ImageView img_farm;
        TextView txt_farm_name,txt_address,txt_plot_capacity,txt_purchased_on,txt_owner_name;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            cardviewdashboard=itemView.findViewById(R.id.cardviewdashboard);
            lin_view_map=itemView.findViewById(R.id.lin_view_map);
            img_farm=itemView.findViewById(R.id.img_farm);
            txt_farm_name=itemView.findViewById(R.id.txt_farm_name);
            txt_address=itemView.findViewById(R.id.txt_address);
            txt_plot_capacity=itemView.findViewById(R.id.txt_plot_capacity);
            txt_purchased_on=itemView.findViewById(R.id.txt_purchased_on);
            txt_owner_name=itemView.findViewById(R.id.txt_owner_name);

        }
    }

    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "MMM dd,yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }


}
