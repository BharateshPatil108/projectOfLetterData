package com.example.cmo_lms.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.cmo_lms.R;

public class NoInternetActivity extends AppCompatActivity {
    Button closeAppButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);

        closeAppButton = findViewById(R.id.closeAppButton);
        closeAppButton.setOnClickListener(v -> finishAffinity());
    }
}