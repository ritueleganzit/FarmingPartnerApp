package com.eleganzit.farmingpartner.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.eleganzit.farmingpartner.LoginActivity;
import com.eleganzit.farmingpartner.R;
import com.eleganzit.farmingpartner.api.RetrofitAPI;
import com.eleganzit.farmingpartner.api.RetrofitInterface;
import com.eleganzit.farmingpartner.model.LoginRespose;
import com.eleganzit.farmingpartner.utils.UserSessionManager;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {
    EditText ed_email,ed_password;
    ProgressDialog progressDialog;
    UserSessionManager userSessionManager;
    TextView txt_forgot,txt_signup;
    Button btn_login;
    private String Token,device_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ed_email=findViewById(R.id.ed_email);
        ed_password=findViewById(R.id.ed_password);
        txt_forgot=findViewById(R.id.txt_forgot);
        btn_login=findViewById(R.id.btn_login);
        userSessionManager=new UserSessionManager(this);

        progressDialog = new ProgressDialog(SignInActivity.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);


        txt_signup=findViewById(R.id.txt_signup);
        txt_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, ForgotPasswordActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        }); txt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
               overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid())
                {

                    if (Token!=null) {
                        loginUser();
                    }
                    else
                    {
                        Thread t=new Thread(new Runnable() {
                            @Override
                            public void run() {
                                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( SignInActivity.this,  new OnSuccessListener<InstanceIdResult>() {
                                    @Override
                                    public void onSuccess(InstanceIdResult instanceIdResult) {
                                        Token = instanceIdResult.getToken();
                                        device_token=Token;
                                        Log.e("get token",Token);
                                    }
                                });
                                if (Token!=null)
                                {
                                    Log.d("mytokenn", Token);

                                    device_token=Token;
                                    StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().build();
                                    StrictMode.setThreadPolicy(threadPolicy);
                                    try {
                                        JSONObject jsonObject=new JSONObject(Token);
                                        Log.d("mytoken", jsonObject.getString("token"));
                                        //devicetoken=jsonObject.getString("token");

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    //getLoginBoth(Token);

                                }
                                else
                                {

                                }
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        });t.start();
                        loginUser();
                    }


                }

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermission();
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( SignInActivity.this,  new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        Token = instanceIdResult.getToken();
                        device_token=Token;
                        Log.e("get token",Token);
                    }
                });
                if (Token!=null)
                {
                    Log.d("mytokenn", Token);

                    device_token=Token;
                    StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().build();
                    StrictMode.setThreadPolicy(threadPolicy);
                    try {
                        JSONObject jsonObject=new JSONObject(Token);
                        Log.d("mytoken", jsonObject.getString("token"));
                        //devicetoken=jsonObject.getString("token");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //getLoginBoth(Token);

                }
                else
                {

                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });t.start();
    }

    private void loginUser() {
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<LoginRespose> call = myInterface.farmingPartnerLogin(ed_email.getText().toString(), ed_password.getText().toString(),"android",""+Token);
call.enqueue(new Callback<LoginRespose>() {
    @Override
    public void onResponse(Call<LoginRespose> call, Response<LoginRespose> response) {

        progressDialog.dismiss();
         if(response.isSuccessful()) {
            if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                if (response.body().getData() != null) {
                    String email="", id="", fname="",lname="", photo="",phone="",dob="",address="",landmark="",sub_location="";
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        email = response.body().getData().get(i).getEmail();
                        id = response.body().getData().get(i).getFarming_partner_id();
                        fname = response.body().getData().get(i).getFarming_partner_name()+" ";
                        lname = response.body().getData().get(i).getLast_name()+" ";
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
                        sub_location = response.body().getData().get(i).getSub_location();
                        if(sub_location==null)
                        {
                            sub_location="";
                        }
                        phone = response.body().getData().get(i).getPhone();
                        userSessionManager.createLoginSession(""+response.body().getData().get(i).getAddress(),id,""+""+response.body().getData().get(i).getFarm_id(), email, ed_password.getText().toString(), fname,lname,phone, dob,address,landmark,sub_location,photo,"");

                    }
                    //Toast.makeText(LoginActivity.this, "--" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }
            } else {

                Toast.makeText(SignInActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }


        }
    }

    @Override
    public void onFailure(Call<LoginRespose> call, Throwable t) {
        progressDialog.dismiss();
        Toast.makeText(SignInActivity.this, "Server or Internet Error", Toast.LENGTH_SHORT).show();
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


    public void checkPermission()
    {


        Dexter.withActivity(SignInActivity.this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,

                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Log.d("prr",""+report.areAllPermissionsGranted());
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                        if (report.getDeniedPermissionResponses().size()>0)
                        {
                            // Toast.makeText(MobileRegisterationActivity.this, ""+report.getDeniedPermissionResponses().get(0).getPermissionName(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                    }


                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(SignInActivity.this, "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    private void showSettingsDialog() {
        android.support.v7.app.AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }
}
