package com.jdk.qwerty.home.Objects;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;

/**
 * Created by Administrador on 03/12/2017.
 */

public class Controller {

    public static SharedPreferences Settings;
    public static SharedPreferences.Editor EditSettings;

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

    }

    //########## START LUZ

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

    public void setMotorEstac(String  value){
        EditSettings.putString(MotorPuerta, value);
        EditSettings.commit();
    }

    public String getMotorEstac(){
        return Settings.getString(MotorPuerta,"none");
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
