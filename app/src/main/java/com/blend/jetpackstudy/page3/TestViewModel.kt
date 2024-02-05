package com.blend.jetpackstudy.page3

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow


class TestViewModel(application: Application) : AndroidViewModel(application) {
    fun loadStudentMessage(): Flow<PagingData<DataX>> {
        // cachedIn 函数的作用是将服务器返回的数据在 viewModelScope 这个作用域内进行缓存，当手机屏幕旋转时便会保存数据。
        return TestRepository().loadStudentMessage().cachedIn(viewModelScope)
    }
}
