package com.eleganzit.farmingpartner.fragment;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.eleganzit.farmingpartner.NavHomeActivity;
import com.eleganzit.farmingpartner.R;
import com.eleganzit.farmingpartner.activity.EditProfileActivity;
import com.eleganzit.farmingpartner.activity.HomeActivity;
import com.eleganzit.farmingpartner.api.RetrofitAPI;
import com.eleganzit.farmingpartner.api.RetrofitInterface;
import com.eleganzit.farmingpartner.model.FarmingPartnerChangePasswordResponse;
import com.eleganzit.farmingpartner.model.GetFarmingPartnerDetailResponse;
import com.eleganzit.farmingpartner.model.UpdatePhotoResponse;
import com.eleganzit.farmingpartner.model.UpdateResponse;
import com.eleganzit.farmingpartner.utils.UserSessionManager;

import java.io.File;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import me.nereo.multi_image_selector.MultiImageSelector;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyProfileFragment extends Fragment {

    TextView btn_edit_profile, txt_edit;
    private boolean allowRefresh;

    public MyProfileFragment() {
        // Required empty public constructor
    }

    private ArrayList<String> mSelectPath2;

    CircleImageView profile_pic;
    String mediapath;
    File file;
    Activity mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = getActivity();

    }

    TextView farming_partner_name,email,phone,address,landmark,sub_location,dob;

    private String photo;
    UserSessionManager userSessionManager;
    ProgressDialog progressDialog;

    private static final int REQUEST_IMAGE2 = 201;
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION2 = 202;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_edit_profile = view.findViewById(R.id.btn_edit_profile);
        txt_edit = view.findViewById(R.id.txt_edit);
        profile_pic = view.findViewById(R.id.profile_pic);
        farming_partner_name = view.findViewById(R.id.farming_partner_name);
        email = view.findViewById(R.id.email);
        address = view.findViewById(R.id.address);
        landmark = view.findViewById(R.id.landmark);
        phone = view.findViewById(R.id.phone);
        sub_location = view.findViewById(R.id.sub_location);
        dob = view.findViewById(R.id.dob);

        userSessionManager = new UserSessionManager(getActivity());
        btn_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EditProfileActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        txt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.change_password_dialog);

                TextView btn_ok = dialog.findViewById(R.id.btn_ok);
                final EditText ed_crntpassword = dialog.findViewById(R.id.ed_crntpassword);
                final EditText ed_newpassword = dialog.findViewById(R.id.ed_newpassword);
                final EditText ed_cpassword = dialog.findViewById(R.id.ed_cpassword);

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

                        } else {
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
        });
        HomeActivity.home_title.setText("My Profile");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("Fragmentt","Refreshed");
        getFarmData();

          }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }


    private void changePassword(final Dialog dialog, String s, String current, String newpassword) {
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<FarmingPartnerChangePasswordResponse> call=myInterface.farmingPartnerChangePassword(s,current,newpassword);
        call.enqueue(new Callback<FarmingPartnerChangePasswordResponse>() {
            @Override
            public void onResponse(Call<FarmingPartnerChangePasswordResponse> call, Response<FarmingPartnerChangePasswordResponse> response) {

                progressDialog.dismiss();
                dialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        Toast.makeText(getActivity(), "Password has been changed successfully", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Current password is wrong", Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<FarmingPartnerChangePasswordResponse> call, Throwable t) {

            }
        });



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

            if (response.body().getData().get(0).getFarmingPartnerName()!=null && !response.body().getData().get(0).getFarmingPartnerName().isEmpty())
            {

                String upperString = response.body().getData().get(0).getFarmingPartnerName().substring(0,1).toUpperCase() + response.body().getData().get(0).getFarmingPartnerName().substring(1);

                farming_partner_name.setText(""+upperString);
            }


            if (response.body().getData().get(0).getPhone()!=null && !response.body().getData().get(0).getPhone().isEmpty())
            {

                String upperString = response.body().getData().get(0).getPhone().substring(0,1).toUpperCase() + response.body().getData().get(0).getPhone().substring(1);

                phone.setText(""+upperString);
            }

  if (response.body().getData().get(0).getDob()!=null && !response.body().getData().get(0).getDob().isEmpty())
            {


                dob.setText(""+response.body().getData().get(0).getDob());
            }


            if (response.body().getData().get(0).getAddress()!=null && !response.body().getData().get(0).getAddress().isEmpty())
            {

                String upperString = response.body().getData().get(0).getAddress().substring(0,1).toUpperCase() + response.body().getData().get(0).getAddress().substring(1);

                address.setText(""+upperString);
            }

            if (response.body().getData().get(0).getLandmark()!=null && !response.body().getData().get(0).getLandmark().isEmpty())
            {

                String upperString = response.body().getData().get(0).getLandmark().substring(0,1).toUpperCase() + response.body().getData().get(0).getLandmark().substring(1);

                landmark.setText(""+upperString);
            }

            if (response.body().getData().get(0).getSubLocation()!=null && !response.body().getData().get(0).getSubLocation().isEmpty())
            {

                String upperString = response.body().getData().get(0).getSubLocation().substring(0,1).toUpperCase() + response.body().getData().get(0).getSubLocation().substring(1);

                sub_location.setText(""+upperString);
            }

            if (response.body().getData().get(0).getEmail()!=null && !response.body().getData().get(0).getEmail().isEmpty())
            {
                email.setText(""+response.body().getData().get(0).getEmail());

            }


            photo = response.body().getData().get(0).getPhoto();

            if(photo!=null && !photo.isEmpty()) {

                Log.d("responseseeeepppp", "" + photo);

                if (mActivity != null)
                    Glide
                            .with(getActivity())
                            .load(photo)
                            .apply(new RequestOptions().placeholder(R.drawable.pr).transforms(new CircleCrop())).into(profile_pic);

                userSessionManager.updateProfilePic(photo);
            }


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


        private void pickImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.mis_permission_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION2);
        } else {

            MultiImageSelector selector = MultiImageSelector.create(getActivity());
            selector.single();
            selector.showCamera(false);

            selector.origin(mSelectPath2);
            selector.start(MyProfileFragment.this, REQUEST_IMAGE2);
        }
    }
    private void requestPermission(final String permission, String rationale, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
            new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.mis_permission_dialog_title)
                    .setMessage(rationale)
                    .setPositiveButton(R.string.mis_permission_dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
                        }
                    })
                    .setNegativeButton(R.string.mis_permission_dialog_cancel, null)
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
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
                    updatePhoto();
                   /* Glide
                            .with(getActivity())
                            .load(mediapath.trim())
                            .apply(new RequestOptions().transform(new CircleCrop()).placeholder(R.drawable.pr).centerCrop().circleCrop())
                            .into(profile_pic);

*/
                }
            }
            }
    }


    public void updatePhoto() {
        Log.d("responseseeeepppp", "" + mediapath+" "+userSessionManager.getUserDetails().get(UserSessionManager.KEY_USER_ID));
        progressDialog.show();

        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), "" + userSessionManager.getUserDetails().get(UserSessionManager.KEY_USER_ID));

        RequestBody requestFile = RequestBody.create(MediaType.parse("*/*"), file);

        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("photo", file.getName(), requestFile);

        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<UpdatePhotoResponse> call = myInterface.updatePhoto(user_id, multipartBody);
        call.enqueue(new Callback<UpdatePhotoResponse>() {
            @Override
            public void onResponse(Call<UpdatePhotoResponse> call, final Response<UpdatePhotoResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    Log.d("responseseeee", "--" + response.body().toString());

                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {

                        Log.d("responseseeeepppp", "" + response.body().getData().get(0).getFarmingPartnerId());
                        Log.d("responseseeeepppp", "" + response.body().getData().get(0).getPhoto());
                        photo = response.body().getData().get(0).getPhoto();

                        if(photo!=null)
                        {

                            Log.d("responseseeeepppp", "" + photo);

                            if(mActivity!=null)
                                Glide
                                        .with(getActivity())
                                        .load(photo)
                                        .apply(new RequestOptions().placeholder(R.drawable.pr).transforms(new CircleCrop())).into(profile_pic);

                                userSessionManager.updateProfilePic(photo);
                            Toast.makeText(getActivity(), "Profile picture updated", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Please try again", Toast.LENGTH_SHORT).show();
                        }

                    } else {

                        Toast.makeText(getActivity(), "" + response.body().getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UpdatePhotoResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}
