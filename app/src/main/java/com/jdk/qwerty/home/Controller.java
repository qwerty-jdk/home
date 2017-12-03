package com.jdk.qwerty.home;

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
    private String TempeHabTwo = "/" + casa + "/" + temperatura + "/" + habitacion + "/" + uno;
    private String TempeHabTree = "/" + casa + "/" + temperatura + "/" + habitacion + "/" + uno;

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

    public void setLuzHabOne(float value){
        EditSettings.putFloat(LuzHabOne, value);
        EditSettings.commit();
    }

    public float getLuzHabOne(){
        return Settings.getFloat(LuzHabOne, 0);
    }

    public void  setLuzHabTwo(float value){
        EditSettings.putFloat(LuzHabTwo, value);
        EditSettings.commit();
    }

    public float getLuzHabTwo(){
        return Settings.getFloat(LuzHabTwo, 0);
    }

    public void  setLuzHabTree(float value){
        EditSettings.putFloat(LuzHabTree, value);
        EditSettings.commit();
    }

    public float getLuzHabTree(){
        return Settings.getFloat(LuzHabTree, 0);
    }

    public void  setLuzBanOne(float value){
        EditSettings.putFloat(LuzBanOne, value);
        EditSettings.commit();
    }

    public float getLuzBanOne(){
        return Settings.getFloat(LuzBanOne, 0);
    }

    public void  setLuzBanTwo(float value){
        EditSettings.putFloat(LuzBanTwo, value);
        EditSettings.commit();
    }

    public float getLuzBanTwo(){
        return Settings.getFloat(LuzBanTwo, 0);
    }

    public void  setLuzCocina(float value){
        EditSettings.putFloat(LuzCocina, value);
        EditSettings.commit();
    }

    public float getLuzCocina(){
        return Settings.getFloat(LuzCocina,0);
    }

    public void  setLuzSala(float value){
        EditSettings.putFloat(LuzSala, value);
        EditSettings.commit();
    }

    public float getLuzSala(){
        return Settings.getFloat(LuzSala,0);
    }

    public void  setLuzEstac(float value){
        EditSettings.putFloat(LuzEstac, value);
        EditSettings.commit();
    }

    public float getLuzEstac(){
        return Settings.getFloat(LuzEstac,0);
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

    public void setTempeHabOne(float value){
        EditSettings.putFloat(TempeHabOne, value);
        EditSettings.commit();
    }

    public float getTempeHabOne(){
        return Settings.getFloat(TempeHabOne,0);
    }

    public void  setTempeHabTwo(float value){
        EditSettings.putFloat(TempeHabTwo, value);
        EditSettings.commit();
    }

    public float getTempeHabTwo(){
        return Settings.getFloat(TempeHabTwo,0);
    }

    public void  setTempeHabTree(float value){
        EditSettings.putFloat(TempeHabTree, value);
        EditSettings.commit();
    }

    public float getTempeHabTree(){
        return Settings.getFloat(TempeHabTree,0);
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

    public void setMotorPuerta(float value){
        EditSettings.putFloat(MotorPuerta, value);
        EditSettings.commit();
    }

    public float getMotorEstac(){
        return Settings.getFloat(MotorPuerta,0);
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
