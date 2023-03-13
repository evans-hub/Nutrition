package com.example.nutrition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.nutrition.Utils.AboutUsActivity;
import com.example.nutrition.Utils.ContactUsActivity;
import com.example.nutrition.Utils.Profile;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        break;
                    case R.id.action_profile:
                        Intent a = new Intent(getApplicationContext(), Profile.class);
                        startActivity(a);
                        finish();
                        break;
                    case R.id.action_about:
                        Intent b = new Intent(getApplicationContext(), AboutUsActivity.class);
                        startActivity(b);
                        finish();
                        break;
                    case R.id.action_contact:
                        Intent bc= new Intent(getApplicationContext(), ContactUsActivity.class);
                        startActivity(bc);
                        finish();
                        break;
                }
                return false;
            }
        });
    }
}