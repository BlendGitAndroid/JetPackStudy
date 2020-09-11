package com.blend.jetpackstudy.room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class StudentViewModel extends AndroidViewModel {

    private MyDatabase mMyDatabase;
    private LiveData<List<Student>> mListLiveData;

    public StudentViewModel(@NonNull Application application) {
        super(application);
        mMyDatabase = MyDatabase.getInstance(application);
        mListLiveData = mMyDatabase.studentDao().getStudentListLiveData();
    }

    public LiveData<List<Student>> getListLiveData() {
        return mListLiveData;
    }
}
