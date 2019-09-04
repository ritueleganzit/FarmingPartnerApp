package com.eleganzit.farmingpartner.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.eleganzit.farmingpartner.R;
import com.eleganzit.farmingpartner.api.RetrofitAPI;
import com.eleganzit.farmingpartner.api.RetrofitInterface;
import com.eleganzit.farmingpartner.model.GetFarmingPartnerDetailResponse;
import com.eleganzit.farmingpartner.model.UpdateFarmingPartnerDetailResponse;
import com.eleganzit.farmingpartner.utils.UserSessionManager;
import com.rilixtech.CountryCodePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {
    UserSessionManager userSessionManager;
    ProgressDialog progressDialog;
    Button btn_submit;
    CountryCodePicker ed_ccode;

    EditText farming_partner_name,email,phone,address,landmark,sub_location,dob,last_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        userSessionManager = new UserSessionManager(EditProfileActivity.this);
        farming_partner_name = findViewById(R.id.farming_partner_name);
        email = findViewById(R.id.email);
        address = findViewById(R.id.address);
        last_name = findViewById(R.id.last_name);
        ed_ccode=findViewById(R.id.ed_ccode);

        btn_submit = findViewById(R.id.btn_submit);
        landmark = findViewById(R.id.landmark);
        phone = findViewById(R.id.phone);
        sub_location = findViewById(R.id.sub_location);
        dob = findViewById(R.id.dob);
        progressDialog = new ProgressDialog(EditProfileActivity.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                final int mYear = c.get(Calendar.YEAR);
                final int mMonth = c.get(Calendar.MONTH);
                final int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        int month= monthOfYear+1;
                        String fm=""+month;
                        String fd=""+dayOfMonth;
                        if(month<10){
                            fm ="0"+month;
                        }
                        if (dayOfMonth<10){
                            fd="0"+dayOfMonth;
                        }
                        String date= ""+year+"-"+fm+"-"+fd;
                        Log.d("tag",""+date);
                        dob.setText(date);
                    }
                }, mYear, mMonth, mDay);
                // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

                datePickerDialog.show();
            }
        });
        getFarmData();

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid())
                {
                    updateProfile();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public void getFarmData() {
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<GetFarmingPartnerDetailResponse> call=myInterface.getFarmingPartnerDetail(userSessionManager.getUserDetails().get(UserSessionManager.KEY_USER_ID));
        call.enqueue(new Callback<GetFarmingPartnerDetailResponse>() {
            @Override
            public void onResponse(Call<GetFarmingPartnerDetailResponse> call, Response<GetFarmingPartnerDetailResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful())
                {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1"))
                    {

                        if (response.body().getData()!=null)
                        {

                            if (response.body().getData().get(0).getPhone()!=null &&  !response.body().getData().get(0).getPhone().isEmpty())
                            {
                                String result = response.body().getData().get(0).getPhone().replaceAll("[-+.^:,]","");
                                phone.setText(""+result.substring(3));

                            }

                            farming_partner_name.setText(""+response.body().getData().get(0).getFarmingPartnerName());

                            dob.setText(""+response.body().getData().get(0).getDob());
                            address.setText(""+response.body().getData().get(0).getAddress());
                            landmark.setText(""+response.body().getData().get(0).getLandmark());
                            sub_location.setText(""+response.body().getData().get(0).getSubLocation());
                            email.setText(""+response.body().getData().get(0).getEmail());
                            last_name.setText(""+response.body().getData().get(0).getLastName());



                        }
                    }
                }


            }

            @Override
            public void onFailure(Call<GetFarmingPartnerDetailResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });


                }






    public boolean isValid() {
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email.getText().toString());

        if (farming_partner_name.getText().toString().trim().equals("")) {

            farming_partner_name.setError("First name is mandatory");

            farming_partner_name.requestFocus();

            return false;
        } else if (last_name.getText().toString().trim().equals("")) {

            last_name.setError("Last name is mandatory");

            last_name.requestFocus();

            return false;
        } else if (address.getText().toString().trim().equals("")) {

            address.setError("Address is mandatory");

            address.requestFocus();

            return false;
        }   else if (landmark.getText().toString().trim().equals("")) {

            landmark.setError("Landmark is mandatory");

            landmark.requestFocus();

            return false;
        } else if (sub_location.getText().toString().trim().equals("")) {

            sub_location.setError("Sub Location is mandatory");

            sub_location.requestFocus();

            return false;
        } else if (!matcher.matches()) {
            email.setError("Please enter a Valid e-mail id");

            email.requestFocus();
            return false;
        } else if (phone.getText().toString().trim().equals("") || phone.getText().toString().trim().length()<10) {

            phone.setError("Phone number must contain atleast 10 digits");

            phone.requestFocus();

            return false;
        }



        return true;
    }


    public void updateProfile()
    {

        Log.d("ggggg",""+ed_ccode.getSelectedCountryCodeWithPlus()+" "+phone.getText().toString());
        Log.d("ggggg",""+email.getText().toString());
        Log.d("ggggg",""+farming_partner_name.getText().toString());
        Log.d("ggggg",""+phone.getText().toString());
        Log.d("ggggg",""+last_name.getText().toString());
        Log.d("ggggg",""+address.getText().toString());
        Log.d("ggggg",""+landmark.getText().toString());
        Log.d("ggggg",""+sub_location.getText().toString());
        progressDialog.show();
      String  calender= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<UpdateFarmingPartnerDetailResponse> call=myInterface.updateFarmingPartnerDetail(farming_partner_name.getText().toString(),
                email.getText().toString(),
                ed_ccode.getSelectedCountryCodeWithPlus()+" "+ phone.getText().toString(),
                calender,last_name.getText().toString(),address.getText().toString(),landmark.getText().toString(),sub_location.getText().toString(),dob.getText().toString(),userSessionManager.getUserDetails().get(UserSessionManager.KEY_USER_ID));

        call.enqueue(new Callback<UpdateFarmingPartnerDetailResponse>() {
            @Override
            public void onResponse(Call<UpdateFarmingPartnerDetailResponse> call, Response<UpdateFarmingPartnerDetailResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful())
                {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1"))
                    {

                        Toast.makeText(EditProfileActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                        if (response.body().getData()!=null)
                        {

                            if (response.body().getData().get(0).getPhone()!=null &&  !response.body().getData().get(0).getPhone().isEmpty())
                            {

                                userSessionManager.updateUserData(""+response.body().getData().get(0).getAddress(),
                                        "",""+response.body().getData().get(0).getFarmingPartnerName(),
                                        ""+response.body().getData().get(0).getLastName(),
                                        ""+response.body().getData().get(0).getPhone(),
                                        ""+response.body().getData().get(0).getDob(),
                                        ""+response.body().getData().get(0).getAddress(),
                                        ""+response.body().getData().get(0).getLandmark(),
                                        ""+response.body().getData().get(0).getSubLocation(),
                                        "","")   ;



                            }




                        }

                    }else
                    {
                        Toast.makeText(EditProfileActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(EditProfileActivity.this, "Server Internet", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UpdateFarmingPartnerDetailResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditProfileActivity.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();


            }
        });

    }


}
