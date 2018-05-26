package com.example.user.myapplication;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    private SensorManager sensorManager; //менеджер сенсоров



    MathModel model = new MathModel();
    //model.addPlane(I(0), (double)0, (double)100);
    Sensors sensors = new Sensors();


    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);


        model.addPlane(0,0,100);


        setContentView(R.layout.activity_main);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        sensors.checkSensors(event);//!!

        if(sensors.haveNewInfo()){

            TextView text;text = (TextView) findViewById(R.id.text);
            String output = model.getString();
            output += '\n' + sensors.getString();
            output += " ==== " + model.len();
            text.setText(output);

            model.check(sensors.getXY(), sensors.getXZ(), sensors.getZY()); //!!

            /*
            model.isOnScreen();
            model.percentX();
            model.percentY();
            */

        }



    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //используется для получения уведомлений от SensorManager при изменении значений датчика
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI );
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_UI );
    }


    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }


}
