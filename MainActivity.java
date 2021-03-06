package com.flappy.user.finalbeta;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.CountDownTimer;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;


public class MainActivity extends Activity implements SensorEventListener {

    Coordinate coordinate = null;


    Sensors sensors = new Sensors();
    private SensorManager sensorManager;


    FlyInfo flyinfo = null;


    //Потоки
    Thread a;


    LocationInfo locationInfo;

    MathModel model = new MathModel();

    Preview mPreview;
    DrawOnTop mDraw;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        mPreview = new Preview(this);
        mDraw = new DrawOnTop(this);

        setContentView(mPreview);
        addContentView(mDraw, new LayoutParams
                (LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));


        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);


        model.addPlane(0,0,100);



    }


    @Override
    protected void onResume() {
        super.onResume();

        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI );
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_UI );

        //threadStart();
        locationInfo = new LocationInfo(this);
        a = new Thread(){
            @Override
            public void run(){
                coordinate = locationInfo.getResult();
            }
        };
        //Timer t = new Timer();
        //drawTimer.start();
        //flyInfoTimer.start();

        a.start();


    }

    @Override
    protected void onPause() {
        super.onPause();

        sensorManager.unregisterListener(this);

        /*try {
            a.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        sensors.checkSensors(event);

        if(sensors.haveNewInfo()){

            model.check(sensors.getXY(), sensors.getXZ(), sensors.getZY());

            if(model.isOnScreen())
                mDraw.setPercentCoordinate(model.percentX(),model.percentY());
            mDraw.invalidate();
        }



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //используется для получения уведомлений от SensorManager при изменении значений датчика
    }




    private FlyInfo getFlyInformation(){
        return null;
    }


    CountDownTimer flyInfoTimer = new CountDownTimer(Integer.MAX_VALUE, 10000){

        @Override
        public void onTick(long l) {
            flyinfo = getFlyInformation();
        }

        @Override
        public void onFinish() {

        }
    };



}



