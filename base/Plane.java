package com.example.user.lapin;

public class Plane{

    private double x, y, z;

    private double coordinate1, coordinate2;
    private int angle, height, speed, time;
    private String model, airport_from, airport_to, company;

    private double x_percent, y_percent;
    private boolean onScreen = false;



    Plane(double x, double y, double z){
        this.x = x; this.y = y; this.z = z;
    }

    Plane(String string, double my_coordinate1, double my_coordinate2, double my_height){
        parse(string);
        z = height - my_height;
        x = coordinate1 - my_coordinate1;
        x *= 111000;
        y = coordinate2 - my_coordinate2;
        y *= 111000;
    }



    private void parse(String string){
        String data[] = string.split(" ");
        coordinate1 = Double.parseDouble(data[2]);
        coordinate2 = Double.parseDouble(data[3]);
        angle = Integer.parseInt(data[4]);
        height = Integer.parseInt(data[5]);
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

    public double getCoordinate1() {
        return coordinate1;
    }

    public double getCoordinate2() {
        return coordinate2;
    }

    public int getAngle() {
        return angle;
    }

    public int getHeight() {
        return height;
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

    public void setX_percent(int x_percent) {
        this.x_percent = x_percent;
    }

    public void setY_percent(int y_percent) {
        this.y_percent = y_percent;
    }
}
