package com.sandy.e3646.lifeblabla;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.sandy.e3646.lifeblabla.mainactivity.MainActivity;
import com.sandy.e3646.lifeblabla.setting.SettingFragment;

import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service {

    private Timer mTimer;
    private SettingFragment mSettingFragment;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {



        return null;
    }

    @Override
    public void onCreate() {
        Log.d("service is created", "");

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        sendNotification("diaryedit");
        printService();

        Log.d("service is running", "");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    private void sendNotification(String string) {

        Log.d("service is running", "");

        Intent intent = new Intent(getApplication(), MainActivity.class);
        intent.putExtra("fragment", string);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplication(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager = (NotificationManager)getApplication().getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplication());
        builder.setSmallIcon(R.mipmap.ic_launcher_foreground)
                .setContentTitle("Brandy Note")
                .setContentText("快來編寫今天的日記吧！")
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent);
        notificationManager.notify(1, builder.build());


    }

    public void printService() {


        if (mTimer == null){
            mTimer = new Timer();
        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.d("service is running", "");

                if (mSettingFragment == null) {
                    mSettingFragment = new SettingFragment();
                }

                mSettingFragment.printSometing();
            }
        },1000,500 * 10);

    }

}
