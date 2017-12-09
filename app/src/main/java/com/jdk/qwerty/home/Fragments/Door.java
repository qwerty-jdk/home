package com.jdk.qwerty.home.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.jdk.qwerty.home.Adapter.RecSensorsAdapter;
import com.jdk.qwerty.home.MainActivity;
import com.jdk.qwerty.home.Objects.Sensor;
import com.jdk.qwerty.home.Objects.Status_Sensor;
import com.jdk.qwerty.home.Objects.Type_Sensor;
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
   // private ImageView ImageButtonDoor;

    /*private void Start(){
        //Default state of ImageButton and respective tag for next id
        ImageButtonDoor.setBackground(getResources().getDrawable(R.drawable.door_off));
        ImageButtonDoor.setTag("off"); //off id
    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Choose the Layout for my Fragment
        View view = inflater.inflate(R.layout.door_tab, container, false);

        //ImageButtonDoor = (ImageButton) view.findViewById(R.id.ImageButtonDoor);
        /*
        ImageButtonDoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (ImageButtonDoor.getTag().toString()){
                    case "on":
                        ImageButtonDoor.setBackground(getResources().getDrawable(R.drawable.door_off));
                        ImageButtonDoor.setTag("off");
                        break;
                    case "off":
                        ImageButtonDoor.setBackground(getResources().getDrawable(R.drawable.door_on));
                        ImageButtonDoor.setTag("on");
                        break;
                }
            }
        }); */

        //Using door_tab.xml objects with view.
        recSensors = (RecyclerView) view.findViewById(R.id.recSensors);
        sensors = new ArrayList<>();
        Sensor door = null;

        try{
            JSONObject json = new JSONObject(MainActivity.My_Controller.getMotorEstac());
            Status_Sensor status;
            switch (json.getString("status")){
                case "Auto": status = Status_Sensor.Off; break;
                case "On": status = Status_Sensor.On; break;
                case "Off": status = Status_Sensor.Off; break;
                default: status = Status_Sensor.Off; break;
            }
            door = new Sensor(json.getString("ubication"), Type_Sensor.Door,  status, R.drawable.door_off);
        }catch (JSONException e) {}

        if(door != null)
            sensors.add(door);
        else
            sensors.add(new Sensor("PORTÓN", Type_Sensor.Door, Status_Sensor.Off, R.drawable.door_off));

        recSensors.setLayoutManager(new GridLayoutManager(this.getContext(), 1));
        RecSensorsAdapter adapter = new RecSensorsAdapter(view.getContext(), sensors);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sensor data = sensors.get(recSensors.getChildAdapterPosition(view));
                switch (data.getStatus()){
                    case On:
                        data.setStatus(Status_Sensor.Off);
                        break;
                    case Off:
                        data.setStatus(Status_Sensor.On);
                        break;
                }
                MainActivity.My_Controller.setMotorEstac(data.toJSON());
                Toast.makeText(getContext(), data.getStatus().toString().equals("On") ? "ABRIENDO": "CERRANDO", Toast.LENGTH_SHORT).show();
            }
        });
        recSensors.setAdapter(adapter);
      //  this.Start();

        return view;
    }
}

