package com.example.user.myapplication;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.user.myapplication.MathModel;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    private SensorManager sensorManager; //менеджер сенсоров

    long  i = 0;
    int xy_summ, xz_summ, zy_summ, count;

    private float[] rotationMatrix; //матрица поворота

    private float[] accelerometer;  //данные с акселерометра
    private float[] geomagnetism;   //данные геомагнитного датчика
    private float[] orientation;    //матрица положения в пространстве

    MathModel model = new MathModel(100, 100, 100);


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

        String xy_string, xz_string, zy_string;
        int xy_int, xz_int, zy_int;

        xy_int = (int) Math.round(Math.toDegrees(orientation[0]));
        xz_int = (int) Math.round(Math.toDegrees(orientation[1]));
        zy_int = (int) Math.round(Math.toDegrees(orientation[2]));

        xy_string = String.valueOf(xy_int);
        xz_string = String.valueOf(xz_int);
        zy_string = String.valueOf(zy_int);

        xy_summ += xy_int; xz_summ += xz_int; zy_summ += zy_int;
        count += 1;

        if(count == 10){
            int xy, xz, zy;
            xy = xy_summ / count;
            xz = xz_summ / count;
            zy = zy_summ / count;


            String test = model.checkPlane(xy, xz, zy);
//        //вывод результата

//
        TextView text;text = (TextView) findViewById(R.id.text);text.setText(xy_string + " " + xz_string + " " + zy_string);
//            TextView text;text = (TextView) findViewById(R.id.text);text.setText(test);


            xy_summ = 0; xz_summ = 0; zy_summ = 0; count = 0;

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

