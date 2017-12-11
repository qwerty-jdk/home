package com.jdk.qwerty.home.restAPI;

import android.content.SharedPreferences;

import com.jdk.qwerty.home.Objects.door;
import com.jdk.qwerty.home.Objects.light;
import com.jdk.qwerty.home.Objects.temp;
import com.jdk.qwerty.home.Objects.user;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrador on 03/12/2017.
 */

public class Controller {

    public static SharedPreferences Settings;
    public static SharedPreferences.Editor EditSettings;
    private Retrofit retrofit;
    private RestClient restClient;

    //Sensores
    private final String led = "light";
    private final String fotoresistencia = "photoresist";
    private final String temperatura = "temperature";
    private final String servomotor = "servomotor";
    private final String motor = "motor";

    //Capacidades
    private final String uno = "one";
    private final String dos = "two";
    private final String tres = "tree";

    //Ubicaciones
    private final String casa = "home";
    private final String habitacion = "bedroom";
    private final String banio = "bathroom";
    private final String estacionamiento = "parking";
    private final String sala = "living_room";
    private final String cocina = "kitchen";

    //############### START ÁREAS

    //LUCES
    private String LuzHabOne = "/" + casa + "/" + led + "/" + habitacion + "/" + uno;
    private String LuzHabTwo = "/" + casa + "/" + led + "/" + habitacion + "/" + dos;
    private String LuzHabTree = "/" + casa + "/" + led + "/" + habitacion + "/" + tres;
    private String LuzBanOne = "/" + casa + "/" + led + "/" + banio + "/" + uno;
    private String LuzBanTwo = "/" + casa + "/" + led + "/" + banio + "/" + dos;
    private String LuzCocina = "/" + casa + "/" + led + "/" + cocina;
    private String LuzEstac = "/" + casa + "/" + led + "/" + estacionamiento;
    private String LuzSala = "/" + casa + "/" + led + "/" + sala;

    //FOTORESISTENCIAS
    private String FotoResHabOne = "/" + casa + "/" + fotoresistencia + "/" + habitacion + "/" + uno;
    private String FotoResHabTwo  = "/" + casa + "/" + fotoresistencia + "/" + habitacion + "/" + dos;
    private String FotoResHabTree = "/" + casa + "/" + fotoresistencia + "/" + habitacion + "/" + tres;
    private String FotoResBanOne = "/" + casa + "/" + fotoresistencia + "/" + banio + "/" + uno;
    private String FotoResBanTwo = "/" + casa + "/" + fotoresistencia + "/" + banio + "/" + dos;
    private String FotoResCocina = "/" + casa + "/" + fotoresistencia + "/" + cocina;
    private String FotoResEstac = "/" + casa + "/" + fotoresistencia + "/" + estacionamiento;
    private String FotoResSala = "/" + casa + "/" + fotoresistencia + "/" + sala ;

    //ACONDICIONAMIENTO
    /*private String AireHabOne = "/" + casa + "/" + acondicionamiento + "/" + habitacion + "/" + uno;
    private String AireHabTwo = "/" + casa + "/" + acondicionamiento + "/" + habitacion + "/" + uno;
    private String AireHabTree = "/" + casa + "/" + acondicionamiento + "/" + habitacion + "/" + uno;*/

    //TEMPERATURA
    private String TempeHabOne = "/" + casa + "/" + temperatura + "/" + habitacion + "/" + uno;
    private String TempeHabTwo = "/" + casa + "/" + temperatura + "/" + habitacion + "/" + dos;
    private String TempeHabTree = "/" + casa + "/" + temperatura + "/" + habitacion + "/" + tres;

    //MOTORES
    private String MotorAire = "/" + casa + "/" + motor + "/" + uno;
    private String MotorPuerta = "/" + casa + "/" + motor + "/" + dos;
    private String SerMotorAire = "/" + casa + "/" + servomotor;

    //############### END ÁREAS

