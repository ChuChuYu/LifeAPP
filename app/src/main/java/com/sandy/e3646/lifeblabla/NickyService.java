package com.sandy.e3646.lifeblabla;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

public class NickyService extends Service {
    private Timer mTimer;

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d("broadcast", "recieved: " + action);
            mTimer.cancel();
            stopSelf();
//            if(action.equals("com.sandy.e3646.SEND_LAST_BROADCAST")){
//                stopSelf();
//                Log.d("broadcast", "service stop");
//            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        IntentFilter filter = new IntentFilter();
        filter.addAction("com.sandy.e3646.SEND_LAST_BROADCAST");
        registerReceiver(receiver, filter);
        Log.d("service", "oncreate");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startTimer();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        Log.d("Service", "destroy");
    }

    public void startTimer(){
        if (mTimer == null){
            mTimer = new Timer();
        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                logWhenRecieveBroadcast();
                Intent intent = new Intent();
                intent.setAction("com.sandy.e3646.SEND_SECOND_BROADCAST");
                sendBroadcast(intent);
            }
        },1000,1000 * 10);
    }

    public void logWhenRecieveBroadcast(){
        Log.d("broadcast", "service");
    }

}
