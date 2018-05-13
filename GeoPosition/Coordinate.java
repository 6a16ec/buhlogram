package com.flappy.user.gps;

/**
 * Created by user on 13.05.18.
 */

public class Coordinate {
    Coordinate(double Latitude ,double Longitude,double Altitude){
        this.Latitude = Latitude;
        this.Longitude=Longitude;
        this.Altitude=Altitude;
    }

    @Override
    public String toString(){
        return Latitude + " " + Longitude + " " + Altitude;
    }
    double Latitude;
    double Longitude;
    double Altitude;
}