    public Controller(SharedPreferences sharedPreferences){

        Settings = sharedPreferences;
        EditSettings = Settings.edit();

        retrofit = new Retrofit.Builder()
                .baseUrl(RestClient.BASE_URL)//"https://api-rest-mqtt.herokuapp.com"
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        restClient = retrofit.create(RestClient.class);

    }

    //########## START USER

    public void signUp(user _user, Callback<user> callback){
        Call<user> call = this.restClient.signUp(_user);
        call.enqueue(callback);
    }

    private Boolean isSignIn;
    public void signIn(user _user, Callback<user> callback){
        Call<user> call = this.restClient.signIn(_user);
        call.enqueue(callback);
    }

    public void setToken(String value){
        EditSettings.putString("token", value);
        EditSettings.commit();
    }

    private String getToken(){
        return "Bearer " + "code";
        //return "Bearer " + Settings.getString("token", "none");
    }

    //########## END USER

    //########## START LUZ

    public void getLightAll(Callback<List<light>> callback){
        Call<List<light>> call = this.restClient.getLights(this.getToken());
        call.enqueue(callback);
    }

    public void setLight(light data, Callback<light> callback){
        Call<light> call = this.restClient.setLight(this.getToken(), data);
        call.enqueue(callback);
    }

    public void getTempAll(Callback<List<temp>> callback){
        Call<List<temp>> call = this.restClient.getTemps(this.getToken());
        call.enqueue(callback);
    }

    public void setTemp(temp data, Callback<temp> callback){
        Call<temp> call = this.restClient.setTemp(this.getToken(), data);
        call.enqueue(callback);
    }

    public void setLuzHabOne(String value){
        EditSettings.putString(LuzHabOne, value);
        EditSettings.commit();
    }

    public String getLuzHabOne(){
        return Settings.getString(LuzHabOne, "none");
    }

    public void  setLuzHabTwo(String value){
        EditSettings.putString(LuzHabTwo, value);
        EditSettings.commit();
    }

    public String getLuzHabTwo(){
        return Settings.getString(LuzHabTwo, "none");
    }

    public void  setLuzHabTree(String value){
        EditSettings.putString(LuzHabTree, value);
        EditSettings.commit();
    }

    public String getLuzHabTree(){
        return Settings.getString(LuzHabTree, "none");
    }

    public void  setLuzBanOne(String value){
        EditSettings.putString(LuzBanOne, value);
        EditSettings.commit();
    }

    public String getLuzBanOne(){
        return Settings.getString(LuzBanOne, "none");
    }

    public void  setLuzBanTwo(String value){
        EditSettings.putString(LuzBanTwo, value);
        EditSettings.commit();
    }

    public String getLuzBanTwo(){
        return Settings.getString(LuzBanTwo, "none");
    }

    public void  setLuzCocina(String value){
        EditSettings.putString(LuzCocina, value);
        EditSettings.commit();
    }

    public String getLuzCocina(){
        return Settings.getString(LuzCocina,"none");
    }

    public void  setLuzSala(String value){
        EditSettings.putString(LuzSala, value);
        EditSettings.commit();
    }

    public String getLuzSala(){
        return Settings.getString(LuzSala,"none");
    }

    public void  setLuzEstac(String value){
        EditSettings.putString(LuzEstac, value);
        EditSettings.commit();
    }

    public String getLuzEstac(){
        return Settings.getString(LuzEstac,"none");
    }

    //########## END LUZ

    //########### START FOTORESISTENCIAS

    public void setFotoResHabOne(float value){
        EditSettings.putFloat(FotoResHabOne, value);
        EditSettings.commit();
    }

    public float getFotoResHabOne(){
        return Settings.getFloat(FotoResHabOne, 0);
    }

    public void  setFotoResHabTwo(float value){
        EditSettings.putFloat(FotoResHabTwo, value);
        EditSettings.commit();
    }

