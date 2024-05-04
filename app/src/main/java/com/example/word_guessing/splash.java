package com.example.word_guessing;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // user can go back to splash screen
                Intent goToMainActivity = new Intent(splash.this, MainActivity.class);
                startActivity(goToMainActivity);
                finish();
                // user can't go back using the code below but if used the app doesn't work
//                setContentView(R.layout.activity_main);
                // ----------------------------------- temp
            }
        };
        new Handler().postDelayed(runnable,3000);
    }
}