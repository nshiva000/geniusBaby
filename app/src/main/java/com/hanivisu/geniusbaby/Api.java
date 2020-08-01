package com.hanivisu.geniusbaby;

import com.hanivisu.geniusbaby.Models.DODModel.DODModel;
import com.hanivisu.geniusbaby.Models.LoginModel.LoginModel;
import com.hanivisu.geniusbaby.Models.MusicListModel.MusicListModel;
import com.hanivisu.geniusbaby.Models.SignUpModel.SignUpModel;
import com.hanivisu.geniusbaby.Models.UpdateModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {


    @FormUrlEncoded
    @POST("signup.php")
    Call<SignUpModel> user_signUp(
            @Field("name") String name,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("password") String password
    );


    @FormUrlEncoded
    @POST("login.php")
    Call<LoginModel> user_login(
            @Field("phone") String phone,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("otpconfirm.php")
    Call<UpdateModel> activate_user(
            @Field("userid") String id
    );

    @FormUrlEncoded
    @POST("albums.php")
    Call<MusicListModel> getSongs(
            @Field("day") String day,
            @Field("week") String week,
            @Field("session") String seesion
    );

    @FormUrlEncoded
    @POST("dod.php")
    Call<DODModel> sendDOD(
            @Field("dod") String dod,
            @Field("userid") String id
    );


}
