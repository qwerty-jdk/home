package com.jdk.qwerty.home.Objects;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrador on 04/12/2017.
 */

public class temp extends door {

    private Integer maxTemp;

    public temp(String location, String displayName, String status, Integer image, Integer maxTemp){
        super(location, displayName, status, image);
        this.setMaxTemp(maxTemp);
    }

    public Integer getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(Integer maxTemp) {
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
