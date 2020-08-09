package com.example.nasav1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView t1,t2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1 = (TextView)findViewById(R.id.FirstAct);
        t2 = (TextView)findViewById(R.id.SecAct);
    }
    public void getIOTD(View view)
    {
        Intent i = new Intent(this,ImageOftheDay.class);
        startActivity(i);
    }
    public void getROVERS(View view)
    {
        Intent  i = new Intent(this,Rovers.class);
        startActivity(i);
    }
}