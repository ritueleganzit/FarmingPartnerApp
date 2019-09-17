package com.eleganzit.farmingpartner.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.eleganzit.farmingpartner.MainActivity;
import com.eleganzit.farmingpartner.R;
import com.eleganzit.farmingpartner.RegistrationActivity;
import com.eleganzit.farmingpartner.RegistrationAddress;
import com.eleganzit.farmingpartner.api.RetrofitAPI;
import com.eleganzit.farmingpartner.api.RetrofitInterface;
import com.eleganzit.farmingpartner.fragment.MyProfileFragment;
import com.eleganzit.farmingpartner.model.ValidateFarmingpartner;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import me.nereo.multi_image_selector.MultiImageSelector;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignUpFarmDetails extends AppCompatActivity {
    String password,farming_partner_name,last_name,address,landmark,sub_location,email,phone,dob;
EditText farm_name,edfarming_partner_name,ed_email,ed_location,ed_address,ed_capacity,ed_description;
    Button btn_signupnext;
    LinearLayout farm_imagelin;
    ImageView farm_image;
    private ArrayList<String> mSelectPath2;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;
    CircleImageView profile_pic;
    String mediapath;
    File file;
    private static final int REQUEST_IMAGE2 = 201;
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION2 = 202;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_farm_details);
        btn_signupnext=findViewById(R.id.btn_signupnext);
        farm_name=findViewById(R.id.farm_name);
        ed_address=findViewById(R.id.ed_address);
        farm_image=findViewById(R.id.farm_image);
        farm_imagelin=findViewById(R.id.farm_imagelin);
        ed_description=findViewById(R.id.ed_description);
        ed_capacity=findViewById(R.id.ed_capacity);
        edfarming_partner_name=findViewById(R.id.farming_partner_name);
        ed_email=findViewById(R.id.ed_email);
        ed_location=findViewById(R.id.ed_location);

        sharedPreferences=getSharedPreferences("regis",MODE_PRIVATE);
        sharedPreferencesEditor=sharedPreferences.edit();


        progressDialog = new ProgressDialog(SignUpFarmDetails.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);





        ed_location.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
       // Toast.makeText(SignUpFarmDetails.this, ""+mediapath, Toast.LENGTH_SHORT).show();

        sharedPreferencesEditor.putString("farm_address",""+ed_address.getText().toString());
        sharedPreferencesEditor.putString("farm_name",""+farm_name.getText().toString());
        sharedPreferencesEditor.putString("farm_location",""+ed_location.getText().toString());
        sharedPreferencesEditor.putString("farm_description",""+ed_description.getText().toString());
        sharedPreferencesEditor.putString("plot_capacity",""+ed_capacity.getText().toString());
        sharedPreferencesEditor.commit();
        startActivity(new Intent(SignUpFarmDetails.this, RegistrationAddress.class));
        finish();

    }
});
        btn_signupnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid())
                {
                    if (sharedPreferences.getString("photo","")!=null  && !(sharedPreferences.getString("photo","").isEmpty()))                    {
                        Log.d("sadasd",""+ed_email.getText().toString());
                        validateFarmingpartner();          }

                    else
                    {
                        Toast.makeText(SignUpFarmDetails.this, "Please upload the photo of your farm", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

        farm_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedPreferencesEditor.putString("farm_address",""+ed_address.getText().toString());
                sharedPreferencesEditor.putString("farm_name",""+farm_name.getText().toString());
                sharedPreferencesEditor.putString("farm_location",""+ed_location.getText().toString());
                sharedPreferencesEditor.putString("farm_description",""+ed_description.getText().toString());
                sharedPreferencesEditor.putString("plot_capacity",""+ed_capacity.getText().toString());
                sharedPreferencesEditor.commit();
pickImage();
            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


public void validateFarmingpartner()
{  progressDialog.show();
    RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
    Call<ValidateFarmingpartner> call=myInterface.validateFarmingpartner(ed_email.getText().toString(),farm_name.getText().toString());
    call.enqueue(new Callback<ValidateFarmingpartner>() {
        @Override
        public void onResponse(Call<ValidateFarmingpartner> call, Response<ValidateFarmingpartner> response) {
            progressDialog.dismiss();
            if (response.isSuccessful())
            {

                if(response.body().getStatus().toString().equalsIgnoreCase("1"))
                {
                    sharedPreferencesEditor.putString("farm_address",""+ed_address.getText().toString());
                    sharedPreferencesEditor.putString("farm_name",""+farm_name.getText().toString());
                    sharedPreferencesEditor.putString("farm_location",""+ed_location.getText().toString());
                    sharedPreferencesEditor.putString("farm_description",""+ed_description.getText().toString());
                    sharedPreferencesEditor.putString("plot_capacity",""+ed_capacity.getText().toString());
                    sharedPreferencesEditor.putString("email",""+ed_email.getText().toString());
                    sharedPreferencesEditor.commit();

                    startActivity(new Intent(SignUpFarmDetails.this, MainActivity.class));
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

                }
                else
                {
                    Toast.makeText(SignUpFarmDetails.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        }

        @Override
        public void onFailure(Call<ValidateFarmingpartner> call, Throwable t) {
            progressDialog.dismiss();
        }
    });

}
    public boolean isValid() {
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(ed_email.getText().toString());

        if (edfarming_partner_name.getText().toString().trim().equals("")) {

            edfarming_partner_name.setError("Farming Partner name is mandatory");

            edfarming_partner_name.requestFocus();

            return false;
        } else if (farm_name.getText().toString().trim().equals("")) {

            farm_name.setError("Farm name is mandatory");

            farm_name.requestFocus();

            return false;
        } else if (ed_location.getText().toString().trim().equals("")) {


            Toast.makeText(this, "Select Farm Location", Toast.LENGTH_SHORT).show();


            return false;
        }   else if (ed_location.getText().toString().trim().equals("")) {

            ed_location.setError("Location selection is mandatory");

            ed_location.requestFocus();

            return false;
        } else if (ed_address.getText().toString().trim().equals("")) {

            ed_address.setError("Address is mandatory");

            ed_address.requestFocus();

            return false;
        }
        else if (ed_capacity.getText().toString().trim().equals("")) {

            ed_capacity.setError("Plot capacity is mandatory");

            ed_capacity.requestFocus();

            return false;
        }

        else if (ed_description.getText().toString().trim().equals("")) {

            ed_description.setError("Farm Description is mandatory");

            ed_description.requestFocus();

            return false;
        }   else if (!matcher.matches()) {
            ed_email.setError("Please enter a Valid e-mail id");

            ed_email.requestFocus();
            return false;
        }


        return true;
    }
    private void pickImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
                && ActivityCompat.checkSelfPermission(SignUpFarmDetails.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.mis_permission_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION2);
        } else {

            MultiImageSelector selector = MultiImageSelector.create(SignUpFarmDetails.this);
            selector.single();
            selector.showCamera(false);

            selector.origin(mSelectPath2);
            selector.start(SignUpFarmDetails.this, REQUEST_IMAGE2);
        }
    }
    private void requestPermission(final String permission, String rationale, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(SignUpFarmDetails.this, permission)) {
            new AlertDialog.Builder(SignUpFarmDetails.this)
                    .setTitle(R.string.mis_permission_dialog_title)
                    .setMessage(rationale)
                    .setPositiveButton(R.string.mis_permission_dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(SignUpFarmDetails.this, new String[]{permission}, requestCode);
                        }
                    })
                    .setNegativeButton(R.string.mis_permission_dialog_cancel, null)
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(SignUpFarmDetails.this, new String[]{permission}, requestCode);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == REQUEST_IMAGE2) {
                if (resultCode == Activity.RESULT_OK) {
                    mSelectPath2 = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                    StringBuilder sb = new StringBuilder();
                    for (String p : mSelectPath2) {
                        sb.append(p);
                        sb.append("\n");
                    }

                    mediapath = "" + sb.toString().trim();
                    file = new File(mediapath);

                    Glide
                            .with(SignUpFarmDetails.this)
                            .load(mediapath.trim())
                            .apply(new RequestOptions().transform(new CircleCrop()).centerCrop().circleCrop())
                            .into(farm_image);

                    sharedPreferencesEditor.putString("photo",""+mediapath.trim());
                    sharedPreferencesEditor.commit();

                    //updatePhoto();
                   /* Glide
                            .with(SignUpFarmDetails.this)
                            .load(mediapath.trim())
                            .apply(new RequestOptions().transform(new CircleCrop()).placeholder(R.drawable.pr).centerCrop().circleCrop())
                            .into(profile_pic);

*/
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SignUpFarmDetails.this, SignUpActivity.class)


        );
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String param = getIntent().getStringExtra("add");
        String lat = getIntent().getStringExtra("lat");
        String lng= getIntent().getStringExtra("lng");
        farm_name.setText(""+sharedPreferences.getString("farm_name",""));
        ed_address.setText(""+sharedPreferences.getString("farm_address",""));
        ed_location.setText(""+sharedPreferences.getString("farm_location",""));
        if (param!=null && !(param.isEmpty()))
        {
            ed_location.setText(""+param);

        }if (lat!=null && !(lat.isEmpty()))
        {
            sharedPreferencesEditor.putString("lat",""+lat);
            sharedPreferencesEditor.putString("lng",""+lng);
            sharedPreferencesEditor.commit();


        }
        ed_description.setText(""+sharedPreferences.getString("farm_description",""));
        ed_capacity.setText(""+sharedPreferences.getString("plot_capacity",""));
        edfarming_partner_name.setText(""+sharedPreferences.getString("farming_partner_name",""));
        ed_email.setText(""+sharedPreferences.getString("email",""));
        if (sharedPreferences.getString("photo","")!=null  && !(sharedPreferences.getString("photo","").isEmpty()))
        {
            Glide
                    .with(SignUpFarmDetails.this)
                    .load(sharedPreferences.getString("photo",""))
                    .apply(new RequestOptions().transform(new CircleCrop()).centerCrop().circleCrop())
                    .into(farm_image);
        }

    }
}
