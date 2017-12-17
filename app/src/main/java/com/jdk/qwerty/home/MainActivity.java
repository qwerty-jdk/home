package com.jdk.qwerty.home;

import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.jdk.qwerty.home.Adapter.SectionsPageAdapter;
import com.jdk.qwerty.home.Fragments.Door;
import com.jdk.qwerty.home.Fragments.Light;
import com.jdk.qwerty.home.Fragments.Temp;
import com.jdk.qwerty.home.Objects.door;
import com.jdk.qwerty.home.Objects.light;
import com.jdk.qwerty.home.Objects.mqttBroker;
import com.jdk.qwerty.home.Objects.temp;
import com.jdk.qwerty.home.restAPI.ClientMQTT;
import com.jdk.qwerty.home.restAPI.Controller;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static Controller My_Controller;
    public static ClientMQTT MqttClient;

    //Developent message  (logt + enter) #shortcut
    private static final String TAG = "MainActivity";

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private mqttBroker MqttBroker;
    private SectionsPageAdapter adapter;
    private int[] tabs = new int[3];

    //We need to Override onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildController();
        setContentView(R.layout.activity_main);

        //Get config to mqtt server on api rest
        My_Controller.getMqttBroker(this.CallbackGetMqttBroker());

        //Event log
        Log.d(TAG, "onCreate: Starting Domoticer...");

    }

    private Callback<List<door>> CallBackGetDoors() {
        return new Callback<List<door>>() {
            @Override
            public void onResponse(Call<List<door>> call, Response<List<door>> response) {
                if(response.code() == 200){

                    List<door> list = response.body();
                    for(door data: list)
                        MainActivity.MqttClient.Subscribe("door$" + data.getLocation());

                    adapter.addFragment(new Door(list), "");
                    adapter.notifyDataSetChanged();
                    tabs[tabLayout.getTabCount()-1] = R.drawable.door;
                    setupTabLayout();

                }
            }

            @Override
            public void onFailure(Call<List<door>> call, Throwable t) {

            }
        };
    }

    private Callback<List<temp>> CallBackGetTemps() {
        return new Callback<List<temp>>() {
            @Override
            public void onResponse(Call<List<temp>> call, Response<List<temp>> response) {
                if(response.code() == 200){

                    List<temp> list = response.body();
                    for(temp data : list)
                        MainActivity.MqttClient.Subscribe("temp$" + data.getLocation());

                    adapter.addFragment(new Temp(list), "");
                    adapter.notifyDataSetChanged();
                    tabs[tabLayout.getTabCount()-1] = R.drawable.temp;
                    setupTabLayout();

                }
            }

            @Override
            public void onFailure(Call<List<temp>> call, Throwable t) {

            }
        };
    }

    private Callback<List<light>> CallBackGetLights() {
        return new Callback<List<light>>() {
            @Override
            public void onResponse(Call<List<light>> call, Response<List<light>> response) {
                if(response.code() == 200){

                    List<light> list = response.body();
                    for (light data: list)
                        MainActivity.MqttClient.Subscribe("light$" + data.getLocation());

                    adapter.addFragment(new Light(list), "");
                    adapter.notifyDataSetChanged();
                    tabs[tabLayout.getTabCount()-1] = R.drawable.light;
                    setupTabLayout();

                }
            }

            @Override
            public void onFailure(Call<List<light>> call, Throwable t) {

            }
        };
    }

    private IMqttMessageListener MqttMessageListener(){
        return new IMqttMessageListener() {
            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Toast.makeText(getApplicationContext(), topic + ": " + message.toString(), Toast.LENGTH_LONG);
            }
        };
    }

    private Callback<mqttBroker> CallbackGetMqttBroker(){
        return new Callback<mqttBroker>() {
            @Override
            public void onResponse(Call<mqttBroker> call, Response<mqttBroker> response) {
                switch (response.code()){
                    case 200:

                        mqttBroker data = response.body();
                        String url = data.getUrl().substring(4, data.getUrl().length());
                        data.setUrl("tcp" + url);
                        MainActivity.MqttClient = new ClientMQTT(getApplicationContext(),"ExampleAndroidClient", data, MqttMessageListener());

                        MainActivity.My_Controller.getLightAll(CallBackGetLights());
                        MainActivity.My_Controller.getTempAll(CallBackGetTemps());
                        MainActivity.My_Controller.getDoorAll(CallBackGetDoors());

                        //Event log
                        Log.d(TAG, "Connecting Domoticer...");

                        //Set up the ViewPager (content) with the sections adapter.
                        viewPager = (ViewPager) findViewById(R.id.container);
                        adapter = new SectionsPageAdapter(getSupportFragmentManager());
                        viewPager.setAdapter(adapter);
                        viewPager.setCurrentItem(0);

                        tabLayout = (TabLayout) findViewById(R.id.tabs);
                        tabLayout.setupWithViewPager(viewPager);

                        break;
                    case 500: Log.w(TAG, "Error en el servidor"); break;
                    default: Log.w(TAG, "Código " + response.code() + " no soportado."); break;
                }
            }

            @Override
            public void onFailure(Call<mqttBroker> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "No pudo conectarse con el servidor.", Toast.LENGTH_LONG).show();
                Log.w(TAG, t.getMessage());
            }
        };
    }

    //Método que contruye el conrolador.
    private void buildController() {
        SharedPreferences settings = getSharedPreferences("DATA", MODE_PRIVATE);
        My_Controller = new Controller(settings);
    }

    //Customize Tab Layout
    private void setupTabLayout() {
        try {
            for(int i = 0 ; i < tabs.length; i++){
                tabLayout.getTabAt(i).setIcon(tabs[i]);
            }
        }catch(Exception ex){
            System.out.println("KLG-Error en " + this.getClass().toString() + ".setupTabLayout(): " + ex.getMessage());
        }
    }

}
