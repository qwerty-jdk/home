package com.jdk.qwerty.home.Objects;

/**
 * Created by Administrador on 02/12/2017.
 */

public class Sensor {

    String name;
    String type;
    String status;
    int image;

    public Sensor(String name, String type, String  status, int image) {
        this.name = name;
        this.type = type;
        this.status = status;
        this.image = image;
    }

    public String getName() {
        //return "Sector: " + name;
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return "Tipo: " + type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getStatus() {
        return "Estado: " + status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}