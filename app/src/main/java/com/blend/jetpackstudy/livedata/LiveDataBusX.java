package com.blend.jetpackstudy.livedata;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

class LiveDataBusX {

    //存放订阅者
    private Map<String, BusMutiableLiveData<Object>> bus = new HashMap();
    private static LiveDataBusX liveDataBus = new LiveDataBusX();

    public static LiveDataBusX getInstance() {
        return liveDataBus;
    }

    //注册订阅者
    public synchronized <T> BusMutiableLiveData<T> with(String key, Class<T> type) {
        if (!bus.containsKey(key)) {
            bus.put(key, new BusMutiableLiveData<Object>());
        }
        return (BusMutiableLiveData<T>) bus.get(key);
    }

    public static class BusMutiableLiveData<T> extends MutableLiveData<T> {
        @Override
        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer observer) {
            super.observe(owner, observer);
            hook(observer);
        }

        private void hook(Observer<? super T> observer) {
            try {
                //1.得到mLastVersion
                Class<LiveData> liveDataClass = LiveData.class;
                Field mObserversFeild = liveDataClass.getDeclaredField("mObservers");
                mObserversFeild.setAccessible(true);
                //获取到这个成员变量的对象
                Object mObserversObject = mObserversFeild.get(this);
                //得到map对应的class对象
                Class<?> mObserversClass = mObserversObject.getClass();
                //需要执行get方法
                Method get = mObserversClass.getDeclaredMethod("get", Object.class);
                get.setAccessible(true);
                Object invokeEntry = get.invoke(mObserversObject, observer);

                Object observerWraper = null;

                if (invokeEntry != null && invokeEntry instanceof Map.Entry) {
                    observerWraper = ((Map.Entry) invokeEntry).getValue();
                }
                if (observerWraper == null) {
                    throw new NullPointerException("observerWraper is null!");
                }
                //得到ObserveWraper的类对象 ,编译擦除问题
                Class<?> superclass = observerWraper.getClass().getSuperclass();
                Field mLastVersion = superclass.getDeclaredField("mLastVersion");
                mLastVersion.setAccessible(true);
                //2.得到mVersion
                Field mVersion = liveDataClass.getDeclaredField("mVersion");
                mVersion.setAccessible(true);
                //3.mLastVersion填到mVersion中
                Object mVersionValue = mVersion.get(this);
                mLastVersion.set(observerWraper, mVersionValue);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
