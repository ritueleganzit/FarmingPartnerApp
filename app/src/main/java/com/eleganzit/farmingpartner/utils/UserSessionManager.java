package com.eleganzit.farmingpartner.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.eleganzit.farmingpartner.LoginActivity;
import com.eleganzit.farmingpartner.NavHomeActivity;
import com.eleganzit.farmingpartner.activity.HomeActivity;
import com.eleganzit.farmingpartner.activity.SignInActivity;

import java.util.HashMap;

public class UserSessionManager {

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context context;
    Activity activity;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "MySession";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_FARM_ID = "farm_id";
    public static final String KEY_FNAME = "fname";
    public static final String KEY_LNAME = "lname";
    public static final String KEY_PHONE = "phone";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";

    public static final String KEY_PASSWORD = "password";
    public static final String KEY_REGION = "region";
    public static final String KEY_MOBILE = "mobile";
    public static final String KEY_PHOTO = "photo";
    public static final String KEY_DOB = "dob";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_LANDMARK = "landmark";
    public static final String KEY_SUB_LOCATION = "sub_location";
    public static final String KEY_COUNTRY = "country";
    public static final String KEY_STATE = "state";
    public static final String KEY_HOUSE = "house_no";

    // Constructor
    public UserSessionManager(Context context){
        this.context = context;
        this.activity = (Activity) context;
        pref = this.context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */

    public void createLoginSession(String region,String user_id,String farm_id, String email, String password, String fname, String lname, String phone, String dob, String address, String landmark, String sub_location, String photo,String house_no){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_USER_ID , user_id);
        editor.putString(KEY_FARM_ID , farm_id);
        // Storing email in pref
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_REGION, region);

        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_FNAME, fname);
        editor.putString(KEY_LNAME, lname);
        editor.putString(KEY_PHONE, phone);
        editor.putString(KEY_PHOTO, photo);
        editor.putString(KEY_DOB, dob);
        editor.putString(KEY_ADDRESS, address);
        editor.putString(KEY_LANDMARK, landmark);
        editor.putString(KEY_SUB_LOCATION, sub_location);
        editor.putString(KEY_HOUSE, house_no);

        // commit changes
        editor.commit();

        Intent i = new Intent(context, HomeActivity.class);
        // Closing all the Activities

        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // Staring Login Activity
        context.startActivity(i);
        activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        activity.finish();
    }

    public void updateUserData(String region,String password, String fname, String lname, String phone, String dob, String address, String landmark, String sub_location, String photo,String house_no){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);


        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_FNAME, fname);
        editor.putString(KEY_LNAME, lname);
        editor.putString(KEY_PHONE, phone);
        editor.putString(KEY_REGION, region);
        editor.putString(KEY_DOB, dob);
        editor.putString(KEY_ADDRESS, address);
        editor.putString(KEY_LANDMARK, landmark);
        editor.putString(KEY_SUB_LOCATION, sub_location);
        editor.putString(KEY_PHOTO, photo);
        editor.putString(KEY_HOUSE, house_no);

        // commit changes
        editor.commit();

    }

    public void updateProfilePic(String photo)
    {
        editor.putString(KEY_PHOTO, photo);

        // commit changes
        editor.commit();
    }

    public void updatePassword(String password)
    {
        editor.putString(KEY_PASSWORD, password);

        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(context, SignInActivity.class);

            // Closing all the Activities
            /*i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
*/
            // Staring Login Activity
            context.startActivity(i);
            activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        }

    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_USER_ID , pref.getString(KEY_USER_ID , null));
        user.put(KEY_FARM_ID, pref.getString(KEY_FARM_ID , null));

        user.put(KEY_FNAME, pref.getString(KEY_FNAME, null));
        user.put(KEY_REGION, pref.getString(KEY_REGION, null));
        user.put(KEY_LNAME, pref.getString(KEY_LNAME, null));
        user.put(KEY_PHONE, pref.getString(KEY_PHONE, null));
        user.put(KEY_DOB, pref.getString(KEY_DOB, null));
        user.put(KEY_ADDRESS, pref.getString(KEY_ADDRESS, null));
        user.put(KEY_LANDMARK, pref.getString(KEY_LANDMARK, null));
        user.put(KEY_SUB_LOCATION, pref.getString(KEY_SUB_LOCATION, null));

        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));

        user.put(KEY_MOBILE, pref.getString(KEY_MOBILE, null));

        user.put(KEY_PHOTO, pref.getString(KEY_PHOTO, null));

        user.put(KEY_COUNTRY, pref.getString(KEY_COUNTRY, null));

        user.put(KEY_STATE, pref.getString(KEY_STATE, null));
        user.put(KEY_HOUSE, pref.getString(KEY_HOUSE, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(context, SignInActivity.class);
        // Closing all the Activities
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // Staring Login Activity
        context.startActivity(i);
        activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

}