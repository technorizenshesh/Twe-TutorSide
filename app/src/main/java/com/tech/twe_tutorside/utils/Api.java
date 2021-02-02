package com.tech.twe_tutorside.utils;

import com.tech.twe_tutorside.model.GetCalculaterModel;
import com.tech.twe_tutorside.model.TimeSlotModel;
import com.tech.twe_tutorside.model.TutorCategory_Model;
import com.tech.twe_tutorside.model.TutorSubCategory;
import com.tech.twe_tutorside.model.TutorSubjectMode;
import com.tech.twe_tutorside.model.getAddress;
import com.tech.twe_tutorside.model.myprofile_model;

import org.androidannotations.annotations.rest.Get;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {

    String signUp ="signup";
    String login ="login";
    String social_login ="social_login";
    String add_address_onw ="add_address";
    String get_address ="get_address";
    String get_profile ="get_profile";
    String forgot_password ="forgot_password";
    String get_tutor_category ="get_tutor_category";
    String get_tutor_sub_category ="get_tutor_sub_category";
    String get_tutor_subject ="get_tutor_subject";
    String add_time ="add_time";
    String get_time_slot ="get_time_slot";
    String get_calculation ="get_calculation";
    String add_details ="add_details";

   @FormUrlEncoded
   @POST(login)
    Call<ResponseBody>login(
           @Field("email") String email,
           @Field("password") String password,
           @Field("type") String type,
           @Field("lat") String lat,
           @Field("lon") String lon,
           @Field("register_id") String register_id
   );

   @FormUrlEncoded
   @POST(add_time)
    Call<ResponseBody>dd_time(
           @Field("user_id") String user_id,
           @Field("opentime") String opentime,
           @Field("cLosetime") String cLosetime,
           @Field("slottime") String slottime
   );

   @FormUrlEncoded
   @POST(get_time_slot)
    Call<TimeSlotModel>get_time_slot(
           @Field("user_id") String user_id
   );

    @FormUrlEncoded
    @POST(social_login)
    Call<ResponseBody>Social_login(
            @Field("name") String username,
            @Field("email") String email,
            @Field("mobile") String mobile,
            @Field("type") String type,
            @Field("lat") String lat,
            @Field("lon") String lon,
            @Field("social_id") String social_id,
            @Field("register_id") String register_id
    );

   @FormUrlEncoded
   @POST(add_address_onw)
    Call<ResponseBody>add_address(
           @Field("user_id") String user_id,
           @Field("address_type") String address_type,
           @Field("address") String address,
           @Field("lat") String lat,
           @Field("lon") String lon
   );

   @FormUrlEncoded
   @POST(get_address)
    Call<getAddress>get_address(
           @Field("user_id") String user_id
   );

 @FormUrlEncoded
   @POST(get_profile)
    Call<myprofile_model>get_profile(
           @Field("user_id") String user_id
   );

   @FormUrlEncoded
   @POST(signUp)
    Call<ResponseBody>signUp(
           @Field("username") String username,
           @Field("email") String email,
           @Field("password") String password,
           @Field("mobile") String mobile,
           @Field("type") String type,
           @Field("lat") String lat,
           @Field("lon") String lon,
           @Field("register_id") String register_id
   );

    @Multipart
    @POST(add_details)
    Call<ResponseBody>add_details(
            @Part("user_id") RequestBody user_id,
            @Part("about") RequestBody about,
            @Part("location") RequestBody location,
            @Part("dob") RequestBody dob,
            @Part("education") RequestBody education,
            @Part("gender") RequestBody gender,
            @Part("language") RequestBody language,
            @Part("award") RequestBody award,
            @Part("certification") RequestBody certification,
            @Part("affiliations") RequestBody affiliations,
            @Part("where_to_teach") RequestBody where_to_teach,
            @Part("teach_distance") RequestBody teach_distance,
            @Part("time_zone") RequestBody time_zone,
            @Part("monday") RequestBody monday,
            @Part("tuesday") RequestBody tuesday,
            @Part("wednesday") RequestBody wednesday,
            @Part("thursday") RequestBody thursday,
            @Part("friday") RequestBody friday,
            @Part("saturday") RequestBody saturday,
            @Part("sunday") RequestBody sunday,
            @Part("tutorcategory") RequestBody tutorcategory,
            @Part("tutorsubcategory") RequestBody tutorsubcategory,
            @Part("tutorsubject") RequestBody tutorsubject,
            @Part("per_hour_payment") RequestBody per_hour_payment,
            @Part("full_course_time") RequestBody full_course_time,
            @Part("per_hour_payment_group") RequestBody per_hour_payment_group,
            @Part("full_course_time_group") RequestBody full_course_time_group,
            @Part("check_status") RequestBody check_status,
           @Part MultipartBody.Part part,
           @Part MultipartBody.Part part1,
           @Part MultipartBody.Part part2
   );



   @FormUrlEncoded
   @POST(forgot_password)
    Call<ResponseBody>forgotPassword(
           @Field("email") String email
   );


    @POST(get_tutor_category)
    Call<TutorCategory_Model> get_tutor_category();

    @FormUrlEncoded
    @POST(get_tutor_sub_category)
    Call<TutorSubCategory>get_tutor_sub_category(
            @Field("category_id") String category_id
    );

    @FormUrlEncoded
    @POST(get_tutor_subject)
    Call<TutorSubjectMode>get_tutor_subject(
            @Field("category_id") String category_id,
            @Field("sub_category_id") String sub_category_id
    );

    @FormUrlEncoded
    @POST(get_calculation)
    Call<GetCalculaterModel>get_calculation(
            @Field("tutor_id") String tutor_id,
            @Field("per_hour_payment") String per_hour_payment,
            @Field("full_course_time") String full_course_time
    );

}
