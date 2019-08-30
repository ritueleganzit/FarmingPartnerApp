package com.eleganzit.farmingpartner.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.eleganzit.farmingpartner.R;
import com.eleganzit.farmingpartner.RegistrationActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SignUpActivity extends AppCompatActivity {

    EditText edpassword,edfarming_partner_name,edlast_name,edaddress,edlandmark,edsub_location,edemail,edphone,eddob,edcpassword;
    Button btn_signupnext;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        btn_signupnext=findViewById(R.id.btn_signupnext);
        edaddress=findViewById(R.id.address);
        edpassword=findViewById(R.id.password);
        edcpassword=findViewById(R.id.cpassword);
        edfarming_partner_name=findViewById(R.id.farming_partner_name);
        edlast_name=findViewById(R.id.last_name);
        edlandmark=findViewById(R.id.landmark);
        edsub_location=findViewById(R.id.sub_location);
        edemail=findViewById(R.id.email);
        edphone=findViewById(R.id.phone);
        eddob=findViewById(R.id.dob);
        sharedPreferences=getSharedPreferences("regis",MODE_PRIVATE);
        eddob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                final int mYear = c.get(Calendar.YEAR);
                final int mMonth = c.get(Calendar.MONTH);
                final int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(SignUpActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        eddob.setText(i + "-" + (i1 + 1) + "-" + i2);
                    }
                }, mYear, mMonth, mDay);
                // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

                datePickerDialog.show();
            }
        });
        sharedPreferencesEditor=sharedPreferences.edit();
        btn_signupnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid()) {

                    sharedPreferencesEditor.putString("farming_partner_name",edfarming_partner_name.getText().toString());
                    sharedPreferencesEditor.putString("last_name",edlast_name.getText().toString());
                    sharedPreferencesEditor.putString("address",edaddress.getText().toString());
                    sharedPreferencesEditor.putString("password",edpassword.getText().toString());
                    sharedPreferencesEditor.putString("landmark",edlandmark.getText().toString());
                    sharedPreferencesEditor.putString("sub_location",edsub_location.getText().toString());
                    sharedPreferencesEditor.putString("email",edemail.getText().toString());
                    sharedPreferencesEditor.putString("phone",edphone.getText().toString());
                    sharedPreferencesEditor.putString("dob",eddob.getText().toString());
                    sharedPreferencesEditor.commit();

                    startActivity(new Intent(SignUpActivity.this, SignUpFarmDetails.class)


                    );
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }
            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        sharedPreferencesEditor.clear();
        sharedPreferencesEditor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        edfarming_partner_name.setText(""+sharedPreferences.getString("farming_partner_name",""));
        edlast_name.setText(""+sharedPreferences.getString("last_name",""));
        edaddress.setText(""+sharedPreferences.getString("address",""));
        edlandmark.setText(""+sharedPreferences.getString("landmark",""));
        edsub_location.setText(""+sharedPreferences.getString("sub_location",""));
        edemail.setText(""+sharedPreferences.getString("email",""));
        edphone.setText(""+sharedPreferences.getString("phone",""));
        eddob.setText(""+sharedPreferences.getString("dob",""));
    }


    public boolean isValid() {
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(edemail.getText().toString());

        if (edfarming_partner_name.getText().toString().trim().equals("")) {

            edfarming_partner_name.setError("First name is mandatory");

            edfarming_partner_name.requestFocus();

            return false;
        } else if (edlast_name.getText().toString().trim().equals("")) {

            edlast_name.setError("Last name is mandatory");

            edlast_name.requestFocus();

            return false;
        } else if (edaddress.getText().toString().trim().equals("")) {

            edaddress.setError("Address is mandatory");

            edaddress.requestFocus();

            return false;
        }   else if (edlandmark.getText().toString().trim().equals("")) {

            edlandmark.setError("Landmark is mandatory");

            edlandmark.requestFocus();

            return false;
        } else if (edsub_location.getText().toString().trim().equals("")) {

            edsub_location.setError("Sub Location is mandatory");

            edsub_location.requestFocus();

            return false;
        } else if (!matcher.matches()) {
            edemail.setError("Please enter a Valid e-mail id");

            edemail.requestFocus();
            return false;
        } else if (edphone.getText().toString().trim().equals("") || edphone.getText().toString().trim().length()<10) {

            edphone.setError("Phone number must contain atleast 10 digits");

            edphone.requestFocus();

            return false;
        }
        else if (edpassword.getText().toString().trim().equals("") || edpassword.getText().toString().trim().length() < 6) {

            edpassword.setError("Password must contain atleast 6 characters");

            edpassword.requestFocus();

            return false;
        } else if (!edpassword.getText().toString().trim().equals(edcpassword.getText().toString().trim())) {

            edcpassword.setError("Password doesn't match");

            edcpassword.requestFocus();

            return false;

        }


        return true;
    }



}
