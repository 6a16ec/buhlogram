package com.example.user.lapin;

import android.util.Log;

public class Plane{

    private double x_start, y_start, z_start;
    private double x, y, z;

    private double latitude, longitude;
    private int angle, altitude, speed, time;
    private String model, airport_from, airport_to, company;

    private double x_percent, y_percent;
    private boolean onScreen = false;



    Plane(double x, double y, double z){
        this.x = x; this.y = y; this.z = z;
    }

    Plane(String string, double deviceLatitude, double deviceLongitude, double deviceAltitude){
        parse(string);
        z = altitude - deviceAltitude;
        x = latitude - deviceLatitude;
        x *= 111000;
        y = longitude - deviceLongitude;
        y *= 111000;

        x_start = x; y_start = y; z_start = z;
    }

    public void setDefault(){
        x = x_start; y = y_start; z = z_start;
    }


    private void parse(String string){
        String data[] = string.split(" ");
        latitude = Double.parseDouble(data[2]);
        longitude = Double.parseDouble(data[3]);
        angle = Integer.parseInt(data[4]);
        altitude = Integer.parseInt(data[5]);
        speed = Integer.parseInt(data[6]);
        model = data[9];
        airport_from = data[12];
        airport_to = data[13];
        company = data[19];
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

    public int getAltitude() {
        return altitude;
    }

    public int getSpeed() {
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
