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
    private static final LiveDataBusX liveDataBus = new LiveDataBusX();

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

    /**
     * 当LiveData调用observe方法添加观察者的时候，使用HOOK技术，使ObserverWrapper的mLastVersion等于LiveData的Version
     * <p>
     * 为了避免粘性事件，使用hook技术，当Livedata添加观察值的时候，就是调用observe方法的时候，将LiveData的version赋值给Observer的
     * version。因为LiveData就是判断当LiveData的version大于Observer的Version的时候，就不会回调Observer。
     *
     * @param <T>
     */
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
                //获取到字段
                Field mObserversFeild = liveDataClass.getDeclaredField("mObservers");
                mObserversFeild.setAccessible(true);
                //获取到这个成员变量对象的值
                Object mObserversObject = mObserversFeild.get(this);
                //得到map对应的class对象
                Class<?> mObserversClass = mObserversObject.getClass();
                //需要执行get方法
                Method get = mObserversClass.getDeclaredMethod("get", Object.class);
                get.setAccessible(true);
                //调用map的get方法，得到Entry<K, V>键值对
                Object invokeEntry = get.invoke(mObserversObject, observer);

                Object observerWraper = null;

                if (invokeEntry != null && invokeEntry instanceof Map.Entry) {
                    observerWraper = ((Map.Entry) invokeEntry).getValue();
                }
                if (observerWraper == null) {
                    throw new NullPointerException("observerWraper is null!");
                }
                //之前得到的是LifecycleBoundObserver，getSuperclass得到ObserverWrapper对象
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
