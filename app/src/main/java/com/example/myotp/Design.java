package com.example.myotp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.myotp.login.DeshboadActivity;
import com.example.myotp.menu.Drawables;

public class Design extends AppCompatActivity {
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.design);

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent  intent = new Intent(this, DeshboadActivity.class);
        startActivity(intent);
        finish();
    }
}