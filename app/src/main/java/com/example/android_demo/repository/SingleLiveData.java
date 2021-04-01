package com.example.android_demo.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

/**
 * Created by: var_rain.
 * Created date: 2020/7/23.
 * Description: 单例观察者LiveData对象
 */
public class SingleLiveData<T> extends MutableLiveData<T> {

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
        if (!this.hasObservers()) {
            super.observe(owner, observer);
        }
    }
}
