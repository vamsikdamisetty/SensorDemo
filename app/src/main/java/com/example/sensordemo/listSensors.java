package com.example.sensordemo;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class listSensors extends AppCompatActivity {

    ListView listView ;
    SensorManager sensorManager ;
    List<Sensor> listsensor;
    List<String> liststring ;
    ArrayAdapter<String> adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_sensors);



        listView = findViewById(R.id.listview1);

        liststring  = new ArrayList<String>();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        listsensor = sensorManager.getSensorList(Sensor.TYPE_ALL);

        for (Sensor sensor : listsensor)
        {
            liststring.add(sensor.getName());
        }

        adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,liststring);

        listView.setAdapter(adapter);
    }

}
