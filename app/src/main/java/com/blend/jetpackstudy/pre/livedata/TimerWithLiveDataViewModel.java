package com.blend.jetpackstudy.pre.livedata;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Timer;
import java.util.TimerTask;

public class TimerWithLiveDataViewModel extends ViewModel {

    private MutableLiveData<Integer> currentSecond;

    private Timer mTimer;

    public void startTiming() {
        getCurrentSecond().setValue(0);
        if (mTimer == null) {
            mTimer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    currentSecond.postValue(currentSecond.getValue() + 1);
                }
            };
            mTimer.schedule(timerTask, 1000, 1000);
        }
    }

    public MutableLiveData<Integer> getCurrentSecond() {
        if (currentSecond == null) {
            currentSecond = new MutableLiveData<>();
        }
        return currentSecond;
    }


}
