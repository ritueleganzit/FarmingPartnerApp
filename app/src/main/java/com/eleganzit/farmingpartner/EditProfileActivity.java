package com.eleganzit.farmingpartner;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.eleganzit.farmingpartner.api.RetrofitAPI;
import com.eleganzit.farmingpartner.api.RetrofitInterface;
import com.eleganzit.farmingpartner.model.ForgotPasswordResponse;
import com.eleganzit.farmingpartner.model.RegionResponse;
import com.eleganzit.farmingpartner.model.SubLocationData;
import com.eleganzit.farmingpartner.model.SubLocationResponse;
import com.eleganzit.farmingpartner.model.UpdateResponse;
import com.eleganzit.farmingpartner.utils.UserSessionManager;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.rilixtech.CountryCodePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    TextInputEditText ed_birthdate;
    EditText ed_fname,ed_lname,ed_address,ed_landmark,ed_sub_location,ed_email,ed_phone,ed_referral_code,search_address,ed_region,ed_flatno;
    Button btn_submit;
    ProgressDialog progressDialog;
    UserSessionManager userSessionManager;
    CountryCodePicker ed_ccode;
    LinearLayout lin_change_password;
    List<String> sublocationList;
    List<String> regionlocationList;
    List<SubLocationData> msublocationList;
    private String state_id;
    private static final int PLACE_PICKER_REQUEST2 = 1001;
    private String TAG="EditProfileAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        userSessionManager=new UserSessionManager(this);
       String param = getIntent().getStringExtra("add");

        // Get a result somewhere.



        progressDialog = new ProgressDialog(EditProfileActivity.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        search_address = findViewById(R.id.search_address);

        ImageView back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ed_birthdate=findViewById(R.id.ed_birthdate);
        ed_flatno=findViewById(R.id.ed_flatno);
        ed_fname=findViewById(R.id.ed_fname);
        ed_lname=findViewById(R.id.ed_lname);
        ed_region=findViewById(R.id.ed_region);
        ed_address=findViewById(R.id.ed_address);
        ed_landmark=findViewById(R.id.ed_landmark);
        ed_sub_location=findViewById(R.id.ed_sub_location);
        ed_email=findViewById(R.id.ed_email);
        ed_ccode=findViewById(R.id.ed_ccode);
        ed_ccode.setClickable(false);
        ed_phone=findViewById(R.id.ed_phone);
        ed_referral_code=findViewById(R.id.ed_referral_code);
        btn_submit=findViewById(R.id.btn_submit);
       // lin_change_password=findViewById(R.id.lin_change_password);
        //ed_ccode.registerPhoneNumberTextView(ed_phone);
        search_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditProfileActivity.this,AddressSelectionActivity.class));
finish();
               /* Intent intent = null;
                try {
                    intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(EditProfileActivity.this);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
                startActivityForResult(intent, PLACE_PICKER_REQUEST2);*/
            }
        });
        ed_fname.setText(userSessionManager.getUserDetails().get(UserSessionManager.KEY_FNAME).trim());
        ed_lname.setText(userSessionManager.getUserDetails().get(UserSessionManager.KEY_LNAME).trim());
        ed_address.setText(userSessionManager.getUserDetails().get(UserSessionManager.KEY_ADDRESS));
        ed_landmark.setText(userSessionManager.getUserDetails().get(UserSessionManager.KEY_LANDMARK));
        ed_sub_location.setText(userSessionManager.getUserDetails().get(UserSessionManager.KEY_SUB_LOCATION));
        ed_email.setText(userSessionManager.getUserDetails().get(UserSessionManager.KEY_EMAIL));
        ed_phone.setText(userSessionManager.getUserDetails().get(UserSessionManager.KEY_PHONE));
        ed_region.setText(userSessionManager.getUserDetails().get(UserSessionManager.KEY_REGION));
        ed_flatno.setText(userSessionManager.getUserDetails().get(UserSessionManager.KEY_HOUSE));
        ed_birthdate.setText(userSessionManager.getUserDetails().get(UserSessionManager.KEY_DOB));
        /*ed_password.setText(userSessionManager.getUserDetails().get(UserSessionManager.KEY_PASSWORD));
        ed_cpassword.setText(userSessionManager.getUserDetails().get(UserSessionManager.KEY_PASSWORD));*/
