package com.example.user.myapplication;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    private SensorManager sensorManager; //менеджер сенсоров

    private float[] rotationMatrix; //матрица поворота

    private float[] accelerometer;  //данные с акселерометра
    private float[] geomagnetism;   //данные геомагнитного датчика
    private float[] orientation;    //матрица положения в пространстве


    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);


        rotationMatrix = new float[16];
        accelerometer = new float[3];
        geomagnetism = new float[3];
        orientation = new float[3];



        setContentView(R.layout.activity_main);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        loadSensorData(event); // получаем данные с датчика
        SensorManager.getRotationMatrix(rotationMatrix, null, accelerometer, geomagnetism); //получаем матрицу поворота
        SensorManager.getOrientation(rotationMatrix, orientation); //получаем данные ориентации устройства в пространстве
//
//        if((xyAngle ==null)||(xzAngle==null)||(zyAngle ==null)){
//            xyAngle = (TextView) findViewById(R.id.xyValue);
//            xzAngle = (TextView) findViewById(R.id.xzValue);
//            zyAngle = (TextView) findViewById(R.id.zyValue);
//        }

        String xy, xz, zy;
        xy = String.valueOf(Math.round(Math.toDegrees(orientation[0])));
        xz = String.valueOf(Math.round(Math.toDegrees(orientation[1])));
        zy = String.valueOf(Math.round(Math.toDegrees(orientation[2])));
//        //вывод результата
//
        TextView text;text = (TextView) findViewById(R.id.text);text.setText(xy + " " + xz + " " + zy);
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

    private void loadSensorData(SensorEvent event) {
        final int type = event.sensor.getType(); //определяем тип датчика
        if (type == Sensor.TYPE_ACCELEROMETER) { //если акселерометр
            accelerometer = event.values.clone();
        }

        if (type == Sensor.TYPE_MAGNETIC_FIELD) { //если геомагнитный датчик
            geomagnetism = event.values.clone();
        }
    }
}

