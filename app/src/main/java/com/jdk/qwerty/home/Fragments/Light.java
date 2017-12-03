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
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.jdk.qwerty.home.Adapter.RecSensorsAdapter;
import com.jdk.qwerty.home.Objects.Sensor;
import com.jdk.qwerty.home.R;

import java.util.ArrayList;

/**
 * Created by Administrador on 02/12/2017.
 */

public class Light extends Fragment {
    private static final String TAG = "Light tab";
    private RecyclerView recSensors;
    private ImageView Image;
    private Switch _Switch;
    private SeekBar _SeekBar;
    private TextView _TextView;
    private LinearLayout _Manager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Choose the Layout for my Fragment
        View view = inflater.inflate(R.layout.light_tab, container, false);

        Image = (ImageView)view.findViewById(R.id.Image);
        _Manager = (LinearLayout)view.findViewById(R.id.manager);
        _Switch = (Switch)view.findViewById(R.id.switchAuto);
        _SeekBar = (SeekBar)view.findViewById(R.id.seekBar);
        _TextView = (TextView)view.findViewById(R.id.txtSeekBarDescription);

        Image.setImageResource(R.drawable.light);

        _Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                _SeekBar.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                _TextView.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            }
        });
        _SeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                switch (progress){
                    case 0: _TextView.setText("BAJO"); break;
                    case 1: _TextView.setText("MEDIO"); break;
                    case 2: _TextView.setText("ALTO"); break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        _TextView.setText("MEDIO");


        //Using light_tab.xml objects with view.
        recSensors = (RecyclerView) view.findViewById(R.id.recSensors);
        ArrayList<Sensor> sensors = new ArrayList<>();
        sensors.add(new Sensor("Hall", "Light", "OFF", R.drawable.light));
        sensors.add(new Sensor("Living", "Light", "AUTO", R.drawable.light));
        sensors.add(new Sensor("Kitchen", "Light", "ON", R.drawable.light));

        //recSensors.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        recSensors.setLayoutManager(new GridLayoutManager(this.getContext(), 2));

        RecSensorsAdapter adapter = new RecSensorsAdapter(view.getContext(), sensors);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeShow(true);
            }
        });
        recSensors.setAdapter(adapter);
        changeShow(false);
        return view;
    }

    private void changeShow(Boolean show){
        _Manager.setVisibility(show ? View.VISIBLE: View.GONE);
        recSensors.setVisibility(show ? View.GONE: View.VISIBLE);
    }

}

