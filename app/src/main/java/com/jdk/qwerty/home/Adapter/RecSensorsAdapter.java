package com.jdk.qwerty.home.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jdk.qwerty.home.MainActivity;
import com.jdk.qwerty.home.Objects.Sensor;
import com.jdk.qwerty.home.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by Administrador on 02/12/2017.
 */

public class RecSensorsAdapter extends RecyclerView.Adapter<RecSensorsAdapter.SensorViewHolder> implements View.OnClickListener {

    private final Context context;
    private final ArrayList<Sensor> sensors;
    private int count = 0;
    private View.OnClickListener listenerOnClick;

    public RecSensorsAdapter(Context context, ArrayList<Sensor> sensors){
        this.context = context;
        this.sensors = sensors;
    }

    @Override
    public SensorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        /*LayoutInflater inflater = LayoutInflater.from(context);
        Log.d("counts", String.valueOf(++count));
        return new SensorViewHolder(inflater.inflate(R.layout.sensor_row, parent, false));*/

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sensor_row, null, false);
        view.setOnClickListener(this);
        return new SensorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SensorViewHolder holder, final int position) {
        try {
            Sensor sensor = sensors.get(position);
            holder.txtName.setText(sensor.getUbication());
            //holder.txtType.setText(sensor.getType());
            //holder.txtStatus.setText(sensor.getStatus());
            holder.imgSensor.setImageResource(sensor.getImage());
        }catch(Exception ex){
            System.out.println("KLG-Error en " + this.getClass().toString() + ".onBindViewHolder(): " + ex.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return sensors.size();
    }

    public void setOnClickListener(View.OnClickListener _listenerOnClick){
        this.listenerOnClick = _listenerOnClick;
    }

    @Override
    public void onClick(View view) {
        if(this.listenerOnClick != null){
            this.listenerOnClick.onClick(view);
        }
    }

    class SensorViewHolder extends RecyclerView.ViewHolder {
        final TextView txtName;
        //final TextView txtType;
        //final TextView txtStatus;
        final ImageView imgSensor;

        SensorViewHolder(View view) {
            super(view);
            txtName = (TextView)  view.findViewById(R.id.txtName);
            //txtType = (TextView)  view.findViewById(R.id.txtType);
            //txtStatus = (TextView)  view.findViewById(R.id.txtStatus);
            imgSensor = (ImageView) view.findViewById(R.id.imgSensor);
        }
    }
}
