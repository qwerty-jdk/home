package com.jdk.qwerty.home.Objects;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrador on 04/12/2017.
 */

public class _Temp extends Sensor {

    private Double maxTemp;

    public _Temp(){
        this.setMaxTemp(-1.0);
    }

    public _Temp(String _ubication, Type_Sensor _type, Status_Sensor _status, int _image, Double _maxTemp){
        super(_ubication, _type, _status, _image);
        this.setMaxTemp(getMaxTemp());
    }

    public Double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(Double maxTemp) {
        this.maxTemp = maxTemp;
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
