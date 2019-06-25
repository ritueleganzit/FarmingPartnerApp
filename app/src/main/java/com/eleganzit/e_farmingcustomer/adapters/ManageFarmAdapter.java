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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eleganzit.e_farmingcustomer.ManageMyFarmActivity;
import com.eleganzit.e_farmingcustomer.R;
import com.eleganzit.e_farmingcustomer.fragments.AvailablePlotsFragment;
import com.eleganzit.e_farmingcustomer.fragments.ViewAvailablePlotsFragment;
import com.eleganzit.e_farmingcustomer.model.AvailablePlotsData;
import com.eleganzit.e_farmingcustomer.model.ManageFarmData;

import java.util.ArrayList;

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

}
