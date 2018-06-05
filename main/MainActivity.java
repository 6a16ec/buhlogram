package com.example.user.lapin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.JsonReader;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends Activity implements SensorEventListener {
    int counter = 0;
    int diapason = 50;

    Sensors sensors = new Sensors();
    GPS gps = new GPS();


    UpdateInformation updater;





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





//        model.addPlane("# # 0 0 0 100 300 # # A320 # # MSK NN # # # # # LOL", 0 , 0 , 0 );
        ArrayList plane = new ArrayList();

        for(int i = 0; i < 20; i++)
            plane.add(new Object());

        plane.add(1, 0.001);
        plane.add(2, 0.001);
        plane.add(3, 0);
        plane.add(4, 300);
        plane.add(5, 500);
        plane.add(8, "A320");
        plane.add(11, "MSK");
        plane.add(12, "NN");
        plane.add(18, "LOL");
        model.addPlane(new Plane(plane, 0, 0 ,0));
//        model.addPlane("# # 0 0 0 100 300 # # A320 # # MSK NN # # # # # LOL", 0 , 0 , 0 );
//
        flyInfoTimer.start();

    }


    @Override
    protected void onResume() {
        super.onResume();

        sensors.onResume(this);
        gps.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        sensors.onPause(this);
        gps.onPause();
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

//
//        if(gps.isNewInfo()) {
//            updater = new UpdateInformation(gps.getLatitude(), gps.getLongitude(), 50);
//            Log.e("njvgkjkfvbv vbndfjvnfj", String.valueOf(gps.getLatitude()) +  " " + String.valueOf(gps.getLongitude() +  " " + String.valueOf(gps.getAltitude())));
//            gps.setNewInfo(false);
//        }

//        if(updater != null) Log.e("njdkvnsjkvnjdfv", String.valueOf(updater.isNewInfo()));


        if(updater != null && updater.isNewInfo()){
            Log.e("-----mdfklbvnfdklb---", String.valueOf(updater.getAmount()));
            Log.e("Link", updater.request);
            model.clearArray();
            for(int i = 0; i < updater.getAmount(); i++){
                model.addPlane(new Plane(updater.getPlane(i), gps.getLatitude(), gps.getLongitude(), gps.getAltitude()));
            }
            updater.setNewInfo(false);
        }

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





    CountDownTimer flyInfoTimer = new CountDownTimer(Integer.MAX_VALUE, 10000){


        int i = 0;
        @Override
        public void onTick(long l) {

            updater = new UpdateInformation(gps.getLatitude(), gps.getLongitude(), diapason);
            updater.execute();

//            UpdateInformation updater = new UpdateInformation(56, 44, 50);
//            while(!updater.isNewInfo());
//            model.clearArray();
//            for(int i = 0; i < updater.getAmount(); i++){
//                model.addPlane(new Plane(updater.getPlane(i), 56, 44, 100));
//            }
//            Log.d("nTIIIMEEE-----", "hello" + String.valueOf(counter));
//            counter += 1;
//            JsonReader jsonReader = new JsonReader();
//            jsonReader.
        }

        @Override
        public void onFinish() {

        }
    };



}
