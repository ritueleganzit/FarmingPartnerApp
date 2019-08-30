package com.eleganzit.farmingpartner.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.view.WindowManager;

import com.eleganzit.farmingpartner.LoginActivity;
import com.eleganzit.farmingpartner.NavHomeActivity;
import com.eleganzit.farmingpartner.R;
import com.eleganzit.farmingpartner.utils.UserSessionManager;


public class SplashActivity extends AppCompatActivity {
    UserSessionManager userSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        setContentView(R.layout.activity_splash);
        userSessionManager=new UserSessionManager(this);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fade fade = new Fade();
            fade.excludeTarget(android.R.id.statusBarBackground, true);
            //fade.excludeTarget(android.R.id.navigationBarBackground, true);

            getWindow().setEnterTransition(fade);
            getWindow().setExitTransition(fade);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (userSessionManager.isLoggedIn())
                {
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }
                else {
                    startActivity(new Intent(SplashActivity.this, SignInActivity.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }






            }
        },3000);
    }
}
