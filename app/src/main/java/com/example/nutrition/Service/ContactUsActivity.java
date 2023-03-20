package com.example.nutrition.Service;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nutrition.Entity.User;
import com.example.nutrition.Mail.JavaMailAPI;
import com.example.nutrition.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactUsActivity extends AppCompatActivity {
    private EditText your_name, your_email, your_subject, your_message;
    private ProgressDialog loading;
    FirebaseAuth mAuth;
    DatabaseReference reference;
    AlertDialog.Builder build;
    ImageView user_whatsapp,user_facebook,user_linkedin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        your_name = findViewById(R.id.your_name);
        your_email = findViewById(R.id.your_email);
        your_subject = findViewById(R.id.your_subject);
        your_message = findViewById(R.id.your_message);
        user_facebook=findViewById(R.id.facebook);
        user_linkedin=findViewById(R.id.linkedin);
        user_whatsapp=findViewById(R.id.whatsapp);
        Button email = (Button) findViewById(R.id.post_message);
        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("mails");
        loading = new ProgressDialog(this);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();

            }
        });
        this.build = new AlertDialog.Builder(this);
        this.build.setMessage("Email").setTitle("Sending Email");
        this.build.setMessage("Email Sent Successfully").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id2) {
                dialog.dismiss();
                ContactUsActivity.this.startActivity(new Intent(getApplicationContext(), ContactUsActivity.class));
            }
        });
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
                        Intent b = new Intent(getApplicationContext(), AboutUsActivity.class);
                        startActivity(b);
                        finish();
                        break;
                    case R.id.action_contact:
                        break;
                }
                return false;
            }
        });
   user_whatsapp.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
        String sNumber="+254789427421";
        Uri uri= Uri.parse(String.format("https://api.whatsapp.com/send?phone=%s",sNumber));
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
       }
   });
   user_facebook.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           String sApplink="fb://page/237564710351658";
           String sPackage="com.facebook.katana";
           String sWeblink="https://web.facebook.com/groups/1729270327378979/";
           openLink(sApplink,sPackage,sWeblink);
       }
   });
user_linkedin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Uri uri = Uri.parse("https://www.linkedin.com/company/software-developer-group/");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
});
    }

    private void openLink(String sApplink, String sPackage, String sWeblink) {
    try {
        Uri uri=Uri.parse(sApplink);
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        intent.setPackage(sPackage);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }catch (ActivityNotFoundException activityNotFoundException){
        Uri uri=Uri.parse(sWeblink);
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    }

    private void sendEmail() {
        loading.setTitle("Email");
        loading.setMessage("Sending...");
        loading.show();
        String mEmail = your_email.getText().toString().trim();
        String mSubject = your_subject.getText().toString().trim();
        String mMessage = your_message.getText().toString().trim();
        String mName = your_name.getText().toString().trim();
        if (mEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
            your_email.setError("Invalid Email");
        }
        if (mSubject.isEmpty()) {
            your_subject.setError("Subject Cannot be empty");
        }
        if (mMessage.isEmpty()) {
            your_message.setError("Message cannot be Empty");
        }
        JavaMailAPI javaMailAPI = new JavaMailAPI(this, mEmail, mSubject, mMessage);
        javaMailAPI.execute();
        String uuid = FirebaseAuth.getInstance().getCurrentUser()
                .getUid();
        User user = new User(mEmail, mSubject, mMessage, uuid, mName);
        reference.child(mAuth.getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    your_email.setText("");
                    your_message.setText("");
                    your_subject.setText("");
                    your_name.setText("");
                    loading.dismiss();
                    AlertDialog alert = ContactUsActivity.this.build.create();
                    alert.setTitle("Sending Email");
                    alert.show();

                } else {
                    loading.dismiss();
                }
            }
        });
    }


}