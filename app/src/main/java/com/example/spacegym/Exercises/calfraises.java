package com.example.spacegym.Exercises;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;

import com.example.spacegym.R;

public class calfraises extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calfraises);

        getSupportActionBar().setTitle("Calf Raises");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.red_700)));
    }
}