package com.eleganzit.e_farmingcustomer.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.eleganzit.e_farmingcustomer.R;
import com.eleganzit.e_farmingcustomer.fragments.AvailablePlotsFragment;
import com.eleganzit.e_farmingcustomer.fragments.ViewAvailablePlotsFragment;
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

        final AvailablePlotsData availablePlotsData=arrayList.get(i);

        Glide
                .with(context)
                .asBitmap()
                .apply(new RequestOptions().transform(new RoundedCorners(25)).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL))
                .load(availablePlotsData.getFarmPhoto())
                .thumbnail(.1f)
                .into(myViewHolder.img_farm);

        myViewHolder.lin_view_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.co.in/maps?q=" + availablePlotsData.getFarmLocation()));
                context.startActivity(i);
            }
        });

        if(availablePlotsData.getFarmName().equalsIgnoreCase(""))
        {
            myViewHolder.txt_farm_name.setText("Not Provided");
        }
        else
        {
            myViewHolder.txt_farm_name.setText(availablePlotsData.getFarmName());
        }
        //myViewHolder.txt_price.setText("");
        if(availablePlotsData.getFarmAddress().equalsIgnoreCase(""))
        {
            myViewHolder.txt_address.setText("Not Provided");
        }
        else {
            myViewHolder.txt_address.setText(availablePlotsData.getFarmAddress());
        }
        //myViewHolder.txt_plot_capacity.setText(availablePlotsData.getFarmName());
        if(availablePlotsData.getPlotCapacity().equalsIgnoreCase(""))
        {
            myViewHolder.txt_total_capacity.setText("Not Provided");
        }
        else {
            myViewHolder.txt_total_capacity.setText(availablePlotsData.getPlotCapacity()+"/"+availablePlotsData.getPlotCapacity());
        }
        if(availablePlotsData.getFarmingPartnerName().equalsIgnoreCase(""))
        {
            myViewHolder.txt_owner_name.setText("Not Provided");
        }
        else {
            myViewHolder.txt_owner_name.setText(availablePlotsData.getFarmingPartnerName());
        }
        myViewHolder.cardviewdashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //((FragmentActivity)context).getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                ViewAvailablePlotsFragment viewAvailablePlotsFragment= new ViewAvailablePlotsFragment();
                Bundle bundle=new Bundle();
                bundle.putString("farm_id", availablePlotsData.getFarmId());
                bundle.putString("farm_name", availablePlotsData.getFarmId());
                bundle.putString("farm_desc", availablePlotsData.getFarmId());
                viewAvailablePlotsFragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack("NavHomeActivity");
                fragmentTransaction.replace(R.id.container, viewAvailablePlotsFragment, "TAG");
                fragmentTransaction.commit();
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
        TextView txt_farm_name,txt_price,txt_address,txt_plot_capacity,txt_total_capacity,txt_owner_name;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            cardviewdashboard=itemView.findViewById(R.id.cardviewdashboard);
            lin_view_map=itemView.findViewById(R.id.lin_view_map);
            img_farm=itemView.findViewById(R.id.img_farm);
            txt_farm_name=itemView.findViewById(R.id.txt_farm_name);
            txt_price=itemView.findViewById(R.id.txt_price);
            txt_address=itemView.findViewById(R.id.txt_address);
            txt_plot_capacity=itemView.findViewById(R.id.txt_plot_capacity);
            txt_total_capacity=itemView.findViewById(R.id.txt_total_capacity);
            txt_owner_name=itemView.findViewById(R.id.txt_owner_name);

        }
    }

}
