package com.eleganzit.farmingpartner;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.eleganzit.farmingpartner.activity.HomeActivity;
import com.eleganzit.farmingpartner.activity.SignInActivity;
import com.eleganzit.farmingpartner.api.RetrofitAPI;
import com.eleganzit.farmingpartner.api.RetrofitInterface;
import com.eleganzit.farmingpartner.databinding.MainActivityBinding;
import com.eleganzit.farmingpartner.model.AvailablePlotsData;
import com.eleganzit.farmingpartner.model.GetAllVegetables;
import com.eleganzit.farmingpartner.model.GetAllVegetablesResponse;
import com.eleganzit.farmingpartner.model.LoginRespose;
import com.eleganzit.farmingpartner.model.RegistrationResponse;
import com.eleganzit.farmingpartner.model.SubmitPlotResponse;
import com.eleganzit.farmingpartner.model.UpdateResponse;
import com.eleganzit.farmingpartner.model.VegetablesResponse;
import com.eleganzit.farmingpartner.utils.ClickListener;
import com.eleganzit.farmingpartner.utils.RecyclerTouchListener;
import com.eleganzit.farmingpartner.utils.UserSessionManager;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements View.OnDragListener {
    SharedPreferences sharedPreferences;
    String password,farming_partner_name,last_name,address,landmark,sub_location,email,phone,dob,calender,device_id,device_token,farm_name,farm_description,farm_location,plot_capacity,vegetable_list,farm_lat,farm_long,photo;

    private MainActivityBinding mainActivityBinding;
    public ObservableArrayList<GetAllVegetables> exerciseList ;
    public ObservableArrayList<GetAllVegetables> exerciseSelectedList = new ObservableArrayList<>();
    public GetAllVegetables exerciseToMove;
    private int newContactPosition = -1;
    ProgressDialog progressDialog;
    UserSessionManager userSessionManager;
    private int currentPosition = -1;
    private boolean isExerciseAdded = false;
    public static boolean isFromExercise = false;
    private String farm_id,  farm_desc,farm_address;
    private String Token;
    File file;
    SharedPreferences.Editor sharedPreferencesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        sharedPreferences=getSharedPreferences("regis",MODE_PRIVATE);
        sharedPreferencesEditor=sharedPreferences.edit();

        userSessionManager=new UserSessionManager(this);



        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        password=""+sharedPreferences.getString("password","");
        farming_partner_name=""+sharedPreferences.getString("farming_partner_name","");
        last_name=""+sharedPreferences.getString("last_name","");
        address=""+sharedPreferences.getString("address","");
        landmark=""+sharedPreferences.getString("landmark","");
        sub_location=""+sharedPreferences.getString("sub_location","");
        plot_capacity=""+sharedPreferences.getString("plot_capacity","");
        email=""+sharedPreferences.getString("email","");
        phone=""+sharedPreferences.getString("phone","");
        dob=""+sharedPreferences.getString("dob","");
        device_id=""+sharedPreferences.getString("device_id","");
        device_token=""+sharedPreferences.getString("device_token","");
        farm_address=""+sharedPreferences.getString("farm_address","");
        farm_name=""+sharedPreferences.getString("farm_name","");
        farm_location=""+sharedPreferences.getString("farm_location","");
        farm_description=""+sharedPreferences.getString("farm_description","");
        farm_lat=""+sharedPreferences.getString("lat","");
        farm_long=""+sharedPreferences.getString("lng","");
        photo=""+sharedPreferences.getString("photo","");
        loadExerciseData();
        file = new File(photo);


        calender= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Log.d("MainActivityData",""+farming_partner_name);
        Log.d("MainActivityData",""+email);
        Log.d("MainActivityData",""+phone);
        Log.d("MainActivityData",""+calender);
        Log.d("MainActivityData",""+last_name);
        Log.d("MainActivityData",""+address);
        Log.d("MainActivityData",""+landmark);
        Log.d("MainActivityData",""+sub_location);
        Log.d("MainActivityData",""+dob);
        Log.d("MainActivityData",""+password);
        Log.d("MainActivityData",""+device_id);
        Log.d("MainActivityData",""+device_token);
        Log.d("MainActivityData",""+farm_name);
        Log.d("MainActivityData",""+farm_description);
        Log.d("MainActivityData",""+farm_address);
        Log.d("MainActivityData",""+farm_location);
        Log.d("MainActivityData",""+plot_capacity);
        Log.d("MainActivityData",""+farm_lat);
        Log.d("MainActivityData",""+farm_long);
        Log.d("MainActivityData",""+photo);

       // getVegetables();

        mainActivityBinding.setMainActivity(this);
        mainActivityBinding.rcvSelectedExercise.setOnDragListener(this);

        mainActivityBinding.rcvChooseExercise.setOnDragListener(new MyDragInsideRcvListener());
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.scale_3dp);
        mainActivityBinding.rcvSelectedExercise.addItemDecoration(new SpaceItemDecoration(spacingInPixels));

        mainActivityBinding.rcvChooseExercise.addOnItemTouchListener(new RecyclerTouchListener(this, mainActivityBinding.rcvChooseExercise, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                GetAllVegetables excercisePojo=((ExcerciseListAdapter)mainActivityBinding.rcvChooseExercise.getAdapter()).getItem(position);
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.veg_details_dialog);

                TextView txt_sapling_date,txt_deweeding1,txt_deweeding2,txt_deweeding3,txt_fertilising1,txt_fertilising2,txt_fertilising3,txt_harvesting;

                txt_sapling_date=dialog.findViewById(R.id.txt_sapling_date);
                txt_deweeding1=dialog.findViewById(R.id.txt_deweeding1);
                txt_deweeding2=dialog.findViewById(R.id.txt_deweeding2);
                txt_deweeding3=dialog.findViewById(R.id.txt_deweeding3);
                txt_fertilising1=dialog.findViewById(R.id.txt_fertilising1);
                txt_fertilising2=dialog.findViewById(R.id.txt_fertilising2);
                txt_fertilising3=dialog.findViewById(R.id.txt_fertilising3);
                txt_harvesting=dialog.findViewById(R.id.txt_harvesting);


                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes(lp);
                Window window = dialog.getWindow();
                window.setBackgroundDrawableResource(android.R.color.transparent);

              //  dialog.show();

            }

            @Override
            public void onLongClick(View view, int position) {
                //Toast.makeText(MainActivity.this, ""+((ExcerciseListAdapter)mainActivityBinding.rcvChooseExercise.getAdapter()).getItem(position).name, Toast.LENGTH_SHORT).show();
            }
        }));

        mainActivityBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mainActivityBinding.btnSignupnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              //  Toast.makeText(MainActivity.this, ""+exerciseSelectedList.size(), Toast.LENGTH_SHORT).show();

                final Dialog d=new Dialog(MainActivity.this,
                        R.style.Theme_Dialog);
                d.setContentView(R.layout.vegetablesubmit);

                TextView ok=d.findViewById(R.id.ok);
                TextView cancel=d.findViewById(R.id.cancel);
                TextView vegetablenum=d.findViewById(R.id.vegetablenum);
                vegetablenum.setText("You have selected "+ exerciseSelectedList.size()+" vegetables. Would you like to submit? ");

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                    }
                });
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        d.dismiss();

                        if (exerciseSelectedList.size()>0){
                            if (Token!=null) {
                                sendVegetables();
                            }
                            else
                            {
                                Thread t=new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( MainActivity.this,  new OnSuccessListener<InstanceIdResult>() {
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
                                sendVegetables();
                            }
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "No Vegetables Selected", Toast.LENGTH_SHORT).show();
                        }

                      /*  Intent i = new Intent(MainActivity.this, HomeActivity.class).putExtra("from","select_veg");
                        // Closing all the Activities

                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        // Staring Login Activity
                        startActivity(i);
                        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                        finish();*/





                    }
                });


                d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                if(!isFinishing())
                {
                    d.show();
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


    private void sendVegetables() {

        StringBuilder sb = new StringBuilder();
        for(int i=0;i<exerciseSelectedList.size();i++)
        {
            Log.d("productsssssssss",exerciseSelectedList.get(i).getVegName()+"");
            if (i==exerciseSelectedList.size()-1)
            {
                sb.append(exerciseSelectedList.get(i).getVegetableId()).append("");
            }
            else {
                sb.append(exerciseSelectedList.get(i).getVegetableId()).append(",");

            }
        }

        Log.d("uuuuuuuuuuu",sb.toString()+"  "+Token);



        progressDialog.show();

        RequestBody rfarming_partner_name = RequestBody.create(MediaType.parse("text/plain"), "" +farming_partner_name);
        RequestBody remail = RequestBody.create(MediaType.parse("text/plain"), "" +email);
        RequestBody rphone = RequestBody.create(MediaType.parse("text/plain"), "" +phone);
        RequestBody rcal= RequestBody.create(MediaType.parse("text/plain"), "" +calender);
        RequestBody rlastname = RequestBody.create(MediaType.parse("text/plain"), "" +last_name);
        RequestBody raddress = RequestBody.create(MediaType.parse("text/plain"), "" +address);
        RequestBody rlandmark = RequestBody.create(MediaType.parse("text/plain"), "" +landmark);
        RequestBody rsub_location = RequestBody.create(MediaType.parse("text/plain"), "" +sub_location);
        RequestBody rdob = RequestBody.create(MediaType.parse("text/plain"), "" +dob);
        RequestBody rpassword = RequestBody.create(MediaType.parse("text/plain"), "" +password);
        RequestBody randroid = RequestBody.create(MediaType.parse("text/plain"), "android");
        RequestBody rToken = RequestBody.create(MediaType.parse("text/plain"), ""+Token);
        RequestBody rfarm_name = RequestBody.create(MediaType.parse("text/plain"), ""+farm_name);
        RequestBody rfarm_description = RequestBody.create(MediaType.parse("text/plain"), ""+farm_description);
        RequestBody rfarm_address = RequestBody.create(MediaType.parse("text/plain"), ""+farm_address);
        RequestBody rfarm_location = RequestBody.create(MediaType.parse("text/plain"), ""+farm_location);
        RequestBody rplot_capacity = RequestBody.create(MediaType.parse("text/plain"), ""+plot_capacity);
        RequestBody rsb = RequestBody.create(MediaType.parse("text/plain"), ""+sb);
        RequestBody rfarm_lat = RequestBody.create(MediaType.parse("text/plain"), ""+farm_lat);
        RequestBody rfarm_long = RequestBody.create(MediaType.parse("text/plain"), ""+farm_long);

        RequestBody requestFile = RequestBody.create(MediaType.parse("*/*"), file);

        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("farm_photo", file.getName(), requestFile);

        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<RegistrationResponse> call = myInterface.farmingPartnerRegistration(rfarming_partner_name,remail,rphone,rcal,rlastname,raddress,rlandmark,rsub_location,rdob,rpassword,randroid,rToken,rfarm_name,rfarm_description,rfarm_address,rfarm_location,rplot_capacity,rsb,rfarm_lat,rfarm_long,multipartBody);
        call.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, final Response<RegistrationResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        Log.d("responseseeee", "--" + response.body().getData().get(0).getAddress());
                        Log.d("responseseeee", "--" + response.body().getData().get(0).getCalender());
                        Log.d("responseseeee", "--" + response.body().getData().get(0).getDob());

                        String email="", id="", fname="",lname="", photo="",phone="",dob="",address="",landmark="",sub_location="";
                        email = response.body().getData().get(0).getEmail();
                        id = ""+response.body().getData().get(0).getFarmingPartnerId();
                        fname = response.body().getData().get(0).getFarmingPartnerName()+" ";
                        lname = response.body().getData().get(0).getLastName()+" ";
                        if(photo==null)
                        {
                            photo="";
                        }
                        dob = response.body().getData().get(0).getDob();
                        if(dob==null)
                        {
                            dob="";
                        }
                        address = response.body().getData().get(0).getAddress();
                        if(address==null)
                        {
                            address="";
                        }
                        landmark = response.body().getData().get(0).getLandmark();
                        if(landmark==null)
                        {
                            landmark="";
                        }
                        sub_location = response.body().getData().get(0).getSubLocation();
                        if(sub_location==null)
                        {
                            sub_location="";
                        }
                        phone = response.body().getData().get(0).getPhone();
                        Toast.makeText(MainActivity.this, "Registered Successfully ", Toast.LENGTH_SHORT).show();
                        sharedPreferencesEditor.clear();
                        sharedPreferencesEditor.commit();

                        userSessionManager.createLoginSession(""+response.body().getData().get(0).getAddress(),id,""+""+response.body().getData().get(0).getFarm_id(), email, "", fname,lname,phone, dob,address,landmark,sub_location,photo,"");
/*
                        Intent i = new Intent(MainActivity.this, SignInActivity.class);
                        // Closing all the Activities

                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        // Staring Login Activity
                        startActivity(i);
                        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                        finish();*/

                       

                    } else {

                        Toast.makeText(MainActivity.this, "" + response.body().getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Server or Internet Error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Server or Internet Error", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });



    }



    @Override
    protected void onResume() {
        super.onResume();
        final Dialog d=new Dialog(MainActivity.this,
                R.style.Theme_Dialog);
        d.setContentView(R.layout.draganddrop);

        TextView ok=d.findViewById(R.id.ok);
         ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
d.dismiss();

            }
        });


        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if(!isFinishing())
        {
            d.show();
        }



        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( MainActivity.this,  new OnSuccessListener<InstanceIdResult>() {
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

    /*private void getVegetables() {
        progressDialog.show();

        exerciseList = new ObservableArrayList<>();

        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Log.d("iddddsdas", farm_id+"");
        Call<VegetablesResponse> call=myInterface.vegetablesList(farm_id);
        call.enqueue(new Callback<VegetablesResponse>() {
            @Override
            public void onResponse(Call<VegetablesResponse> call, Response<VegetablesResponse> response) {
                progressDialog.dismiss();

                if (response.isSuccessful())
                {

                    ArrayList<AvailablePlotsData> arrayList=new ArrayList<>();
                    if (response.body().getStatus().toString().equalsIgnoreCase("1"))
                    {
                        if (response.body().getData()!=null)
                        {

                            for (int i=0;i<response.body().getAdminVegList().size();i++)
                            {

                                ExcercisePojo excercisePojo=new ExcercisePojo(response.body().getAdminVegList().get(i).getVegetableId(), response.body().getAdminVegList().get(i).getVegName(), response.body().getAdminVegList().get(i).getVegImage(),response.body().getAdminVegList().get(i).getVegCatId(),response.body().getAdminVegList().get(i).getLocalLanguage());

                                exerciseSelectedList.add(excercisePojo);
                                Log.d("certecccc","1  "+response.body().getAdminVegList().get(i).exerciseId+"   ");

                            }

                            for (int i=0;i<response.body().getData().size();i++)
                            {
                                Log.d("dsfsfs",""+response.body().getData().get(i).getSaplingDate());

                                ExcercisePojo excercisePojo=new ExcercisePojo(response.body().getData().get(i).getVegetableId(), response.body().getData().get(i).getVegName(), response.body().getData().get(i).getVegImage(),response.body().getData().get(i).getVegCatId(),response.body().getData().get(i).getLocalLanguage());
                                excercisePojo.setSaplingDate(response.body().getData().get(i).getSaplingDate());
                                excercisePojo.setDeweeding1(response.body().getData().get(i).getDeweeding1());
                                excercisePojo.setDeweeding2(response.body().getData().get(i).getDeweeding2());
                                excercisePojo.setDeweeding3(response.body().getData().get(i).getDeweeding3());
                                excercisePojo.setFertilizing1(response.body().getData().get(i).getFertilizing1());
                                excercisePojo.setFertilizing2(response.body().getData().get(i).getFertilizing2());
                                excercisePojo.setFertilizing3(response.body().getData().get(i).getFertilizing3());
                                excercisePojo.setHarvesting(response.body().getData().get(i).getHarvesting());
                                exerciseList.add(excercisePojo);
                                Log.d("certecccc","2  "+"   "+response.body().getData().get(i).exerciseId);

                            }

                            exerciseList.removeAll(exerciseSelectedList);

                            if(exerciseList.size()==0)
                            {
                                mainActivityBinding.noVeg.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                mainActivityBinding.noVeg.setVisibility(View.GONE);
                            }

                        }
                        else
                        {

                        }

                    }
                    else
                    {
                    }
                }
                else
                {

                    Toast.makeText(MainActivity.this, "Server or Internet Error", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<VegetablesResponse> call, Throwable t) {
                progressDialog.dismiss();

                Toast.makeText(MainActivity.this, "Server or Internet Error", Toast.LENGTH_SHORT).show();

            }
        });

    }*/

    public void loadExerciseData() {

     /*   exerciseList.add(new ExcercisePojo("1", "Vegetable " + 1, "https://c.ndtvimg.com/2018-09/4a6d49go_vegetables_625x300_26_September_18.jpg","",""));
        exerciseList.add(new ExcercisePojo("2", "Vegetable " + 2, "https://images-prod.healthline.com/hlcmsresource/images/topic_centers/Food-Nutrition/high-protein-veggies/388x210_potatoes.jpg","",""));
        exerciseList.add(new ExcercisePojo("3", "Vegetable " + 3, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSHC45t_GzF-5OXLFJoFqt21pVu2fn53z-yi4tJm3Q1i0-ozOZP","",""));
        exerciseList.add(new ExcercisePojo("4", "Vegetable " + 4, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQB-4buwvMxmDdc3QlyYvQkR06V_9Ya9fegwGahfMIBFxv4amFLwg","",""));
        exerciseList.add(new ExcercisePojo("5", "Vegetable " + 5, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTXbpZ09GUV14WRhViNQNLSiZc6qJVV8Ju-ohjFrtdqgOYTJMisyg","",""));
        exerciseList.add(new ExcercisePojo("6", "Vegetable " + 1, "https://c.ndtvimg.com/2018-09/4a6d49go_vegetables_625x300_26_September_18.jpg","",""));
        exerciseList.add(new ExcercisePojo("7", "Vegetable " + 2, "https://images-prod.healthline.com/hlcmsresource/images/topic_centers/Food-Nutrition/high-protein-veggies/388x210_potatoes.jpg","",""));
        exerciseList.add(new ExcercisePojo("8", "Vegetable " + 6, "https://c.ndtvimg.com/2018-09/4a6d49go_vegetables_625x300_26_September_18.jpg","",""));
        exerciseList.add(new ExcercisePojo("9", "Vegetable " + 7, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTXbpZ09GUV14WRhViNQNLSiZc6qJVV8Ju-ohjFrtdqgOYTJMisyg","",""));
        exerciseList.add(new ExcercisePojo("10", "Vegetable " + 8, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQB-4buwvMxmDdc3QlyYvQkR06V_9Ya9fegwGahfMIBFxv4amFLwg","",""));
        exerciseList.add(new ExcercisePojo("11", "Vegetable " + 9, "https://images-prod.healthline.com/hlcmsresource/images/topic_centers/Food-Nutrition/high-protein-veggies/388x210_potatoes.jpg","",""));
        exerciseList.add(new ExcercisePojo("12", "Vegetable " + 10, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSHC45t_GzF-5OXLFJoFqt21pVu2fn53z-yi4tJm3Q1i0-ozOZP","",""));
*/
       // exerciseSelectedList.add(new ExcercisePojo("1", "Vegetable " + 1, "https://c.ndtvimg.com/2018-09/4a6d49go_vegetables_625x300_26_September_18.jpg","",""));
      //  exerciseSelectedList.add(new ExcercisePojo("2", "Vegetable " + 2, "https://images-prod.healthline.com/hlcmsresource/images/topic_centers/Food-Nutrition/high-protein-veggies/388x210_potatoes.jpg","",""));
      //  exerciseSelectedList.add(new ExcercisePojo("3", "Vegetable " + 3, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSHC45t_GzF-5OXLFJoFqt21pVu2fn53z-yi4tJm3Q1i0-ozOZP","",""));
     //   exerciseSelectedList.add(new ExcercisePojo("4", "Vegetable " + 4, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQB-4buwvMxmDdc3QlyYvQkR06V_9Ya9fegwGahfMIBFxv4amFLwg","",""));
      //  exerciseSelectedList.add(new ExcercisePojo("5", "Vegetable " + 5, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTXbpZ09GUV14WRhViNQNLSiZc6qJVV8Ju-ohjFrtdqgOYTJMisyg","",""));
      //  exerciseSelectedList.add(new ExcercisePojo("6", "Vegetable " + 6, "https://c.ndtvimg.com/2018-09/4a6d49go_vegetables_625x300_26_September_18.jpg","",""));
      //  exerciseSelectedList.add(new ExcercisePojo("7", "Vegetable " + 7, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTXbpZ09GUV14WRhViNQNLSiZc6qJVV8Ju-ohjFrtdqgOYTJMisyg","",""));


        exerciseList = new ObservableArrayList<>();;
            progressDialog.show();
            RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
            Call<GetAllVegetablesResponse> call = myInterface.getVegList();
            call.enqueue(new Callback<GetAllVegetablesResponse>() {
                @Override
                public void onResponse(Call<GetAllVegetablesResponse> call, Response<GetAllVegetablesResponse> response) {
                    progressDialog.dismiss();
                    if(response.isSuccessful()) {
                        if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                            if (response.body().getData() != null) {
                                for (int i=0;i<response.body().getData().size();i++)
                                {
                                    GetAllVegetables getAllVegetables=new GetAllVegetables(response.body().getData().get(i).getVegetableId(),response.body().getData().get(i).getVegCatId(),response.body().getData().get(i).getVegImage(),response.body().getData().get(i).getVegName(),response.body().getData().get(i).getLocalLanguage());
                                    exerciseList.add(getAllVegetables);



                                }
                            }
                        }
                    }

                }

                @Override
                public void onFailure(Call<GetAllVegetablesResponse> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });
    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        View selectedView = (View) dragEvent.getLocalState();
        RecyclerView rcvSelected = (RecyclerView) view;
        int currentPosition=0;
        try {
            currentPosition = mainActivityBinding.rcvChooseExercise.getChildAdapterPosition(selectedView);

            // Ensure the position is valid.
            if (currentPosition != -1) {
                exerciseToMove = exerciseList.get(currentPosition);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        switch (dragEvent.getAction()) {
            case DragEvent.ACTION_DRAG_LOCATION:

                View onTopOf = rcvSelected.findChildViewUnder(dragEvent.getX(), dragEvent.getY());
                newContactPosition = rcvSelected.getChildAdapterPosition(onTopOf);

                break;
            case DragEvent.ACTION_DRAG_ENTERED:
/*
                Toast.makeText(this, "selected currentPosition : " + currentPosition, Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "selected newContactPosition : " + newContactPosition, Toast.LENGTH_SHORT).show();
*/

                if(exerciseList.size()==0)
                {
                    mainActivityBinding.noVeg.setVisibility(View.VISIBLE);
                }
                else
                {
                    mainActivityBinding.noVeg.setVisibility(View.GONE);
                }
                break;
            case DragEvent.ACTION_DRAG_EXITED:

                if(exerciseList.size()==0)
                {
                    mainActivityBinding.noVeg.setVisibility(View.VISIBLE);
                }
                else
                {
                    mainActivityBinding.noVeg.setVisibility(View.GONE);
                }
                break;
            case DragEvent.ACTION_DROP:
                //when Item is dropped off to recyclerview.

                /*if(currentPosition>6) {*/
                    if (isFromExercise) {
                        exerciseSelectedList.add(exerciseToMove);
                        exerciseList.remove(exerciseToMove);
                        if (exerciseList.size() == 0) {
                            mainActivityBinding.noVeg.setVisibility(View.VISIBLE);
                        } else {
                            mainActivityBinding.noVeg.setVisibility(View.GONE);
                        }
                        mainActivityBinding.rcvChooseExercise.getAdapter().notifyItemRemoved(currentPosition);
                        mainActivityBinding.executePendingBindings();
                    }
                /*}
                else
                {
                    Toast.makeText(MainActivity.this, "These are required vegetables", Toast.LENGTH_SHORT).show();
                }*/
                //This is to hide/add the container!
                /*ViewGroup owner = (ViewGroup) (view.getParent());
                if (owner != null) {
                    //owner.removeView(selectedView);
                    //owner.addView(selectedView);

                    try {
                        LinearLayout rlContainer = (LinearLayout) view;
                        rlContainer.addView(selectedView);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //selectedView.setVisibility(View.VISIBLE);
                }*/

                break;

            case DragEvent.ACTION_DRAG_ENDED:
                // Reset the visibility for the Contact item's view.
                // This is done to reset the state in instances where the drag action didn't do anything.
                selectedView.setVisibility(View.VISIBLE);

                // Boundary condition, scroll to top is moving list item to position 0.
                if (newContactPosition != -1) {
                    rcvSelected.scrollToPosition(newContactPosition);
                    newContactPosition = -1;
                } else {
                    rcvSelected.scrollToPosition(0);
                }
                if(exerciseList.size()==0)
                {
                    mainActivityBinding.noVeg.setVisibility(View.VISIBLE);
                }
                else
                {
                    mainActivityBinding.noVeg.setVisibility(View.GONE);
                }
            default:
                break;
        }
        return true;
    }
    /*public void loadExerciseData() {

       *//* exerciseList.add(new ExcercisePojo("1", "Vegetable " + 1, "https://c.ndtvimg.com/2018-09/4a6d49go_vegetables_625x300_26_September_18.jpg"));
        exerciseList.add(new ExcercisePojo("2", "Vegetable " + 2, "https://images-prod.healthline.com/hlcmsresource/images/topic_centers/Food-Nutrition/high-protein-veggies/388x210_potatoes.jpg"));
        exerciseList.add(new ExcercisePojo("3", "Vegetable " + 3, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSHC45t_GzF-5OXLFJoFqt21pVu2fn53z-yi4tJm3Q1i0-ozOZP"));
        exerciseList.add(new ExcercisePojo("4", "Vegetable " + 4, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQB-4buwvMxmDdc3QlyYvQkR06V_9Ya9fegwGahfMIBFxv4amFLwg"));
        exerciseList.add(new ExcercisePojo("5", "Vegetable " + 5, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTXbpZ09GUV14WRhViNQNLSiZc6qJVV8Ju-ohjFrtdqgOYTJMisyg"));
        exerciseList.add(new ExcercisePojo("6", "Vegetable " + 1, "https://c.ndtvimg.com/2018-09/4a6d49go_vegetables_625x300_26_September_18.jpg"));
        exerciseList.add(new ExcercisePojo("7", "Vegetable " + 2, "https://images-prod.healthline.com/hlcmsresource/images/topic_centers/Food-Nutrition/high-protein-veggies/388x210_potatoes.jpg"));
        exerciseList.add(new ExcercisePojo("8", "Vegetable " + 6, "https://c.ndtvimg.com/2018-09/4a6d49go_vegetables_625x300_26_September_18.jpg"));
        exerciseList.add(new ExcercisePojo("9", "Vegetable " + 7, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTXbpZ09GUV14WRhViNQNLSiZc6qJVV8Ju-ohjFrtdqgOYTJMisyg"));
        exerciseList.add(new ExcercisePojo("10", "Vegetable " + 8, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQB-4buwvMxmDdc3QlyYvQkR06V_9Ya9fegwGahfMIBFxv4amFLwg"));
        exerciseList.add(new ExcercisePojo("22", "Vegetable " + 9, "https://images-prod.healthline.com/hlcmsresource/images/topic_centers/Food-Nutrition/high-protein-veggies/388x210_potatoes.jpg"));
        exerciseList.add(new ExcercisePojo("10", "Vegetable " + 10, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSHC45t_GzF-5OXLFJoFqt21pVu2fn53z-yi4tJm3Q1i0-ozOZP"));

        exerciseSelectedList.add(new ExcercisePojo(1, "Vegetable " + 1, "https://c.ndtvimg.com/2018-09/4a6d49go_vegetables_625x300_26_September_18.jpg"));
        exerciseSelectedList.add(new ExcercisePojo(2, "Vegetable " + 2, "https://images-prod.healthline.com/hlcmsresource/images/topic_centers/Food-Nutrition/high-protein-veggies/388x210_potatoes.jpg"));
        exerciseSelectedList.add(new ExcercisePojo(3, "Vegetable " + 3, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSHC45t_GzF-5OXLFJoFqt21pVu2fn53z-yi4tJm3Q1i0-ozOZP"));
        exerciseSelectedList.add(new ExcercisePojo(4, "Vegetable " + 4, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQB-4buwvMxmDdc3QlyYvQkR06V_9Ya9fegwGahfMIBFxv4amFLwg"));
        exerciseSelectedList.add(new ExcercisePojo(5, "Vegetable " + 5, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTXbpZ09GUV14WRhViNQNLSiZc6qJVV8Ju-ohjFrtdqgOYTJMisyg"));
        exerciseSelectedList.add(new ExcercisePojo(6, "Vegetable " + 6, "https://c.ndtvimg.com/2018-09/4a6d49go_vegetables_625x300_26_September_18.jpg"));
        exerciseSelectedList.add(new ExcercisePojo(7, "Vegetable " + 7, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTXbpZ09GUV14WRhViNQNLSiZc6qJVV8Ju-ohjFrtdqgOYTJMisyg"));*//*

    }*/
    /**
     * This listener class is for Vertical Recyclerview.
     */
    class MyDragInsideRcvListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            RecyclerView rcv = (RecyclerView) v;
            int currentPosition=0;
            View selectedView = (View) event.getLocalState();
            try {
                currentPosition = rcv.getChildAdapterPosition(selectedView);
                // Ensure the position is valid.
                if (currentPosition != -1) {
                    exerciseToMove = exerciseSelectedList.get(currentPosition);
                    //exerciseSelectedList.get(currentPosition);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_LOCATION:
                    View onTopOf = rcv.findChildViewUnder(event.getX(), event.getY());
                    newContactPosition = rcv.getChildAdapterPosition(onTopOf);

                    //Flag for our own understanding!
                    //isFromExercise = true;

                    //This is for internal dragging (inside recyclerview only).  VVIP!
                    // Ensure the new position is valid.

                    //If you wanted to swap the items in recyclerview only.
                    //It requires bit changes.
                   /* if (newContactPosition != -1) {
                        LinearLayoutManager layoutManager = (LinearLayoutManager) rcv.getLayoutManager();
                        int firstVisiblePosition = layoutManager.findFirstCompletelyVisibleItemPosition();
                        int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();

                        // Scroll up or down one if we are over the first or last visible list item.
                        if (newContactPosition >= lastVisiblePosition)
                            layoutManager.scrollToPositionWithOffset(firstVisiblePosition + 1, 0);
                        else if (newContactPosition <= firstVisiblePosition)
                            layoutManager.scrollToPositionWithOffset(firstVisiblePosition - 1, 0);

                        // Update the location of the Contact
                        exerciseList.remove(exerciseToMove);
                        exerciseList.add(newContactPosition, exerciseToMove);
                        rcv.getAdapter().notifyDataSetChanged();
                    }*/
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    // Reset the visibility for the Contact item's view.
                    // This is done to reset the state in instances where the drag action didn't do anything.

/*

                    Toast.makeText(MainActivity.this, "choose currentPosition : "+currentPosition, Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, "choose newContactPosition : "+newContactPosition, Toast.LENGTH_SHORT).show();
*/

                    selectedView.setVisibility(View.VISIBLE);
                    // Boundary condition, scroll to top is moving list item to position 0.
                    if (newContactPosition != -1) {
                        rcv.scrollToPosition(newContactPosition);
                        newContactPosition = -1;
                    } else {
                        rcv.scrollToPosition(0);
                    }
                    break;
                case DragEvent.ACTION_DROP:
                    if(currentPosition>6)
                    {
                        //Toast.makeText(MainActivity.this, "can drag", Toast.LENGTH_SHORT).show();
                        if (!isFromExercise) {
                            //THIS IS FOR WHEN WE TAKE ITEM OF OTHER LIST AND DROP IN THIS LIST.
                            exerciseList.add(exerciseToMove);
                            exerciseSelectedList.remove(exerciseToMove);
                            mainActivityBinding.rcvChooseExercise.getAdapter().notifyItemInserted(currentPosition);
                            mainActivityBinding.executePendingBindings();
                        }
                    }
                    else
                    {
                        if (!isFromExercise) {
                            Toast.makeText(MainActivity.this, "These are required vegetables", Toast.LENGTH_SHORT).show();
                        }
                    }

                    break;

                default:
                    break;
            }
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}

