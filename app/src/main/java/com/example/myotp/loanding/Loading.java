package com.example.myotp.loanding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myotp.R;
import com.example.myotp.menu.Drawables;

public class Loading extends AppCompatActivity {
    private static int SPLASH_SCREEN = 7000;
    Animation topAnim, buttonAnim;
    ImageView imageView;
    TextView textView,textView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.loading);

            topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
            buttonAnim = AnimationUtils.loadAnimation(this, R.anim.button_animation);

            imageView = findViewById(R.id.img_emergency);
            textView = findViewById(R.id.textView);
            textView1 = findViewById(R.id.textView1);

            imageView.setAnimation(topAnim);
            textView.setAnimation(buttonAnim);
            textView1.setAnimation(buttonAnim);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(Loading.this, Drawables.class);
                    startActivity(intent);
                    finish();
                }
            },SPLASH_SCREEN);

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}