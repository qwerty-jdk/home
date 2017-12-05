package com.jdk.qwerty.home.Objects;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrador on 04/12/2017.
 */

public class _Light extends Sensor {

    private Mode_Light mode;
    private String methodName;

    public _Light(){
        this.setMode(null);
    }

    public _Light(String _ubication, Type_Sensor _type, Status_Sensor _status, int _image, Mode_Light _mode, String _methodName){
        super(_ubication, _type, _status, _image);
        this.setMode(_mode);
        this.setMethodName(_methodName);
    }

    public Mode_Light getMode() {
        return mode;
    }

    public void setMode(Mode_Light mode) {
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
