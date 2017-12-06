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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jdk.qwerty.home.Adapter.RecSensorsAdapter;
import com.jdk.qwerty.home.MainActivity;
import com.jdk.qwerty.home.Objects.Sensor;
import com.jdk.qwerty.home.Objects.Status_Sensor;
import com.jdk.qwerty.home.Objects.Type_Sensor;
import com.jdk.qwerty.home.Objects._Temp;
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
    private ImageView ImageButtonTemp;
    private LinearLayout _ManagerTemp;
    private RelativeLayout _BackLayoutTemp;
    private ImageButton _fabOkTemp;
    private ImageButton _fabCancelTemp;

    private ArrayList<Sensor> sensors;

    private void Start(){

        //Default state of ImageButton and respective tag for next id
        ImageButtonTemp.setBackground(getResources().getDrawable(R.drawable.light_off));
        ImageButtonTemp.setTag("off"); //off id

        changeShow(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Choose the Layout for my Fragment
        View view = inflater.inflate(R.layout.temp_tab, container, false);

        //We indentify ids of light_tab.xml objects
        ImageButtonTemp = (ImageButton)view.findViewById(R.id.ImageButtonTemp);
        _ManagerTemp = view.findViewById(R.id.managerTemp);
        _BackLayoutTemp = view.findViewById(R.id.backLayoutTemp);
        _fabOkTemp = view.findViewById(R.id.okButtonTemp);
        _fabCancelTemp = view.findViewById(R.id.cancelButtonTemp);

        //ImageButton Click Listener
        ImageButtonTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If tags appears to be off change to on
                switch (ImageButtonTemp.getTag().toString()){
                    case "on":
                        ImageButtonTemp.setBackground(getResources().getDrawable(R.drawable.light_off));
                        ImageButtonTemp.setTag("off");
                        break;
                    case "off":
                        ImageButtonTemp.setBackground(getResources().getDrawable(R.drawable.light_auto)); //Agregar nuevo icono para auto
                        ImageButtonTemp.setTag("auto");
                        break;
                    case "auto":
                        ImageButtonTemp.setBackground(getResources().getDrawable(R.drawable.light_on));
                        ImageButtonTemp.setTag("on");
                        break;
                }
            }
        });

        _fabOkTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Guardar estado de sensor
                _Temp data = getForm();

                try {
                    for(Method method: MainActivity.My_Controller.getClass().getMethods()){
                        if(method.getName().equals("set" + recSensors.getTag().toString())){
                            try { method.invoke(MainActivity.My_Controller, data.toJSON()); } catch (Exception ex ){}
                            Toast.makeText(getContext(), "Guardado exitosamente", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                } catch (Error e) { Toast.makeText(getContext(), "Cambios no almacenados", Toast.LENGTH_SHORT).show(); }
                Start();
            }
        });

        _fabCancelTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Start();
            }
        });

        //Using temp_tab.xml objects with view.
        recSensors = (RecyclerView) view.findViewById(R.id.recSensorsTemp);
        sensors = new ArrayList<>();
        Type_Sensor defaultType = Type_Sensor.Temp;
        Status_Sensor defaultStatus = Status_Sensor.Off;
        sensors.add(new _Temp("Hall", defaultType, defaultStatus, R.drawable.temp, 0.0, "TempeHabOne"));
        sensors.add(new _Temp("Living", defaultType, defaultStatus, R.drawable.temp, 0.0, "TempeHabTwo"));
        sensors.add(new _Temp("Kitchen", defaultType, defaultStatus, R.drawable.temp, 0.0, "TempeHabTree"));

        //recSensors.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        recSensors.setLayoutManager(new GridLayoutManager(this.getContext(), 1));

        RecSensorsAdapter adapter = new RecSensorsAdapter(view.getContext(), sensors);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeShow(true);
                _Temp _default = (_Temp)sensors.get(recSensors.getChildAdapterPosition(view));
                recSensors.setTag(_default.getMethodName());

                Object json = null;
                try {
                    for(Method method: MainActivity.My_Controller.getClass().getMethods()){
                        if(method.getName().equals("get" + _default.getMethodName())){
                            try {
                                json = method.invoke(MainActivity.My_Controller);
                            } catch (Exception ex ){}
                            break;
                        }
                    }
                } catch (Error e) { }

                _Temp data = null;
                if(json != null)
                    data = parseJsonToTemp(json.toString(), _default.getMethodName());

                //Si encuentra sensor almacenado lo refresca, sino coloca el por defecto
                setForm(data != null ? data : _default);
            }
        });
        recSensors.setAdapter(adapter);
        this.Start();
        return view;
    }

    private _Temp parseJsonToTemp(String _json, String methodName){
        try {
            JSONObject json = new JSONObject(_json);

            int image;
            Status_Sensor status;
            switch (json.getString("status")){
                case "Auto":
                    status = Status_Sensor.Auto;
                    image = R.drawable.light_auto;
                    break;
                case "On":
                    status = Status_Sensor.On;
                    image = R.drawable.light_on;
                    break;
                case "Off":
                    status = Status_Sensor.Off;
                    image = R.drawable.light_off;
                    break;
                default:
                    status = Status_Sensor.Off;
                    image = R.drawable.light_off;
                    break;
            }

            return new _Temp(json.getString("ubication"), Type_Sensor.Light, status, image, 0.0, methodName);
        }catch(Exception ex){
            return null;
        }
    }

    private _Temp getForm(){

        TextView ubication = getActivity().findViewById(R.id.txtNameSensorTemp);

        //ImageButton imageButton = getActivity().findViewById(R.id.ImageButton);
        Status_Sensor status;
        int image = 0;
        switch (ImageButtonTemp.getTag().toString()){
            case "auto":
                status = Status_Sensor.Auto;
                image = R.drawable.light_auto;
                break;
            case "on":
                status = Status_Sensor.On;
                image = R.drawable.light_on;
                break;
            case "off":
                status = Status_Sensor.Off;
                image = R.drawable.light_off;
                break;
            default:
                status = Status_Sensor.Off;
                image = R.drawable.light_off;
                break;
        }

        return new _Temp(ubication.getText().toString(), Type_Sensor.Light, status, image, 0.0, "");
    }

    private void setForm(_Temp data){

        ((TextView)getActivity().findViewById(R.id.txtNameSensorTemp)).setText(data.getUbication());
        //ImageButton imageButton = getActivity().findViewById(R.id.ImageButton);

        switch (data.getStatus()){
            case Auto:
                ImageButtonTemp.setBackground(getResources().getDrawable(R.drawable.light_auto));
                ImageButtonTemp.setTag("auto");
                break;
            case On:
                ImageButtonTemp.setBackground(getResources().getDrawable(R.drawable.light_on));
                ImageButtonTemp.setTag("on");
                break;
            case Off:
                ImageButtonTemp.setBackground(getResources().getDrawable(R.drawable.light_off));
                ImageButtonTemp.setTag("off");
                break;
        }
    }

    //Change show between recycler item view and recycler list item view...
    private void changeShow(Boolean show){
        if (show) {
            _ManagerTemp.setVisibility(View.VISIBLE);
            _BackLayoutTemp.setVisibility(View.VISIBLE);
            recSensors.setVisibility(View.GONE);
        } else {
            _ManagerTemp.setVisibility(View.GONE);
            _BackLayoutTemp.setVisibility(View.GONE);
            recSensors.setVisibility(View.VISIBLE);
        }
    }

}
