package com.jdk.qwerty.home.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jdk.qwerty.home.Adapter.RecSensorsAdapter;
import com.jdk.qwerty.home.MainActivity;
import com.jdk.qwerty.home.Objects.Sensor;
import com.jdk.qwerty.home.Objects.statusSensor;
import com.jdk.qwerty.home.Objects.typeSensor;
import com.jdk.qwerty.home.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrador on 02/12/2017.
 */

public class Door extends Fragment {


    private static final String TAG = "Door tab";
    private RecyclerView recSensors;
    private ArrayList<Sensor> sensors;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Choose the Layout for my Fragment
        View view = inflater.inflate(R.layout.door_tab, container, false);

        //Using door_tab.xml objects with view.
        recSensors = (RecyclerView) view.findViewById(R.id.recSensorsLight);
        sensors = new ArrayList<>();
        Sensor door = null;

        try{
            JSONObject json = new JSONObject(MainActivity.My_Controller.getMotorEstac());
            statusSensor status;
            switch (json.getString("status")){
                case "Auto": status = statusSensor.Off; break;
                case "On": status = statusSensor.On; break;
                case "Off": status = statusSensor.Off; break;
                default: status = statusSensor.Off; break;
            }
            door = new Sensor(json.getString("ubication"), typeSensor.Door,  status, R.drawable.door_off);
        }catch (JSONException e) {}

        if(door != null)
            sensors.add(door);
        else
            sensors.add(new Sensor("PORTÓN", typeSensor.Door, statusSensor.Off, R.drawable.door_off));

        recSensors.setLayoutManager(new GridLayoutManager(this.getContext(), 1));
        RecSensorsAdapter adapter = new RecSensorsAdapter(view.getContext(), sensors);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sensor data = sensors.get(recSensors.getChildAdapterPosition(view));
                switch (data.getStatus()){
                    case On:
                        data.setStatus(statusSensor.Off);
                        break;
                    case Off:
                        data.setStatus(statusSensor.On);
                        break;
                }
                MainActivity.My_Controller.setMotorEstac(data.toJSON());
                Toast.makeText(getContext(), data.getStatus().toString().equals("On") ? "ABRIENDO": "CERRANDO", Toast.LENGTH_SHORT).show();
            }
        });
        recSensors.setAdapter(adapter);

        return view;
    }
}

