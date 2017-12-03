package com.jdk.qwerty.home.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.jdk.qwerty.home.R;

/**
 * Created by Administrador on 02/12/2017.
 */

public class Door extends Fragment {
    private static final String TAG = "Door tab";
    private Button btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Choose the Layout for my Fragment
        View view = inflater.inflate(R.layout.door_tab, container, false);

        //Using door_tab.xml objects
        btn = (Button) view.findViewById(R.id.btnDoor);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"door button pressed", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}

