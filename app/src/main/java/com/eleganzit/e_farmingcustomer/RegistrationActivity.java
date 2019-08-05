package com.eleganzit.e_farmingcustomer;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.os.PersistableBundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.eleganzit.e_farmingcustomer.api.RetrofitAPI;
import com.eleganzit.e_farmingcustomer.api.RetrofitInterface;
import com.eleganzit.e_farmingcustomer.model.LoginRespose;
import com.eleganzit.e_farmingcustomer.model.RegionResponse;
import com.eleganzit.e_farmingcustomer.model.RegisterResponse;
import com.eleganzit.e_farmingcustomer.model.SubLocationData;
import com.eleganzit.e_farmingcustomer.model.SubLocationResponse;
import com.eleganzit.e_farmingcustomer.utils.UserSessionManager;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.iid.FirebaseInstanceId;
import com.rilixtech.CountryCodePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {
    private static final int PLACE_PICKER_REQUEST2 = 1001;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;
    TextView txt_signin;
    TextInputEditText ed_birthdate;
    EditText ed_fname, ed_lname, ed_address, ed_landmark, ed_sub_location, ed_email, ed_phone, ed_referral_code, ed_password, ed_cpassword,search_address,ed_region,ed_flatno;
    Button btn_register;
    ProgressDialog progressDialog;
    UserSessionManager userSessionManager;
    CountryCodePicker ed_ccode;
    private String devicetoken;

    List<String> sublocationList;
    List<String> regionlocationList;
    List<SubLocationData> msublocationList;
    private String state_id,redirect="no";
    private String TAG="RegistrationAct";

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
        String param = getIntent().getStringExtra("add");
        redirect = getIntent().getStringExtra("redirect");
        txt_signin = findViewById(R.id.txt_signin);
        ed_fname = findViewById(R.id.ed_fname);
        ed_flatno = findViewById(R.id.ed_flatno);
        ed_lname = findViewById(R.id.ed_lname);
        search_address = findViewById(R.id.search_address);
        ed_address = findViewById(R.id.ed_address);
        ed_landmark = findViewById(R.id.ed_landmark);
        ed_sub_location = findViewById(R.id.ed_sub_location);
        ed_email = findViewById(R.id.ed_email);
        ed_region = findViewById(R.id.ed_region);
        ed_ccode = findViewById(R.id.ed_ccode);
        ed_ccode.setClickable(false);
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
if (param!=null && !(param.isEmpty()))
{
    ed_address.setText(""+param);

}
        ed_region.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRegions();
            }
        });
        ed_birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(RegistrationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        ed_birthdate.setText(i + "-" + (i1 + 1) + "-" + i2);
                    }
                }, mYear, mMonth, mDay);
               // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

                datePickerDialog.show();
            }
        });
        sharedPreferences=getSharedPreferences("regis",MODE_PRIVATE);
        sharedPreferencesEditor=sharedPreferences.edit();
        ed_sub_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSublocations();
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
        search_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferencesEditor.putString("fname",ed_fname.getText().toString());
                sharedPreferencesEditor.putString("lname",ed_lname.getText().toString());
                sharedPreferencesEditor.putString("landmark",ed_landmark.getText().toString());
                sharedPreferencesEditor.putString("region",ed_region.getText().toString());
                sharedPreferencesEditor.putString("location",ed_sub_location.getText().toString());
                sharedPreferencesEditor.putString("email",ed_email.getText().toString());
                sharedPreferencesEditor.putString("phone",ed_phone.getText().toString());
                sharedPreferencesEditor.putString("dob",ed_birthdate.getText().toString());
                sharedPreferencesEditor.putString("referal",ed_referral_code.getText().toString());
                sharedPreferencesEditor.commit();
                startActivity(new Intent(RegistrationActivity.this,RegistrationAddress.class));

                finish();
                /*Intent intent = null;
                try {
                    intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(RegistrationActivity.this);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
                startActivityForResult(intent, PLACE_PICKER_REQUEST2);*/
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


    @Override
    protected void onResume() {
        super.onResume();
        if (redirect!=null && !(redirect.isEmpty()))
        {
            if (redirect.equalsIgnoreCase("yes")) {
                ed_fname.setText("" + sharedPreferences.getString("fname", ""));
                ed_lname.setText("" + sharedPreferences.getString("lname", ""));
                ed_landmark.setText("" + sharedPreferences.getString("landmark", ""));
                ed_region.setText("" + sharedPreferences.getString("region", ""));
                ed_sub_location.setText("" + sharedPreferences.getString("location", ""));
                ed_email.setText("" + sharedPreferences.getString("email", ""));
                ed_phone.setText("" + sharedPreferences.getString("phone", ""));
                ed_birthdate.setText("" + sharedPreferences.getString("dob", ""));
                ed_referral_code.setText("" + sharedPreferences.getString("referal", ""));
            }
        }


    }

    private void getRegions() {
        regionlocationList=new ArrayList<>();
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<RegionResponse> call = myInterface.getRegions();
        call.enqueue(new Callback<RegionResponse>() {
            @Override
            public void onResponse(Call<RegionResponse> call, Response<RegionResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {

                            for(int i=0;i<response.body().getData().size();i++)
                            {
                                regionlocationList.add(response.body().getData().get(i).getSublocationName());
                            }
                            showRegionLocationDialog();

                        }
                    }
                    else
                    {
                        Toast.makeText(RegistrationActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    Toast.makeText(RegistrationActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<RegionResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(RegistrationActivity.this, "Server or Internet Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void registerUser() {

        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<RegisterResponse> call = myInterface.registerUser(ed_fname.getText().toString(), ed_lname.getText().toString(),
                ed_address.getText().toString(),ed_flatno.getText().toString(),ed_region.getText().toString()  , ed_landmark.getText().toString(), ed_sub_location.getText().toString(),
                ed_email.getText().toString(), ed_phone.getText().toString(), ed_birthdate.getText().toString(),
                ed_referral_code.getText().toString(), ed_cpassword.getText().toString(), "android", devicetoken);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {
                            String email="", id="",farm_id="", fname="",lname="", photo="",phone="",dob="",address="",landmark="",sub_location="";

                            for (int i = 0; i < response.body().getData().size(); i++) {
                                email = response.body().getData().get(i).getEmail();
                                id = String.valueOf(response.body().getData().get(i).getCustomerId());
                                //farm_id = response.body().getData().get(i).getFarm_id();
                                fname = response.body().getData().get(i).getFname()+" ";
                                lname = response.body().getData().get(i).getLname()+" ";
                                //photo = response.body().getData().get(i).getPhoto();
                                if(photo==null)
                                {
                                    photo="";
                                }
                                dob = response.body().getData().get(i).getDob();
                                if(dob==null)
                                {
                                    dob="";
                                }
                                address = response.body().getData().get(i).getAddress();
                                if(address==null)
                                {
                                    address="";
                                }
                                landmark = response.body().getData().get(i).getLandmark();
                                if(landmark==null)
                                {
                                    landmark="";
                                }
                                sub_location = response.body().getData().get(i).getSubLocation();
                                if(sub_location==null)
                                {
                                    sub_location="";
                                }
                                phone = response.body().getData().get(i).getPhone();
                                String house_no=response.body().getData().get(i).getHouse_no();
                                userSessionManager.createLoginSession(""+response.body().getData().get(i).getRegion(),id,farm_id, email, ed_password.getText().toString(), fname,lname,phone, dob,address,landmark,sub_location,photo,house_no);

                            }
                            Toast.makeText(RegistrationActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
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

    private void getSublocations() {
        sublocationList=new ArrayList<>();
        msublocationList=new ArrayList<>();
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<SubLocationResponse> call = myInterface.getSublocations(ed_region.getText().toString());
        call.enqueue(new Callback<SubLocationResponse>() {
            @Override
            public void onResponse(Call<SubLocationResponse> call, Response<SubLocationResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {

                                for(int i=0;i<response.body().getData().size();i++)
                                {
                                    sublocationList.add(response.body().getData().get(i).getSublocationName());
                                    msublocationList.add(response.body().getData().get(i));
                                }
                                showLocationDialog();

                        }
                    }
                    else
                    {
                        Toast.makeText(RegistrationActivity.this, "Please try again or select region", Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    Toast.makeText(RegistrationActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<SubLocationResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(RegistrationActivity.this, "Server or Internet Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST2) {
            if (resultCode == RESULT_OK) {

                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("%s", place.getName());
                LatLng latLng=place.getLatLng();


                Log.d(TAG,""+latLng.latitude);
                Log.d(TAG,""+latLng.longitude);
                if (toastMsg.equalsIgnoreCase(""))
                {

                }
                else {
ed_address.setText(toastMsg);
                }



            }
            else {

                //    Toast.makeText(this, "Close", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean isValid() {
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(ed_email.getText().toString());

        if (ed_fname.getText().toString().trim().equals("")) {

            ed_fname.setError("First name is mandatory");

            ed_fname.requestFocus();

            return false;
        } else if (ed_lname.getText().toString().trim().equals("")) {

            ed_lname.setError("Last name is mandatory");

            ed_lname.requestFocus();

            return false;
        } else if (ed_address.getText().toString().trim().equals("")) {

            ed_address.setError("Address is mandatory");

            ed_address.requestFocus();

            return false;
        }  else if (ed_flatno.getText().toString().trim().equals("")) {

            ed_flatno.setError("House No is mandatory");

            ed_flatno.requestFocus();

            return false;
        } else if (ed_landmark.getText().toString().trim().equals("")) {

            ed_landmark.setError("Landmark is mandatory");

            ed_landmark.requestFocus();

            return false;
        } else if (ed_sub_location.getText().toString().trim().equals("")) {

            ed_sub_location.setError("Sub Location is mandatory");

            ed_sub_location.requestFocus();

            return false;
        } else if (!matcher.matches()) {
            ed_email.setError("Please enter a Valid e-mail id");

            ed_email.requestFocus();
            return false;
        } else if (ed_phone.getText().toString().trim().equals("") || ed_phone.getText().toString().trim().length()<10) {

            ed_phone.setError("Phone number must contain atleast 10 digits");

            ed_phone.requestFocus();

            return false;
        }
        else if (ed_password.getText().toString().trim().equals("") || ed_password.getText().toString().trim().length() < 6) {

            ed_password.setError("Password must contain atleast 6 characters");

            ed_password.requestFocus();

            return false;
        } else if (!ed_password.getText().toString().trim().equals(ed_cpassword.getText().toString().trim())) {

            ed_cpassword.setError("Password doesn't match");

            ed_cpassword.requestFocus();

            return false;
        } else  if (ed_phone.getText().toString().trim().equals("") || ed_phone.getText().toString().trim().length()<10) {

            ed_phone.setError("Phone number must contain 10 digits");

            ed_phone.requestFocus();

            return false;
        }


        return true;
    }

    void showLocationDialog() {

        final ListAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, android.R.id.text1, sublocationList);

        final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogCustom));

        builder.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

                ed_sub_location.setText(msublocationList.get(i).getSublocationName());

                state_id=msublocationList.get(i).getLocationId();

            }
        });

        builder.show();

    }
    void showRegionLocationDialog() {

        final ListAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, android.R.id.text1, regionlocationList);

        final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogCustom));

        builder.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

                ed_region.setText(regionlocationList.get(i));
                ed_sub_location.setText("");
               // state_id=msublocationList.get(i).getLocationId();

            }
        });

        builder.show();

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
