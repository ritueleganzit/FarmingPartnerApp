package com.eleganzit.farmingpartner;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.eleganzit.farmingpartner.fragments.AvailablePlotsFragment;
import com.eleganzit.farmingpartner.fragments.ContactOfficeFragment;
import com.eleganzit.farmingpartner.fragments.InstructFarmerFragment;
import com.eleganzit.farmingpartner.fragments.ManageFarmFragment;
import com.eleganzit.farmingpartner.fragments.MyProfileFragment;

import com.eleganzit.farmingpartner.fragments.PaymentHistoryFragment;
import com.eleganzit.farmingpartner.utils.UserSessionManager;
import com.infideap.drawerbehavior.AdvanceDrawerLayout;

import de.hdodenhof.circleimageview.CircleImageView;

public class NavHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static TextView user_name;
    public static CircleImageView profile_image;
    public static TextView home_title;
    private String photo, name;
    AdvanceDrawerLayout drawer;
    ImageView notification_bell;
    UserSessionManager userSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(NavHomeActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        setContentView(R.layout.activity_main2);
        userSessionManager=new UserSessionManager(this);

        home_title = findViewById(R.id.textTitle);
        notification_bell = findViewById(R.id.notification_bell);
        SharedPreferences p_pref = getSharedPreferences("passenger_pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor p_editor = p_pref.edit();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerview = navigationView.getHeaderView(0);
        LinearLayout header_main = headerview.findViewById(R.id.header_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        toggle.setDrawerIndicatorEnabled(false);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_ham, getTheme());
        toggle.setHomeAsUpIndicator(drawable);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerVisible(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        notification_bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NavHomeActivity.this,NotificationsActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        header_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(NavHomeActivity.this, HomeProfileActivity.class).putExtra("from", "home");
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(NavHomeActivity.this,
                                profile_image,
                                ViewCompat.getTransitionName(profile_image));
                startActivity(intent, options.toBundle());
*/

            }
        });

        profile_image = headerview.findViewById(R.id.profile_image);


        user_name = headerview.findViewById(R.id.user_name);

        Log.d("sjhgdfhdfd",userSessionManager.getUserDetails().get(UserSessionManager.KEY_FNAME)+" ----- "+userSessionManager.getUserDetails().get(UserSessionManager.KEY_LNAME));

        user_name.setText(userSessionManager.getUserDetails().get(UserSessionManager.KEY_FNAME)+" "+userSessionManager.getUserDetails().get(UserSessionManager.KEY_LNAME));


        if(getIntent()!=null)
        {
            if(getIntent().getStringExtra("from")!=null)
            {
                if(getIntent().getStringExtra("from").equalsIgnoreCase("select_veg"))
                {
                    ManageFarmFragment manageFarmFragment= new ManageFarmFragment();
                    getSupportFragmentManager().beginTransaction()//
                            .replace(R.id.container, manageFarmFragment, "TAG")
                            .commit();

                }
                else
                {
                    AvailablePlotsFragment availablePlotsFragment= new AvailablePlotsFragment();
                    getSupportFragmentManager().beginTransaction()//
                            .replace(R.id.container, availablePlotsFragment, "TAG")
                            .commit();

                }
            }
            else
            {
                AvailablePlotsFragment availablePlotsFragment= new AvailablePlotsFragment();
                getSupportFragmentManager().beginTransaction()//
                        .replace(R.id.container, availablePlotsFragment, "TAG")
                        .commit();

            }
        }
        else
        {
            AvailablePlotsFragment availablePlotsFragment= new AvailablePlotsFragment();
            getSupportFragmentManager().beginTransaction()//
                    .replace(R.id.container, availablePlotsFragment, "TAG")
                    .commit();

        }


        navigationView.setNavigationItemSelectedListener(this);
        drawer.setViewScale(Gravity.START, 0.9f);
        drawer.setRadius(Gravity.START, 15);
        drawer.setViewElevation(Gravity.START, 20);

    }

    @Override
    protected void onResume() {
        super.onResume();

        photo=userSessionManager.getUserDetails().get(UserSessionManager.KEY_PHOTO);

        Glide
                .with(this)
                .load(photo).apply(new RequestOptions().placeholder(R.drawable.pr))
                .into(profile_image);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_my_profile) {
            // Handle the camera action
            MyProfileFragment myProfileFragment = new MyProfileFragment();

            getSupportFragmentManager().beginTransaction()
                    .addToBackStack("NavHomeActivity")
                    .replace(R.id.container, myProfileFragment, "TAG")
                    .commit();
        } else if (id == R.id.nav_avlbl_plots) {
            AvailablePlotsFragment availablePlotsFragment= new AvailablePlotsFragment();
            getSupportFragmentManager().beginTransaction()//
                    .addToBackStack("NavHomeActivity")
                    .replace(R.id.container, availablePlotsFragment, "TAG")
                    .commit();

        } else if (id == R.id.nav_manage_farm) {
            ManageFarmFragment manageFarmFragment= new ManageFarmFragment();
            getSupportFragmentManager().beginTransaction()//
                    .addToBackStack("NavHomeActivity")
                    .replace(R.id.container, manageFarmFragment, "TAG")
                    .commit();
        } else if (id == R.id.nav_instruct_farmer) {
            InstructFarmerFragment instructFarmerFragment= new InstructFarmerFragment();
            getSupportFragmentManager().beginTransaction()//
                    .addToBackStack("NavHomeActivity")
                    .replace(R.id.container, instructFarmerFragment, "TAG")
                    .commit();
        } else if(id == R.id.nav_payment_history) {
            PaymentHistoryFragment paymentHistoryFragment= new PaymentHistoryFragment();
            getSupportFragmentManager().beginTransaction()//
                    .addToBackStack("NavHomeActivity")
                    .replace(R.id.container, paymentHistoryFragment, "TAG")
                    .commit();

        }else if(id == R.id.nav_contact_office) {
            ContactOfficeFragment contactOfficeFragment= new ContactOfficeFragment();
            getSupportFragmentManager().beginTransaction()//
                    .addToBackStack("NavHomeActivity")
                    .replace(R.id.container, contactOfficeFragment, "TAG")
                    .commit();
        }
        else if(id == R.id.nav_logout) {
            new android.app.AlertDialog.Builder(NavHomeActivity.this).setTitle("Logout").setMessage("Are you sure you want to logout?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            userSessionManager.logoutUser();

                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();

        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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

