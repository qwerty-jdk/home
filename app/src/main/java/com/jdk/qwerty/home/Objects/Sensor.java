package com.jdk.qwerty.home.Objects;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrador on 02/12/2017.
 */

public class Sensor {

    private String ubication;
    private typeSensor type;
    private statusSensor status;
    private int image;

    public Sensor(){
        this.setUbication("");
        this.setType(null);
        this.setStatus(statusSensor.Off);
        this.setImage(-1);
    }

    public Sensor(String location, typeSensor type, statusSensor status, int image){
        this.setUbication(location);
        this.setType(type);
        this.setStatus(status);
        this.setImage(image);
    }

    public String getUbication() {
        return ubication;
    }

    public void setUbication(String ubication) {
        this.ubication = ubication;
    }

    public typeSensor getType() {
        return type;
    }

    public void setType(typeSensor type) {
        this.type = type;
    }

    public statusSensor getStatus() {
        return status;
    }

    public void setStatus(statusSensor status) {
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