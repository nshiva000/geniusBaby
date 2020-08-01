package com.hanivisu.geniusbaby;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.hanivisu.geniusbaby.storage.SharedPrefManager;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SharedPrefManager.get_mInstance(SplashScreenActivity.this).isLoggedIn()) {
                    Intent intent = new Intent(SplashScreenActivity.this, DateOfDelivaryActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                finish();

            }
        },2000);
    }
}

