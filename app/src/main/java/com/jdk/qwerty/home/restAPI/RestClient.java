package com.jdk.qwerty.home.restAPI;

import com.jdk.qwerty.home.Objects.door;
import com.jdk.qwerty.home.Objects.light;
import com.jdk.qwerty.home.Objects.temp;
import com.jdk.qwerty.home.Objects.user;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by Administrador on 09/12/2017.
 */

public interface RestClient {

    public final static String BASE_URL = "https://api-rest-mqtt.herokuapp.com";

    @POST("SignIn")
    Call<user> signIn(@Body user _user);

    @POST("SignUp")
    Call<user> signUp(@Body user _user);

    @PUT("api/light")
    Call<light> setLight(@Header("authorization") String token, @Body light _light);

    @GET("api/lights")
    Call<List<light>> getLights(@Header("authorization") String token);

    @PUT("api/temp")
    Call<temp> setTemp(@Header("authorization") String token, @Body temp _temp);

    @GET("api/temps")
    Call<List<temp>> getTemps(@Header("authorization") String token);

    @PUT("api/door")
    Call<door> setDoor(@Header("authorization") String token, @Body door _door);

    @GET("api/door")
    Call<door> getDoor(@Header("authorization") String token, @Header("location") String location);

}
