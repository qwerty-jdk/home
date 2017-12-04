package com.jdk.qwerty.home.Objects;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

/**
 * Created by Administrador on 02/12/2017.
 */

public class Sensor {

    private String ubication;
    private Type_Sensor type;
    private Status_Sensor status;
    private int image;

    public Sensor(){
        this.setUbication("");
        this.setType(null);
        this.setStatus(Status_Sensor.Off);
        this.setImage(-1);
    }

    public Sensor(String _ubication, Type_Sensor _type, Status_Sensor _status, int _image){
        this.setUbication(_ubication);
        this.setType(_type);
        this.setStatus(_status);
        this.setImage(_image);
    }

    public String getUbication() {
        return ubication;
    }

    public void setUbication(String ubication) {
        this.ubication = ubication;
    }

    public Type_Sensor getType() {
        return type;
    }

    public void setType(Type_Sensor type) {
        this.type = type;
    }

    public Status_Sensor getStatus() {
        return status;
    }

    public void setStatus(Status_Sensor status) {
        this.status = status;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    protected JSONObject getObjectJSON(){
        try {
            JSONObject obj = new JSONObject();
            obj.put("ubication", this.getUbication());
            obj.put("type", this.getType());
            obj.put("status", this.getStatus());
            obj.put("image", this.getImage());
            return obj;
        } catch (JSONException e) {
            return null;
        }
    }

    public String toJSON(){
        JSONObject obj = this.getObjectJSON();
        if(obj != null)
            return obj.toString();
        else
            return "none";
    }

}