package com.example.sensordemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    LinearLayout Rl;
    SensorManager sm = null;
    TextView textView1 = null,magtv = null;
    List list;
    Sensor myProximitySensor,magSensor;
    private View view;
    private boolean isColor = false;
    private long lastUpdate;

    SensorEventListener sel = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {

            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                float[] values = event.values;

                textView1.setText("x: " + values[0] + "\ny: " + values[1] + "\nz: " + values[2]);
                getAccelerometer(event);
            }

            if(event.sensor.getType() == Sensor.TYPE_PROXIMITY){

                if(event.values[0] == 0)
                {
                    Rl.setBackgroundColor(Color.BLACK);
                }
                else
                {
                    Rl.setBackgroundColor(Color.CYAN);
                }
            }

            if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
               // getMagneticSensor(event);

                float values[] = event.values;

                float x = values[0];
                float y = values[1];
                float z = values[2];
                magtv.setText("Magnetic Sensor Values :\n"+ "x :" + x +"\n y:" + y + "\n z :" + z );
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* Get a SensorManager instance */
        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        textView1 = (TextView)findViewById(R.id.textview1);
        magtv = findViewById(R.id.tv_magn);
        view = (TextView)findViewById(R.id.textview1);


        Rl = findViewById(R.id.rl);
        Rl.setBackgroundColor(Color.CYAN);
        myProximitySensor = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        magSensor = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sm.registerListener(sel,myProximitySensor,SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(sel,magSensor,SensorManager.SENSOR_DELAY_NORMAL);

        view.setBackgroundColor(Color.GREEN);


        list = sm.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if(list.size()>0){
            //sm.registerListener(sel, (Sensor) list.get(0), SensorManager.SENSOR_DELAY_NORMAL);
            sm.registerListener(sel,(Sensor) list.get(0), SensorManager.SENSOR_DELAY_NORMAL );
        }else{
            Toast.makeText(getBaseContext(), "Error: No Accelerometer.", Toast.LENGTH_LONG).show();
        }
    }

    private void getAccelerometer(SensorEvent event) {
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];

        float accelationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);

        long actualTime = System.currentTimeMillis();
//        Toast.makeText(getApplicationContext(),String.valueOf(accelationSquareRoot)+" "+
//                SensorManager.GRAVITY_EARTH,Toast.LENGTH_SHORT).show();

        if (accelationSquareRoot >= 2) //it will be executed if you shuffle
        {

            if (actualTime - lastUpdate < 200) {
                return;
            }
            lastUpdate = actualTime;//updating lastUpdate for next shuffle
            if (isColor) {
                view.setBackgroundColor(Color.GREEN);

            } else {
                view.setBackgroundColor(Color.RED);
            }
            isColor = !isColor;
        }
    }





    @Override
    protected void onDestroy() {
        if(list.size()>0){
            sm.unregisterListener(sel);
        }
        super.onDestroy();
    }

    public void List(View view) {

        Intent intent = new Intent(MainActivity.this,listSensors.class);
        startActivity(intent);
    }
}
