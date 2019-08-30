package com.eleganzit.farmingpartner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eleganzit.farmingpartner.activity.SignInActivity;
import com.eleganzit.farmingpartner.api.RetrofitAPI;
import com.eleganzit.farmingpartner.api.RetrofitInterface;
import com.eleganzit.farmingpartner.model.ForgotPasswordResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivity extends AppCompatActivity {

    EditText ed_password,ed_cpassword;
    Button btn_submit;
    ProgressDialog progressDialog;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        progressDialog = new ProgressDialog(ResetPasswordActivity.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        ed_password=findViewById(R.id.ed_password);
        ed_cpassword=findViewById(R.id.ed_cpassword);
        btn_submit=findViewById(R.id.btn_submit);
        email=getIntent().getStringExtra("email");

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValid())
                {
                    resetPassword();
                }
            }
        });

    }

    private void resetPassword() {
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<ForgotPasswordResponse> call=myInterface.resetPassword(email,ed_cpassword.getText().toString());
        call.enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful())
                {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) 
                    {
                        Toast.makeText(ResetPasswordActivity.this, "Password has been reset successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ResetPasswordActivity.this, SignInActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)

                                // Staring Login Activity
                        );
                        finish();
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    }
                }
            }

            @Override
            public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ResetPasswordActivity.this, "Server or Internet Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean isValid() {

        if (ed_password.getText().toString().trim().equals("") || ed_password.getText().toString().trim().length()<6) {

            ed_password.setError("Password must contain atleast 6 characters");

            ed_password.requestFocus();

            return false;
        }
        else  if (!ed_cpassword.getText().toString().trim().equals(ed_password.getText().toString().trim())) {

            ed_cpassword.setError("Password doesn't match");

            ed_cpassword.requestFocus();

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
