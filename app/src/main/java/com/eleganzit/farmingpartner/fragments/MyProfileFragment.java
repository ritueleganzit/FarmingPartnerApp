package com.eleganzit.farmingpartner.fragments;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.eleganzit.farmingpartner.EditProfileActivity;
import com.eleganzit.farmingpartner.NavHomeActivity;
import com.eleganzit.farmingpartner.R;
import com.eleganzit.farmingpartner.api.RetrofitAPI;
import com.eleganzit.farmingpartner.api.RetrofitInterface;
import com.eleganzit.farmingpartner.model.UpdateResponse;
import com.eleganzit.farmingpartner.model.UserDetailsResponse;
import com.eleganzit.farmingpartner.utils.UserSessionManager;

import java.io.File;
import java.util.ArrayList;

import me.nereo.multi_image_selector.MultiImageSelector;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.eleganzit.farmingpartner.NavHomeActivity.user_name;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyProfileFragment extends Fragment {

    TextView btn_edit_profile,txt_username,txt_email,txt_phone,txt_dob,txt_address,txt_landmark,txt_sublocation,txt_region;
    ImageView profile_pic;
    private static final int REQUEST_IMAGE2 = 201;
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION2 = 202;
    private ArrayList<String> mSelectPath2;
    private String photo;
    UserSessionManager userSessionManager;
    ProgressDialog progressDialog;
    String mediapath;

    public MyProfileFragment() {
        // Required empty public constructor
    }
    Activity mActivity;
    File file;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = getActivity();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_my_profile, container, false);
        userSessionManager=new UserSessionManager(getActivity());

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        NavHomeActivity.home_title.setText("My Profile");

        btn_edit_profile=v.findViewById(R.id.btn_edit_profile);
        profile_pic=v.findViewById(R.id.profile_pic);
        //txt_username=v.findViewById(R.id.txt_username);
        txt_email=v.findViewById(R.id.txt_email);
        txt_region=v.findViewById(R.id.txt_region);
       // txt_phone=v.findViewById(R.id.txt_phone);
       // txt_dob=v.findViewById(R.id.txt_dob);
        txt_address=v.findViewById(R.id.txt_address);
       // txt_landmark=v.findViewById(R.id.txt_landmark);
       // txt_sublocation=v.findViewById(R.id.txt_sublocation);

        profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EditProfileActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        getCustomer();
    }

    @Override
    public void onResume() {
        super.onResume();

        photo=userSessionManager.getUserDetails().get(UserSessionManager.KEY_PHOTO);

        txt_username.setText(userSessionManager.getUserDetails().get(UserSessionManager.KEY_FNAME).trim()+" "+userSessionManager.getUserDetails().get(UserSessionManager.KEY_LNAME).trim());
        user_name.setText(userSessionManager.getUserDetails().get(UserSessionManager.KEY_FNAME)+" "+userSessionManager.getUserDetails().get(UserSessionManager.KEY_LNAME));
        txt_email.setText(userSessionManager.getUserDetails().get(UserSessionManager.KEY_EMAIL));
        txt_phone.setText(userSessionManager.getUserDetails().get(UserSessionManager.KEY_PHONE));
        if(userSessionManager.getUserDetails().get(UserSessionManager.KEY_DOB).equalsIgnoreCase("0000-00-00"))
        {
            txt_dob.setText("Not Provided");
        }
        else
        {
            txt_dob.setText(userSessionManager.getUserDetails().get(UserSessionManager.KEY_DOB));
        }

        if(userSessionManager.getUserDetails().get(UserSessionManager.KEY_ADDRESS).equalsIgnoreCase(""))
        {
            txt_address.setText("Not Provided");
        }
        else {
            txt_address.setText(userSessionManager.getUserDetails().get(UserSessionManager.KEY_ADDRESS));
        }
        if(userSessionManager.getUserDetails().get(UserSessionManager.KEY_LANDMARK).equalsIgnoreCase(""))
        {
            txt_landmark.setText("Not Provided");
        }
        else {
            txt_landmark.setText(userSessionManager.getUserDetails().get(UserSessionManager.KEY_LANDMARK));
        }
        if(userSessionManager.getUserDetails().get(UserSessionManager.KEY_SUB_LOCATION).equalsIgnoreCase(""))
        {
            txt_sublocation.setText("Not Provided");
        }
        else {
            txt_sublocation.setText(userSessionManager.getUserDetails().get(UserSessionManager.KEY_SUB_LOCATION));
        } if(userSessionManager.getUserDetails().get(UserSessionManager.KEY_REGION).equalsIgnoreCase(""))
        {
            txt_region.setText("Not Provided");
        }
        else {
            txt_region.setText(userSessionManager.getUserDetails().get(UserSessionManager.KEY_REGION));
        }
        Glide
                .with(this)
                .load(photo).apply(new RequestOptions().placeholder(R.drawable.pr))
                .into(profile_pic);


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



    private void getCustomer() {

        Log.d("hjhh",userSessionManager.getUserDetails().get(UserSessionManager.KEY_USER_ID));;

        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);

        Call<UserDetailsResponse> call = myInterface.getCustomer(userSessionManager.getUserDetails().get(UserSessionManager.KEY_USER_ID));
        call.enqueue(new Callback<UserDetailsResponse>() {
            @Override
            public void onResponse(Call<UserDetailsResponse> call, Response<UserDetailsResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {
                            String house_no, id, fname,lname, photo,phone,dob,address,landmark,sub_location,region;
                            for (int i = 0; i < response.body().getData().size(); i++) {

                                fname = response.body().getData().get(i).getFname()+" ";
                                lname = response.body().getData().get(i).getLname()+" ";
                                photo = response.body().getData().get(i).getPhoto();
                                dob = response.body().getData().get(i).getDob();
                                address = response.body().getData().get(i).getAddress();
                                region= response.body().getData().get(i).getRegion();
                                house_no= response.body().getData().get(i).getHouse_no();
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
                                }if(region==null)
                                {
                                    region="";
                                }
                                txt_region.setText(""+response.body().getData().get(i).getRegion());
                                phone = response.body().getData().get(i).getPhone();

                                userSessionManager.updateUserData(""+response.body().getData().get(i).getRegion(),userSessionManager.getUserDetails().get(UserSessionManager.KEY_PASSWORD), fname,lname,phone, dob,address,landmark,sub_location,photo,house_no);
                                photo=userSessionManager.getUserDetails().get(UserSessionManager.KEY_PHOTO);

                                txt_username.setText(userSessionManager.getUserDetails().get(UserSessionManager.KEY_FNAME).trim()+" "+userSessionManager.getUserDetails().get(UserSessionManager.KEY_LNAME).trim());
                                user_name.setText(userSessionManager.getUserDetails().get(UserSessionManager.KEY_FNAME)+" "+userSessionManager.getUserDetails().get(UserSessionManager.KEY_LNAME));
                                txt_email.setText(userSessionManager.getUserDetails().get(UserSessionManager.KEY_EMAIL));
                                txt_phone.setText(userSessionManager.getUserDetails().get(UserSessionManager.KEY_PHONE));
                                if(userSessionManager.getUserDetails().get(UserSessionManager.KEY_DOB).equalsIgnoreCase("0000-00-00"))
                                {
                                    txt_dob.setText("Not Provided");
                                }
                                else
                                {
                                    txt_dob.setText(userSessionManager.getUserDetails().get(UserSessionManager.KEY_DOB));
                                }

                                if(userSessionManager.getUserDetails().get(UserSessionManager.KEY_ADDRESS).equalsIgnoreCase(""))
                                {
                                    txt_address.setText("Not Provided");
                                }
                                else {
                                    txt_address.setText(userSessionManager.getUserDetails().get(UserSessionManager.KEY_ADDRESS));
                                }
                                if(userSessionManager.getUserDetails().get(UserSessionManager.KEY_LANDMARK).equalsIgnoreCase(""))
                                {
                                    txt_landmark.setText("Not Provided");
                                }
                                else {
                                    txt_landmark.setText(userSessionManager.getUserDetails().get(UserSessionManager.KEY_LANDMARK));
                                }
                                if(userSessionManager.getUserDetails().get(UserSessionManager.KEY_SUB_LOCATION).equalsIgnoreCase(""))
                                {
                                    txt_sublocation.setText("Not Provided");
                                }
                                else {
                                    txt_sublocation.setText(userSessionManager.getUserDetails().get(UserSessionManager.KEY_SUB_LOCATION));
                                }
                                Glide
                                        .with(getActivity())
                                        .load(photo).apply(new RequestOptions().placeholder(R.drawable.pr))
                                        .into(profile_pic);

                                Glide
                                        .with(getActivity())
                                        .load(photo)
                                        .apply(new RequestOptions().placeholder(R.drawable.pr).transforms(new CircleCrop())).into(NavHomeActivity.profile_image);

                            }
                            //Toast.makeText(getActivity(), "--" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    } else {

                        Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }


                }
                else
                {
                    Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<UserDetailsResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void updatePhoto() {
        Log.d("responseseeeepppp", "" + mediapath+" "+userSessionManager.getUserDetails().get(UserSessionManager.KEY_USER_ID));
        progressDialog.show();

        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), "" + userSessionManager.getUserDetails().get(UserSessionManager.KEY_USER_ID));

        RequestBody requestFile = RequestBody.create(MediaType.parse("*/*"), file);

        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("photo", file.getName(), requestFile);

        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
     /*   Call<UpdateResponse> call = myInterface.updatePhoto(user_id, multipartBody);
        call.enqueue(new Callback<UpdateResponse>() {
            @Override
            public void onResponse(Call<UpdateResponse> call, final Response<UpdateResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    Log.d("responseseeee", "--" + response.body().toString());

                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {

                        if(photo!=null)
                        {
                            photo = response.body().getData().get(0).getPhoto();

                            Log.d("responseseeeepppp", "" + photo);

                            if(mActivity!=null)
                                Glide
                                        .with(getActivity())
                                        .load(photo)
                                        .apply(new RequestOptions().placeholder(R.drawable.pr).transforms(new CircleCrop())).into(profile_pic);

                            Glide
                                    .with(getActivity())
                                    .load(photo)

                                    .apply(new RequestOptions().placeholder(R.drawable.pr).dontTransform().diskCacheStrategy(DiskCacheStrategy.ALL).transforms(new CircleCrop())).into(NavHomeActivity.profile_image);
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
            public void onFailure(Call<UpdateResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });*/
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
                    file=new File(mediapath);
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


}
