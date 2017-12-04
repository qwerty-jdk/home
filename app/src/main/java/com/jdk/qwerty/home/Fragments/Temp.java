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
import com.jdk.qwerty.home.Objects._Temp;
import com.jdk.qwerty.home.R;

import java.util.ArrayList;

/**
 * Created by Administrador on 02/12/2017.
 */

public class Temp extends Fragment {
    private static final String TAG = "Temp tab";
    private RecyclerView recSensors;
    //private Button btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Choose the Layout for my Fragment
        View view = inflater.inflate(R.layout.temp_tab, container, false);

        //Using temp_tab.xml objects with view.
        recSensors = (RecyclerView) view.findViewById(R.id.recSensors);
        ArrayList<Sensor> sensors = new ArrayList<>();
        sensors.add(new _Temp("Hall", Type_Sensor.Temp, Status_Sensor.Off, R.drawable.temp, 0.0));
        sensors.add(new _Temp("Living", Type_Sensor.Temp, Status_Sensor.Auto, R.drawable.temp, 0.0));
        sensors.add(new _Temp("Kitchen", Type_Sensor.Temp, Status_Sensor.On, R.drawable.temp, 0.0));

        //recSensors.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        recSensors.setLayoutManager(new GridLayoutManager(this.getContext(), 2));

        RecSensorsAdapter adapter = new RecSensorsAdapter(view.getContext(), sensors);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pisition = recSensors.getChildAdapterPosition(view);
                Toast.makeText(getContext(), "Temp#" + pisition + ": setOnClickListener implementado en clase RecSensorsAdapter.", Toast.LENGTH_SHORT).show();
            }
        });
        recSensors.setAdapter(adapter);

        return view;
    }
}
