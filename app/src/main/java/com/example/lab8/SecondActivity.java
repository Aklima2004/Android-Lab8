package com.example.lab8;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class SecondActivity extends AppCompatActivity {

    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2); //activity_second

        // Инициализация Intent для ForegroundService
        serviceIntent = new Intent(this, MyService.class); //ServiceMusic
    }

    // Запуск сервиса (Foreground Service)
    public void startService(View view) {
        ContextCompat.startForegroundService(this, serviceIntent);
    }

    // Остановка сервиса
    public void stopService(View view) {
        stopService(serviceIntent);
    }

    // Переход обратно в MainActivity (Background Service)
    public void backToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
