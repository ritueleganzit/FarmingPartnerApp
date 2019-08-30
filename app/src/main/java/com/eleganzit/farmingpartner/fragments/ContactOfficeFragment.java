package com.eleganzit.farmingpartner.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eleganzit.farmingpartner.NavHomeActivity;
import com.eleganzit.farmingpartner.R;
import com.eleganzit.farmingpartner.api.RetrofitAPI;
import com.eleganzit.farmingpartner.api.RetrofitInterface;
import com.eleganzit.farmingpartner.model.ContactOfficeResponse;
import com.eleganzit.farmingpartner.utils.UserSessionManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        userSessionManager=new UserSessionManager(getActivity());

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

       // ed_fullname=v.findViewById(R.id.ed_fullname);
        ed_email=v.findViewById(R.id.ed_email);
      //  ed_subject=v.findViewById(R.id.ed_subject);
        ed_message=v.findViewById(R.id.ed_message);
        btn_send=v.findViewById(R.id.btn_send);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                Pattern pattern;
                Matcher matcher;
                pattern = Pattern.compile(EMAIL_PATTERN);
                matcher = pattern.matcher(ed_email.getText().toString());

                if(ed_fullname.getText().toString().trim().isEmpty()) {
                    ed_fullname.setError("Please enter full name");
                    ed_fullname.requestFocus();
                }
                else if(!matcher.matches()) {
                    ed_email.setError("Please enter valid email address");
                    ed_email.requestFocus();
                }
                else if(ed_subject.getText().toString().trim().isEmpty()) {
                    ed_subject.setError("Please enter subject");
                    ed_subject.requestFocus();
                }
                else if(ed_message.getText().toString().trim().isEmpty()) {
                    ed_message.setError("Please enter message");
                    ed_message.requestFocus();
                }
                else {
                    contactOffice();
                }
            }
        });

        return v;
    }


    private void contactOffice() {
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<ContactOfficeResponse> call = myInterface.contactOffice(userSessionManager.getUserDetails().get(UserSessionManager.KEY_USER_ID), ed_fullname.getText().toString(),ed_subject.getText().toString(), ed_message.getText().toString());
        call.enqueue(new Callback<ContactOfficeResponse>() {
            @Override
            public void onResponse(Call<ContactOfficeResponse> call, Response<ContactOfficeResponse> response) {
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
                    Toast.makeText(getActivity(), "Server or Internet Error" , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ContactOfficeResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}
