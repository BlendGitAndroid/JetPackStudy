package com.blend.jetpackstudy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.blend.jetpackstudy.lifecycle.LifeCycleMainActivity;
import com.blend.jetpackstudy.navigation.NavigationMainActivity;
import com.blend.jetpackstudy.viewmodel.ViewModelMainActivity;


public class MainActivity extends AppCompatActivity {


    private Button lifecycle;
    private Button navigation;
    private Button viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lifecycle = findViewById(R.id.lifecycle);
        navigation = findViewById(R.id.navigation);
        viewModel = findViewById(R.id.viewModel);
        lifecycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LifeCycleMainActivity.class));
            }
        });
        navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NavigationMainActivity.class));
            }
        });
        viewModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ViewModelMainActivity.class));
            }
        });

    }
}