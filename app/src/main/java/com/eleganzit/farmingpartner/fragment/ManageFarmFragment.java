package com.eleganzit.farmingpartner.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.eleganzit.farmingpartner.R;
import com.eleganzit.farmingpartner.activity.HomeActivity;
import com.eleganzit.farmingpartner.activity.SignInActivity;
import com.eleganzit.farmingpartner.adapter.ManageMyFarmAdapter;
import com.eleganzit.farmingpartner.api.RetrofitAPI;
import com.eleganzit.farmingpartner.api.RetrofitInterface;
import com.eleganzit.farmingpartner.model.AdminVegetablelist;
import com.eleganzit.farmingpartner.model.FarmSlotsData;
import com.eleganzit.farmingpartner.model.GetFarmDetailResponse;
import com.eleganzit.farmingpartner.utils.UserSessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageFarmFragment extends Fragment {
    RecyclerView rc_farms;
    String farm_id;
ArrayList<FarmSlotsData> arrayList=new ArrayList();
    public ManageFarmFragment() {
        // Required empty public constructor
    }
    ProgressDialog progressDialog;
    LinearLayout lin_map;
    UserSessionManager userSessionManager;
    ArrayList<AdminVegetablelist> list;
    String lat,lng,location;
    TextView owner_name,purched_on,farm_photo,farm_address,plot_capacity,farm_name;
ImageView img_farm;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

View v=inflater.inflate(R.layout.fragment_manage_farm, container, false);
        userSessionManager=new UserSessionManager(getActivity());
farm_id=userSessionManager.getUserDetails().get(UserSessionManager.KEY_FARM_ID);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);


        lin_map=v.findViewById(R.id.lin_view_map);
        img_farm=v.findViewById(R.id.img_farm);
        owner_name=v.findViewById(R.id.owner_name);
        farm_name=v.findViewById(R.id.farm_name);
        purched_on=v.findViewById(R.id.purched_on);
        farm_address=v.findViewById(R.id.farm_address);
        plot_capacity=v.findViewById(R.id.plot_capacity);


        lin_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
         //       Toast.makeText(getActivity(), ""+farm_address.getText().toString(), Toast.LENGTH_SHORT).show();
             /*  // String geoUri = "http://maps.google.com/maps?geo="+lat+","+lng+"?q="+farm_address.getText().toString()+"";
                String geoUri = "http://maps.google.com/maps?geo:37.7749,-122.4194?q=restaurants";
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                startActivity(i);*/

               /* String geoUri = "http://maps.google.com/maps?q=loc:" + lat + "," + lng + " (" + location + ")";
              //  Uri gmmIntentUri = Uri.parse("geo:"+lat+","+lng+"?q="+location+"");
                Uri gmmIntentUri = Uri.parse(geoUri);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
*/


                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.co.in/maps?q=" + location));
                startActivity(i);


            }
        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rc_farms=view.findViewById(R.id.rc_farms);
        HomeActivity.home_title.setText("Manage Farm");
        list=new ArrayList<>();
        getFarmData();

    }

    public void getFarmData()
    {

        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<GetFarmDetailResponse> call=myInterface.getFarmDetail(farm_id);
        call.enqueue(new Callback<GetFarmDetailResponse>() {
            @Override
            public void onResponse(Call<GetFarmDetailResponse> call, Response<GetFarmDetailResponse> response) {
                progressDialog.dismiss();
                if(response.isSuccessful()) {
                  //  Toast.makeText(getActivity(), ""+farm_id, Toast.LENGTH_SHORT).show();
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData()!=null) {

                            if (response.body().getData().get(0).getAdminVegetablelist() != null) {
                                list.addAll(response.body().getData().get(0).getAdminVegetablelist());
                                rc_farms.setAdapter(new ManageMyFarmAdapter(list, getActivity(),  response.body().getData().get(0).getFarmId()));

                            }


                            if (response.body().getData().get(0).getFarmName()!=null && !response.body().getData().get(0).getFarmName().isEmpty())
                            {

                                String upperString = response.body().getData().get(0).getFarmName().substring(0,1).toUpperCase() + response.body().getData().get(0).getFarmName().substring(1);

                                farm_name.setText("" + upperString);
                            }

                            if (response.body().getData().get(0).getPurchedOn()!=null && !response.body().getData().get(0).getPurchedOn().isEmpty())
                            {

                                String upperString = response.body().getData().get(0).getPurchedOn().substring(0,1).toUpperCase() + response.body().getData().get(0).getPurchedOn().substring(1);

                                purched_on.setText("" + upperString);

                            }

                            if (response.body().getData().get(0).getPlotCapacity()!=null && !response.body().getData().get(0).getPlotCapacity().isEmpty())
                            {

                                String upperString = response.body().getData().get(0).getPlotCapacity().substring(0,1).toUpperCase() + response.body().getData().get(0).getPlotCapacity().substring(1);

                                plot_capacity.setText("" + upperString + " SQFT");

                            }
   if (response.body().getData().get(0).getOwnerName()!=null && !response.body().getData().get(0).getOwnerName().isEmpty())
                            {

                                String upperString = response.body().getData().get(0).getOwnerName().substring(0,1).toUpperCase() + response.body().getData().get(0).getOwnerName().substring(1);
                                owner_name.setText("" + upperString);


                            }


                            if (response.body().getData().get(0).getFarmAddress()!=null && !response.body().getData().get(0).getFarmAddress().isEmpty())
                            {

                                String upperString = response.body().getData().get(0).getFarmAddress().substring(0,1).toUpperCase() + response.body().getData().get(0).getFarmAddress().substring(1);
                                farm_address.setText("" + upperString);


                            }


                            lat = response.body().getData().get(0).getFarmLat();
                            lng = response.body().getData().get(0).getFarmLong();
                            location = response.body().getData().get(0).getFarmLocation();
                            Glide
                                    .with(getActivity())
                                    .load(response.body().getData().get(0).getFarmPhoto())
                                    .into(img_farm);
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<GetFarmDetailResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
