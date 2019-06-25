package com.eleganzit.e_farmingcustomer;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Looper;
import android.os.StrictMode;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eleganzit.e_farmingcustomer.api.RetrofitAPI;
import com.eleganzit.e_farmingcustomer.api.RetrofitInterface;
import com.eleganzit.e_farmingcustomer.model.LoginRespose;
import com.eleganzit.e_farmingcustomer.model.RegisterResponse;
import com.eleganzit.e_farmingcustomer.utils.UserSessionManager;
import com.google.firebase.iid.FirebaseInstanceId;
import com.rilixtech.CountryCodePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {

    TextView txt_signin;
    TextInputEditText ed_birthdate;
    EditText ed_fname, ed_lname, ed_address, ed_landmark, ed_sub_location, ed_email, ed_phone, ed_referral_code, ed_password, ed_cpassword;
    Button btn_register;
    ProgressDialog progressDialog;
    UserSessionManager userSessionManager;
    CountryCodePicker ed_ccode;
    private String devicetoken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        userSessionManager = new UserSessionManager(this);

        progressDialog = new ProgressDialog(RegistrationActivity.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        txt_signin = findViewById(R.id.txt_signin);
        ed_fname = findViewById(R.id.ed_fname);
        ed_lname = findViewById(R.id.ed_lname);
        ed_address = findViewById(R.id.ed_address);
        ed_landmark = findViewById(R.id.ed_landmark);
        ed_sub_location = findViewById(R.id.ed_sub_location);
        ed_email = findViewById(R.id.ed_email);
        ed_ccode = findViewById(R.id.ed_ccode);
        ed_phone = findViewById(R.id.ed_phone);
        ed_referral_code = findViewById(R.id.ed_referral_code);
        ed_password = findViewById(R.id.ed_password);
        ed_cpassword = findViewById(R.id.ed_cpassword);
        btn_register = findViewById(R.id.btn_register);
        ed_birthdate = findViewById(R.id.ed_birthdate);
        //ed_ccode.registerPhoneNumberTextView(ed_phone);

        txt_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Calendar c = Calendar.getInstance();
        final int mYear = c.get(Calendar.YEAR);
        final int mMonth = c.get(Calendar.MONTH);
        final int mDay = c.get(Calendar.DAY_OF_MONTH);

        ed_birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(RegistrationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        ed_birthdate.setText(i2 + "-" + (i1 + 1) + "-" + i);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid()) {
                    registerUser();
                }
            }
        });


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String Token = FirebaseInstanceId.getInstance().getToken();
                if (Token != null) {
                    Log.d("Rrrrrmytokenn", Token);

                    devicetoken = Token;
                    StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().build();
                    StrictMode.setThreadPolicy(threadPolicy);
                    try {
                        JSONObject jsonObject = new JSONObject(Token);
                        Log.d("mytoken", jsonObject.getString("token"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Looper.prepare();
                    Log.d("No token", "No token");
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();

    }

    private void registerUser() {

        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<RegisterResponse> call = myInterface.registerUser(ed_fname.getText().toString(), ed_lname.getText().toString(),
                ed_address.getText().toString(), ed_landmark.getText().toString(), ed_sub_location.getText().toString(),
                ed_email.getText().toString(), ed_phone.getText().toString(), ed_birthdate.getText().toString(),
                ed_referral_code.getText().toString(), ed_cpassword.getText().toString(), "android", devicetoken);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {
                            String email, id, username, photo;
                            /*for (int i = 0; i < response.body().getData().size(); i++) {
                                email = response.body().getData().get(i).getVendorEmail();
                                id = response.body().getData().get(i).getVendorId();
                                username = response.body().getData().get(i).getVendorName();
                                photo = response.body().getData().get(i).getVendorStartTime();
                                //userSessionManager.createLoginSession(id, email, ed_password.getText().toString(), username, photo);

                            }*/
                            Toast.makeText(RegistrationActivity.this, "Registered successfully" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } else {

                        Toast.makeText(RegistrationActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }


                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(RegistrationActivity.this, "Server or Internet Error", Toast.LENGTH_SHORT).show();
            }
        });

    }


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
        } else if (ed_password.getText().toString().equals("") || ed_password.getText().toString().length() < 6) {

            ed_password.setError("Password must contain atleast 6 characters");

            ed_password.requestFocus();

            return false;
        } else if (!ed_password.getText().toString().equals(ed_cpassword.getText().toString())) {

            ed_cpassword.setError("Password doesn't match");

            ed_cpassword.requestFocus();

            return false;
        } else if (ed_fname.getText().toString().equals("")) {

            ed_fname.setError("First name is mandatory");

            ed_fname.requestFocus();

            return false;
        } else if (ed_lname.getText().toString().equals("")) {

            ed_lname.setError("Last name is mandatory");

            ed_lname.requestFocus();

            return false;
        } else if (ed_address.getText().toString().equals("")) {

            ed_address.setError("Address is mandatory");

            ed_address.requestFocus();

            return false;
        } else if (ed_landmark.getText().toString().equals("")) {

            ed_landmark.setError("Landmark is mandatory");

            ed_landmark.requestFocus();

            return false;
        } else if (ed_sub_location.getText().toString().equals("")) {

            ed_sub_location.setError("Sub Location is mandatory");

            ed_sub_location.requestFocus();

            return false;
        } else if (ed_phone.getText().toString().equals("")) {

            ed_phone.setError("Phone is mandatory");

            ed_phone.requestFocus();

            return false;
        }


        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
