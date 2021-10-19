package com.eup.rxandroidtimedemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;

import com.eup.rxandroidtimedemo.databinding.ActivityMainBinding;
import com.eup.rxandroidtimedemo.utils.TimeUtil;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private DisposableSubscriber<Long> subscriberInterval;
    private DisposableSubscriber<Long> subscriberDelayInterval;

    private int POLL_INTERVAL = 2;
    private int DELAY_TIME = 10;
    private int INTERVAL_COUNT = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        ButterKnife.bind(this);
    }


    //button interval
    public void startTimerInterval(View view) {
        if (subscriberInterval != null && !subscriberInterval.isDisposed()) {
            subscriberInterval.dispose();
        }
        subscriberInterval = new DisposableSubscriber<Long>() {
            @Override
            public void onNext(Long aLong) {
                addLogMessage("Timer interval:  " + TimeUtil.getCurrentTime());
            }

            @Override
            public void onError(Throwable t) {
                addLogMessage("ERROR Timer interval: " + t.getMessage());
            }

            @Override
            public void onComplete() {
                addLogMessage("Timer interval completed !!");
            }
        };
        addLogMessage("START 2s timer interval...");
        Flowable.interval(POLL_INTERVAL, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(subscriberInterval);
    }

    //button delay timer
    public void startDelayTimer(View view) {
        if (subscriberDelayInterval != null && !subscriberDelayInterval.isDisposed()) {
            subscriberDelayInterval.dispose();
        }
        subscriberDelayInterval = new DisposableSubscriber<Long>() {
            @Override
            public void onNext(Long aLong) {
                addLogMessage("Delay Timer interval: " + TimeUtil.getCurrentTime());
            }

            @Override
            public void onError(Throwable t) {
                addLogMessage("ERROR delay timer: " + t.getMessage());
            }

            @Override
            public void onComplete() {
                addLogMessage("Delay timer completed !!");
            }
        };

        addLogMessage("START timer interval after " + DELAY_TIME + "s !!");

        Flowable.interval(DELAY_TIME, POLL_INTERVAL, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(subscriberDelayInterval);
    }

    //button clear
    public void clearLogMessage(View view) {
        binding.tvLog.setText("");
    }

    //button stop
    public void stopAllTimer(View view) {
        if (subscriberInterval != null && !subscriberInterval.isDisposed()) {
            subscriberInterval.dispose();
            addLogMessage("STOP Timer interval!!");
        }
        if (subscriberDelayInterval != null && !subscriberDelayInterval.isDisposed()) {
            subscriberDelayInterval.dispose();
            addLogMessage("STOP delay timer!!");
        }
    }


    //button message
    public void addLogMessage(String message) {
        binding.tvLog.setText(binding.tvLog.getText().toString() + "\n" + message);
    }
}