package com.eleganzit.e_farmingcustomer;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.eleganzit.e_farmingcustomer.api.RetrofitAPI;
import com.eleganzit.e_farmingcustomer.api.RetrofitInterface;
import com.eleganzit.e_farmingcustomer.model.LoginRespose;
import com.eleganzit.e_farmingcustomer.utils.UserSessionManager;
import com.rilixtech.CountryCodePicker;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    TextInputEditText ed_birthdate;
    EditText ed_fname,ed_lname,ed_address,ed_landmark,ed_sub_location,ed_email,ed_phone,ed_referral_code,ed_password,ed_cpassword;
    Button btn_submit;
    ProgressDialog progressDialog;
    UserSessionManager userSessionManager;
    CountryCodePicker ed_ccode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        userSessionManager=new UserSessionManager(this);

        progressDialog = new ProgressDialog(EditProfileActivity.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        ImageView back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ed_birthdate=findViewById(R.id.ed_birthdate);
        ed_fname=findViewById(R.id.ed_fname);
        ed_lname=findViewById(R.id.ed_lname);
        ed_address=findViewById(R.id.ed_address);
        ed_landmark=findViewById(R.id.ed_landmark);
        ed_sub_location=findViewById(R.id.ed_sub_location);
        ed_email=findViewById(R.id.ed_email);
        ed_ccode=findViewById(R.id.ed_ccode);
        ed_phone=findViewById(R.id.ed_phone);
        ed_referral_code=findViewById(R.id.ed_referral_code);
        ed_password=findViewById(R.id.ed_password);
        ed_cpassword=findViewById(R.id.ed_cpassword);
        btn_submit=findViewById(R.id.btn_submit);
        ed_ccode.registerPhoneNumberTextView(ed_phone);

        Calendar c = Calendar.getInstance();
        final int mYear = c.get(Calendar.YEAR);
        final int mMonth = c.get(Calendar.MONTH);
        final int mDay = c.get(Calendar.DAY_OF_MONTH);

        ed_birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(EditProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        ed_birthdate.setText(i2+"-"+(i1+1)+"-"+i);
                    }
                }, mYear,mMonth,mDay);
                datePickerDialog.show();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValid())
                {
                    //updateProfile();
                }
            }
        });

    }

/*

    private void updateProfile() {

        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<UpdateResponse> call = myInterface.updateProfile(ed_fname.getText().toString(), ed_lname.getText().toString(),
                ed_address.getText().toString(), ed_landmark.getText().toString(),ed_sub_location.getText().toString(),
                ed_email.getText().toString(),ed_phone.getText().toString(), ed_birthdate.getText().toString(),
                ed_referral_code.getText().toString(), ed_cpassword.getText().toString());
        call.enqueue(new Callback<UpdateResponse>() {
            @Override
            public void onResponse(Call<UpdateResponse> call, Response<UpdateResponse> response) {
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
                                //userSessionManager.createLoginSession(id, email, ed_password.getText().toString(), username, photo);

                            }

                        }
                    } else {

                        Toast.makeText(EditProfileActivity.this, "--" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }


                }
            }

            @Override
            public void onFailure(Call<UpdateResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditProfileActivity.this, "Server or Internet Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

*/

    public boolean isValid() {
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(ed_email.getText().toString());

        if (!matcher.matches()) {
            ed_email.setError("Please enter valid email");

            ed_email.requestFocus();
            return false;
        }
        else  if (ed_password.getText().toString().equals("") || ed_password.getText().toString().length()<6) {

            ed_password.setError("Password must contain atleast 6 characters");

            ed_password.requestFocus();

            return false;
        }
        else  if (!ed_password.getText().toString().equals(ed_cpassword.getText().toString())) {

            ed_cpassword.setError("Password doesn't match");

            ed_cpassword.requestFocus();

            return false;
        }
        else  if (ed_fname.getText().toString().equals("")) {

            ed_fname.setError("First name is mandatory");

            ed_fname.requestFocus();

            return false;
        }
        else  if (ed_lname.getText().toString().equals("")) {

            ed_lname.setError("Last name is mandatory");

            ed_lname.requestFocus();

            return false;
        }
        else  if (ed_address.getText().toString().equals("")) {

            ed_address.setError("Address is mandatory");

            ed_address.requestFocus();

            return false;
        }
        else  if (ed_landmark.getText().toString().equals("")) {

            ed_landmark.setError("Landmark is mandatory");

            ed_landmark.requestFocus();

            return false;
        }
        else  if (ed_sub_location.getText().toString().equals("")) {

            ed_sub_location.setError("Sub Location is mandatory");

            ed_sub_location.requestFocus();

            return false;
        }
        else  if (ed_phone.getText().toString().equals("")) {

            ed_phone.setError("Phone is mandatory");

            ed_phone.requestFocus();

            return false;
        }


        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
}
