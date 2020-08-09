package com.example.nasav1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Network extends AsyncTask<String,Void,Bitmap> {

    View v;
    Network(View view)
    {
        this.v = view;
    }
    StringBuilder sb = new StringBuilder();
    @Override
    protected Bitmap doInBackground(String... strings) {
        String Json = convertJsontoString(strings[0]);
        String img_link = null;
        try {
            img_link = extractimgfromjson(Json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Bitmap img = getImage(img_link);
        return img;
    }
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        ImageView i = (ImageView)v.findViewById(R.id.apod_imgView);
        i.setImageBitmap(bitmap);

    }

    public String convertJsontoString(String s)
    {
        URL urlConn = null;
        try {
            urlConn = new URL(s);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URLConnection urlConnection = null;
        try {
            urlConnection = urlConn.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader b = null;
        try {
            b = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line = null;
            while ((line = b.readLine()) != null) {
                sb.append(line);
            }
            return "" + sb;
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("JSONCNVRTD TO STRING = ",""+sb);
        return ""+sb;
    }


    public String extractimgfromjson(String x) throws JSONException {
        JSONObject j =new JSONObject(x);
        String  q  = j.getString("url");
        Log.e("Url from json",q);
        return q;
    }

    public Bitmap getImage(String imglink)
    {
        Bitmap bimage = null;
        try {
            InputStream in = new java.net.URL(imglink).openStream();
            bimage = BitmapFactory.decodeStream(in);

        } catch (Exception e) {
            Log.e("Error Message", e.getMessage());
            e.printStackTrace();
        }
        return bimage;
    }
}
