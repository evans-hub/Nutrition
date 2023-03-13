package com.example.nutrition.Utils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nutrition.MainActivity;
import com.example.nutrition.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private Button login;
    private EditText emailaddress;
    private EditText passwords;
    private FirebaseAuth mAuth;
    private TextView forgot, signup;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ProgressDialog loading;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        login = findViewById(R.id.cirLoginButton);
        emailaddress = findViewById(R.id.email_layout);
        passwords = findViewById(R.id.editTextPassword);
        forgot = findViewById(R.id.forgotPassword);
        signup = findViewById(R.id.signUpInLogin);
        loading = new ProgressDialog(this);
        checkBox = findViewById(R.id.remember_me);
        if (mAuth.getCurrentUser() != null) {
            Intent intent=new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        }
        /*checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBox.isChecked()){
                    if (mAuth.getCurrentUser()!=null){
                        finish();
                        startActivity(new Intent(getApplication(),MainActivity.class));
                    }
                }
            }
        });*/


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPassword();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.startblue));
        }
    }

    private void forgotPassword() {
    }

    private void loginUser() {


        loading.setTitle("Signing in");
        loading.setMessage("Please wait...");
        loading.setCanceledOnTouchOutside(false);

        String email, password;
        email = emailaddress.getText().toString();
        password = passwords.getText().toString();
        if (password.isEmpty() || password.length() < 6) {
            passwords.setError("6 Characters and More Required");

        }
        if (email.isEmpty()) {
            emailaddress.setError("Invalid Email");

        } else {
            loading.show();
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        loading.dismiss();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        String message = task.getException().toString();
                        loading.dismiss();
                        Toast.makeText(
                                        getApplicationContext(),
                                        "Login failed!!"
                                                + " Please try again later" + message,
                                        Toast.LENGTH_LONG)
                                .show();
                    }
                }
            });
        }

    }
}