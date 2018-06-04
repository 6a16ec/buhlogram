package com.example.user.lapin;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;


public class MainActivity extends Activity implements SensorEventListener {

    Sensors sensors = new Sensors();
    GPS gps = new GPS();


    Coordinate coordinate = null;






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

        sensors.onCreate(this);
        gps.onCreate(this);




        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        mPreview = new Preview(this);
        mDraw = new DrawOnTop(this);

        setContentView(mPreview);
        addContentView(mDraw, new LayoutParams
                (LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));





        model.addPlane("# # 0 0 0 100 300 # # A320 # # MSK NN # # # # # LOL", 0 , 0 , 0 );



    }


    @Override
    protected void onResume() {
        super.onResume();

        sensors.onResume(this);
        gps.onResume();


        //threadStart();
        locationInfo = new LocationInfo(this);
        a = new Thread(){
            @Override
            public void run(){
                coordinate = locationInfo.getResult();
            }
        };

        a.start();


    }

    @Override
    protected void onPause() {
        super.onPause();

        sensors.onPause(this);
        gps.onPause();
    }


    @Override
    public void onSensorChanged(SensorEvent event) {


        if(gps.isNewInfo())
//        Log.e("njvgkjkfvbv vbndfjvnfj", String.valueOf(gps.getLatitude()) +  " " + String.valueOf(gps.getLongitude() +  " " + String.valueOf(gps.getAltitude())));

        sensors.checkSensors(event);

        if(sensors.haveNewInfo()){

            model.check(sensors.getXY(), sensors.getXZ(), sensors.getZY());
            mDraw.planes = new ArrayList<Plane>();
            for(int i = 0; i < model.amount(); i++){
                if(model.isOnScreen(i))
                    mDraw.planes.add(model.plane(i));
            }
            mDraw.invalidate();

//            Log.e("0", String.valueOf(sensors.getXY()) + " " + String.valueOf(sensors.getXZ()) + " " + String.valueOf(sensors.getZY()));
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
