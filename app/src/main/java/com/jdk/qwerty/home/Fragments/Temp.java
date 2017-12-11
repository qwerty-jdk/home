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
import android.widget.TextView;
import android.widget.Toast;
import com.jdk.qwerty.home.Adapter.RecSensorsAdapter;
import com.jdk.qwerty.home.MainActivity;
import com.jdk.qwerty.home.Objects.door;
import com.jdk.qwerty.home.Objects.temp;
import com.jdk.qwerty.home.R;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private View view;
    private ArrayList<door> temps;
    private temp currentTemp;
    private int currentIndex;

    private void Start(){

        imageButton.setBackground(getResources().getDrawable(R.drawable.light_off));
        imageButton.setTag("off");

        changeShow(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.temp_tab, container, false);

        //We indentify ids of light_tab.xml objects
        imageButton = (ImageButton)view.findViewById(R.id.imageButtonTemp);
        manager = view.findViewById(R.id.managerTemp);
        backLayout = view.findViewById(R.id.backLayoutTemp);
        imageButtonOk = view.findViewById(R.id.okButtonTemp);
        imageButtonCancel = view.findViewById(R.id.cancelButtonTemp);

        imageButton.setOnClickListener(this.imageButtoOnClick());
        imageButtonOk.setOnClickListener(this.imageButtonOkOnClick());
        imageButtonCancel.setOnClickListener(this.imageButtonCancelOnClick());

        recSensors = (RecyclerView) view.findViewById(R.id.recSensorsTemp);
        //Build RecyclerView with adapter
        recSensors.setLayoutManager(new GridLayoutManager(this.getContext(), 1));

        MainActivity.My_Controller.getTempAll(this.CallBackGet());
        this.Start();
        return view;
    }

    private temp getForm(){

        TextView location = getActivity().findViewById(R.id.txtNameSensorTemp);

        int image = 0;
        String status = imageButton.getTag().toString();
        switch (status){
            case "auto": image = R.drawable.temp_auto; break;
            case "on": image = R.drawable.temp_on; break;
            case "off": image = R.drawable.temp_off; break;
            default: image = R.drawable.temp_off; break;
        }

        temp tempDefault = currentTemp;
        return new temp(tempDefault.getLocation(), location.getText().toString(), status, tempDefault.getImage(), 0);
    }

    private void setForm(temp data){

        ((TextView)getActivity().findViewById(R.id.txtNameSensorTemp)).setText(data.getDisplayName());

        switch (data.getStatus()){
            case "auto":
                imageButton.setBackground(getResources().getDrawable(R.drawable.temp_auto));
                imageButton.setTag("auto");
                break;
            case "on":
                imageButton.setBackground(getResources().getDrawable(R.drawable.temp_on));
                imageButton.setTag("on");
                break;
            case "off":
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

    /*######### START EVENT LISTENERS #########*/
    private View.OnClickListener imageButtoOnClick(){
        return new View.OnClickListener() {
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
        };
    }

    private View.OnClickListener imageButtonOkOnClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                temp data = getForm();
                MainActivity.My_Controller.setTemp(data, CallBackSet());
                temps.set(currentIndex, data);
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
                currentTemp = (temp) temps.get(currentIndex);
                setForm(currentTemp);

            }
        };
    }
    /*######### END EVENT LISTENERS #########*/

    /*########### START CALLBACK LISTENERS ###########*/
    private Callback<List<temp>> CallBackGet() {
        return new Callback<List<temp>>() {
            @Override
            public void onResponse(Call<List<temp>> call, Response<List<temp>> response) {

                temps = new ArrayList<>();
                switch(response.code()){
                    case 200:
                        for(temp data: response.body())
                            temps.add(data);
                        break;
                    case 401:
                        temps.add(new temp("", "Habitación Matrimonial", "off", R.drawable.principal, 0));
                        temps.add(new temp("", "Habitación Niños", "off", R.drawable.secundary, 0));
                        temps.add(new temp("", "Habitación Bebés", "off", R.drawable.kids, 0));
                        break;
                    case 500: Toast.makeText(view.getContext(), "Error en el servidor", Toast.LENGTH_SHORT).show(); break;
                    default: Log.e(TAG, "Respuesta " + response.code() + " no soportada por la aplicacion."); break;
                }
                RecSensorsAdapter adapter = new RecSensorsAdapter(view.getContext(), temps);
                adapter.setOnClickListener(adapterOnItemClick());
                recSensors.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<temp>> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    private Callback<temp> CallBackSet(){
        return new Callback<temp>() {
            @Override
            public void onResponse(Call<temp> call, Response<temp> response) {
                String message = "";
                switch(response.code()){
                    case 200: message = "OK"; break;
                    case 500: message = "Error en el servidor"; break;
                    default: Log.e(TAG, "Respuesta " + response.code() + " no soportada por la aplicacion."); break;
                }
                Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<temp> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
    }
    /*########### END CALLBACK LISTENERS ###########*/

}
