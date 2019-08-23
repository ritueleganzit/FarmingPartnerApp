package com.eleganzit.e_farmingcustomer;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eleganzit.e_farmingcustomer.adapters.AvailablePlotsAdapter;
import com.eleganzit.e_farmingcustomer.adapters.ManageFarmAdapter;
import com.eleganzit.e_farmingcustomer.adapters.ManageMyFarmAdapter;
import com.eleganzit.e_farmingcustomer.api.RetrofitAPI;
import com.eleganzit.e_farmingcustomer.api.RetrofitInterface;
import com.eleganzit.e_farmingcustomer.model.AvailablePlotsData;
import com.eleganzit.e_farmingcustomer.model.FarmSlotsData;
import com.eleganzit.e_farmingcustomer.model.FarmSlotsResponse;
import com.eleganzit.e_farmingcustomer.model.ManageFarmData;
import com.eleganzit.e_farmingcustomer.model.ManageFarmResponse;
import com.eleganzit.e_farmingcustomer.utils.UserSessionManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageMyFarmActivity extends AppCompatActivity {
    Button btn_sell,btn_walk,btn_set;

    RecyclerView rc_farms;
    TextView txt_no_slots;
    ProgressDialog progressDialog;
    String customer_plot_id,farm_id,plotname;
    ImageView reload;
    TextView titlemanagefarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_my_farm);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        customer_plot_id=getIntent().getStringExtra("customer_plot_id");
        farm_id=getIntent().getStringExtra("farm_id");
        plotname=getIntent().getStringExtra("plotname");
        reload=findViewById(R.id.reload);

        ImageView back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_sell=findViewById(R.id.btn_sellveg);
        titlemanagefarm=findViewById(R.id.titlemanagefarm);
        btn_walk=findViewById(R.id.btn_walkiharvest);
        btn_set=findViewById(R.id.btn_setdel);
        rc_farms=findViewById(R.id.rc_farms);
        txt_no_slots=findViewById(R.id.txt_no_slots);
        final Dialog dialog=new Dialog(ManageMyFarmActivity.this);
        dialog.setContentView(R.layout.update_soon_dialog);

        Button btn_ok=dialog.findViewById(R.id.btn_ok);
        titlemanagefarm.setText("Manage My Farm - "+plotname);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);

        getcustomerSlots();
        btn_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.show();
            }
        });

        btn_walk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.show();
            }
        });

        btn_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.show();
            }
        });
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getcustomerSlots();
            }
        });

    }


    private void getcustomerSlots() {
        reload.setVisibility(View.GONE);
        progressDialog.show();
Log.d("customer_plot_id",customer_plot_id);
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<FarmSlotsResponse> call=myInterface.getcustomerSlots(customer_plot_id);
        call.enqueue(new Callback<FarmSlotsResponse>() {
            @Override
            public void onResponse(Call<FarmSlotsResponse> call, Response<FarmSlotsResponse> response) {
                progressDialog.dismiss();

                if (response.isSuccessful())
                {

                    ArrayList<FarmSlotsData> arrayList=new ArrayList<>();
                    if (response.body().getStatus().toString().equalsIgnoreCase("1"))
                    {
                        txt_no_slots.setVisibility(View.GONE);
                        if (response.body().getData()!=null)
                        {

                            for (int i=0;i<response.body().getData().size();i++)
                            {

                                arrayList.add(response.body().getData().get(i));

                            }

                            Log.d("ccccc",""+arrayList.size());

                            rc_farms.setAdapter(new ManageMyFarmAdapter(arrayList,ManageMyFarmActivity.this,farm_id));

                        }
                        else
                        {
                            txt_no_slots.setVisibility(View.VISIBLE);

                        }

                    }
                    else
                    {
                        txt_no_slots.setVisibility(View.VISIBLE);
                    }
                }
                else
                {

                    Toast.makeText(ManageMyFarmActivity.this, "Server or Internet Error", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<FarmSlotsResponse> call, Throwable t) {
                progressDialog.dismiss();
                reload.setVisibility(View.VISIBLE);
                Toast.makeText(ManageMyFarmActivity.this, "Server or Internet Error", Toast.LENGTH_SHORT).show();

            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

}
