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
import android.widget.TextView;
import android.widget.Toast;

import com.jdk.qwerty.home.Adapter.RecSensorsAdapter;
import com.jdk.qwerty.home.MainActivity;
import com.jdk.qwerty.home.Objects.Sensor;
import com.jdk.qwerty.home.Objects.statusSensor;
import com.jdk.qwerty.home.Objects.typeSensor;
import com.jdk.qwerty.home.Objects.temp;
import com.jdk.qwerty.home.R;

import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by Administrador on 02/12/2017.
 */

public class Temp extends Fragment {
    private static final String TAG = "Temp tab";
    private RecyclerView recSensors;
    private ImageView imageButton;
    private LinearLayout manager;
    private RelativeLayout backLayout;
    private ImageButton imageButtonOk;
    private ImageButton imageButtonCancel;

    private ArrayList<Sensor> sensors;

    private void Start(){

        imageButton.setBackground(getResources().getDrawable(R.drawable.light_off));
        imageButton.setTag("off");

        changeShow(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.temp_tab, container, false);

        imageButton = (ImageButton)view.findViewById(R.id.imageButtonTemp);
        manager = view.findViewById(R.id.managerTemp);
        backLayout = view.findViewById(R.id.backLayoutTemp);
        imageButtonOk = view.findViewById(R.id.okButtonTemp);
        imageButtonCancel = view.findViewById(R.id.cancelButtonTemp);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If tags appears to be off change to on
                switch (imageButton.getTag().toString()){
                    case "on":
                        imageButton.setBackground(getResources().getDrawable(R.drawable.temp_off));
                        imageButton.setTag("off");
                        break;
                    case "off":
                        imageButton.setBackground(getResources().getDrawable(R.drawable.temp_auto));
                        imageButton.setTag("auto");
                        break;
                    case "auto":
                        imageButton.setBackground(getResources().getDrawable(R.drawable.temp_on));
                        imageButton.setTag("on");
                        break;
                }
            }
        });

        imageButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                temp data = getForm();

                try {
                    for(Method method: MainActivity.My_Controller.getClass().getMethods()){
                        if(method.getName().equals("set" + recSensors.getTag().toString())){
                            try { method.invoke(MainActivity.My_Controller, data.toJSON()); } catch (Exception ex ){}
                            Toast.makeText(getContext(),"Saved successfully.", Toast.LENGTH_SHORT).show();
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

        recSensors = (RecyclerView) view.findViewById(R.id.recSensorsTemp);
        sensors = new ArrayList<>();
        typeSensor defaultType = typeSensor.Temp;
        statusSensor defaultStatus = statusSensor.Off;
        sensors.add(new temp("Habitación Matrimonial", defaultType, defaultStatus, R.drawable.principal, 0.0, "TempeHabOne"));
        sensors.add(new temp("Habitación Niños", defaultType, defaultStatus, R.drawable.secundary, 0.0, "TempeHabTwo"));
        sensors.add(new temp("Habitación Bebés", defaultType, defaultStatus, R.drawable.kids, 0.0, "TempeHabTree"));

        recSensors.setLayoutManager(new GridLayoutManager(this.getContext(), 1));

        RecSensorsAdapter adapter = new RecSensorsAdapter(view.getContext(), sensors);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeShow(true);
                temp def = (temp)sensors.get(recSensors.getChildAdapterPosition(view));
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

                temp data = null;
                if(json != null)
                    data = parseJsonToTemp(json.toString(), def.getMethodName());

                setForm(data != null ? data : def);
            }
        });
        recSensors.setAdapter(adapter);
        this.Start();
        return view;
    }

    private temp parseJsonToTemp(String s, String methodName){
        try {
            JSONObject json = new JSONObject(s);

            int image;
            statusSensor status;
            switch (json.getString("status")){
                case "Auto":
                    status = statusSensor.Auto;
                    image = R.drawable.temp_auto;
                    break;
                case "On":
                    status = statusSensor.On;
                    image = R.drawable.temp_on;
                    break;
                case "Off":
                    status = statusSensor.Off;
                    image = R.drawable.temp_off;
                    break;
                default:
                    status = statusSensor.Off;
                    image = R.drawable.temp_off;
                    break;
            }

            return new temp(json.getString("ubication"), typeSensor.Light, status, image, 0.0, methodName);
        }catch(Exception ex){
            return null;
        }
    }

    private temp getForm(){

        TextView location = getActivity().findViewById(R.id.txtNameSensorTemp);

        statusSensor status;
        int image = 0;
        switch (imageButton.getTag().toString()){
            case "auto":
                status = statusSensor.Auto;
                image = R.drawable.temp_auto;
                break;
            case "on":
                status = statusSensor.On;
                image = R.drawable.temp_on;
                break;
            case "off":
                status = statusSensor.Off;
                image = R.drawable.temp_off;
                break;
            default:
                status = statusSensor.Off;
                image = R.drawable.temp_off;
                break;
        }

        return new temp(location.getText().toString(), typeSensor.Light, status, image, 0.0, "");
    }

    private void setForm(temp data){

        ((TextView)getActivity().findViewById(R.id.txtNameSensorTemp)).setText(data.getUbication());

        switch (data.getStatus()){
            case Auto:
                imageButton.setBackground(getResources().getDrawable(R.drawable.temp_auto));
                imageButton.setTag("auto");
                break;
            case On:
                imageButton.setBackground(getResources().getDrawable(R.drawable.temp_on));
                imageButton.setTag("on");
                break;
            case Off:
                imageButton.setBackground(getResources().getDrawable(R.drawable.temp_off));
                imageButton.setTag("off");
                break;
        }
    }

    private void changeShow(Boolean show){
        if (show) {
            manager.setVisibility(View.VISIBLE);
            backLayout.setVisibility(View.VISIBLE);
            recSensors.setVisibility(View.GONE);
        } else {
            manager.setVisibility(View.GONE);
            backLayout.setVisibility(View.GONE);
            recSensors.setVisibility(View.VISIBLE);
        }
    }

}
