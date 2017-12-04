package com.jdk.qwerty.home.Fragments;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
    private ImageView ImageButton;
    private Switch _Switch;
    private SeekBar _SeekBar;
    private TextView _TextView;
    private LinearLayout _Manager;
    private RelativeLayout _BackLayout;
    private FloatingActionButton _fabBack;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Choose the Layout for my Fragment
        View view = inflater.inflate(R.layout.light_tab, container, false);

        //We indentify ids of light_tab.xml objects
        ImageButton = (ImageButton)view.findViewById(R.id.ImageButton);
        _Manager = view.findViewById(R.id.manager);
        _BackLayout = view.findViewById(R.id.backLayout);
        _Switch = view.findViewById(R.id.switchAuto);
        _SeekBar = view.findViewById(R.id.seekBar);
        _TextView = view.findViewById(R.id.txtSeekBarDescription);
        _fabBack = view.findViewById(R.id.floatingActionButton);


        //Default state of ImageButton and respective tag for next id
        ImageButton.setImageResource(R.drawable.light_off);
        ImageButton.setTag("off"); //off id

        //Default Visibility
        _SeekBar.setVisibility(View.GONE);
        _TextView.setVisibility(View.GONE);
        _Switch.setVisibility(View.GONE);

        //ImageButton Click Listener
        ImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If tags appears to be off change to on
                if (ImageButton.getTag().equals("off")){
                    ImageButton.setImageResource(R.drawable.light_on);
                    ImageButton.setTag("on");
                    _Switch.setVisibility(View.VISIBLE);
                    _Switch.setChecked(true);
                } else if (ImageButton.getTag().equals("on")){
                    ImageButton.setImageResource(R.drawable.light_off);
                    ImageButton.setTag("off");
                    _Switch.setVisibility(View.GONE);
                    _SeekBar.setVisibility(View.GONE);
                    _TextView.setVisibility(View.GONE);
                }
            }
        });

        //Switch checked change listener
        _Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    _SeekBar.setVisibility(View.GONE);
                    _TextView.setVisibility(View.GONE);
                } else {
                    _SeekBar.setVisibility(View.VISIBLE);
                    _TextView.setVisibility(View.VISIBLE);
                    _TextView.setText("MEDIO");
                }
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
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });

        _fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeShow(false);
            }
        });

        //Using light_tab.xml objects with view.
        recSensors = view.findViewById(R.id.recSensors);
        ArrayList<Sensor> sensors = new ArrayList<>();
        sensors.add(new Sensor("Hall", "Light", "OFF", R.drawable.light));
        sensors.add(new Sensor("Living", "Light", "AUTO", R.drawable.light));
        sensors.add(new Sensor("Kitchen", "Light", "ON", R.drawable.light));

        recSensors.setLayoutManager(new GridLayoutManager(view.getContext(), 2));

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

    //Change show between recycler item view and recycler list item view...
    private void changeShow(Boolean show){
        if (show) {
            _Manager.setVisibility(View.VISIBLE);
            _BackLayout.setVisibility(View.VISIBLE);
            recSensors.setVisibility(View.GONE);
        } else {
            _Manager.setVisibility(View.GONE);
            _BackLayout.setVisibility(View.GONE);
            recSensors.setVisibility(View.VISIBLE);
        }
    }

}

