package com.example.lab8;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText randomCharacterEditText;
    private BroadcastReceiver broadcastReceiver;
    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        randomCharacterEditText = findViewById(R.id.editText_randomCharacter);
        randomCharacterEditText.setCursorVisible(false);
        randomCharacterEditText.setFocusable(false);
        randomCharacterEditText.setFocusableInTouchMode(false);

        serviceIntent = new Intent(getApplicationContext(), ServiceRandomizer.class);

        Button startButton = findViewById(R.id.button_start);
        Button stopButton = findViewById(R.id.button_stop);
        Button nextButton = findViewById(R.id.button_next);

        startButton.setOnClickListener(v -> startService(serviceIntent));
        stopButton.setOnClickListener(v -> {
            stopService(serviceIntent);
            randomCharacterEditText.setText("");
        });
        nextButton.setOnClickListener(v -> {
            stopService(serviceIntent); // Остановим сервис при переходе
            startActivity(new Intent(this, SecondActivity.class));
        });

        broadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter("my.custom.action.tag.lab6");
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopService(serviceIntent); // Остановим сервис при сворачивании
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            char data = intent.getCharExtra("randomCharacter", '?');
            randomCharacterEditText.setText(String.valueOf(data));
        }
    }
}