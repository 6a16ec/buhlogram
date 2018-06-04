package com.example.user.lapin;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import static android.content.Context.LOCATION_SERVICE;

public class GPS{

    private LocationManager locationManager;
    private double latitude, longitude;
    private  boolean newInfo = false;

    public void onCreate(MainActivity mainActivity){
        locationManager = (LocationManager) mainActivity.getSystemService(LOCATION_SERVICE);
    }

    public void onResume(MainActivity mainActivity){
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

        newInfo = true;

        Log.e("njvgkjkfvbv vbndfjvnfj", String.valueOf(getLatitude()) +  " " + String.valueOf(getLongitude()));
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
}
