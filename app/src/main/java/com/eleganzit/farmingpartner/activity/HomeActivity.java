package com.eleganzit.farmingpartner.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.eleganzit.farmingpartner.NavHomeActivity;
import com.eleganzit.farmingpartner.R;
import com.eleganzit.farmingpartner.fragment.ContactOfficeFragment;
import com.eleganzit.farmingpartner.fragment.ManageFarmFragment;
import com.eleganzit.farmingpartner.utils.UserSessionManager;
import com.infideap.drawerbehavior.AdvanceDrawerLayout;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    AdvanceDrawerLayout drawer;
    public static TextView home_title;
    CircleImageView profile_image;
    TextView user_name;
    UserSessionManager userSessionManager;
    private String photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        home_title = findViewById(R.id.textTitle);
        userSessionManager=new UserSessionManager(this);
userSessionManager.checkLogin();
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);


                String firstname = userSessionManager.getUserDetails().get(UserSessionManager.KEY_FNAME).substring(0,1).toUpperCase() + userSessionManager.getUserDetails().get(UserSessionManager.KEY_FNAME).substring(1);
                String lastname = userSessionManager.getUserDetails().get(UserSessionManager.KEY_LNAME).substring(0,1).toUpperCase() + userSessionManager.getUserDetails().get(UserSessionManager.KEY_LNAME).substring(1);
               if (firstname!=null && !firstname.isEmpty())

               {
                   if (!firstname.equalsIgnoreCase("null"))

                   {
                       user_name.setText(firstname+" ");
                   }


                   if(lastname != null && !lastname.isEmpty())
                   {
                       user_name.append(lastname+" ");
                   }
               }
                user_name.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);


                photo=userSessionManager.getUserDetails().get(UserSessionManager.KEY_PHOTO);

                Glide
                        .with(HomeActivity.this)
                        .load(photo).apply(new RequestOptions().placeholder(R.drawable.pr))
                        .into(profile_image);
                invalidateOptionsMenu();

            }
        };


        toggle.setDrawerIndicatorEnabled(false);

        NavigationView navigationView = findViewById(R.id.nav_view);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
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
        navigationView.setNavigationItemSelectedListener(this);
        View headerview = navigationView.getHeaderView(0);
        profile_image = headerview.findViewById(R.id.profile_image);


        user_name = headerview.findViewById(R.id.user_name);
        drawer.setViewScale(Gravity.START, 0.9f);
        drawer.setRadius(Gravity.START, 15);
        drawer.setViewElevation(Gravity.START, 20);
        ManageFarmFragment myProfileFragment = new ManageFarmFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, myProfileFragment, "TAG")
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String firstname = userSessionManager.getUserDetails().get(UserSessionManager.KEY_FNAME).substring(0,1).toUpperCase() + userSessionManager.getUserDetails().get(UserSessionManager.KEY_FNAME).substring(1);
        String lastname = userSessionManager.getUserDetails().get(UserSessionManager.KEY_LNAME).substring(0,1).toUpperCase() + userSessionManager.getUserDetails().get(UserSessionManager.KEY_LNAME).substring(1);

        if (firstname!=null && !firstname.isEmpty())

        {
            if (!firstname.equalsIgnoreCase("null"))
            {
                user_name.setText(firstname+" ");
            }


            if (lastname!=null && !lastname.isEmpty())

            {
                if (!lastname.equalsIgnoreCase("null"))
                {
                    user_name.append(""+lastname);
                }


            }

        }
        user_name.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);


        photo=userSessionManager.getUserDetails().get(UserSessionManager.KEY_PHOTO);

        Glide
                .with(this)
                .load(photo).apply(new RequestOptions().placeholder(R.drawable.pr))
                .into(profile_image);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            com.eleganzit.farmingpartner.fragment.MyProfileFragment myProfileFragment = new  com.eleganzit.farmingpartner.fragment.MyProfileFragment();

            getSupportFragmentManager().beginTransaction()
                    .addToBackStack("NavHomeActivity")
                    .replace(R.id.container, myProfileFragment, "TAG")
                    .commit();
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            ManageFarmFragment myProfileFragment = new ManageFarmFragment();

            getSupportFragmentManager().beginTransaction()
                    .addToBackStack("NavHomeActivity")
                    .replace(R.id.container, myProfileFragment, "TAG")
                    .commit();

        } else if (id == R.id.nav_slideshow) {
            startActivity(new Intent(HomeActivity.this,NotificationsActivity.class));
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

        } else if (id == R.id.nav_tools) {
            ContactOfficeFragment contactOfficeFragment = new ContactOfficeFragment();

            getSupportFragmentManager().beginTransaction()
                    .addToBackStack("NavHomeActivity")
                    .replace(R.id.container, contactOfficeFragment, "TAG")
                    .commit();
        } else if (id == R.id.nav_logout) {
            new android.app.AlertDialog.Builder(HomeActivity.this).setTitle("Logout").setMessage("Are you sure you want to logout?")
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
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
