package com.example.user.lapin;

import android.util.Log;

import java.util.ArrayList;

public class Plane{

    private double x_start, y_start, z_start;
    private double x, y, z;

    private double latitude, longitude, altitude, speed;
    private int angle, altitude_ft, speed_km_per_hour,  time;
    private String model, airport_from, airport_to, company;

    private double x_percent, y_percent;
    private boolean onScreen = false;


    Plane(ArrayList plane, double device_latitude, double device_longitude, double device_altitude){
        latitude = Double.parseDouble(plane.get(1).toString());
        longitude = Double.valueOf(plane.get(2).toString());
        angle = Integer.valueOf(plane.get(3).toString());
        altitude_ft = Integer.valueOf(plane.get(4).toString());
        speed_km_per_hour = Integer.valueOf(plane.get(5).toString());
        model = plane.get(8).toString();
        airport_from = plane.get(11).toString();
        airport_to = plane.get(12).toString();
        company = plane.get(18).toString();

        speed = (double) speed_km_per_hour * 1000 / 60 / 60;
        altitude = (double)altitude_ft * 0.3048;

        z = altitude - device_altitude;
        x = latitude - device_latitude;
        x *= 111000;
        y = longitude - device_longitude;
        y *= 111000;

        x_start = x; y_start = y; z_start = z;

        Log.e("xyz plane", String.valueOf(x) + " " + String.valueOf(y) + " " + String.valueOf(z));
    }

    public void setDefault(){
        x = x_start; y = y_start; z = z_start;
    }




    public double getDistance(){
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getAngle() {
        return angle;
    }

    public double getAltitude() {
        return altitude;
    }

    public double getSpeed() {
        return speed;
    }

    public int getTimeGettingInfo() {
        return time;
    }

    public String getAirport_from() {
        return airport_from;
    }

    public String getAirport_to() {
        return airport_to;
    }

    public String getCompany() {
        return company;
    }

    public String getModel() {
        return model;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public boolean isOnScreen() {
        return onScreen;
    }

    public void setOnScreen(boolean onScreen) {
        this.onScreen = onScreen;
    }

    public double getX_percent() {
        return x_percent;
    }

    public double getY_percent() {
        return y_percent;
    }

    public void setX_percent(double x_percent) {
        this.x_percent = x_percent;
    }

    public void setY_percent(double y_percent) {
        this.y_percent = y_percent;
    }
}
