package com.example.user.lapin;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

public class MathModel {

    final int camera_x_angle = 50;
    final int camera_y_angle = 50;

    private ArrayList <Plane> planes = new ArrayList<Plane>();

    //    MathModel(double x_plane, double y_plane, double z_plane){
//        plane = new plane(x_plane, y_plane, z_plane);
//    }
//    public MathModel(){
//        TCP tcp = new TCP();
//    }





    public void addPlane(double x, double y, double z){
        planes.add(new Plane(x,y,z));
    }

    public void addPlane(String string, double my_coordinate1, double my_coordinate2, double my_height){
        planes.add(new Plane(string, my_coordinate1, my_coordinate2, my_height));
    }

    public void check(int xy_angle, int xz_angle, int zy_angle){
        int amount = amount();
        for(int i = 0; i < amount; i++){
            updatePosition(planes.get(i));
            Log.e("gfmerjklgnjkerngbbf ", String.valueOf(planes.get(i).getX()) + " " + String.valueOf(planes.get(i).getY()) + " " + String.valueOf(planes.get(i).getZ()));
            turnSystem(planes.get(i), xy_angle, xz_angle, zy_angle);
            Log.e("gfmerjklgnjkerngdfb ", String.valueOf(planes.get(i).getX()) + " " + String.valueOf(planes.get(i).getY()) + " " + String.valueOf(planes.get(i).getZ()));
            getOnScreen(planes.get(i));
//            planes.get(i).setOnScreen(true);
//            planes.get(i).setX_percent(0.5);
//            planes.get(i).setY_percent(0.5);
        }
    }

    public double percentX(int i){
        return planes.get(i).getX_percent();
    }
    public double percentY(int i){
        return planes.get(i).getY_percent();
    }
    public boolean isOnScreen(int i){
        return planes.get(i).isOnScreen();
    }
    public int amount(){
        return planes.size();
    }

    public Plane plane(int i){
        return planes.get(i);
    }




    public void updatePosition(Plane plane){

        plane.setDefault();
//        double move = plane.getSpeed() * (unixtime() - plane.getTimeGettingInfo());
//
//        double move_x = Math.cos(Math.toRadians(plane.getAngle())) * move;
//        plane.setX(plane.getX() + move_x);
//
//        double move_y = Math.sin(Math.toRadians(plane.getAngle())) * move;
//        plane.setX(plane.getY() + move_y);
    }

    private int unixtime(){
        Date now = new Date();
        Long longTime = new Long(now.getTime()/1000);
        return longTime.intValue();
    }




    private void getOnScreen(Plane plane){

        double a, b, x_min, x_max, y_min, y_max;
        
        double x = plane.getX(), y = plane.getY(), z = plane.getZ();

        double percent_x = 0, percent_y = 0;
        boolean isOnScreen;


        if(z <= 0) isOnScreen = false;
        else{
            a = 2 * z * Math.tan(Math.toRadians(camera_x_angle / 2));
            b = 2 * z * Math.tan(Math.toRadians(camera_y_angle / 2));

            x_min = (-1) * a / 2;
            x_max = a / 2;
            y_min = (-1) * b / 2;
            y_max = b / 2;

            if(x_min <= x && x <= x_max && y_min <= y && y <= y_max){
                isOnScreen = true;
                percent_x = ((x - x_min) / a);
                percent_y = ((y - y_min) / b);
            }
            else{
                isOnScreen = false;
            }
        }

        plane.setX_percent(percent_x);
        plane.setY_percent(percent_y);
        plane.setOnScreen(isOnScreen);

    }

    public void turnSystem(Plane plane, int xy_angle, int xz_angle, int zy_angle){

        double x, y, z;

        xy_angle *= (-1);
        xz_angle *= (-1);
        zy_angle *= (-1);

        double xy_angle_radian, xz_angle_radian, zy_angle_radian;


        xy_angle_radian = (double) xy_angle / 180 * Math.PI;
        xz_angle_radian = (double) xz_angle / 180 * Math.PI;
        zy_angle_radian = (double) zy_angle / 180 * Math.PI;

//            xz_angle_radian += Math.PI;


        // turn around X
        double[][] x_matrix =  new double[][]{
                {1, 0, 0},
                {0, Math.cos(zy_angle_radian), (-1) * Math.sin(zy_angle_radian)},
                {0, Math.sin(zy_angle_radian), Math.cos(zy_angle_radian)}
        };

        // turn around Y
        double[][] y_matrix =  new double[][]{
                {Math.cos(xz_angle_radian), 0, Math.sin(xz_angle_radian)},
                {0, 1, 0},
                {(-1) * Math.sin(xz_angle_radian), 0, Math.cos(xz_angle_radian)}
        };

        // turn around Z
        double[][] z_matrix =  new double[][]{
                {Math.cos(xy_angle_radian), (-1) * Math.sin(xy_angle_radian), 0},
                {Math.sin(xy_angle_radian), Math.cos(xy_angle_radian), 0},
                {0, 0, 1}
        };

        double[][] main_matrix = new double[][]{
                {plane.getX()},
                {plane.getY()},
                {plane.getZ()}
        };

//            double distance = distance();
//            // turn around X
//            double angle =  Math.acos(y_absolute / distance);
//            angle += (zy_angle / 180 * Math.PI);
//
////            x =  Math.atan(1);
//            z = (int) (distance * Math.sin(angle));
//            y = (int) (distance * Math.cos(angle));



        main_matrix = matrix_multiplication(x_matrix, main_matrix);
        main_matrix = matrix_multiplication(y_matrix, main_matrix);
        main_matrix = matrix_multiplication(z_matrix, main_matrix);

//            main_matrix = matrix_multiplication_second(x_matrix, main_matrix);
//            main_matrix = matrix_multiplication_second(y_matrix, main_matrix);
//            main_matrix = matrix_multiplication_second(z_matrix, main_matrix);

//            double[][] result;
//            result = matrix_multiplication(x_matrix, y_matrix);
//            result = matrix_multiplication(result, z_matrix);
//            main_matrix = matrix_multiplication(result, main_matrix);

        x = Math.round(main_matrix[0][0]);
        y = Math.round(main_matrix[1][0]);
        z = Math.round(main_matrix[2][0]);



//            x =  (double)((int) (x_matrix[1][1] * 100)) / 100;
//            y =  (double)((int) (x_matrix[1][2] * 100)) / 100;
//            z =  (double)((int) (x_matrix[2][1] * 100)) / 100;


        plane.setX(x);
        plane.setY(y);
        plane.setZ(z);
    }


//    public String getString(){
//
//        String x_string, y_string, z_string;
//
//        x_string = String.valueOf(x);
//        y_string = String.valueOf(y);
//        z_string = String.valueOf(z);
//
//        return x_string + " " + y_string + " " + z_string;
//    }


    private double[][] matrix_multiplication(double[][] first, double second[][]) {


        int m = first.length;
        int n = second[0].length;
        int o = second.length;
        double[][] result = new double[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < o; k++) {
                    result[i][j] += first[i][k] * second[k][j];
                }
            }
        }

        return result;
    }

    private double[][] matrix_multiplication_second(double[][] first, double second[][]) {

        double[][] result = new double[3][1];
        result[0][0] = first[0][0] * second[0][0] + first[0][1] * second[1][0] + first[0][2] * second[2][0];
        result[1][0] = first[1][0] * second[0][0] + first[1][1] * second[1][0] + first[1][2] * second[2][0];
        result[2][0] = first[2][0] * second[0][0] + first[2][1] * second[1][0] + first[2][2] * second[2][0];

        return result;
    }




}

