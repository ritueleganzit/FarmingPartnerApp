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

import com.eleganzit.e_farmingcustomer.NavHomeActivity;
import com.eleganzit.e_farmingcustomer.R;
import com.eleganzit.e_farmingcustomer.api.RetrofitAPI;
import com.eleganzit.e_farmingcustomer.api.RetrofitInterface;
import com.eleganzit.e_farmingcustomer.utils.UserSessionManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactOfficeFragment extends Fragment {

    ProgressDialog progressDialog;
    UserSessionManager userSessionManager;
    EditText ed_fullname,ed_email,ed_subject,ed_message;
    Button btn_send;

    public ContactOfficeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_contact_office, container, false);

        NavHomeActivity.home_title.setText("Contact Office");

        ed_fullname=v.findViewById(R.id.ed_fullname);
        ed_email=v.findViewById(R.id.ed_email);
        ed_subject=v.findViewById(R.id.ed_subject);
        ed_message=v.findViewById(R.id.ed_message);
        btn_send=v.findViewById(R.id.btn_send);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ed_fullname.getText().toString().isEmpty()) {
                    ed_fullname.setError("Please enter full name");
                    ed_fullname.requestFocus();
                }
                else if(ed_email.getText().toString().isEmpty()) {
                    ed_email.setError("Please enter email address");
                    ed_email.requestFocus();
                }
                else if(ed_subject.getText().toString().isEmpty()) {
                    ed_subject.setError("Please enter subject");
                    ed_subject.requestFocus();
                }
                else if(ed_message.getText().toString().isEmpty()) {
                    ed_message.setError("Please enter message");
                    ed_message.requestFocus();
                }
                else {
                    //sendMessage();
                }
            }
        });

        return v;
    }

/*

    private void sendMessage() {
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<ContactRespose> call = myInterface.sendMessage(ed_email.getText().toString(), ed_message.getText().toString());
        call.enqueue(new Callback<ContactRespose>() {
            @Override
            public void onResponse(Call<ContactRespose> call, Response<ContactRespose> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {
                            String email, id, username, photo;
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                email = response.body().getData().get(i).getVendorEmail();
                                id = response.body().getData().get(i).getVendorId();
                                username = response.body().getData().get(i).getVendorName();
                                photo = response.body().getData().get(i).getVendorStartTime();
                                userSessionManager.createLoginSession(id, email, ed_password.getText().toString(), username, photo);

                            }

                        }
                    } else {

                        Toast.makeText(getActivity(), "--" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }


                }
            }

            @Override
            public void onFailure(Call<ContactRespose> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
*/


}
