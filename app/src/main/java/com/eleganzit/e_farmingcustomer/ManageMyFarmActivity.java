package com.eleganzit.e_farmingcustomer;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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


    RecyclerView rc_farms;
    TextView txt_no_slots;
    ProgressDialog progressDialog;
    String customer_plot_id,farm_id;
    ImageView reload;

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
        reload=findViewById(R.id.reload);

        ImageView back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        rc_farms=findViewById(R.id.rc_farms);
        txt_no_slots=findViewById(R.id.txt_no_slots);

        getcustomerSlots();

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
