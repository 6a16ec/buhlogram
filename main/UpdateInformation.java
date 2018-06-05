package com.example.user.design;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.BreakIterator;
import java.util.concurrent.TimeUnit;

public class UpdateInformation extends AsyncTask<String, Integer, String> {


    public String request;
    public String content = " ";

    public UpdateInformation(double latitude, double longitude, int diapason){

        double latitudeMIN, latitudeMAX, longitudeMIN, longitudeMAX;

        double diapason_coordinates = ((double)diapason / 111);

        latitudeMIN = latitude - diapason_coordinates;
        latitudeMAX= latitude + diapason_coordinates;
        longitudeMIN = longitude - diapason_coordinates;
        longitudeMAX = longitude + diapason_coordinates;

        request = "https://data-live.flightradar24.com/zones/fcgi/feed.js?bounds=";
        request += String.valueOf(latitudeMAX) + "," + String.valueOf(latitudeMIN) + ",";
        request += String.valueOf(longitudeMIN) + "," + String.valueOf(longitudeMAX);
//        request += "&faa=1&mlat=1&flarm=1&adsb=1&gnd=1&air=1&vehicles=1&estimated=1&maxage=7200&gliders=1&stats=1";
    }


    @Override
    protected String doInBackground(String... strings) {

        try {
            URL requestURL = new URL(request);
            BufferedReader in = null;
            in = new BufferedReader(new InputStreamReader(requestURL.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                content += inputLine + "\n";
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    



}