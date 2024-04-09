package com.example.spacegym.Exercises;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.example.spacegym.R;

public class jumpingjacks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jumpingjacks);

        getSupportActionBar().setTitle("Jumping Jacks");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.red_700)));
    }
}