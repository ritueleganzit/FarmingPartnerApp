package com.eleganzit.e_farmingcustomer.api;


import com.eleganzit.e_farmingcustomer.model.AvailablePlotsResponse;
import com.eleganzit.e_farmingcustomer.model.ContactOfficeResponse;
import com.eleganzit.e_farmingcustomer.model.FarmDetailsResponse;
import com.eleganzit.e_farmingcustomer.model.InstructFarmerResponse;
import com.eleganzit.e_farmingcustomer.model.LoginRespose;
import com.eleganzit.e_farmingcustomer.model.ManageFarmResponse;
import com.eleganzit.e_farmingcustomer.model.RegisterResponse;
import com.eleganzit.e_farmingcustomer.model.UpdateResponse;
import com.eleganzit.e_farmingcustomer.model.UserDetailsResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by eleganz on 30/4/19.
 */

public interface RetrofitInterface {

    @FormUrlEncoded()
    @POST("/efarming-AdminPanel/efarm-api/loginCustomer")
    Call<LoginRespose> loginUser(
            @Field("email") String email,
            @Field("password") String password

    );

    @FormUrlEncoded()
    @POST("/efarming-AdminPanel/efarm-api/getCustomer")
    Call<UserDetailsResponse> getCustomer(
            @Field("customer_id") String customer_id
    );

    @FormUrlEncoded()
    @POST("/efarming-AdminPanel/efarm-api/addCustomer")
    Call<RegisterResponse> registerUser(
            @Field("fname") String fname,
            @Field("lname") String lname,
            @Field("address") String address,
            @Field("landmark") String landmark,
            @Field("sub_location") String sub_location,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("dob") String dob,
            @Field("referal_code") String referal_code,
            @Field("password") String password,
            @Field("device_id") String device_id,
            @Field("device_token") String device_token

    );


    @FormUrlEncoded()
    @POST("/efarming-AdminPanel/efarm-api/updateProfile")
    Call<UpdateResponse> updateProfile(
            @Field("customer_id") String customer_id,
            @Field("fname") String fname,
            @Field("lname") String lname,
            @Field("address") String address,
            @Field("landmark") String landmark,
            @Field("sub_location") String sub_location,
            @Field("phone") String phone,
            @Field("dob") String dob,
            @Field("password") String password

    );

    @Multipart
    @POST("/efarming-AdminPanel/efarm-api/updateProfile")
    Call<UpdateResponse> updatePhoto(
            @Part("customer_id") RequestBody customer_id,
            @Part MultipartBody.Part photo
    );


    @FormUrlEncoded()
    @POST("/efarming-AdminPanel/efarm-api/availablePlots")
    Call<AvailablePlotsResponse> availablePlots(
            @Field("dummy") String dummy
    );

    @FormUrlEncoded()
    @POST("/efarming-AdminPanel/efarm-api/manageFarm")
    Call<ManageFarmResponse> manageFarm(
            @Field("customer_id") String customer_id
    );

    @FormUrlEncoded()
    @POST("/efarming-AdminPanel/efarm-api/plotDetails")
    Call<FarmDetailsResponse> plotDetails(
            @Field("farm_id") String farm_id
    );


    @FormUrlEncoded()
    @POST("/efarming-AdminPanel/efarm-api/instructFarmer")
    Call<InstructFarmerResponse> instructFarmer(
            @Field("customer_id") String customer_id,
            @Field("farming_partner_id") String farming_partner_id,
            @Field("message") String message


    );

    @FormUrlEncoded()
    @POST("/efarming-AdminPanel/efarm-api/contactOffice")
    Call<ContactOfficeResponse> contactOffice(
            @Field("customer_id") String customer_id,
            @Field("fullname") String fullname,
            @Field("subject") String subject,
            @Field("message") String message


    );


}
