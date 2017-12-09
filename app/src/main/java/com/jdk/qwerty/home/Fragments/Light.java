package com.jdk.qwerty.home.Fragments;

import android.graphics.drawable.Drawable;
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
import com.jdk.qwerty.home.MainActivity;
import com.jdk.qwerty.home.Objects.Mode_Light;
import com.jdk.qwerty.home.Objects.Sensor;
import com.jdk.qwerty.home.Objects.Status_Sensor;
import com.jdk.qwerty.home.Objects.Type_Sensor;
import com.jdk.qwerty.home.Objects._Light;
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
    private ImageView ImageButton;
    private SeekBar _SeekBar;
    private TextView _TextView;
    private LinearLayout _Manager;
    private RelativeLayout _BackLayout;
    private ImageButton _fabOk;
    private ImageButton _fabCancel;

    private ArrayList<Sensor> sensors;

    private void Start(){

        //Default state of ImageButton and respective tag for next id
        ImageButton.setBackground(getResources().getDrawable(R.drawable.light_off));
        ImageButton.setTag("off"); //off id

        //Default Visibility
        _SeekBar.setVisibility(View.GONE);
        _TextView.setVisibility(View.GONE);

        changeShow(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Choose the Layout for my Fragment
        View view = inflater.inflate(R.layout.light_tab, container, false);

        //We indentify ids of light_tab.xml objects
        ImageButton = (ImageButton)view.findViewById(R.id.ImageButton);
        _Manager = view.findViewById(R.id.manager);
        _BackLayout = view.findViewById(R.id.backLayout);
        _SeekBar = view.findViewById(R.id.seekBar);
        _TextView = view.findViewById(R.id.txtSeekBarDescription);
        _fabOk = view.findViewById(R.id.okButton);
        _fabCancel = view.findViewById(R.id.cancelButton);

        //ImageButton Click Listener
        ImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If tags appears to be off change to on
                switch (ImageButton.getTag().toString()){
                    case "on":
                        ImageButton.setBackground(getResources().getDrawable(R.drawable.light_off));
                        ImageButton.setTag("off");
                        _SeekBar.setVisibility(View.GONE);
                        _TextView.setVisibility(View.GONE);
                        break;
                    case "off":
                        ImageButton.setBackground(getResources().getDrawable(R.drawable.light_auto_2)); //Agregar nuevo icono para auto
                        ImageButton.setTag("auto");
                        _SeekBar.setVisibility(View.VISIBLE);
                        _TextView.setVisibility(View.VISIBLE);
                        _TextView.setText("MEDIO");
                        break;
                    case "auto":
                        ImageButton.setBackground(getResources().getDrawable(R.drawable.light_on));
                        ImageButton.setTag("on");
                        _SeekBar.setVisibility(View.GONE);
                        _TextView.setVisibility(View.GONE);
                        break;
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

        _fabOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Guardar estado de sensor
                _Light data = getForm();

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

        _fabCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Start();
            }
        });

        //Using light_tab.xml objects with view.
        recSensors = view.findViewById(R.id.recSensors);
        sensors = new ArrayList<>();
        Type_Sensor defaultType = Type_Sensor.Light;
        Status_Sensor defaultStatus = Status_Sensor.Off;
        Mode_Light dafaultMode = Mode_Light.Low;
        sensors.add(new _Light("Habitacion Principal", defaultType, defaultStatus, R.drawable.principal, dafaultMode, "LuzHabOne"));
        sensors.add(new _Light("Habitación Niño", defaultType, defaultStatus, R.drawable.secundary, dafaultMode, "LuzHabTwo"));
        sensors.add(new _Light("Habitación Bebe", defaultType, defaultStatus, R.drawable.kids, dafaultMode, "LuzHabTree"));
        sensors.add(new _Light("Baño público", defaultType, defaultStatus, R.drawable.bpublic, dafaultMode, "LuzBanOne"));
        sensors.add(new _Light("Baño Privado", defaultType, defaultStatus, R.drawable.bprivate, dafaultMode, "LuzBanTwo"));
        sensors.add(new _Light("Cocina", defaultType, defaultStatus, R.drawable.kitchen, dafaultMode, "LuzCocina"));
        sensors.add(new _Light("Living", defaultType, defaultStatus, R.drawable.living, dafaultMode, "LuzSala"));
        sensors.add(new _Light("Estacionamiento", defaultType, defaultStatus, R.drawable.garage, dafaultMode, "LuzEstac"));

        recSensors.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        RecSensorsAdapter adapter = new RecSensorsAdapter(view.getContext(), sensors);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                changeShow(true);
                _Light _default = (_Light)sensors.get(recSensors.getChildAdapterPosition(view));
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

                _Light data = null;
                if(json != null)
                    data = parseJsonToLight(json.toString(), _default.getMethodName());

                //SI encuentra sensor almacenado lo refresca, sino coloca el por defecto
                setForm(data != null ? data : _default);
            }
        });
        recSensors.setAdapter(adapter);

        this.Start();
        return view;
    }

    private _Light parseJsonToLight(String _json, String methodName){
        try {
            JSONObject json = new JSONObject(_json);

            int image;
            Status_Sensor status;
            switch (json.getString("status")){
                case "Auto":
                    status = Status_Sensor.Auto;
                    image = R.drawable.light_auto_2;
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

            Mode_Light mode;
            switch (json.getString("mode")){
                case "High": mode = Mode_Light.High; break;
                case "Medium": mode = Mode_Light.Medium; break;
                case "Low": mode = Mode_Light.Low; break;
                default: mode = Mode_Light.Medium; break;
            }

            return new _Light(json.getString("ubication"), Type_Sensor.Light, status, image, mode, methodName);
        }catch(Exception ex){
            return null;
        }
    }

    private _Light getForm(){

        TextView ubication = getActivity().findViewById(R.id.txtNameSensor);

        ImageButton imageButton = getActivity().findViewById(R.id.ImageButton);
        Status_Sensor status;
        int image = 0;
        switch (imageButton.getTag().toString()){
            case "auto":
                status = Status_Sensor.Auto;
                image = R.drawable.light_auto_2;
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

        SeekBar seekBar = (SeekBar)getActivity().findViewById(R.id.seekBar);
        Mode_Light mode;
        switch (seekBar.getProgress()){
            case 0: mode = Mode_Light.Low; break;
            case 1: mode = Mode_Light.Medium; break;
            case 2: mode = Mode_Light.High; break;
            default: mode = Mode_Light.Medium; break;
        }

        return new _Light(ubication.getText().toString(), Type_Sensor.Light, status, image, mode, "");
    }

    private void setForm(_Light data){

        ((TextView)getActivity().findViewById(R.id.txtNameSensor)).setText(data.getUbication());
        ImageButton imageButton = getActivity().findViewById(R.id.ImageButton);
        TextView txtSeekBarDescription = getActivity().findViewById(R.id.txtSeekBarDescription);
        SeekBar seekBar = (SeekBar)getActivity().findViewById(R.id.seekBar);

        switch (data.getStatus()){
            case Auto:
                imageButton.setBackground(getResources().getDrawable(R.drawable.light_auto_2));
                imageButton.setTag("auto");
                seekBar.setVisibility(View.VISIBLE);
                txtSeekBarDescription.setVisibility(View.VISIBLE);
                switch (data.getMode()){
                    case Low:
                        seekBar.setProgress(0);
                        //imageButton.setBackground(getResources().getDrawable(R.drawable.light_auto_1));
                        txtSeekBarDescription.setText("BAJO");
                        break;
                    case Medium:
                        seekBar.setProgress(1);
                      //  imageButton.setBackground(getResources().getDrawable(R.drawable.light_auto_2));
                        txtSeekBarDescription.setText("MEDIO");
                        break;
                    case High:
                        seekBar.setProgress(2);
                    //    imageButton.setBackground(getResources().getDrawable(R.drawable.light_auto_3));
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

