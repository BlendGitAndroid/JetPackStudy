package com.blend.jetpackstudy.databinding;

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity

/**
 * DataBinding做了3件事情：
 * 1.使用JavaPoet，将xml变成相应的类，继承自ViewDataBinding类。根据xml生成新的一个xml，在这个xml中为每个View
 * 设置了一个TAG。在类初始化的时候根据TAG，递归遍历TAG设置给一个View数组，为每一个View设置名字，如果有ID就不用设置了，
 * 并且初始化Choreographer。设置初始化的DirtFlag，当View在attachToWindow的时候，根据flag进行第一次初始化设置
 * 及以后的双向监听等，进行相应的方法调用来进行初始值的设置。
 * 2.内存数据到UI。使用BaseObservable的setValue，通过层层回调其实就做了两件事情，
 * 1是设置改变UI的相应的flag标志位，2是设置Choreographer回调，通过flag找到UI控件进行新值的设置，通知下一次回调进行数据刷新。
 * 3.UI到内存。通过初始化设置的监听器，当UI改变的时候回调，然后设置给相应的内存数据，这样又到了内存数据到UI的刷新，
 * 所以为了防止出现死循坏，需要在设置的方法上设置新旧数据不能相等的判断。
 */
class DatabindingMainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityDatabindingMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityDatabindingMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        activityMainBinding.lifecycleOwner = this
        activityMainBinding.clickHandlers = ClickHandlers(activityMainBinding)
    }


    class ClickHandlers(private val activityMainBinding: ActivityDatabindingMainBinding) {
        var TAG = "ClickHandlers"

        fun confirm(view: View) {
            Log.d(TAG, "触发点击事件了UserObservable")
            val user = UserObservable()
            user.userPhoto = "https://www.baidu.com/img/bd_logo1.png"
            user.userGender = 1
            activityMainBinding.user = user
        }

        fun confirmLiveData(view: View) {
            Log.d(TAG, "触发点击事件了UserLiveData")
            val user = UserLiveData()
            user.userPhoto.value = "https://www.baidu.com/img/bd_logo1.png"
            user.userGender.value = 1
            activityMainBinding.userLiveData = user
        }

    }
}