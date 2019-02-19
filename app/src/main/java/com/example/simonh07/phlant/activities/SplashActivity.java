package com.example.simonh07.phlant.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.simonh07.phlant.Instructions;
import com.example.simonh07.phlant.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, Instructions.class);
        startActivity(intent);
        finish();
    }
}
