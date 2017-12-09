package com.jdk.qwerty.home.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jdk.qwerty.home.Adapter.RecSensorsAdapter;
import com.jdk.qwerty.home.MainActivity;
import com.jdk.qwerty.home.Objects.modeLight;
import com.jdk.qwerty.home.Objects.Sensor;
import com.jdk.qwerty.home.Objects.statusSensor;
import com.jdk.qwerty.home.Objects.typeSensor;
import com.jdk.qwerty.home.Objects.light;
import com.jdk.qwerty.home.R;

import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by Administrador on 02/12/2017.
 */

public class Light extends Fragment {

    private static final String TAG = "Light tab";
    private RecyclerView recSensors;
    private ImageView imageButton;
    private SeekBar seekBar;
    private TextView textView;
    private LinearLayout manager;
    private RelativeLayout backlayout;
    private ImageButton imageButtonOk;
    private ImageButton imageButtonCancel;

    private ArrayList<Sensor> sensors;

    private void Start(){

        //Default state of imageButton and respective tag for next id
        imageButton.setBackground(getResources().getDrawable(R.drawable.light_off));
        imageButton.setTag("off");

        //Default Visibility
        seekBar.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);

        changeShow(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Choose the Layout for my Fragment
        View view = inflater.inflate(R.layout.light_tab, container, false);

        //We indentify ids of light_tab.xml objects
        imageButton = (ImageButton)view.findViewById(R.id.imageButtonLight);
        manager = view.findViewById(R.id.managerLight);
        backlayout = view.findViewById(R.id.backLayoutLight);
        seekBar = view.findViewById(R.id.seekBarLight);
        textView = view.findViewById(R.id.txtDescriptionLight);
        imageButtonOk = view.findViewById(R.id.okButtonLight);
        imageButtonCancel = view.findViewById(R.id.cancelButtonLight);

        //imageButton Click Listener
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If tags appears to be off change to on
                switch (imageButton.getTag().toString()){
                    case "on":
                        imageButton.setBackground(getResources().getDrawable(R.drawable.light_off));
                        imageButton.setTag("off");
                        seekBar.setVisibility(View.GONE);
                        textView.setVisibility(View.GONE);
                        break;
                    case "off":
                        imageButton.setBackground(getResources().getDrawable(R.drawable.light_auto_2));
                        imageButton.setTag("auto");
                        seekBar.setVisibility(View.VISIBLE);
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("MEDIO");
                        break;
                    case "auto":
                        imageButton.setBackground(getResources().getDrawable(R.drawable.light_on));
                        imageButton.setTag("on");
                        seekBar.setVisibility(View.GONE);
                        textView.setVisibility(View.GONE);
                        break;
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                switch (progress){
                    case 0: textView.setText("BAJO"); break;
                    case 1: textView.setText("MEDIO"); break;
                    case 2: textView.setText("ALTO"); break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });

        imageButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                light data = getForm();

                try {
                    for(Method method: MainActivity.My_Controller.getClass().getMethods()){
                        if(method.getName().equals("set" + recSensors.getTag().toString())){
                            try { method.invoke(MainActivity.My_Controller, data.toJSON()); } catch (Exception ex ){}
                            Toast.makeText(getContext(), "Saved successfully.", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                } catch (Error e) { Toast.makeText(getContext(), "Ooops Wild error appears! ...", Toast.LENGTH_SHORT).show(); }
                Start();
            }
        });

        imageButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Start();
            }
        });

        //Using light_tab.xml objects with view.
        recSensors = view.findViewById(R.id.recSensorsLight);
        sensors = new ArrayList<>();
        typeSensor defaultType = typeSensor.Light;
        statusSensor defaultStatus = statusSensor.Off;
        modeLight defaultMode = modeLight.Low;
        sensors.add(new light("Habitación Matrimonial", defaultType, defaultStatus, R.drawable.principal, defaultMode, "LuzHabOne"));
        sensors.add(new light("Habitación Niños", defaultType, defaultStatus, R.drawable.secundary, defaultMode, "LuzHabTwo"));
        sensors.add(new light("Habitación Bebés", defaultType, defaultStatus, R.drawable.kids, defaultMode, "LuzHabTree"));
        sensors.add(new light("Baño público", defaultType, defaultStatus, R.drawable.bpublic, defaultMode, "LuzBanOne"));
        sensors.add(new light("Baño Privado", defaultType, defaultStatus, R.drawable.bprivate, defaultMode, "LuzBanTwo"));
        sensors.add(new light("Cocina", defaultType, defaultStatus, R.drawable.kitchen, defaultMode, "LuzCocina"));
        sensors.add(new light("Living", defaultType, defaultStatus, R.drawable.living, defaultMode, "LuzSala"));
        sensors.add(new light("Estacionamiento", defaultType, defaultStatus, R.drawable.garage, defaultMode, "LuzEstac"));

        recSensors.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        RecSensorsAdapter adapter = new RecSensorsAdapter(view.getContext(), sensors);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                changeShow(true);
                //def == default
                light def = (light)sensors.get(recSensors.getChildAdapterPosition(view));
                recSensors.setTag(def.getMethodName());

                Object json = null;
                try {
                    for(Method method: MainActivity.My_Controller.getClass().getMethods()){
                        if(method.getName().equals("get" + def.getMethodName())){
                            try {
                               json = method.invoke(MainActivity.My_Controller);
                            } catch (Exception ex ){}
                            break;
                        }
                    }
                } catch (Error e) { }

                light data = null;
                if(json != null)
                    data = parseJsonToLight(json.toString(), def.getMethodName());

                setForm(data != null ? data : def);
            }
        });
        recSensors.setAdapter(adapter);

        this.Start();
        return view;
    }

    private light parseJsonToLight(String s, String methodName){
        try {
            JSONObject json = new JSONObject(s);

            int image;
            statusSensor status;
            switch (json.getString("status")){
                case "Auto":
                    status = statusSensor.Auto;
                    image = R.drawable.light_auto_2;
                    break;
                case "On":
                    status = statusSensor.On;
                    image = R.drawable.light_on;
                    break;
                case "Off":
                    status = statusSensor.Off;
                    image = R.drawable.light_off;
                    break;
                default:
                    status = statusSensor.Off;
                    image = R.drawable.light_off;
                    break;
            }

            modeLight mode;
            switch (json.getString("mode")){
                case "High": mode = modeLight.High; break;
                case "Medium": mode = modeLight.Medium; break;
                case "Low": mode = modeLight.Low; break;
                default: mode = modeLight.Medium; break;
            }

            return new light(json.getString("ubication"), typeSensor.Light, status, image, mode, methodName);
        }catch(Exception ex){
            return null;
        }
    }

    private light getForm(){
        TextView location = getActivity().findViewById(R.id.txtNameSensorLight);
        ImageButton imageButton = getActivity().findViewById(R.id.imageButtonLight);
        statusSensor status;
        int image = 0;
        switch (imageButton.getTag().toString()){
            case "auto":
                status = statusSensor.Auto;
                image = R.drawable.light_auto_2;
                break;
            case "on":
                status = statusSensor.On;
                image = R.drawable.light_on;
                break;
            case "off":
                status = statusSensor.Off;
                image = R.drawable.light_off;
                break;
            default:
                status = statusSensor.Off;
                image = R.drawable.light_off;
                break;
        }

        SeekBar seekBar = (SeekBar)getActivity().findViewById(R.id.seekBarLight);
        modeLight mode;
        switch (seekBar.getProgress()){
            case 0: mode = modeLight.Low; break;
            case 1: mode = modeLight.Medium; break;
            case 2: mode = modeLight.High; break;
            default: mode = modeLight.Medium; break;
        }

        return new light(location.getText().toString(), typeSensor.Light, status, image, mode, "");
    }

    private void setForm(light data){

        ((TextView)getActivity().findViewById(R.id.txtNameSensorLight)).setText(data.getUbication());
        ImageButton imageButton = getActivity().findViewById(R.id.imageButtonLight);
        TextView txtSeekBarDescription = getActivity().findViewById(R.id.txtDescriptionLight);
        SeekBar seekBar = (SeekBar)getActivity().findViewById(R.id.seekBarLight);

        switch (data.getStatus()){
            case Auto:
                imageButton.setBackground(getResources().getDrawable(R.drawable.light_auto_2));
                imageButton.setTag("auto");
                seekBar.setVisibility(View.VISIBLE);
                txtSeekBarDescription.setVisibility(View.VISIBLE);
                switch (data.getMode()){
                    case Low:
                        seekBar.setProgress(0);
                        txtSeekBarDescription.setText("BAJO");
                        break;
                    case Medium:
                        seekBar.setProgress(1);
                        txtSeekBarDescription.setText("MEDIO");
                        break;
                    case High:
                        seekBar.setProgress(2);
                        txtSeekBarDescription.setText("ALTO");
                        break;
                }
                break;
            case On:
                seekBar.setVisibility(View.GONE);
                txtSeekBarDescription.setVisibility(View.GONE);
                imageButton.setBackground(getResources().getDrawable(R.drawable.light_on));
                imageButton.setTag("on");
                break;
            case Off:
                seekBar.setVisibility(View.GONE);
                txtSeekBarDescription.setVisibility(View.GONE);
                imageButton.setBackground(getResources().getDrawable(R.drawable.light_off));
                imageButton.setTag("off");
                break;
        }

    }

    private void changeShow(Boolean show){
        if (show) {
            manager.setVisibility(View.VISIBLE);
            backlayout.setVisibility(View.VISIBLE);
            recSensors.setVisibility(View.GONE);
        } else {
            manager.setVisibility(View.GONE);
            backlayout.setVisibility(View.GONE);
            recSensors.setVisibility(View.VISIBLE);
        }
    }

}

