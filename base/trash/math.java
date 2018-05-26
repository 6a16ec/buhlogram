package com.example.user.myapplication;

import android.widget.TextView;

public class MathModel {

    private plane plane;


    MathModel(double x_plane, double y_plane, double z_plane){
        plane = new plane(x_plane, y_plane, z_plane);
    }



    public String checkPlane(int xy_angle, int xz_angle, int zy_angle){
        plane.updatePosition();
        plane.turnSystem(xy_angle, xz_angle, zy_angle);
        double[] coordinates = plane.position();

        return Double.toString(coordinates[0]) + " " + Double.toString(coordinates[1]) + " " + Double.toString(coordinates[2]);


    }

    private class plane{

        private double x, y, z, speed;

        plane(double x, double y, double z){
            this.x = x; this.y = y; this.z = z;
        }

        public void updatePosition(){
            x = 0; y = 0; z = 100;

        }

        public void turnSystem(int xy_angle, int xz_angle, int zy_angle){

            xy_angle *= (-1); xz_angle *= (-1); zy_angle *= (-1);

            double x, y, z;

            // turn around X
            x = this.x; y = this.y; z = this.z;
            this.x = x * 1 + y * 0 + z * 0;
            this.y = x * 0 + y *  Math.cos(zy_angle)+ z * Math.sin(zy_angle);
            this.z = x * 0 + y * Math.sin(zy_angle) + z * Math.cos(zy_angle);

            // turn around Y
            x = this.x; y = this.y; z = this.z;
            this.x = x * Math.cos(xz_angle) + y * 0 + z * Math.sin(xz_angle);
            this.y = x * 0 + y * 1 + z * 0;
            this.z = (-1) * x * Math.sin(xz_angle) + y * 0 + z * Math.cos(xz_angle);

            // turn around Z
            x = this.x; y = this.y; z = this.z;
            this.x = x * Math.cos(xy_angle) - y * Math.sin(xy_angle) + z * 0;
            this.y = x * Math.sin(xy_angle) + y *  Math.cos(xy_angle)+ z * 0;
            this.z = x * 0 + y * 0 + z * 1;
        }

        double[] position(){

            double[] coordinates = new double[3];

            coordinates[0] = Math.round(this.x);
            coordinates[1] = Math.round(this.y);
            coordinates[2] = Math.round(this.z);

            return coordinates;
        }

    }

}

