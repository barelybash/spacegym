package com.example.spacegym;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    // declare views
    Button getStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        Window g = getWindow();
        g.setNavigationBarColor(ContextCompat.getColor(this, R.color.black));
        g.setStatusBarColor(ContextCompat.getColor(this, R.color.black));

        // bind views
        getStarted = findViewById(R.id.getStartedBtn);

        // make button invisible initially
        getStarted.setVisibility(View.INVISIBLE);

        Handler getStartedHandler = new Handler();
        getStartedHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // show button after 3 secs
                getStarted.setVisibility(View.VISIBLE);
            }
        }, 4000);

        // launch login activity on button click
        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(getApplicationContext(), signup.class);
                startActivity(login);
            }
        });
    }
}