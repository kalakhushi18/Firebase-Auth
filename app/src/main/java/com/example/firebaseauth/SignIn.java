package com.example.firebaseauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignIn extends AppCompatActivity {

    EditText useremail, userpassword;
    Button login, signup;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        useremail = findViewById(R.id.emailsign);
        userpassword = findViewById(R.id.passwordsign);
        signup = findViewById(R.id.signup);
        login = findViewById(R.id.login);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressbar);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignIn.this, MainActivity.class));
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

    }

    public void register() {

        String email = useremail.getText().toString().trim();
        String pass = userpassword.getText().toString().trim();

        if (email.isEmpty()) {

            useremail.setError("Email is required ");
            useremail.requestFocus();
            return;
        }

        if (pass.isEmpty()) {

            userpassword.setError("Password is required");
            userpassword.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            useremail.setError("Email is not valid  ");
            useremail.requestFocus();
            return;

        }
        if (pass.length() < 6) {
            userpassword.setError("Minimum length shld be 6");
            userpassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(SignIn.this, "user registered successfully ", Toast.LENGTH_SHORT).show();
                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException)

                        Toast.makeText(SignIn.this, "You are already registered", Toast.LENGTH_SHORT).show();
                    else {

                        Toast.makeText(SignIn.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        {


        }
    }
}