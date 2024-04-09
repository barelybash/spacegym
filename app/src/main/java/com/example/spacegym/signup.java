package com.example.spacegym;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity {
    TextInputEditText emailInput, passwordInput, weightInput, heightInput;
    Button submit;
    TextView bmiView,hasAccount;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    Double BMI;
    Integer weight;
    Double height;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().hide();

        // bind the views
        emailInput = findViewById(R.id.email);
        passwordInput = findViewById(R.id.password);
        weightInput = findViewById(R.id.weight);
        heightInput = findViewById(R.id.height);
        submit = findViewById(R.id.signup);
        bmiView = findViewById(R.id.BMI);
        hasAccount = findViewById(R.id.goToLogIn);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        Intent goToLogin = new Intent(getApplicationContext(), login.class);
        Intent goToPayment = new Intent(getApplicationContext(), PaymentActivity.class);

        weightInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    weight = Integer.parseInt(weightInput.getText().toString());
                    height = Double.parseDouble(heightInput.getText().toString());
                    calcBMI();
                }
                catch(NumberFormatException e){}
            }
        });

        heightInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    weight = Integer.parseInt(weightInput.getText().toString());
                    height = Double.parseDouble(heightInput.getText().toString());
                    calcBMI();
                }
                catch(NumberFormatException e){}
            }
        });

        hasAccount.setPaintFlags((hasAccount.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG));

        // Save data to firebase on button click
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get all the values
                String email = emailInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();
                Integer weight = Integer.parseInt(weightInput.getText().toString().trim());
                Double height = Double.parseDouble(heightInput.getText().toString().trim());
                String BMI = bmiView.getText().toString();

                // Check email & password length
                if(TextUtils.isEmpty(email)){
                    emailInput.setError("Email is required!");
                }

                if(password.length() < 6){
                    passwordInput.setError("Password must be >= 6 characters");
                }

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(signup.this, "Account created successfully!", Toast.LENGTH_SHORT).show();

                            // Store other user information
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference docReference = fStore.collection("users").document(userID);
                            Map<String, Object> user = new HashMap<>();
                            user.put("email",email);
                            user.put("weight",weight);
                            user.put("height",height);
                            user.put("BMI",BMI);
                            docReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG, "onSuccess: user profile created for" + userID);
                                }
                            });
                            docReference.set(user).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onSuccess: user profile created for" + userID);
                                }
                            });

                            startActivity(goToPayment);
                        } else {
                            Toast.makeText(signup.this, "An error was encountered: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        // Go to login page
        hasAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(goToLogin);
            }
        });
    }

    public void calcBMI() {
        BMI = weight/Math.pow(height, 2);

        if(BMI < 18.5) {
            bmiView.setText("UNDERWEIGHT");
            bmiView.setBackgroundColor(Color.parseColor("#FAA0A0"));
            bmiView.setTextColor(Color.parseColor("#000000"));
        }
        else if (BMI>=18.5 && BMI<=24.9){
            bmiView.setText("HEALTHY");
            bmiView.setBackgroundColor(Color.parseColor("#C1E1C1"));
            bmiView.setTextColor(Color.parseColor("#000000"));
        }

        else if (BMI>=25.0 && BMI<=29.9) {
            bmiView.setText("OVERWEIGHT");
            bmiView.setBackgroundColor(Color.parseColor("#FAC898"));
            bmiView.setTextColor(Color.parseColor("#000000"));
        }

        else if (BMI>=30.0) {
            bmiView.setText("OBESE");
            bmiView.setBackgroundColor(Color.parseColor("#FAA0A0"));
            bmiView.setTextColor(Color.parseColor("#000000"));
        }
    }
}