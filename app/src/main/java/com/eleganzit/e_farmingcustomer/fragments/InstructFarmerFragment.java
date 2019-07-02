package com.eleganzit.e_farmingcustomer.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eleganzit.e_farmingcustomer.LoginActivity;
import com.eleganzit.e_farmingcustomer.NavHomeActivity;
import com.eleganzit.e_farmingcustomer.R;
import com.eleganzit.e_farmingcustomer.api.RetrofitAPI;
import com.eleganzit.e_farmingcustomer.api.RetrofitInterface;
import com.eleganzit.e_farmingcustomer.model.InstructFarmerResponse;
import com.eleganzit.e_farmingcustomer.model.LoginRespose;
import com.eleganzit.e_farmingcustomer.utils.UserSessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class InstructFarmerFragment extends Fragment {

    EditText ed_message;
    Button btn_send;
    ProgressDialog progressDialog;
    UserSessionManager userSessionManager;

    public InstructFarmerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_instruct_farmer, container, false);

        userSessionManager=new UserSessionManager(getActivity());

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        NavHomeActivity.home_title.setText("Instruct Farmer");

        ed_message=v.findViewById(R.id.ed_message);
        btn_send=v.findViewById(R.id.btn_send);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!ed_message.getText().toString().trim().isEmpty())
                {
                     instructFarmer();
                }
            }
        });

        return v;
    }

    private void instructFarmer() {
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<InstructFarmerResponse> call = myInterface.instructFarmer(userSessionManager.getUserDetails().get(UserSessionManager.KEY_USER_ID), userSessionManager.getUserDetails().get(UserSessionManager.KEY_FARM_ID), ed_message.getText().toString());
        call.enqueue(new Callback<InstructFarmerResponse>() {
            @Override
            public void onResponse(Call<InstructFarmerResponse> call, Response<InstructFarmerResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {

                        Toast.makeText(getActivity(), "Message sent successfully", Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().popBackStackImmediate();

                    } else {

                        Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }


                }
                else
                {
                    Toast.makeText(getActivity(), "Server or Internet Error" +response.message() , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InstructFarmerResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
