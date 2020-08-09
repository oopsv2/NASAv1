package com.example.nasav1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageOftheDay extends AppCompatActivity {

    TextView t;
    ImageView i;
    String url = "https://api.nasa.gov/planetary/apod?api_key=3OROfSQR8chlp7L1fqhm9aDF3JUgFXzLwaLFThij";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_ofthe_day);
        View v = findViewById(R.id.apod_imgView);

        Network n = new Network(v);
        n.execute(url);
        TextView t = (TextView)findViewById(R.id.tView);
        t.setText("Astronomical Picture Of the Day");

    }
}