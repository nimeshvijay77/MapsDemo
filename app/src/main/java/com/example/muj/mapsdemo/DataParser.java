package com.example.muj.mapsdemo;

import android.content.SyncRequest;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by muj on 3/8/2018.
 */

public class DataParser {

    private HashMap<String, String> getDuration(JSONArray googleDirectionsJson)
    {
        HashMap<String, String> googleDirectionMap = new HashMap<>();
        String duration = "";
        String distance = "";

        Log.d("json response", googleDirectionsJson.toString());
        try {

            duration = googleDirectionsJson.getJSONObject(0).getJSONObject("duration").getString("text");
            distance = googleDirectionsJson.getJSONObject(0).getJSONObject("distance").getString("text");
            googleDirectionMap.put("duration", duration);
            googleDirectionMap.put("distance", distance);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return googleDirectionMap;
    }



    private HashMap<String, String> getPlace(JSONObject googlePlaceJson)
    {
        HashMap<String , String> googlePlacesMap = new HashMap<>();
        String placeName = "-NA-";
        String vicinity = "-NA-";
        String latitude = "";
        String longitude = "";
        String references = "";

        try {
            if (!googlePlaceJson.isNull("name")) {
                placeName = googlePlaceJson.getString("name");
            }
            if (!googlePlaceJson.isNull("vicinity"))
            {
                vicinity = googlePlaceJson.getString("vicinity");
            }
            latitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");


            references = googlePlaceJson.getString("reference");

            googlePlacesMap.put("place_name" , placeName);
            googlePlacesMap.put("vicinity" , vicinity);
            googlePlacesMap.put("lat" , latitude);
            googlePlacesMap.put("lng" , longitude);
            googlePlacesMap.put("reference" , references);

        }catch (JSONException e) {
                e.printStackTrace();
            }
       return googlePlacesMap;
        }
        private List<HashMap<String,String>> getPlaces(JSONArray jsonArray)
        {
            int count = jsonArray.length();
            List<HashMap<String,String>> placesList = new ArrayList<>();
            HashMap<String ,String> placeMap = null;

            for(int i= 0; i<count;i++)
            {
                try{
                    placeMap = getPlace((JSONObject)jsonArray.get(i));
                    placesList.add(placeMap);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
           return placesList;
        }

        public List<HashMap<String, String>> parse(String jsonData)
        {
            JSONArray jsonArray = null;
            JSONObject jsonObject;

            try {
                jsonObject = new JSONObject(jsonData);
                jsonArray = jsonObject.getJSONArray("result");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return  getPlaces(jsonArray);
        }

        public  String[] parseDirections(String jsonData)
        {
            JSONArray jsonArray = null;
            JSONObject jsonObject;

            try {
                jsonObject = new JSONObject(jsonData);
                jsonArray = jsonObject.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONArray("steps");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return getPaths(jsonArray);
        }


      public String[]  getPaths(JSONArray googleStepsJson)
      {
          int count = googleStepsJson.length();
          String[] polylines = new String[count];

          for (int i=0; i<count;i++ )
          {
              try {
                  polylines[i]= getPath(googleStepsJson.getJSONObject(i));
              } catch (JSONException e) {
                  e.printStackTrace();
              }
          }
          return polylines;
      }


        public String getPath(JSONObject googlePathJson )
        {
            String polyline ="";
            try {
                polyline = googlePathJson.getJSONObject("polyline").getString("points");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return polyline;
        }
    }

