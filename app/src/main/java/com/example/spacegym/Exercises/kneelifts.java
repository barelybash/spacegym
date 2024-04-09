package com.example.spacegym.Exercises;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.example.spacegym.R;

public class kneelifts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kneelifts);

        getSupportActionBar().setTitle("High Knee Lifts");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.red_700)));
    }
}