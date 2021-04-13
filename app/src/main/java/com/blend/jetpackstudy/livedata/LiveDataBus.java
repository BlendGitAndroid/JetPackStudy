package com.blend.jetpackstudy.livedata;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.HashMap;
import java.util.Map;

class LiveDataBus {

    private Map<String, MutableLiveData<Object>> bus = new HashMap<>();
    private static LiveDataBus liveDataBus = new LiveDataBus();


    public static LiveDataBus getInstance() {
        return liveDataBus;
    }

    /**
     * 这里的type的目的就是为了拿到泛型的具体类型，默认支持粘性事件
     */
    public synchronized <T> MutableLiveData<T> with(String key, Class<T> type) {
        if (!bus.containsKey(key)) {
            bus.put(key, new MutableLiveData<Object>());
        }
        return (MutableLiveData<T>) bus.get(key);
    }

    public static void main(String[] args) {
        //根据Key，拿到相应的LiveData
        final Class<Integer> integerClass = Integer.class;
        LiveDataBus.getInstance().with("a", Integer.class).observeForever(new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                LiveDataBus.getInstance().with("a", Integer.class).setValue(1);
            }
        });
    }

}
