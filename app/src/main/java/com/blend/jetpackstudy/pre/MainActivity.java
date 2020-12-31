package com.blend.jetpackstudy.pre;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.blend.jetpackstudy.R;
import com.blend.jetpackstudy.pre.databinding.DataBindingMainActivity;
import com.blend.jetpackstudy.pre.lifecycle.LifeCycleMainActivity;
import com.blend.jetpackstudy.pre.livedata.LiveDataMainActivity;
import com.blend.jetpackstudy.pre.navigation.NavigationMainActivity;
import com.blend.jetpackstudy.pre.room.RoomMainActivity;
import com.blend.jetpackstudy.pre.viewmodel.ViewModelMainActivity;
import com.blend.jetpackstudy.pre.workmanager.WorkManagerMainActivity;


/**
 * https://juejin.im/user/1503787635450584/posts
 * 这个人的博客，讲解了JetPack的知识和对Android的反思，等把JetPack那本书看完，就着手看这个博客。
 */
public class MainActivity extends AppCompatActivity {


    private Button lifecycle;
    private Button navigation;
    private Button viewModel;
    private Button liveData;
    private Button room;
    private Button workManager;
    private Button dataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lifecycle = findViewById(R.id.lifecycle);
        navigation = findViewById(R.id.navigation);
        viewModel = findViewById(R.id.viewModel);
        liveData = findViewById(R.id.liveData);
        room = findViewById(R.id.room);
        workManager = findViewById(R.id.workManager);
        dataBinding = findViewById(R.id.dataBinding);
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
        liveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LiveDataMainActivity.class));
            }
        });
        room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RoomMainActivity.class));
            }
        });
        workManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, WorkManagerMainActivity.class));
            }
        });
        dataBinding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DataBindingMainActivity.class));
            }
        });
    }
}