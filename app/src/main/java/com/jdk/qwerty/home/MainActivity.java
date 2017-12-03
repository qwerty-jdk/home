package com.jdk.qwerty.home;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jdk.qwerty.home.Adapter.SectionsPageAdapter;
import com.jdk.qwerty.home.Fragments.Door;
import com.jdk.qwerty.home.Fragments.Light;
import com.jdk.qwerty.home.Fragments.Temp;

public class MainActivity extends AppCompatActivity {

    //Developent message  (logt + enter) #shortcut
    private static final String TAG = "MainActivity";

    private ViewPager viewPager;
    private TabLayout tabLayout;

    //We need to Override onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Event log
        Log.d(TAG, "onCreate: Starting Domoticer...");

        //Set up the ViewPager (content) with the sections adapter.
        viewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(viewPager);

        /*viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Funciona", Toast.LENGTH_SHORT).show();
            }
        });*/

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabLayout();
    }

    //Customize Tab Layout
    private void setupTabLayout() {
        try {
            //Light:0
            tabLayout.getTabAt(0).setIcon(R.drawable.light);
            //Door:1
            tabLayout.getTabAt(1).setIcon(R.drawable.door);
            //Temp:2
            tabLayout.getTabAt(2).setIcon(R.drawable.temp);
        }catch(Exception ex){
            System.out.println("KLG-Error en " + this.getClass().toString() + ".setupTabLayout(): " + ex.getMessage());
        }
    }

    //Populate Tab Method
    private void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        //Here we can add another fragments in the future
        // .addFragment(Fragment fragment, String title)
        adapter.addFragment(new Light(), "Light");
        adapter.addFragment(new Door(), "Door");
        adapter.addFragment(new Temp(), "Temp");

        //Finaly we set the adapter with the SectionsPageAdapter adapter.
        viewPager.setAdapter(adapter);
    }

}
