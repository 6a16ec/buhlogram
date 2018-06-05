package com.example.user.lapin;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import static android.content.Context.LOCATION_SERVICE;

public class GPS{

    private LocationManager locationManager;
    private double latitude, longitude, altitude = 1.0;
    private  boolean newInfo = false;

    public void onCreate(Context context){
        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
    }

    public void onResume(){
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000 * 10, 10, locationListener);
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 1000 * 10, 10,
                locationListener);
    }

    public void onPause(){
        locationManager.removeUpdates(locationListener);
    }


    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            changeLocation(location);
        }

        @Override
        public void onProviderDisabled(String provider) {
            checkEnabled();
        }

        @Override
        public void onProviderEnabled(String provider) {
            checkEnabled();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            if (provider.equals(LocationManager.GPS_PROVIDER)) {
                String.valueOf(status);
            } else if (provider.equals(LocationManager.NETWORK_PROVIDER)) {
                String.valueOf(status);
            }
        }
    };



    private void changeLocation(Location location) {
        if (location == null) return;

        latitude = location.getLatitude();
        longitude = location.getLongitude();
        altitude = location.getAltitude();

        newInfo = true;

        Log.e("njvgkjkfvbv vbndfjvnfj", String.valueOf(getLatitude()) +  " " + String.valueOf(getLongitude()) +  " " + String.valueOf(getAltitude()));
    }

    private void checkEnabled() {
        boolean EnabledGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean EnabledNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private boolean isEnabledGPS(){
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public boolean isNewInfo() {
        return newInfo;
    }

    public void setNewInfo(boolean newInfo) {
        this.newInfo = newInfo;
    }

    public double getAltitude() {
        return altitude;
    }
}
