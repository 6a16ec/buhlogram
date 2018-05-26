package com.example.user.myapplication;

import java.util.ArrayList;

public class MathModel {

    final int camera_x_angle = 30;
    final int camera_y_angle = 30;

    private ArrayList <plane> planes = new ArrayList<plane>();

//    MathModel(double x_plane, double y_plane, double z_plane){
//        plane = new plane(x_plane, y_plane, z_plane);
//    }
    public MathModel(){

    }



    public void addPlane(double x, double y, double z){
        planes.add(new plane(x,y,z));
    }

    public void check(int xy_angle, int xz_angle, int zy_angle){
        planes.get(0).updatePosition();
        planes.get(0).turnSystem(xy_angle, xz_angle, zy_angle);
        planes.get(0).getOnScreen();
    }

    public double percentX(){
        return planes.get(0).percent_x;
    }
    public double percentY(){
        return planes.get(0).percent_y;
    }
    public boolean isOnScreen(){
        return planes.get(0).isOnScreen;
    }

    public double len(){
        return planes.get(0).len();
    }

    public String getString(){
        return planes.get(0).getString();
    }


//    public String checkPlane(int xy_angle, int xz_angle, int zy_angle){
//        plane.updatePosition();
//        plane.turnSystem(xy_angle, xz_angle, zy_angle);
//        double[] coordinates = plane.position();
//
//        return Double.toString(coordinates[0]) + " " + Double.toString(coordinates[1]) + " " + Double.toString(coordinates[2]);
//
//
//    }

    private class plane{

        private double x, y, z;
        private double x_absolute, y_absolute, z_absolute;

        private boolean isOnScreen;
        private double percent_x, percent_y;

        plane(double x, double y, double z){
            this.x_absolute = x; this.y_absolute = y; this.z_absolute = z;
        }


        public double len(){
            return Math.sqrt(x*x + y*y + z*z);
        }


        public void updatePosition(){
            //speed and other.....
            // change x_absolute and other...
        }

//        public void turnSystem(int xy_angle, int xz_angle, int zy_angle){
//
//            xy_angle *= (-1); xz_angle *= (-1); zy_angle *= (-1);
//
//            double x, y, z;
//
//            // turn around X
//            x = this.x; y = this.y; z = this.z;
//            this.x = x * 1 + y * 0 + z * 0;
//            this.y = x * 0 + y *  Math.cos(zy_angle)+ z * Math.sin(zy_angle);
//            this.z = x * 0 + y * Math.sin(zy_angle) + z * Math.cos(zy_angle);
//
//            // turn around Y
//            x = this.x; y = this.y; z = this.z;
//            this.x = x * Math.cos(xz_angle) + y * 0 + z * Math.sin(xz_angle);
//            this.y = x * 0 + y * 1 + z * 0;
//            this.z = (-1) * x * Math.sin(xz_angle) + y * 0 + z * Math.cos(xz_angle);
//
//            // turn around Z
//            x = this.x; y = this.y; z = this.z;
//            this.x = x * Math.cos(xy_angle) - y * Math.sin(xy_angle) + z * 0;
//            this.y = x * Math.sin(xy_angle) + y *  Math.cos(xy_angle)+ z * 0;
//            this.z = x * 0 + y * 0 + z * 1;
//        }

        private void getOnScreen(){

            double a, b, x_min, x_max, y_min, y_max;

            a = 2 * z * Math.tan(camera_x_angle / 2);
            b = 2 * z * Math.tan(camera_y_angle / 2);

            x_min = (-1) * a / 2;
            x_max = a / 2;
            y_min = (-1) * b / 2;
            y_max = b / 2;

            if(x_min <= x && x <= x_max && y_min <= y && y <= y_max){
                isOnScreen = true;
                percent_x = (x - x_min) / a;
                percent_y = (y - y_min) / b;
            }
            else{
                isOnScreen = false;
            }
        }

        public double percentX(){
            return percent_x;
        }
        public double percentY(){
            return percent_y;
        }
        public boolean isOnScreen(){
            return isOnScreen;
        }

        public void turnSystem(int xy_angle, int xz_angle, int zy_angle){
            xy_angle *= (-1); xz_angle *= (-1); zy_angle *= (-1);

            // turn around X
            double[][] x_matrix =  new double[][]{
                    {1, 0, 0},
                    {0, Math.cos(zy_angle), (-1) * Math.sin(zy_angle)},
                    {0, Math.sin(zy_angle), Math.cos(zy_angle)}
            };

            // turn around Y
            double[][] y_matrix =  new double[][]{
                    {Math.cos(xz_angle), 0, Math.sin(xz_angle)},
                    {0, 1, 0},
                    {(-1) * Math.sin(xz_angle), 0, Math.cos(xz_angle)}
            };

            // turn around Z
            double[][] z_matrix =  new double[][]{
                    {Math.cos(xy_angle), (-1) * Math.sin(xy_angle), 0},
                    {Math.sin(xy_angle), Math.cos(xy_angle), 0},
                    {0, 0, 1}
            };

            double[][] main_matrix = new double[][]{
                    {x_absolute},
                    {y_absolute},
                    {z_absolute}
            };

            main_matrix = matrix_multiplication(x_matrix, main_matrix);
            main_matrix = matrix_multiplication(y_matrix, main_matrix);
            main_matrix = matrix_multiplication(z_matrix, main_matrix);


            x = Math.round(main_matrix[0][0]);
            y = Math.round(main_matrix[1][0]);
            z = Math.round(main_matrix[2][0]);

        }

        double[] position(){

            double[] coordinates = new double[3];

            coordinates[0] = this.x;
            coordinates[1] = this.y;
            coordinates[2] = this.z;

            return coordinates;
        }

        public String getString(){

            String x_string, y_string, z_string;

            x_string = String.valueOf(x);
            y_string = String.valueOf(y);
            z_string = String.valueOf(z);

            return x_string + " " + y_string + " " + z_string;
        }


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
    }

}

