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

import java.util.ArrayList;

/**
 * Created by Administrador on 02/12/2017.
 */

public class Light extends Fragment {

    private static final String TAG = "Light tab";
    private RecyclerView recSensors;
    private ImageView ImageButton;
    //private Switch _Switch;
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
                        ImageButton.setBackground(getResources().getDrawable(R.drawable.light_auto)); //Agregar nuevo icono para auto
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
                Toast.makeText(getContext(), "Guardar estado del sensor", Toast.LENGTH_SHORT).show();
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
        sensors.add(new _Light("Hall", Type_Sensor.Light, Status_Sensor.Off, R.drawable.light, Mode_Light.Medium));
        sensors.add(new _Light("Living", Type_Sensor.Light, Status_Sensor.Auto, R.drawable.light, Mode_Light.Medium));
        sensors.add(new _Light("Kitchen", Type_Sensor.Light, Status_Sensor.On, R.drawable.light, Mode_Light.Medium));

        recSensors.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        RecSensorsAdapter adapter = new RecSensorsAdapter(view.getContext(), sensors);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeShow(true);
                setForm((_Light)sensors.get(recSensors.getChildAdapterPosition(view)));
            }
        });
        recSensors.setAdapter(adapter);

        this.Start();
        return view;
    }

    private void setForm(_Light data){

        ((TextView)getActivity().findViewById(R.id.txtNameSensor)).setText(data.getUbication());
        ImageButton imageButton = getActivity().findViewById(R.id.ImageButton);
        TextView txtSeekBarDescription = getActivity().findViewById(R.id.txtSeekBarDescription);
        SeekBar seekBar = (SeekBar)getActivity().findViewById(R.id.seekBar);

        switch (data.getStatus()){
            case Auto:
                imageButton.setBackground(getResources().getDrawable(R.drawable.light_auto));
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

