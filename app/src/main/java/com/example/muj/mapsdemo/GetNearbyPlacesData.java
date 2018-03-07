package com.example.muj.mapsdemo;

import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;

import java.io.IOException;

/**
 * Created by muj on 3/8/2018.
 */

public class GetNearbyPlacesData extends AsyncTask<Object, String, String> {

   String googlePlacesData;
   GoogleMap mMap;
   String url;

    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap)objects[0];
        url = (String)objects[1];

        DownloadUrl downloadUrl = new DownloadUrl();
        try {
            googlePlacesData = downloadUrl.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
