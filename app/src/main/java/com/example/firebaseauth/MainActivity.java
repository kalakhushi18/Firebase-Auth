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

public class MainActivity extends AppCompatActivity {
    Button sign,login;
    FirebaseAuth mauth ;
    EditText email, password ;
ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sign = findViewById(R.id.signin);
        login=findViewById(R.id.loginintial);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        mauth = FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressbar1);
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SignIn.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userlogin();
            }
        });
    }

    public void userlogin(){
        String mail = email.getText().toString().trim();
        String pass = password.getText().toString().trim();


        if (mail.isEmpty()) {

            email.setError("Email is required ");
            email.requestFocus();
            return ;
        }

        if (pass.isEmpty()) {

            password.setError("Password is required");
            password.requestFocus();
            return ;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            email.setError("Email is not valid  ");
            email.requestFocus();
            return ;

        }
        if(pass.length()<6){
            password.setError("Minimum length shld be 6");
            password.requestFocus();
            return ;
        }
        progressBar.setVisibility(View.VISIBLE);

        mauth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(getApplicationContext(),Profile.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
startActivity(intent);
finish();

                }else{

                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}