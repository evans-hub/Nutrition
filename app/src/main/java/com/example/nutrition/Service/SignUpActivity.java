package com.example.nutrition.Service;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nutrition.Entity.User;
import com.example.nutrition.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    private Button regist;
    private EditText email_address;
    private EditText phones;
    private EditText names;
    private EditText idnumber;
    private EditText passwords;
    private EditText heig;
    private EditText ag;
    private EditText dis;
    private EditText wei;
    private EditText gen;
    private FirebaseAuth mAuth;
    ProgressDialog loading;
    private TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        regist = findViewById(R.id.register);
        login = findViewById(R.id.signinInRegister);
        loading = new ProgressDialog(this);
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        phones = findViewById(R.id.layout_mobile_Number);
        names = findViewById(R.id.layout_name);
        passwords = findViewById(R.id.layout_password);
        email_address = findViewById(R.id.layout_email);
        idnumber=findViewById(R.id.layout_id_number);
        gen=findViewById(R.id.layout_gender);
        ag=findViewById(R.id.layout_age);
        heig=findViewById(R.id.layout_height);
        dis=findViewById(R.id.layout_diseases);
        wei=findViewById(R.id.layout_weight);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.startblue));
        }
    }

    private void registerUser() {

        loading.setTitle("Signing Up");
        loading.setMessage("Please wait...");
        loading.setCanceledOnTouchOutside(false);
        String email, password, phone, name, number,height,age,weight,diseases,gender;
        email = email_address.getText().toString().trim();
        password = passwords.getText().toString().trim();
        phone = phones.getText().toString().trim();
        name = names.getText().toString().trim();
        number=idnumber.getText().toString().trim();
        height=heig.getText().toString().trim();
        age=ag.getText().toString().trim();
        weight=wei.getText().toString().trim();
        diseases=dis.getText().toString().trim();
        gender=gen.getText().toString().trim();
        if (name.isEmpty()) {
            names.setError("invalid name");
        }
        if (height.isEmpty()) {
            heig.setError("invalid height");
        }
        if (weight.isEmpty()) {
            wei.setError("invalid weight");
        }
        if (age.isEmpty()) {
            ag.setError("invalid age");
        }
        if (gender.isEmpty()) {
            gen.setError("invalid gender");
        }
        if (number.isEmpty()) {
            idnumber.setError("invalid Id number");
        }
        if (password.isEmpty() || password.length() < 6) {
            passwords.setError("6 Characters and More Required");

        }
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_address.setError("wrong email address");


        }
        if (phone.length() != 10 || phone.isEmpty()) {
            phones.setError("invalid phone number");

        }else {
            loading.show();
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        String uuid = FirebaseAuth.getInstance().getCurrentUser()
                                .getUid();
                        User user = new User(number, name, phone,email, uuid,height,weight,gender,diseases,age);

                        FirebaseDatabase.getInstance().getReference("users").child(uuid)
                                .setValue(user)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Intent intent
                                                = new Intent(getApplicationContext(),
                                                MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });

                    } else {
                        String message = task.getException().toString();
                        Toast.makeText(
                                        getApplicationContext(),
                                        "Registration failed!!"
                                                + " Please try again later" + message,
                                        Toast.LENGTH_LONG)
                                .show();

                    }

                }
            });
        }


    }


}