    public float getFotoResHabTwo(){
        return Settings.getFloat(FotoResHabTwo, 0);
    }

    public void  setFotoResHabTree(float value){
        EditSettings.putFloat(FotoResHabTree, value);
        EditSettings.commit();
    }

    public float getFotoResHabTree(){
        return Settings.getFloat(FotoResHabTree, 0);
    }

    public void  setFotoResBanOne(float value){
        EditSettings.putFloat(FotoResBanOne, value);
        EditSettings.commit();
    }

    public float getFotoResBanOne(){
        return Settings.getFloat(FotoResBanOne, 0);
    }

    public void  setFotoResBanTwo(float value){
        EditSettings.putFloat(FotoResBanTwo, value);
        EditSettings.commit();
    }

    public float getFotoResBanTwo(){
        return Settings.getFloat(FotoResBanTwo, 0);
    }

    public void  setFotoResCocina(float value){
        EditSettings.putFloat(FotoResCocina, value);
        EditSettings.commit();
    }

    public float getFotoResCocina(){
        return Settings.getFloat(FotoResCocina,0);
    }

    public void  setFotoResSala(float value){
        EditSettings.putFloat(FotoResSala, value);
        EditSettings.commit();
    }

    public float getFotoResSala(){
        return Settings.getFloat(FotoResSala,0);
    }

    public void  setFotoResEstac(float value){
        EditSettings.putFloat(FotoResEstac, value);
        EditSettings.commit();
    }

    public float getFotoResEstac(){
        return Settings.getFloat(FotoResEstac,0);
    }

    //########### END FOTORESISTENCIAS

    //########### START ACONDICIONAMIENTO

    /*public void setAireHabOne(float value){
        EditSettings.putFloat(AireHabOne, value);
    }

    public float getAireHabOne(){
        return Settings.getFloat(AireHabOne,0);
    }

    public void  setAireHabTwo(float value){
        EditSettings.putFloat(AireHabTwo, value);
    }

    public float getAireHabTwo(){
        return Settings.getFloat(AireHabTwo,0);
    }

    public void  setAireHabTree(float value){
        EditSettings.putFloat(AireHabTree, value);
    }

    public float getAireHabTree(){
        return Settings.getFloat(AireHabTree,0);
    }*/

    //########### END ACONDICIONAMIENTO

    //########### START TEMPERATURA

    public void setTempeHabOne(String value){
        EditSettings.putString(TempeHabOne, value);
        EditSettings.commit();
    }

    public String getTempeHabOne(){
        return Settings.getString(TempeHabOne,"none");
    }

    public void  setTempeHabTwo(String value){
        EditSettings.putString(TempeHabTwo, value);
        EditSettings.commit();
    }

    public String getTempeHabTwo(){
        return Settings.getString(TempeHabTwo,"none");
    }

    public void  setTempeHabTree(String value){
        EditSettings.putString(TempeHabTree, value);
        EditSettings.commit();
    }

    public String getTempeHabTree(){
        return Settings.getString(TempeHabTree,"none");
    }

    //########### END TEMPERATURA

    //########### START MOTOR

    public void setMotorAire(float value){
        EditSettings.putFloat(MotorAire, value);
        EditSettings.commit();
    }

    public float getMotorAire(){
        return Settings.getFloat(MotorAire,0);
    }

    public void setMotorEstac(door data, Callback<door> callback){
        data.setLocation(MotorPuerta);
        Call<door> call = this.restClient.setDoor(this.getToken(), data);
        call.enqueue(callback);
    }

    public void getMotorEstac(Callback<door> callback){
        Call<door> call = this.restClient.getDoor(this.getToken(), MotorPuerta);
        call.enqueue(callback);
    }

    public void setSerMotorAire(float value){
        EditSettings.putFloat(SerMotorAire, value);
        EditSettings.commit();
    }

    public float getSerMotorAire(){
        return Settings.getFloat(SerMotorAire,0);
    }

    //########### END MOTOR

}
