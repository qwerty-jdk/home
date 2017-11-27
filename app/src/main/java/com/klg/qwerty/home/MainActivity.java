package com.klg.qwerty.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton btnLight, btnTemperature, btnDoor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLight = (ImageButton)findViewById(R.id.btnLight);
        btnLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ligthActivity = new Intent(MainActivity.this, LightActivity.class);
                startActivity(ligthActivity);
            }
        });
        btnTemperature = (ImageButton)findViewById(R.id.btnTemperature);
        btnTemperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent temperatureActivity = new Intent(MainActivity.this, TemperatureActivity.class);
                startActivity(temperatureActivity);
            }
        });
        btnDoor = (ImageButton)findViewById(R.id.btnDoor);
        btnDoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent doorActivity = new Intent(MainActivity.this, DoorActivity.class);
                startActivity(doorActivity);
            }
        });

    }
}
