package com.eleganzit.e_farmingcustomer.adapters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.eleganzit.e_farmingcustomer.EditProfileActivity;
import com.eleganzit.e_farmingcustomer.R;
import com.eleganzit.e_farmingcustomer.api.RetrofitAPI;
import com.eleganzit.e_farmingcustomer.api.RetrofitInterface;
import com.eleganzit.e_farmingcustomer.fragments.AvailablePlotsFragment;
import com.eleganzit.e_farmingcustomer.fragments.ViewAvailablePlotsFragment;
import com.eleganzit.e_farmingcustomer.model.AvailablePlotsData;
import com.eleganzit.e_farmingcustomer.model.PlotListData;
import com.eleganzit.e_farmingcustomer.model.PlotListResponse;
import com.eleganzit.e_farmingcustomer.model.SubLocationResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AvailablePlotsAdapter extends RecyclerView.Adapter<AvailablePlotsAdapter.MyViewHolder> {

    ArrayList<AvailablePlotsData> arrayList;
    Context context;
    ProgressDialog progressDialog;

    public AvailablePlotsAdapter(ArrayList<AvailablePlotsData> arrayList, Context context, ProgressDialog progressDialog) {
        this.arrayList = arrayList;
        this.context = context;
        this.progressDialog = progressDialog;
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
            myViewHolder.txt_total_capacity.setText(availablePlotsData.getRemain_capacity()+"/"+availablePlotsData.getPlotCapacity());
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
                bundle.putString("farm_name", availablePlotsData.getFarmName());
                bundle.putString("farm_desc", availablePlotsData.getFarmDescription());
                viewAvailablePlotsFragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack("NavHomeActivity");
                fragmentTransaction.replace(R.id.container, viewAvailablePlotsFragment, "TAG");
                fragmentTransaction.commit();
                //farmPlotlist(availablePlotsData.getFarmId(),availablePlotsData.getFarmDescription());

            }
        });

    }


    void showPlotsDialog(final ArrayList<String> plotList, final String farm_id, final String farm_desc) {

        final ListAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, android.R.id.text1, plotList);

        final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.AlertDialogCustom));

        builder.setTitle("Select Plot");

        builder.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

                ViewAvailablePlotsFragment viewAvailablePlotsFragment= new ViewAvailablePlotsFragment();
                Bundle bundle=new Bundle();
                bundle.putString("farm_id", farm_id);
                bundle.putString("farm_name", plotList.get(i));
                bundle.putString("farm_desc", farm_desc);
                viewAvailablePlotsFragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack("NavHomeActivity");
                fragmentTransaction.replace(R.id.container, viewAvailablePlotsFragment, "TAG");
                fragmentTransaction.commit();

            }
        });

        builder.show();

    }

    private void farmPlotlist(final String farm_id,final String farm_desc) {

        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<PlotListResponse> call = myInterface.farmPlotlist(farm_id);
        call.enqueue(new Callback<PlotListResponse>() {
            @Override
            public void onResponse(Call<PlotListResponse> call, Response<PlotListResponse> response) {
                progressDialog.dismiss();
                ArrayList<String> plotList=new ArrayList<>();
                ArrayList<PlotListData> mplotList=new ArrayList<>();

                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {

                            for(int i=0;i<response.body().getData().size();i++)
                            {
                                plotList.add(response.body().getData().get(i).getPlotName());
                                mplotList.add(response.body().getData().get(i));
                            }
                            showPlotsDialog(plotList,farm_id,farm_desc);

                        }
                    }
                    else
                    {
                        Toast.makeText(context, "Please try again", Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    Toast.makeText(context, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<PlotListResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, "Server or Internet Error", Toast.LENGTH_SHORT).show();
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
