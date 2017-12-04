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
import android.widget.Toast;

import com.jdk.qwerty.home.Adapter.RecSensorsAdapter;
import com.jdk.qwerty.home.Objects.Sensor;
import com.jdk.qwerty.home.Objects.Status_Sensor;
import com.jdk.qwerty.home.Objects.Type_Sensor;
import com.jdk.qwerty.home.R;

import java.util.ArrayList;

/**
 * Created by Administrador on 02/12/2017.
 */

public class Door extends Fragment {
    private static final String TAG = "Door tab";
    private RecyclerView recSensors;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Choose the Layout for my Fragment
        View view = inflater.inflate(R.layout.door_tab, container, false);

        //Using door_tab.xml objects with view.
        recSensors = (RecyclerView) view.findViewById(R.id.recSensors);
        ArrayList<Sensor> sensors = new ArrayList<>();
        sensors.add(new Sensor("Hall", Type_Sensor.Door, Status_Sensor.Off, R.drawable.door));
        sensors.add(new Sensor("Living", Type_Sensor.Door, Status_Sensor.Auto, R.drawable.door));
        sensors.add(new Sensor("Kitchen", Type_Sensor.Door, Status_Sensor.On, R.drawable.door));

        //recSensors.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        recSensors.setLayoutManager(new GridLayoutManager(this.getContext(), 2));

        RecSensorsAdapter adapter = new RecSensorsAdapter(view.getContext(), sensors);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pisition = recSensors.getChildAdapterPosition(view);
                Toast.makeText(getContext(), "Door#" + pisition + ": setOnClickListener implementado en clase RecSensorsAdapter.", Toast.LENGTH_SHORT).show();
            }
        });
        recSensors.setAdapter(adapter);

        return view;
    }
}