if (param!=null && !(param.isEmpty()))
        {
            ed_address.setText(param);
        }
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
                        ed_birthdate.setText(i+"-"+(i1+1)+"-"+i2);
                    }
                }, mYear,mMonth,mDay);

                datePickerDialog.show();
            }
        });

        ed_sub_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSublocations();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValid())
                {
                    updateProfile();
                }

            }
        });

        ed_region.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getRegions();
            }
        });
       /* lin_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog=new Dialog(EditProfileActivity.this);
                dialog.setContentView(R.layout.change_password_dialog);

                Button btn_ok=dialog.findViewById(R.id.btn_ok);
                final EditText ed_crntpassword=dialog.findViewById(R.id.ed_crntpassword);
                final EditText ed_newpassword=dialog.findViewById(R.id.ed_newpassword);
                final EditText ed_cpassword=dialog.findViewById(R.id.ed_cpassword);

                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (ed_crntpassword.getText().toString().trim().equals("") || ed_crntpassword.getText().toString().trim().length() < 6) {

                            ed_crntpassword.setError("Please enter valid password");

                            ed_crntpassword.requestFocus();

                        } else if (ed_newpassword.getText().toString().trim().equals("") || ed_newpassword.getText().toString().trim().length() < 6) {

                            ed_newpassword.setError("Password must contain atleast 6 characters");

                            ed_newpassword.requestFocus();

                        } else if (!ed_newpassword.getText().toString().trim().equals(ed_cpassword.getText().toString().trim())) {

                            ed_cpassword.setError("Password doesn't match");

                            ed_cpassword.requestFocus();

                        }
                        else
                        {
                            changePassword(dialog,userSessionManager.getUserDetails().get(UserSessionManager.KEY_EMAIL),ed_crntpassword.getText().toString(),ed_newpassword.getText().toString());
                        }

                    }
                });

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes(lp);
                Window window = dialog.getWindow();
                window.setBackgroundDrawableResource(android.R.color.transparent);

                dialog.show();
            }
        });*/

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
                        Toast.makeText(EditProfileActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    Toast.makeText(EditProfileActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<RegionResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditProfileActivity.this, "Server or Internet Error", Toast.LENGTH_SHORT).show();
            }
        });

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
                        Toast.makeText(EditProfileActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    Toast.makeText(EditProfileActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<SubLocationResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditProfileActivity.this, "Server or Internet Error", Toast.LENGTH_SHORT).show();
            }
        });

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

    private void changePassword(final Dialog dialog, String email, String old_password, final String new_password) {

        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<ForgotPasswordResponse> call=myInterface.changePassword(email,old_password,new_password);
        call.enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful())
                {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1"))
                    {
                        userSessionManager.updatePassword(new_password);
                        dialog.dismiss();
                        Toast.makeText(EditProfileActivity.this, "Password has been changed successfully", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(EditProfileActivity.this, "Current password is wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditProfileActivity.this, "Server or Internet Error", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void updateProfile() {

        Log.d("sssss",""+ed_region.getText().toString());

        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<UpdateResponse> call = myInterface.updateProfile(userSessionManager.getUserDetails().get(UserSessionManager.KEY_USER_ID),
                ed_fname.getText().toString().trim(), ed_lname.getText().toString().trim(),
                ed_address.getText().toString(),ed_flatno.getText().toString(),ed_region.getText().toString(), ed_landmark.getText().toString(),ed_sub_location.getText().toString(),
                ed_phone.getText().toString().trim(), ed_birthdate.getText().toString().trim());

        call.enqueue(new Callback<UpdateResponse>() {
            @Override
            public void onResponse(Call<UpdateResponse> call, Response<UpdateResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {

                        userSessionManager.updateUserData(ed_region.getText().toString(),userSessionManager.getUserDetails().get(UserSessionManager.KEY_PASSWORD), ed_fname.getText().toString(),ed_lname.getText().toString(),ed_phone.getText().toString(), ed_birthdate.getText().toString(),ed_address.getText().toString(),ed_landmark.getText().toString(),ed_sub_location.getText().toString(),userSessionManager.getUserDetails().get(UserSessionManager.KEY_PHOTO),ed_flatno.getText().toString());
                        Toast.makeText(EditProfileActivity.this, "Successfully updated", Toast.LENGTH_SHORT).show();
                        finish();

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
        }
        else  if (ed_lname.getText().toString().trim().equals("")) {

            ed_lname.setError("Last name is mandatory");

            ed_lname.requestFocus();

            return false;
        }
        else  if (ed_address.getText().toString().trim().equals("")) {

            ed_address.setError("Address is mandatory");

            ed_address.requestFocus();

            return false;
        } else if (ed_flatno.getText().toString().trim().equals("")) {

            ed_flatno.setError("House No is mandatory");

            ed_flatno.requestFocus();

            return false;
        }
        else  if (ed_landmark.getText().toString().trim().equals("")) {

            ed_landmark.setError("Landmark is mandatory");

            ed_landmark.requestFocus();

            return false;
        }
        else  if (ed_sub_location.getText().toString().trim().equals("")) {

            ed_sub_location.setError("Sub Location is mandatory");

            ed_sub_location.requestFocus();

            return false;
        }
        else if (!matcher.matches()) {
            ed_email.setError("Please enter a Valid e-mail id");

            ed_email.requestFocus();
            return false;
        }
        else  if (ed_phone.getText().toString().trim().equals("") || ed_phone.getText().toString().trim().length()<10) {

            ed_phone.setError("Phone number must contain 10 digits");

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
