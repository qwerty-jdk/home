package com.jdk.qwerty.home.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.jdk.qwerty.home.Objects.door;
import com.jdk.qwerty.home.Objects.light;
import com.jdk.qwerty.home.R;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private ArrayList<door> lights;
    private View view;
    private light currentLight;
    private int currentIndex;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Choose the Layout for my Fragment
        view = inflater.inflate(R.layout.light_tab, container, false);

        //We indentify ids of light_tab.xml objects
        imageButton = (ImageButton)view.findViewById(R.id.imageButtonLight); //Representación de light
        manager = view.findViewById(R.id.managerLight); //Contenedor para modificar estado de light seleccionado
        backlayout = view.findViewById(R.id.backLayoutLight); //Contenedor de identificador y acciones [btnOK, txtName, btnCancel]
        seekBar = view.findViewById(R.id.seekBarLight); //Control para cambiar modo de light [low, medium, high]
        textView = view.findViewById(R.id.txtDescriptionLight); //Texto que identifica light seleccionado
        imageButtonOk = view.findViewById(R.id.okButtonLight); //Botón para caeptar cambio de light seleccionado
        imageButtonCancel = view.findViewById(R.id.cancelButtonLight); //Botón para cancelar cambios de light seleccionado

        //Events Listeners
        imageButton.setOnClickListener(this.imageButtonOnClick()); //Cambia estados de light [on, off, auto]
        seekBar.setOnSeekBarChangeListener(this.seekBarOnChange()); //Define intensidad de light [low, medium, high]
        imageButtonOk.setOnClickListener(this.imageButtonOkOnClick()); //Acepta cambios realizados en light seleccionado
        imageButtonCancel.setOnClickListener(this.imageButtonCancelOnClick()); //Cancela cambios realizados en light seleccionado

        //Using light_tab.xml objects with view.
        recSensors = view.findViewById(R.id.recSensorsLight); //Contenedor lista de lights
        //Build RecyclerView with adapter
        recSensors.setLayoutManager(new GridLayoutManager(view.getContext(), 2));

        MainActivity.My_Controller.getLightAll(this.CallBackGet());
        this.Start();
        return view;
    }

    private void Start(){

        //Default state of imageButton and respective tag for next id
        imageButton.setBackground(getResources().getDrawable(R.drawable.light_off));
        imageButton.setTag("off");

        //Default Visibility
        seekBar.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);

        currentLight = null;
        currentIndex = 0;

        changeShow(false);
    }

    private light getForm(){
        TextView location = getActivity().findViewById(R.id.txtNameSensorLight);
        ImageButton imageButton = getActivity().findViewById(R.id.imageButtonLight);
        String status = imageButton.getTag().toString();

        SeekBar seekBar = (SeekBar)getActivity().findViewById(R.id.seekBarLight);
        String mode;
        switch (seekBar.getProgress()){
            case 0: mode = "low"; break;
            case 1: mode = "medium"; break;
            case 2: mode = "high"; break;
            default: mode = "medium"; break;
        }

        light lightDefault = currentLight;
        return new light(lightDefault.getLocation(), location.getText().toString(), status, lightDefault.getImage(), mode);
    }

    private void setForm(light data){

        ((TextView)getActivity().findViewById(R.id.txtNameSensorLight)).setText(data.getDisplayName());
        ImageButton imageButton = getActivity().findViewById(R.id.imageButtonLight);
        TextView txtSeekBarDescription = getActivity().findViewById(R.id.txtDescriptionLight);
        SeekBar seekBar = (SeekBar)getActivity().findViewById(R.id.seekBarLight);

        switch (data.getStatus()){
            case "auto":
                imageButton.setBackground(getResources().getDrawable(R.drawable.light_auto_2));
                imageButton.setTag("auto");
                seekBar.setVisibility(View.VISIBLE);
                txtSeekBarDescription.setVisibility(View.VISIBLE);
                switch (data.getMode()){
                    case "low":
                        seekBar.setProgress(0);
                        txtSeekBarDescription.setText("BAJO");
                        break;
                    case "medium":
                        seekBar.setProgress(1);
                        txtSeekBarDescription.setText("MEDIO");
                        break;
                    case "high":
                        seekBar.setProgress(2);
                        txtSeekBarDescription.setText("ALTO");
                        break;
                }
                break;
            case "on":
                seekBar.setVisibility(View.GONE);
                txtSeekBarDescription.setVisibility(View.GONE);
                imageButton.setBackground(getResources().getDrawable(R.drawable.light_on));
                imageButton.setTag("on");
                break;
            case "off":
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

    /*########### START EVENTS LISTENERS ###########*/
    private View.OnClickListener imageButtonOnClick(){
        return new View.OnClickListener() {
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
        };
    }

    private SeekBar.OnSeekBarChangeListener seekBarOnChange(){
        return new SeekBar.OnSeekBarChangeListener() {
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

        };
    }

    private View.OnClickListener imageButtonOkOnClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                light data = getForm();
                MainActivity.My_Controller.setLight(data, CallBackSet());
                lights.set(currentIndex, data);
                recSensors.getAdapter().notifyDataSetChanged();
                Start();
            }
        };
    }

    private View.OnClickListener imageButtonCancelOnClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Start();
            }
        };
    }

    private View.OnClickListener adapterOnItemClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                changeShow(true);
                currentIndex = recSensors.getChildAdapterPosition(view);
                currentLight = (light) lights.get(currentIndex);
                setForm(currentLight);

            }
        };
    }
    /*########### END EVENTS LISTENERS ###########*/

    /*########### START CALLBACK LISTENERS ###########*/
    private Callback<List<light>> CallBackGet() {
        return new Callback<List<light>>() {
            @Override
            public void onResponse(Call<List<light>> call, Response<List<light>> response) {

                lights = new ArrayList<>();
                switch(response.code()){
                    case 200:
                        for(light data: response.body())
                            lights.add(data);
                        break;
                    case 401:
                        lights.add(new light("", "Habitación Matrimonial", "off", R.drawable.principal, "low"));
                        lights.add(new light("", "Habitación Niños", "off", R.drawable.secundary, "low"));
                        lights.add(new light("", "Habitación Bebés", "off", R.drawable.kids, "low"));
                        lights.add(new light("", "Baño público", "off", R.drawable.bpublic, "low"));
                        lights.add(new light("", "Baño Privado", "off", R.drawable.bprivate, "low"));
                        lights.add(new light("", "Cocina", "off", R.drawable.kitchen, "low"));
                        lights.add(new light("", "Living", "off", R.drawable.living, "low"));
                        lights.add(new light("", "Estacionamiento", "off", R.drawable.garage, "low"));
                        break;
                    case 500: Toast.makeText(view.getContext(), "Error en el servidor", Toast.LENGTH_SHORT).show(); break;
                    default: Log.e(TAG, "Respuesta " + response.code() + " no soportada por la aplicacion."); break;
                }
                RecSensorsAdapter adapter = new RecSensorsAdapter(view.getContext(), lights);
                adapter.setOnClickListener(adapterOnItemClick());
                recSensors.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<light>> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    private Callback<light> CallBackSet(){
        return new Callback<light>() {
            @Override
            public void onResponse(Call<light> call, Response<light> response) {
                String message = "";
                switch(response.code()){
                    case 200: message = "OK"; break;
                    case 500: message = "Error en el servidor"; break;
                    default: Log.e(TAG, "Respuesta " + response.code() + " no soportada por la aplicacion."); break;
                }
                Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<light> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
    }
    /*########### END CALLBACK LISTENERS ###########*/

}

