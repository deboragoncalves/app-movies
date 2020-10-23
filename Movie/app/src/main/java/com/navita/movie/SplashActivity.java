package com.navita.movie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_activity);

        // Handler para começar a Splash e terminar depois de um certo tempo, chamando a activity da intent

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                // Se já tiver logado, ir para main. Se não, login

                SharedPreferences sharedPreferences = getSharedPreferences("data_login", MODE_PRIVATE);

                Intent intent;

                if (!sharedPreferences.getAll().isEmpty()) {

                    intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                } else {

                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    
                }
            }
        }, 4000);
    }
}