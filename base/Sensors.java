package com.example.user.myapplication;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;

public class Sensors {

    private int xy, xz, zy;
    private int xy_summ, xz_summ, zy_summ, count;
    final private int calibrate_count = 3; // !!!!!

    private float[] rotationMatrix = new float[16]; //матрица поворота

    private float[] accelerometer = new float[3];  //данные с акселерометра
    private float[] geomagnetism = new float[3];   //данные геомагнитного датчика
    private float[] orientation = new float[3];    //матрица положения в пространстве

    public Sensors(){
        xy_summ = 0; xz_summ = 0; zy_summ = 0; count = 0;
    }

    public void checkSensors(SensorEvent event){

        loadSensorData(event);

        SensorManager.getRotationMatrix(rotationMatrix, null, accelerometer, geomagnetism); //получаем матрицу поворота
        SensorManager.getOrientation(rotationMatrix, orientation); //получаем данные ориентации устройства в пространстве


        xy_summ += (int) Math.round(Math.toDegrees(orientation[0]));
        zy_summ += (int) Math.round(Math.toDegrees(orientation[1]));
        xz_summ += (int) (Math.round(Math.toDegrees(orientation[2])) + Math.toDegrees(Math.PI)) % 360;
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

    public String getString(){

        String xy_string, xz_string, zy_string;

        xy_string = String.valueOf(xy);
        xz_string = String.valueOf(zy);
        zy_string = String.valueOf(xz);

        return xy_string + " " + xz_string + " " + zy_string;
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
