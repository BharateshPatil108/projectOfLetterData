package com.example.cmo_lms.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cmo_lms.R;

public class MainActivity extends AppCompatActivity {
    private final Handler handler = new Handler();
    private final Runnable openLoginActivityRunnable = new Runnable() {
        @Override
        public void run() {
            openYourLoginActivity();
        }
    };
    ImageView imgIntro, imgLogo;
    TextView cmoTxt, lmsTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            imgIntro = findViewById(R.id.introImg);
            imgLogo = findViewById(R.id.logo);
            cmoTxt = findViewById(R.id.cmo);
            lmsTxt = findViewById(R.id.lms);

            Animation scaleAnim_cmo = AnimationUtils.loadAnimation(this, R.anim.cmo_txt_anim);
            cmoTxt.startAnimation(scaleAnim_cmo);

            Animation scaleAnim_lms = AnimationUtils.loadAnimation(this, R.anim.lms_txt_anim);
            lmsTxt.startAnimation(scaleAnim_lms);

            Animation scaleAnim = AnimationUtils.loadAnimation(this, R.anim.zoom_in_fade_in);
            imgIntro.startAnimation(scaleAnim);

            handler.postDelayed(openLoginActivityRunnable, 3000);

    }

    private void openYourLoginActivity() {
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
        finish();
    }
}