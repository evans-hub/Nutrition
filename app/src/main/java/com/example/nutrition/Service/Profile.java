package com.example.nutrition.Service;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nutrition.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;

public class Profile extends AppCompatActivity {
    TextView  email, name,editing,phone,age,height,weight,gender,status,disease,bm;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Button logout;
    ProgressDialog loading;
    FirebaseAuth mAuth;

    private final int PICK_IMAGE_REQUEST = 71;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth=FirebaseAuth.getInstance();
        email = findViewById(R.id.profile_emailaddress);
        name = findViewById(R.id.profile_name);
        editing = findViewById(R.id.edit_profile);
        phone=findViewById(R.id.profile_phone);
        age=findViewById(R.id.layout_age);
        height=findViewById(R.id.layout_height);
        weight=findViewById(R.id.layout_weight);
        gender=findViewById(R.id.layout_gender);
        status=findViewById(R.id.status);
        bm=findViewById(R.id.bmi);
        disease=findViewById(R.id.profile_disease);
        loading = new ProgressDialog(this);
        loading.getWindow().setGravity(Gravity.CENTER_HORIZONTAL);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");
        if (mAuth.getCurrentUser() == null) {
            Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();

        }
        editing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        Intent a = new Intent(getApplicationContext(), Recommendation.class);
                        startActivity(a);
                        finish();
                        break;
                    case R.id.action_profile:
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
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.startblue));
        }

    }




    @Override
    protected void onStart() {
        super.onStart();
        loading.show();
        ss();
    }


    private void ss() {



        loading.setTitle("Updating ");
        loading.setMessage("Please wait...");
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        databaseReference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot != null) {
                    loading.dismiss();
                    String email_address = snapshot.child("email").getValue(String.class);
                    String naming = snapshot.child("name").getValue(String.class);
                    String phone_number = snapshot.child("phone").getValue(String.class);
                    String id = snapshot.child("number").getValue(String.class);
                    String ages = snapshot.child("age").getValue(String.class);
                    String genders = snapshot.child("gender").getValue(String.class);
                    String heights = snapshot.child("height").getValue(String.class);
                    String weights = snapshot.child("weight").getValue(String.class);
                    String diseasess = snapshot.child("diseases").getValue(String.class);


                    email.setText(email_address);
                    name.setText(naming);
                    phone.setText(phone_number);
                    height.setText(heights);
                    gender.setText(genders);
                    weight.setText(weights);
                    disease.setText(diseasess);
                    age.setText(ages);

                    Double bmi_formula=(Double.parseDouble(weights)/(Double.parseDouble(heights)*Double.parseDouble(heights)))*703;

                    DecimalFormat df = new DecimalFormat("#.##");
                    String formattedNumber = df.format(bmi_formula);
                    bm.setText(formattedNumber);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}