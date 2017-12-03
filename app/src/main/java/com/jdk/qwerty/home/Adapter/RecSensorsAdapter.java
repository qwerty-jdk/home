package com.jdk.qwerty.home.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jdk.qwerty.home.Objects.Sensor;
import com.jdk.qwerty.home.R;

import java.util.ArrayList;

/**
 * Created by Administrador on 02/12/2017.
 */

public class RecSensorsAdapter extends RecyclerView.Adapter<RecSensorsAdapter.SensorViewHolder>  {

    private final Context context;
    private final ArrayList<Sensor> sensors;
    private int count = 0;

    public RecSensorsAdapter(Context context, ArrayList<Sensor> sensors){
        this.context = context;
        this.sensors = sensors;
    }

    @Override
    public SensorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        Log.d("counts", String.valueOf(++count));
        return new SensorViewHolder(inflater.inflate(R.layout.sensor_row, parent, false));
    }

    @Override
    public void onBindViewHolder(SensorViewHolder holder, int position) {
        try {
            Sensor sensor = sensors.get(position);
            holder.txtName.setText(sensor.getName());
            holder.txtType.setText(sensor.getType());
            holder.txtStatus.setText(sensor.getStatus());
            holder.imgSensor.setImageResource(sensor.getImage());
        }catch(Exception ex){
            System.out.println("KLG-Error en " + this.getClass().toString() + ".onBindViewHolder(): " + ex.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return sensors.size();
    }

    class SensorViewHolder extends RecyclerView.ViewHolder {
        final TextView txtName;
        final TextView txtType;
        final TextView txtStatus;
        final ImageView imgSensor;

        SensorViewHolder(View view) {
            super(view);
            txtName = (TextView)  view.findViewById(R.id.txtName);
            txtType = (TextView)  view.findViewById(R.id.txtType);
            txtStatus = (TextView)  view.findViewById(R.id.txtStatus);
            imgSensor = (ImageView) view.findViewById(R.id.imgSensor);
        }
    }
}
