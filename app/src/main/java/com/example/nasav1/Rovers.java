package com.example.nasav1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Rovers extends AppCompatActivity {

    ListView l ;
    private String url = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&page=2&api_key=3OROfSQR8chlp7L1fqhm9aDF3JUgFXzLwaLFThij";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roverslistview);
        View v = findViewById(android.R.id.content).getRootView();
        RoverNw r = new RoverNw(v,this);
        r.execute(url);

    }
}

class customAdapter extends ArrayAdapter<String>
{
    ArrayList<String> arrayList;
    Activity context;

    public customAdapter(Activity context, ArrayList<String> a) {
        super(context,R.layout.activity_rovers_listviewitems,a);
        this.arrayList = a;
        this.context = context;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater l = context.getLayoutInflater();
        if (convertView == null) {
            convertView = l.inflate(R.layout.activity_rovers_listviewitems, null);
        }
        View v = l.inflate(R.layout.activity_rovers_listviewitems,null,false);
        Log.e("Inflating Successful","woho");
        ImageView i = (ImageView)v.findViewById(R.id.itemImgView);
        imgBitmap ib =new imgBitmap(i);
        ib.execute(arrayList.get(position));
        Log.e("returning","view");
        return v;
    }
}

class RoverNw extends AsyncTask<String,Void,ArrayList> {
    View v;
    Activity a;

    RoverNw(View view, Activity activity) {
        this.v = view;
        this.a = activity;
    }

    ListView l;

    @Override
    protected ArrayList doInBackground(String... strings) {
        String Json = convertJsontoString(strings[0]);
        ArrayList<String> arr = new ArrayList<>();
        try {
            arr = extractimgfromjson(Json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        Log.e("First url of arr", arr.get());
        return arr;
    }

    @Override
    protected void onPostExecute(ArrayList arrayList) {
        customAdapter c = new customAdapter(a, arrayList);
        Log.e("done", "till here");
        l = v.findViewById(R.id.listv1);
        l.setAdapter(c);
    }

    StringBuilder sb = new StringBuilder();

    public String convertJsontoString(String s) {
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
        Log.e("JSONCNVRTD TO STRING = ", "" + sb);
        return "" + sb;
    }

    public ArrayList<String> extractimgfromjson(String x) throws JSONException {
        ArrayList<String> imglist = new ArrayList<>();
        JSONObject j = new JSONObject(x);
        JSONArray jo = j.getJSONArray("photos");
        for(int i=0;i<20;i++) {
            JSONObject d = jo.getJSONObject(i);
            imglist.add(d.getString("img_src"));
        }
        return imglist;
    }
}

class imgBitmap extends AsyncTask<String, Void, Bitmap> {
    ImageView i;
    imgBitmap(ImageView i)
    {
        this.i = i;
    }
    @Override
    protected Bitmap doInBackground(String... s) {
        Bitmap bimage = null;
        try {
            //convert http to https

            String url = s[0];
            StringBuilder sb = new StringBuilder(url);
            sb.insert(4,'s');
            url = ""+sb;

            InputStream inputStream = new java.net.URL(url).openStream();
            bimage = BitmapFactory.decodeStream(inputStream);

        } catch (Exception e) {
            Log.e("Error Message", "Bitmap Error");
            e.printStackTrace();
        }
        return bimage;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        i.setImageBitmap(bitmap);
        Log.e("reached img insertion","ok");
    }
}