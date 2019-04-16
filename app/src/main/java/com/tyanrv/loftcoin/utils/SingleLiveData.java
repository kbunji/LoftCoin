package com.tyanrv.loftcoin.utils;

import java.util.concurrent.atomic.AtomicBoolean;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import timber.log.Timber;


public class SingleLiveData<T> extends MutableLiveData<T> {

    private AtomicBoolean pending = new AtomicBoolean(false);


    @Override
    @MainThread
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {

        if (hasActiveObservers()) {
            Timber.d("Multiple observers registered but only one will be notified of changes.");
        }

        super.observe(owner, t -> {
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(t);
            }
        });
    }

    @MainThread
    @Override
    public void setValue(T value) {
        pending.set(true);
        super.setValue(value);
    }

    void call() {
        setValue(null);
    }
}
