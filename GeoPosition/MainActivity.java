package com.flappy.user.gps;

import java.util.Date;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    LocationInfo locationInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        locationInfo = new LocationInfo(this);
        a = new Thread(){
            @Override
            public void run(){
                Coordinate coordinate = locationInfo.getResult();
                Log.e("AAAAANTOOOOOOOOOONPIDOR",coordinate.toString());
            }
        };
        a.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            a.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    Thread a;


}
