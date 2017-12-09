package com.jdk.qwerty.home.Objects;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrador on 04/12/2017.
 */

public class light extends Sensor {

    private modeLight mode;
    private String methodName;

    public light(){
        this.setMode(null);
    }

    public light(String location, typeSensor type, statusSensor status, int image, modeLight mode, String methodName){
        super(location, type, status, image);
        this.setMode(mode);
        this.setMethodName(methodName);
    }

    public modeLight getMode() {
        return mode;
    }

    public void setMode(modeLight mode) {
        this.mode = mode;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public String toJSON(){
        try {
            JSONObject obj = super.getObjectJSON();
            if(obj != null){
                obj.put("mode", this.getMode());
                return obj.toString();
            }
            else
                return "none";
        } catch (JSONException e) {
            return "error";
        }
    }

}
