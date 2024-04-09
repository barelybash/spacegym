package com.example.spacegym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    // declare views
    TextInputEditText emailInput, passwordInput;
    Button login;
    TextView createAccount;

    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        // bind views
        emailInput = findViewById(R.id.email);
        passwordInput = findViewById(R.id.password);
        login = findViewById(R.id.login);
        createAccount = findViewById(R.id.goToSignUp);

        fAuth = FirebaseAuth.getInstance();

        createAccount.setPaintFlags((createAccount.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG));

        // Go to signup page
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToSignUp = new Intent(getApplicationContext(), signup.class);
                startActivity(goToSignUp);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                // Check email & password length
                if(TextUtils.isEmpty(email)){
                    emailInput.setError("Email is required!");
                }

                if(password.length() < 6){
                    passwordInput.setError("Password must be >= 6 characters");
                }

                // authenticate user
                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(login.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                            Intent goToExercises = new Intent(getApplicationContext(), exercises.class);
                            startActivity(goToExercises);
                        } else {
                            Toast.makeText(login.this, "An error was encountered: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}