package com.eleganzit.e_farmingcustomer.api;


import com.eleganzit.e_farmingcustomer.model.LoginRespose;
import com.eleganzit.e_farmingcustomer.model.RegisterResponse;
import com.eleganzit.e_farmingcustomer.model.UpdateResponse;

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
/*

    @FormUrlEncoded()
    @POST("/VKC-API/message")
    Call<MessageRespose> sendMessage(
            @Field("id") String id,
            @Field("message") String message


    );
*/
/*

    @FormUrlEncoded()
    @POST("/VKC-API/message")
    Call<ContactRespose> contactOffice(
            @Field("id") String id,
            @Field("full_name") String full_name,
            @Field("email") String email,
            @Field("subject") String subject,
            @Field("message") String message


    );
*/


}
