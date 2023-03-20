package com.example.nutrition.Service;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.nutrition.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        Intent a = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(a);
                        finish();
                        break;
                    case R.id.action_profile:
                        Intent gg = new Intent(getApplicationContext(), Profile.class);
                        startActivity(gg);
                        finish();
                        break;
                    case R.id.action_about:
                        break;
                    case R.id.action_contact:
                        Intent b = new Intent(getApplicationContext(), ContactUsActivity.class);
                        startActivity(b);
                        finish();
                        break;
                }
                return false;
            }
        });

    }
}