package com.eleganzit.farmingpartner.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eleganzit.farmingpartner.R;
import com.eleganzit.farmingpartner.activity.HomeActivity;
import com.eleganzit.farmingpartner.api.RetrofitAPI;
import com.eleganzit.farmingpartner.api.RetrofitInterface;
import com.eleganzit.farmingpartner.model.ContactOfficeResponse;
import com.eleganzit.farmingpartner.utils.UserSessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactOfficeFragment extends Fragment {

    UserSessionManager userSessionManager;
    public ContactOfficeFragment() {
        // Required empty public constructor
    }
EditText full_name,email_id,subject,message;
    ProgressDialog progressDialog;

    Button btn_send;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        userSessionManager=new UserSessionManager(getActivity());

        View v=inflater.inflate(R.layout.fragment_contact_office, container, false);

        full_name=v.findViewById(R.id.full_name);
        email_id=v.findViewById(R.id.email_id);
        subject=v.findViewById(R.id.subject);
        message=v.findViewById(R.id.message);
        btn_send=v.findViewById(R.id.btn_send);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);


        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactOfficeFarmingPartner();
            }
        });
        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HomeActivity.home_title.setText("Contact Office");

    }

    public void contactOfficeFarmingPartner()
    {
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<ContactOfficeResponse> call=myInterface.contactOfficeFarmingPartner(userSessionManager.getUserDetails().get(UserSessionManager.KEY_USER_ID),email_id.getText().toString(),subject.getText().toString()
        ,subject.getText().toString(),message.getText().toString());
        call.enqueue(new Callback<ContactOfficeResponse>() {
            @Override
            public void onResponse(Call<ContactOfficeResponse> call, Response<ContactOfficeResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful())
                {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1"))
                    {
                        Toast.makeText(getActivity(), "Message sent successfully", Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().popBackStackImmediate();                    }
                }
                
            }

            @Override
            public void onFailure(Call<ContactOfficeResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();

            }
        });


    }
}
