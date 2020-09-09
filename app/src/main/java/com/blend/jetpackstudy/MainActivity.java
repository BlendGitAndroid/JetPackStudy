package com.blend.jetpackstudy;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.blend.jetpackstudy.lifecycle.MyLocationListener;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}