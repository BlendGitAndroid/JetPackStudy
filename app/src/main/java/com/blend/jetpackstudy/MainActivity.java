package com.blend.jetpackstudy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.blend.jetpackstudy.databinding.ActivityMainBinding;
import com.blend.jetpackstudy.databinding.DatabindingMainActivity;
import com.blend.jetpackstudy.flow.FlowMainActivity;
import com.blend.jetpackstudy.hit.HitMainActivity;
import com.blend.jetpackstudy.lifecycle.LifeCycleMainActivity;
import com.blend.jetpackstudy.livedata.LiveDataMainActivity;
import com.blend.jetpackstudy.navigation.NavigationMainActivity;
import com.blend.jetpackstudy.page3.Page3MainActivity;
import com.blend.jetpackstudy.project.ProjectMainActivity;
import com.blend.jetpackstudy.room.RoomMainActivity;
import com.blend.jetpackstudy.viewbinding.BaseActivity;
import com.blend.jetpackstudy.viewbinding.ViewBindingActivity;
import com.blend.jetpackstudy.viewmodel.ViewModelMainActivity;


public class MainActivity extends BaseActivity<ActivityMainBinding> {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinding.lifecycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LifeCycleMainActivity.class));
            }
        });
        mViewBinding.navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NavigationMainActivity.class));
            }
        });
        mViewBinding.viewModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ViewModelMainActivity.class));
            }
        });
        mViewBinding.liveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LiveDataMainActivity.class));
            }
        });
        mViewBinding.room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RoomMainActivity.class));
            }
        });
        mViewBinding.dataBinding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DatabindingMainActivity.class));
            }
        });
        mViewBinding.viewBinding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ViewBindingActivity.class));
            }
        });

        mViewBinding.hit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HitMainActivity.class));
            }
        });

        mViewBinding.flow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FlowMainActivity.class));
            }
        });

        mViewBinding.page3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Page3MainActivity.class));
            }
        });
        mViewBinding.project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProjectMainActivity.class));
            }
        });
    }
}