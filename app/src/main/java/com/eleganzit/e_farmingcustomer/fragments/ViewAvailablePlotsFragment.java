package com.eleganzit.e_farmingcustomer.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.eleganzit.e_farmingcustomer.LoginActivity;
import com.eleganzit.e_farmingcustomer.MainActivity;
import com.eleganzit.e_farmingcustomer.NavHomeActivity;
import com.eleganzit.e_farmingcustomer.R;
import com.eleganzit.e_farmingcustomer.api.RetrofitAPI;
import com.eleganzit.e_farmingcustomer.api.RetrofitInterface;
import com.eleganzit.e_farmingcustomer.model.FarmDetailsResponse;
import com.eleganzit.e_farmingcustomer.model.LoginRespose;
import com.eleganzit.e_farmingcustomer.utils.UserSessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewAvailablePlotsFragment extends Fragment {

    Button btn_continue;
    ImageView img_farm;
    TextView txt_owner_name,txt_plot_number,txt_plot_price,txt_contact,txt_email,txt_farm_name,txt_allowed_capacity,txt_address,txt_description;
    LinearLayout lin_view_map;
    UserSessionManager userSessionManager;
    ProgressDialog progressDialog;
    String farm_id;
    public ViewAvailablePlotsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_view_available_plots, container, false);
        farm_id=getArguments().getString("farm_id");
        userSessionManager=new UserSessionManager(getActivity());

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        NavHomeActivity.home_title.setText("View Available Plots");

        btn_continue=v.findViewById(R.id.btn_continue);
        img_farm=v.findViewById(R.id.img_farm);
        txt_owner_name=v.findViewById(R.id.txt_owner_name);
        txt_plot_number=v.findViewById(R.id.txt_plot_number);
        txt_plot_price=v.findViewById(R.id.txt_plot_price);
        txt_contact=v.findViewById(R.id.txt_contact);
        txt_email=v.findViewById(R.id.txt_email);
        txt_farm_name=v.findViewById(R.id.txt_farm_name);
        txt_allowed_capacity=v.findViewById(R.id.txt_allowed_capacity);
        txt_address=v.findViewById(R.id.txt_address);
        txt_description=v.findViewById(R.id.txt_description);
        lin_view_map=v.findViewById(R.id.lin_view_map);

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        plotDetails();
    }

    private void plotDetails() {
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<FarmDetailsResponse> call = myInterface.plotDetails(farm_id);
        call.enqueue(new Callback<FarmDetailsResponse>() {
            @Override
            public void onResponse(Call<FarmDetailsResponse> call, Response<FarmDetailsResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {



                            Glide
                                    .with(getActivity())
                                    .asBitmap()
                                    .apply(new RequestOptions().transform(new RoundedCorners(25)).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL))
                                    .load(response.body().getData().get(0).getFarmPhoto())
                                    .thumbnail(.1f)
                                    .into(img_farm);

                            if(response.body().getData().get(0).getFarmingPartnerName().equalsIgnoreCase(""))
                            {
                                txt_owner_name.setText("Not Provided");
                            }
                            else
                            {
                                txt_owner_name.setText(response.body().getData().get(0).getFarmingPartnerName());
                            }

                            //txt_plot_number.setText("");
                            //txt_plot_price.setText("");
                            if(response.body().getData().get(0).getPhone().equalsIgnoreCase(""))
                            {
                                txt_contact.setText("Not Provided");
                            }
                            else {
                                txt_contact.setText(response.body().getData().get(0).getPhone());
                            }
                            if(response.body().getData().get(0).getEmail().equalsIgnoreCase(""))
                            {
                                txt_email.setText("Not Provided");
                            }
                            else {
                                txt_email.setText(response.body().getData().get(0).getEmail());
                            }
                            if(response.body().getData().get(0).getFarmName().equalsIgnoreCase(""))
                            {
                                txt_farm_name.setText("Not Provided");
                            }
                            else {
                                txt_farm_name.setText(response.body().getData().get(0).getFarmName());
                            }
                            //txt_allowed_capacity.setText("");
                            if(response.body().getData().get(0).getFarmAddress().equalsIgnoreCase(""))
                            {
                                txt_address.setText("Not Provided");
                            }
                            else {
                                txt_address.setText(response.body().getData().get(0).getFarmAddress());
                            }
                            if(response.body().getData().get(0).getFarmDescription().equalsIgnoreCase(""))
                            {
                                txt_description.setText("Not Provided");
                            }
                            else {
                                txt_description.setText(response.body().getData().get(0).getFarmDescription());
                            }

                            //Toast.makeText(getActivity(), "--" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    } else {

                        Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }


                }
                else
                {
                    Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<FarmDetailsResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}
