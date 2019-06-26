package com.eleganzit.e_farmingcustomer.fragments;


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
import android.widget.TextView;
import android.widget.Toast;

import com.eleganzit.e_farmingcustomer.NavHomeActivity;
import com.eleganzit.e_farmingcustomer.R;
import com.eleganzit.e_farmingcustomer.adapters.AvailablePlotsAdapter;
import com.eleganzit.e_farmingcustomer.api.RetrofitAPI;
import com.eleganzit.e_farmingcustomer.api.RetrofitInterface;
import com.eleganzit.e_farmingcustomer.model.AvailablePlotsData;
import com.eleganzit.e_farmingcustomer.model.AvailablePlotsResponse;
import com.eleganzit.e_farmingcustomer.utils.UserSessionManager;

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

        NavHomeActivity.home_title.setText("Available Plots");

        rc_plots=v.findViewById(R.id.rc_plots);
        txt_no_plots=v.findViewById(R.id.txt_no_plots);



        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getAllPoDetail();
    }

    private void getAllPoDetail() {
        progressDialog.show();

        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<AvailablePlotsResponse> call=myInterface.availablePlots("");
        call.enqueue(new Callback<AvailablePlotsResponse>() {
            @Override
            public void onResponse(Call<AvailablePlotsResponse> call, Response<AvailablePlotsResponse> response) {

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



                            rc_plots.setAdapter(new AvailablePlotsAdapter(arrayList,getActivity()));

                            progressDialog.dismiss();

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

                Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();

            }
        });

    }

}
