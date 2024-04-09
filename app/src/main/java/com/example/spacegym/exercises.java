package com.example.spacegym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spacegym.Exercises.calfraises;
import com.example.spacegym.Exercises.deadlifts;
import com.example.spacegym.Exercises.hipraises;
import com.example.spacegym.Exercises.inclinepushups;
import com.example.spacegym.Exercises.jumpingjacks;
import com.example.spacegym.Exercises.kneelifts;
import com.example.spacegym.Exercises.lunges;
import com.example.spacegym.Exercises.pushups;
import com.example.spacegym.Exercises.sidebends;
import com.example.spacegym.Exercises.sideleglifts;
import com.example.spacegym.Exercises.squats;
import com.example.spacegym.Exercises.stationarybike;
import com.example.spacegym.Exercises.swimming;
import com.example.spacegym.Exercises.treadmill;
import com.example.spacegym.Exercises.walking;
import com.example.spacegym.Exercises.wateraerobics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class exercises extends AppCompatActivity {
    // declare views
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    View header;
    CardView ex1, ex2, ex3, ex4;
    TextView bmiHeader, ex1Text, ex2Text, ex3Text, ex4Text;

    Intent exercise1;
    Intent exercise2;
    Intent exercise3;
    Intent exercise4;

    String userID;
    String BMI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        getSupportActionBar().hide();

        // bind views
        header = findViewById(R.id.header);
        bmiHeader = findViewById(R.id.bmiHeader);
        ex1 = findViewById(R.id.ex1);
        ex2 = findViewById(R.id.ex2);
        ex3 = findViewById(R.id.ex3);
        ex4 = findViewById(R.id.ex4);
        ex1Text = findViewById(R.id.ex1Text);
        ex2Text = findViewById(R.id.ex2Text);
        ex3Text = findViewById(R.id.ex3Text);
        ex4Text = findViewById(R.id.ex4Text);


        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        userID = fAuth.getCurrentUser().getUid();
        DocumentReference ref = fStore.collection("users").document(userID);

        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot docSnap = task.getResult();
                    BMI = docSnap.getString("BMI");

                    // recommend exercise based on BMI
                    if(BMI.equals("UNDERWEIGHT")) {
                        underweightEx();
                    }
                    else if(BMI.equals("HEALTHY")) {
                        healthyEx();
                    }
                    else if(BMI.equals("OVERWEIGHT")) {
                        overweightEx();
                    }
                    else if(BMI.equals("OBESE")) {
                        obeseEx();
                    }
                } else {
                    Toast.makeText(exercises.this, "Failed to read data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Exercises for each BMI category
    private void underweightEx(){
        header.setBackgroundColor(Color.parseColor("#FAA0A0"));
        bmiHeader.setText(BMI);
        bmiHeader.setTextColor(Color.parseColor("#000000"));

        // exercises
        ex1Text.setText("2x8 PUSH-UPS");
        ex1Text.setTextColor(Color.parseColor("#000000"));
        ex2Text.setText("2x8 SQUATS");
        ex2Text.setTextColor(Color.parseColor("#000000"));
        ex3Text.setText("2x8 LUNGES");
        ex3Text.setTextColor(Color.parseColor("#000000"));
        ex4Text.setText("3 DEADLIFTS");
        ex4Text.setTextColor(Color.parseColor("#000000"));

        // go to Exercise1
        ex1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exercise1 = new Intent(getApplicationContext(), pushups.class);
                startActivity(exercise1);
            }
        });

        // go to Exercise2
        ex2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exercise2 = new Intent(getApplicationContext(), squats.class);
                startActivity(exercise2);
            }
        });

        // go to Exercise3
        ex3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exercise3 = new Intent(getApplicationContext(), lunges.class);
                startActivity(exercise3);
            }
        });

        // go to Exercise4
        ex4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exercise4 = new Intent(getApplicationContext(), deadlifts.class);
                startActivity(exercise4);
            }
        });
    }

    private void healthyEx(){
        header.setBackgroundColor(Color.parseColor("#C1E1C1"));
        bmiHeader.setText(BMI);
        bmiHeader.setTextColor(Color.parseColor("#000000"));

        // exercises
        ex1Text.setText("30SECS JUMPING JACKS");
        ex1Text.setTextColor(Color.parseColor("#000000"));
        ex2Text.setText("2x8 SIDE BENDS");
        ex2Text.setTextColor(Color.parseColor("#000000"));
        ex3Text.setText("2x8 INCLINE PUSH-UPS");
        ex3Text.setTextColor(Color.parseColor("#000000"));
        ex4Text.setText("2x8 CALF RAISES");
        ex4Text.setTextColor(Color.parseColor("#000000"));

        // go to Exercise1
        ex1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exercise1 = new Intent(getApplicationContext(), jumpingjacks.class);
                startActivity(exercise1);
            }
        });

        // go to Exercise2
        ex2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exercise2 = new Intent(getApplicationContext(), sidebends.class);
                startActivity(exercise2);
            }
        });

        // go to Exercise3
        ex3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exercise3 = new Intent(getApplicationContext(), inclinepushups.class);
                startActivity(exercise3);
            }
        });

        // go to Exercise4
        ex4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exercise4 = new Intent(getApplicationContext(), calfraises.class);
                startActivity(exercise4);
            }
        });
    }

    private void overweightEx(){
        header.setBackgroundColor(Color.parseColor("#FAC898"));
        bmiHeader.setText(BMI);
        bmiHeader.setTextColor(Color.parseColor("#000000"));

        // exercises
        ex1Text.setText("2x8 SIDE LEG LIFTS");
        ex1Text.setTextColor(Color.parseColor("#000000"));
        ex2Text.setText("2x8 HIP RAISES");
        ex2Text.setTextColor(Color.parseColor("#000000"));
        ex3Text.setText("2x8 KNEE LIFTS");
        ex3Text.setTextColor(Color.parseColor("#000000"));
        ex4Text.setText("SWIMMING");
        ex4Text.setTextColor(Color.parseColor("#000000"));

        // go to Exercise1
        ex1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exercise1 = new Intent(getApplicationContext(), sideleglifts.class);
                startActivity(exercise1);
            }
        });

        // go to Exercise2
        ex2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exercise2 = new Intent(getApplicationContext(), hipraises.class);
                startActivity(exercise2);
            }
        });

        // go to Exercise3
        ex3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exercise3 = new Intent(getApplicationContext(), kneelifts.class);
                startActivity(exercise3);
            }
        });

        // go to Exercise4
        ex4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exercise4 = new Intent(getApplicationContext(), swimming.class);
                startActivity(exercise4);
            }
        });
    }

    private void obeseEx(){
        header.setBackgroundColor(Color.parseColor("#FAA0A0"));
        bmiHeader.setText(BMI);
        bmiHeader.setTextColor(Color.parseColor("#000000"));

        // exercises
        ex1Text.setText("WALKING");
        ex1Text.setTextColor(Color.parseColor("#000000"));
        ex2Text.setText("WATER AEROBICS");
        ex2Text.setTextColor(Color.parseColor("#000000"));
        ex3Text.setText("STATIONARY BIKE");
        ex3Text.setTextColor(Color.parseColor("#000000"));
        ex4Text.setText("TREADMILL");
        ex4Text.setTextColor(Color.parseColor("#000000"));

        // go to Exercise1
        ex1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exercise1 = new Intent(getApplicationContext(), walking.class);
                startActivity(exercise1);
            }
        });

        // go to Exercise2
        ex2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exercise2 = new Intent(getApplicationContext(), wateraerobics.class);
                startActivity(exercise2);
            }
        });

        // go to Exercise3
        ex3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exercise3 = new Intent(getApplicationContext(), stationarybike.class);
                startActivity(exercise3);
            }
        });

        // go to Exercise4
        ex4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exercise4 = new Intent(getApplicationContext(), treadmill.class);
                startActivity(exercise4);
            }
        });
    }
}