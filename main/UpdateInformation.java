package com.example.user.lapin;


import android.util.Log;

import java.net.*;
import java.io.*;

public class UpdateInformation {
    String request;

    public UpdateInformation(double latitude, double longitude, int diapason){

        double latitudeMIN, latitudeMAX, longitudeMIN, longitudeMAX;

        double diapason_coordinates = ((double)diapason / 111);

        latitudeMIN = latitude - diapason_coordinates;
        latitudeMAX= latitude + diapason_coordinates;
        longitudeMIN = longitude - diapason_coordinates;
        longitudeMAX = longitude + diapason_coordinates;

        request = "https://data-live.flightradar24.com/zones/fcgi/feed.js?";
        request += String.valueOf(latitudeMAX) + "," + String.valueOf(latitudeMIN) + ",";
        request += String.valueOf(longitudeMIN) + "," + String.valueOf(longitudeMAX);
    }

    public void get() throws IOException {
        String information = new String();

        URL requestURL = new URL(request);
        BufferedReader in = new BufferedReader(new InputStreamReader(requestURL.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null)
            information += inputLine;
        in.close();

        Log.e("getgetgetgetget  --", information);
    }
}
