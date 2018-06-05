package com.example.user.design;

import android.os.AsyncTask;
import android.util.JsonReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class UpdateInformation extends AsyncTask<String, Integer, String> {


    private String request;
    private ArrayList planes = new ArrayList();

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

            JsonReader jsonReader = new JsonReader(in);

            jsonReader.beginObject();

            jsonReader.nextName();
            int full_count = jsonReader.nextInt();
            jsonReader.nextName();
            int version = jsonReader.nextInt();

            while(jsonReader.hasNext()){
                String id = jsonReader.nextName();
                jsonReader.beginArray();

                ArrayList plane = new ArrayList();

                while(true){
                    try{
                        plane.add(jsonReader.nextString());
                    }
                    catch(Exception e1){
                        try{
                            plane.add(jsonReader.nextInt());
                        }
                        catch (Exception e2){
                            try {
                                plane.add(jsonReader.nextDouble());
                            }
                            catch(Exception e3){
                                break;
                            }
                        }
                    }
                }
                jsonReader.endArray();

                planes.add(plane);

            }
            jsonReader.endObject();
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getAmount(){
        return planes.size();
    }

    public ArrayList getPlane(int i) {
        return (ArrayList) planes.get(i);
    }
}
