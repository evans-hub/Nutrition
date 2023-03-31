package com.example.nutrition;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nutrition.Service.LoginActivity;
import com.example.nutrition.Service.MainActivity;
import com.example.nutrition.Service.Recommendation;
import com.example.nutrition.Service.SignUpActivity;
import com.google.firebase.auth.FirebaseAuth;

public class Homepage extends AppCompatActivity {
TextView skipping,signup;
Button log;
FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        log=findViewById(R.id.logging);
        skipping=findViewById(R.id.skip);
        signup=findViewById(R.id.signingup);
        mAuth=FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            Intent intent=new Intent(getApplicationContext(), Recommendation.class);
            startActivity(intent);
            finish();

        }
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
        skipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}