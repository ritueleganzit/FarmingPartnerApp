package com.eleganzit.farmingpartner.fragments;


import android.app.ProgressDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.eleganzit.farmingpartner.NavHomeActivity;
import com.eleganzit.farmingpartner.R;
import com.eleganzit.farmingpartner.adapters.AvailablePlotsAdapter;
import com.eleganzit.farmingpartner.api.RetrofitAPI;
import com.eleganzit.farmingpartner.api.RetrofitInterface;
import com.eleganzit.farmingpartner.model.AvailablePlotsData;
import com.eleganzit.farmingpartner.model.AvailablePlotsResponse;
import com.eleganzit.farmingpartner.utils.UserSessionManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AvailablePlotsFragment extends Fragment {

    RecyclerView rc_plots;
    UserSessionManager userSessionManager;
    ProgressDialog progressDialog;
    TextView txt_no_plots;
    ImageView reload;

    public AvailablePlotsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v=inflater.inflate(R.layout.fragment_available_plots, container, false);
        userSessionManager=new UserSessionManager(getActivity());

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        NavHomeActivity.home_title.setText("Rent a Farm");

        rc_plots=v.findViewById(R.id.rc_plots);
        txt_no_plots=v.findViewById(R.id.txt_no_plots);
        reload=v.findViewById(R.id.reload);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        availablePlots();

        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                availablePlots();
            }
        });

    }

    private void availablePlots() {
        progressDialog.show();
        reload.setVisibility(View.GONE);
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<AvailablePlotsResponse> call=myInterface.availablePlots("");
        call.enqueue(new Callback<AvailablePlotsResponse>() {
            @Override
            public void onResponse(Call<AvailablePlotsResponse> call, Response<AvailablePlotsResponse> response) {
                progressDialog.dismiss();

                if (response.isSuccessful())
                {

                    ArrayList<AvailablePlotsData> arrayList=new ArrayList<>();
                    if (response.body().getStatus().toString().equalsIgnoreCase("1"))
                    {
                        txt_no_plots.setVisibility(View.GONE);
                        if (response.body().getData()!=null)
                        {

                            for (int i=0;i<response.body().getData().size();i++)
                            {
                                arrayList.add(response.body().getData().get(i));

                            }

                            Log.d("ccccc",""+arrayList.size());



                            rc_plots.setAdapter(new AvailablePlotsAdapter(arrayList,getActivity(),progressDialog));


                        }
                        else
                        {
                            txt_no_plots.setVisibility(View.VISIBLE);

                        }

                    }
                    else
                    {
                        txt_no_plots.setVisibility(View.VISIBLE);
                    }
                }
                else
                {

                    Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<AvailablePlotsResponse> call, Throwable t) {
                progressDialog.dismiss();
                reload.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();

            }
        });

    }

}
