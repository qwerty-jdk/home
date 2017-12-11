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
import android.widget.Toast;
import com.jdk.qwerty.home.Adapter.RecSensorsAdapter;
import com.jdk.qwerty.home.MainActivity;
import com.jdk.qwerty.home.Objects.door;
import com.jdk.qwerty.home.R;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrador on 02/12/2017.
 */

public class Door extends Fragment {


    private static final String TAG = "Door tab";
    private RecyclerView recSensors;
    private ArrayList<door> doors;
    private door door = null;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Choose the Layout for my Fragment
        view = inflater.inflate(R.layout.door_tab, container, false);

        //Using door_tab.xml objects with view.
        recSensors = (RecyclerView) view.findViewById(R.id.recSensorsDoor);
        doors = new ArrayList<>();

        MainActivity.My_Controller.getMotorEstac(this.CallBackGet());

        return view;
    }

    private RecSensorsAdapter adapter;
    private Callback<door> CallBackGet(){
        return new Callback<door>() {
            @Override
            public void onResponse(Call<door> call, Response<door> response) {
                recSensors.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 1));
                doors = new ArrayList<>();
                switch(response.code()){
                    case 200: doors.add(response.body()); break;
                    case 401: doors.add(new door("", "PORTÓN", "off", R.drawable.door_off)); break;
                    case 500: Toast.makeText(view.getContext(), "Error en el servidor", Toast.LENGTH_SHORT).show(); break;
                    default: Log.e(TAG, "Respuesta " + response.code() + " no soportada por la aplicacion."); break;
                }
                adapter = new RecSensorsAdapter(view.getContext(), doors);
                adapter.setOnClickListener(RecSensorsItemClick());
                recSensors.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<door> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    private View.OnClickListener RecSensorsItemClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                door data = doors.get(recSensors.getChildAdapterPosition(view));
                switch (data.getStatus()){
                    case "on": data.setStatus("off"); data.setImage(R.drawable.door_off); break;
                    case "off": data.setStatus("on"); data.setImage(R.drawable.door_on); break;
                }
                MainActivity.My_Controller.setMotorEstac(data, CallBackSet());
                recSensors.getAdapter().notifyDataSetChanged();
            }
        };
    }

    private Callback<door> CallBackSet(){
        return new Callback<com.jdk.qwerty.home.Objects.door>() {
            @Override
            public void onResponse(Call<door> call, Response<door> response) {
                String message = "";
                switch(response.code()){
                    case 200: message = "OK"; break;
                    case 500: message = "Error en el servidor"; break;
                    default: Log.e(TAG, "Respuesta " + response.code() + " no soportada por la aplicacion."); break;
                }
                Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<door> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
    }

}

