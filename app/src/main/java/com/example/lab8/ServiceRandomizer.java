package com.example.lab8;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import java.util.Random;

public class ServiceRandomizer extends Service {

    private boolean isRunning;
    private final char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private final String TAG = "RandomCharacterService";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        isRunning = true;
        new Thread(() -> {
            while (isRunning) {
                try {
                    Thread.sleep(500);
                    char c = alphabet[new Random().nextInt(alphabet.length)];
                    Intent broadcast = new Intent("my.custom.action.tag.lab6");
                    broadcast.putExtra("randomCharacter", c);
                    sendBroadcast(broadcast);
                } catch (InterruptedException e) {
                    Log.e(TAG, "Thread interrupted", e);
                }
            }
        }).start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        isRunning = false;
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
