package com.eleganzit.farmingpartner;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.eleganzit.farmingpartner.utils.UserSessionManager;

public class SplashActivity extends AppCompatActivity {

    UserSessionManager userSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        userSessionManager=new UserSessionManager(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (userSessionManager.isLoggedIn())
                {
                    startActivity(new Intent(SplashActivity.this,NavHomeActivity.class));
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                    finish();
                }
                else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }



            }
        },3000);
    }
}
