package com.eleganzit.farmingpartner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eleganzit.farmingpartner.activity.ForgotPasswordActivity;
import com.eleganzit.farmingpartner.api.RetrofitAPI;
import com.eleganzit.farmingpartner.api.RetrofitInterface;
import com.eleganzit.farmingpartner.model.LoginRespose;
import com.eleganzit.farmingpartner.utils.UserSessionManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextView txt_signup,txt_forgot;
    Button btn_login;
    EditText ed_email,ed_password;
    ProgressDialog progressDialog;
    UserSessionManager userSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userSessionManager=new UserSessionManager(this);

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        txt_signup=findViewById(R.id.txt_signup);
        txt_forgot=findViewById(R.id.txt_forgot);
        ed_email=findViewById(R.id.ed_email);
        ed_password=findViewById(R.id.ed_password);
        btn_login=findViewById(R.id.btn_login);

        txt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        txt_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid())
                {
                    loginUser();
                }
            }
        });
    }


    private void loginUser() {
//        progressDialog.show();
//        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
//        Call<LoginRespose> call = myInterface.loginUser(ed_email.getText().toString(), ed_password.getText().toString());
//        call.enqueue(new Callback<LoginRespose>() {
//            @Override
//            public void onResponse(Call<LoginRespose> call, Response<LoginRespose> response) {
//                progressDialog.dismiss();
//                if (response.isSuccessful()) {
//                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
//                        if (response.body().getData() != null) {
//                            String email="", id="",farm_id="", fname="",lname="", photo="",phone="",dob="",address="",landmark="",sub_location="";
//                            for (int i = 0; i < response.body().getData().size(); i++) {
//                                email = response.body().getData().get(i).getEmail();
//                                id = response.body().getData().get(i).getCustomerId();
//                                farm_id = response.body().getData().get(i).getFarm_id();
//                                fname = response.body().getData().get(i).getFname()+" ";
//                                lname = response.body().getData().get(i).getLname()+" ";
//                                photo = response.body().getData().get(i).getPhoto();
//                                if(photo==null)
//                                {
//                                    photo="";
//                                }
//                                dob = response.body().getData().get(i).getDob();
//                                if(dob==null)
//                                {
//                                    dob="";
//                                }
//                                address = response.body().getData().get(i).getAddress();
//                                if(address==null)
//                                {
//                                    address="";
//                                }
//                                landmark = response.body().getData().get(i).getLandmark();
//                                if(landmark==null)
//                                {
//                                    landmark="";
//                                }
//                                sub_location = response.body().getData().get(i).getSubLocation();
//                                if(sub_location==null)
//                                {
//                                    sub_location="";
//                                }
//                             String   house_no=response.body().getData().get(i).getHouse_no();
//                                phone = response.body().getData().get(i).getPhone();
//                                userSessionManager.createLoginSession(""+response.body().getData().get(i).getRegion(),id,farm_id, email, ed_password.getText().toString(), fname,lname,phone, dob,address,landmark,sub_location,photo,house_no);
//
//                            }
//                            //Toast.makeText(LoginActivity.this, "--" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
//
//                        }
//                    } else {
//
//                        Toast.makeText(LoginActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//
//
//                }
//                else
//                {
//                    Toast.makeText(LoginActivity.this, "Server or Internet Error", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<LoginRespose> call, Throwable t) {
//                progressDialog.dismiss();
//                Toast.makeText(LoginActivity.this, "Server or Internet Error", Toast.LENGTH_SHORT).show();
//            }
//        });

    }


    public boolean isValid() {
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(ed_email.getText().toString());

        if (!matcher.matches()) {
            ed_email.setError("Please enter a Valid e-mail id");

            ed_email.requestFocus();
            return false;
        }
        else  if (ed_password.getText().toString().trim().equals("") || ed_password.getText().toString().trim().length()<6) {

            ed_password.setError("Password must contain atleast 6 characters");

            ed_password.requestFocus();

            return false;
        }


        return true;
    }
}
