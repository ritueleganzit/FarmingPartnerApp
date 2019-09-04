package com.eleganzit.farmingpartner.api;


import com.eleganzit.farmingpartner.model.AvailablePlotsResponse;
import com.eleganzit.farmingpartner.model.ContactOfficeResponse;
import com.eleganzit.farmingpartner.model.FarmDetailsResponse;
import com.eleganzit.farmingpartner.model.FarmSlotsResponse;
import com.eleganzit.farmingpartner.model.FarmingPartnerChangePasswordResponse;
import com.eleganzit.farmingpartner.model.FarmingpartnerNotificationResponse;
import com.eleganzit.farmingpartner.model.ForgotPasswordResponse;
import com.eleganzit.farmingpartner.model.GetAllVegetablesResponse;
import com.eleganzit.farmingpartner.model.GetFarmDetailResponse;
import com.eleganzit.farmingpartner.model.GetFarmingPartnerDetailResponse;
import com.eleganzit.farmingpartner.model.GetVegDetailResponse;
import com.eleganzit.farmingpartner.model.InstructFarmerResponse;
import com.eleganzit.farmingpartner.model.LoginRespose;
import com.eleganzit.farmingpartner.model.ManageFarmResponse;
import com.eleganzit.farmingpartner.model.PlotListResponse;
import com.eleganzit.farmingpartner.model.RegionResponse;
import com.eleganzit.farmingpartner.model.RegisterResponse;
import com.eleganzit.farmingpartner.model.RegistrationResponse;
import com.eleganzit.farmingpartner.model.SlotDetailsResponse;
import com.eleganzit.farmingpartner.model.SubLocationResponse;
import com.eleganzit.farmingpartner.model.SubmitPlotResponse;
import com.eleganzit.farmingpartner.model.UpdateFarmingPartnerDetailResponse;
import com.eleganzit.farmingpartner.model.UpdateNotificationStatus;
import com.eleganzit.farmingpartner.model.UpdatePhotoResponse;
import com.eleganzit.farmingpartner.model.UpdateResponse;
import com.eleganzit.farmingpartner.model.UserDetailsResponse;
import com.eleganzit.farmingpartner.model.VegetablesResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

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
    @POST("/efarming-AdminPanel/efarm-api/farmingPartnerLogin")
    Call<LoginRespose> farmingPartnerLogin(
            @Field("email") String email,
                @Field("password") String password,
                @Field("device_id") String device_id,
                @Field("device_token") String device_token

    );
    @POST("/efarming-AdminPanel/efarm-api/getVegList")
    Call<GetAllVegetablesResponse> getVegList(

    );

    @FormUrlEncoded()
    @POST("/efarming-AdminPanel/efarm-api/getCustomer")
    Call<UserDetailsResponse> getCustomer(
            @Field("customer_id") String customer_id
    );



    @FormUrlEncoded()
    @POST("/efarming-AdminPanel/efarm-api/getFarmDetail")
    Call<GetFarmDetailResponse> getFarmDetail(
            @Field("farm_id") String farm_id
    );

    @FormUrlEncoded()
    @POST("/efarming-AdminPanel/efarm-api/contactOfficeFarmingPartner")
    Call<ContactOfficeResponse> contactOfficeFarmingPartner(
            @Field("farming_partner_id") String farming_partner_id,
            @Field("full_name") String full_name,
            @Field("email_id") String email_id,
            @Field("subject") String subject,
            @Field("message") String message
    );

  @FormUrlEncoded()
    @POST("/efarming-AdminPanel/efarm-api/getFarmingPartnerDetail")
    Call<GetFarmingPartnerDetailResponse> getFarmingPartnerDetail(
            @Field("farming_partner_id") String farming_partner_id
    );


  @FormUrlEncoded()
    @POST("/efarming-AdminPanel/efarm-api/farmingPartnerChangePassword")
    Call<FarmingPartnerChangePasswordResponse> farmingPartnerChangePassword(
            @Field("email") String email,
            @Field("old_password") String old_password,
            @Field("new_password") String new_password
    );




    @POST("/efarming-AdminPanel/efarm-api/getRegions")
    Call<RegionResponse> getRegions(
    );


    @FormUrlEncoded()
    @POST("/efarming-AdminPanel/efarm-api/addCustomer")
    Call<RegisterResponse> registerUser(
            @Field("fname") String fname,
            @Field("lname") String lname,
            @Field("address") String address,
            @Field("house_no") String house_no,
            @Field("region") String region,
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
            @Field("house_no") String house_no,
            @Field("region") String region,
            @Field("landmark") String landmark,
            @Field("sub_location") String sub_location,
            @Field("phone") String phone,
            @Field("dob") String dob

    );

    @Multipart
    @POST("/efarming-AdminPanel/efarm-api/updatePhoto")
    Call<UpdatePhotoResponse> updatePhoto(
            @Part("farming_partner_id") RequestBody farming_partner_id,
            @Part MultipartBody.Part photo
    );
  @Multipart
    @POST("/efarming-AdminPanel/efarm-api/farmingPartnerRegistration")
    Call<RegistrationResponse> farmingPartnerRegistration(
            @Part("farming_partner_name") RequestBody farming_partner_name,
            @Part("email") RequestBody email,
            @Part("phone") RequestBody phone,
            @Part("calender") RequestBody calender,
            @Part("last_name") RequestBody last_name,
            @Part("address") RequestBody address,
            @Part("landmark") RequestBody landmark,
            @Part("sub_location") RequestBody sub_location,
            @Part("dob") RequestBody dob,
            @Part("password") RequestBody password,
            @Part("device_id") RequestBody device_id,
            @Part("device_token") RequestBody device_token,
            @Part("farm_name") RequestBody farm_name,
            @Part("farm_description") RequestBody farm_description,
            @Part("farm_address") RequestBody farm_address,
            @Part("farm_location") RequestBody farm_location,
            @Part("plot_capacity") RequestBody plot_capacity,
            @Part("vegetable_list") RequestBody vegetable_list,
            @Part("farm_lat") RequestBody farm_lat,
            @Part("farm_long") RequestBody farm_long,
            @Part MultipartBody.Part photo
    );



  @FormUrlEncoded
    @POST("/efarming-AdminPanel/efarm-api/updateFarmingPartnerDetail")
    Call<UpdateFarmingPartnerDetailResponse> updateFarmingPartnerDetail(
            @Field("farming_partner_name") String farming_partner_name,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("calender") String calender,
            @Field("last_name") String last_name,
            @Field("address") String address,
            @Field("landmark") String landmark,
            @Field("sub_location") String sub_location,
            @Field("dob") String dob,
            @Field("farming_partner_id") String farming_partner_id


    );


    @FormUrlEncoded()
    @POST("/efarming-AdminPanel/efarm-api/availablePlots")
    Call<AvailablePlotsResponse> availablePlots(
            @Field("dummy") String dummy
    );
    @FormUrlEncoded()
    @POST("/efarming-AdminPanel/efarm-api/getVegDetail")
    Call<GetVegDetailResponse> getVegDetail(
            @Field("vegetable_id") String vegetable_id ,
            @Field("farm_id") String farm_id
    );

    @FormUrlEncoded()
    @POST("/efarming-AdminPanel/efarm-api/getSublocations")
    Call<SubLocationResponse> getSublocations(
            @Field("region") String region
    );

    @FormUrlEncoded()
    @POST("/efarming-AdminPanel/efarm-api/manageCustomerfarm")
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
    @POST("/efarming-AdminPanel/contact_data_mail.php")
    Call<ContactOfficeResponse> contactOffice(
            @Field("customer_id") String customer_id,
            @Field("fullname") String fullname,
            @Field("subject") String subject,
            @Field("message") String message


    );

    @FormUrlEncoded()
    @POST("/efarming-AdminPanel/efarm-api/vegetablesList")
    Call<VegetablesResponse> vegetablesList(
            @Field("farm_id") String farm_id
    );

    @GET("/efarming-AdminPanel/api-forgotpassword-farming-partner.php")
    Call<ForgotPasswordResponse> sendCode(@Query("email") String email);


    @FormUrlEncoded()
    @POST("/efarming-AdminPanel/efarm-api/checkCode")
    Call<ForgotPasswordResponse> matchOtp(
            @Field("email") String email,
            @Field("sentcode") String sentcode
    );

    @FormUrlEncoded()
    @POST("/efarming-AdminPanel/efarm-api/resetPassword")
    Call<ForgotPasswordResponse> resetPassword(
            @Field("email") String email,
            @Field("password") String password
    );  @FormUrlEncoded()
    @POST("/efarming-AdminPanel/efarm-api/farmingpartnerNotification")
      Call<FarmingpartnerNotificationResponse> farmingpartnerNotification(
            @Field("farming_partner_id") String farming_partner_id
    );

    @FormUrlEncoded()
    @POST("/efarming-AdminPanel/efarm-api/getcustomerSlots")
    Call<FarmSlotsResponse> getcustomerSlots(
            @Field("customer_plot_id") String customer_plot_id
    );

    @FormUrlEncoded()
    @POST("/efarming-AdminPanel/efarm-api/vegetablesPopup")
    Call<SlotDetailsResponse> vegetablesDetails(
            @Field("farm_id") String farm_id,
            @Field("vegetable_id") String vegetable_id
    );

    @FormUrlEncoded()
    @POST("/efarming-AdminPanel/efarm-api/customerNewpassword")
    Call<ForgotPasswordResponse> changePassword(
            @Field("email") String email,
            @Field("old_password") String old_password,
            @Field("new_password") String new_password
    );    @FormUrlEncoded()


  @POST("/efarming-AdminPanel/efarm-api/updateNotificationStatus")
    Call<UpdateNotificationStatus> updateNotificationStatus(
            @Field("veg_cal_status_id") String veg_cal_status_id,
            @Field("customer_id") String customer_id,
            @Field("farm_name") String farm_name,
            @Field("veg_name") String veg_name,
            @Field("plot_name") String plot_name,
            @Field("status_name") String status_name,
            @Field("progress_name") String progress_name,
            @Field("status") String status
    );


    //->add plot id
    @FormUrlEncoded()
    @POST("/efarming-AdminPanel/efarm-api/addcustomerSlots")
    Call<SubmitPlotResponse> sendVegetables(
            @Field("customer_id") String customer_id,
            @Field("farm_id") String farm_id,
            @Field("plot_name") String plot_name,
            @Field("plot_description") String plot_description,
            @Field("sub_location") String sub_location,
            @Field("landmark") String landmark,
            @Field("address") String address,
            @Field("referal_code") String referal_code,
            @Field("payment_date") String payment_date,
            @Field("payment_referance") String payment_referance,
            @Field("amount") String amount,
            @Field("veg_list") String veg_list

    );

    @FormUrlEncoded()
    @POST("/efarming-AdminPanel/efarm-api/farmPlotlist")
    Call<PlotListResponse> farmPlotlist(
            @Field("farm_id") String farm_id
    );


}
