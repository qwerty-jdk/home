package com.jdk.qwerty.home.Objects;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrador on 04/12/2017.
 */

public class _Temp extends Sensor {

    private Double maxTemp;
    private String methodName;

    public _Temp(){
        this.setMaxTemp(-1.0);
        this.setMethodName("");
    }

    public _Temp(String _ubication, Type_Sensor _type, Status_Sensor _status, int _image, Double _maxTemp, String _methodName){
        super(_ubication, _type, _status, _image);
        this.setMaxTemp(getMaxTemp());
        this.setMethodName(_methodName);
    }

    public Double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(Double maxTemp) {
        this.maxTemp = maxTemp;
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
                obj.put("max_temp", this.getMaxTemp());
                return obj.toString();
            }
            else
                return "none";
        } catch (JSONException e) {
            return "error";
        }
    }

}
