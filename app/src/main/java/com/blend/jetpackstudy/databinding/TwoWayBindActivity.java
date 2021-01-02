package com.blend.jetpackstudy.databinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.blend.jetpackstudy.R;

public class TwoWayBindActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_two_way_bind);
        final TwoWayBindingViewModel twoWayBindingViewModel = new TwoWayBindingViewModel();
        ActivityTwoWayBindBinding bindBinding = DataBindingUtil.setContentView(this, R.layout.activity_two_way_bind);
        bindBinding.setViewModel(twoWayBindingViewModel);

        bindBinding.toWayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                twoWayBindingViewModel.setUserName(twoWayBindingViewModel.getUserName() + " Blend");
            }
        });
    }
}