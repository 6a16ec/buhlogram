package com.example.computer.compass;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import android.app.Activity;
public class MainActivity extends Activity implements SensorEventListener {


    //Объявляем работу с сенсором устройства
    private SensorManager mSensorManager;
    //Объявляем объект TextView
    TextView CompOrient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //TextView в котором будет отображаться градус поворота:
        CompOrient = (TextView) findViewById(R.id.Header);

        //Инициализируем возможность работать с сенсором устройства:
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Устанавливаем слушателя ориентации сенсора
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();

        //Останавливаем при надобности слушателя ориентации
        //сенсора с целью сбережения заряда батареи:
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        //Получаем градус поворота от оси, которая направлена на север, север = 0 градусов:
        String data = "";

        data += "Отклонение " + Integer.toString(0) + " : " + Float.toString(Math.round(event.values[0])) + " градусов\n";
        data += "Отклонение " + Integer.toString(1) + " : " + Float.toString(Math.round(event.values[1])) + " градусов\n";
        data += "Отклонение " + Integer.toString(2) + " : " + Float.toString(Math.round(event.values[2])) + " градусов\n";

        CompOrient.setText(data);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //Этот метод не используется, но без него программа будет ругаться
    }
}
