package com.example.lab8;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class ServiceMusic extends Service {

    private MediaPlayer player;
    private final String CHANNEL_ID = "MusicChannel";

    @Override
    public void onCreate() {
        super.onCreate();

        player = new MediaPlayer();

        player.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            mp.setVolume(3.5f, 3.5f);// Зациклить
            mp.start();          // Запустить
        });

        // Установить источник из res/raw/song.mp3
        try {
            player.setDataSource(getApplicationContext().getResources().openRawResourceFd(R.raw.song));
            player.prepareAsync(); // Асинхронная подготовка (ускоряет старт)
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // Канал уведомлений
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "Music Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
        );

        NotificationManager manager = getSystemService(NotificationManager.class);
        if (manager != null) {
            manager.createNotificationChannel(channel);
        }

        // Уведомление
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Music Player")
                .setContentText("Playing music in background")
                .setSmallIcon(R.drawable.music_note)
                .build();

        startForeground(1, notification);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player != null) {
            if (player.isPlaying()) player.stop();
            player.release();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}