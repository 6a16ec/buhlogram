package com.example.user.lapin;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;


public class Sensors {

    private SensorManager sensorManager;

    private int xy, xz, zy;
    private int xy_summ, xz_summ, zy_summ, count;
    final private int calibrate_count = 1; // !!!!!

    private float[] rotationMatrix = new float[16]; //матрица поворота

    private float[] accelerometer = new float[3];  //данные с акселерометра
    private float[] geomagnetism = new float[3];   //данные геомагнитного датчика
    private float[] orientation = new float[3];    //матрица положения в пространстве

    public Sensors(){
        xy_summ = 0; xz_summ = 0; zy_summ = 0; count = 0;
    }

    public void onCreate(Context context){
        sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
    }

    public void onResume(Context context){
        sensorManager.registerListener((SensorEventListener) context, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI );
        sensorManager.registerListener((SensorEventListener) context, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_UI );
    }

    public void onPause(Context context){
        sensorManager.unregisterListener((SensorEventListener) context);
    }

    public void checkSensors(SensorEvent event){

        loadSensorData(event);

        SensorManager.getRotationMatrix(rotationMatrix, null, accelerometer, geomagnetism); //получаем матрицу поворота
        SensorManager.getOrientation(rotationMatrix, orientation); //получаем данные ориентации устройства в пространстве


        xy_summ += (int) Math.round(Math.toDegrees(orientation[0]));
        zy_summ += (int) Math.round(Math.toDegrees(orientation[1]));
        xz_summ += (int) (Math.round(Math.toDegrees(orientation[2])));// + Math.toDegrees(Math.PI)) % 360;
        count += 1;

        if(count >= calibrate_count){
            xy = xy_summ / count;
            xz = xz_summ / count;
            zy = zy_summ / count;

            xy_summ = 0; xz_summ = 0; zy_summ = 0; count = 0;
        }
    }

    public boolean haveNewInfo(){
        return (count == 0);
    }

    public int getXY(){
        return xy;
    }
    public int getXZ(){
        return xz;
    }
    public int getZY(){
        return zy;
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
