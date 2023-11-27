package com.example.nutrition.Service;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nutrition.Entity.Home;
import com.example.nutrition.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class Post extends AppCompatActivity {

    private EditText mTitleInput;
    private EditText mFileInput;
    private EditText mDescriptionInput;
    private EditText mCaloriesInput;
    private EditText mFatInput;
    private EditText mProteinInput;
    private EditText mCarboInput;
    private Button mSaveButton;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mTitleInput = findViewById(R.id.title_input);
        mFileInput = findViewById(R.id.file_input);
        mDescriptionInput = findViewById(R.id.description_input);
        mCaloriesInput = findViewById(R.id.calories_input);
        mFatInput = findViewById(R.id.fat_input);
        mProteinInput = findViewById(R.id.protein_input);
        mCarboInput = findViewById(R.id.carbo_input);
        mSaveButton = findViewById(R.id.save_button);

        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mDatabase.getReference("food").child("covid");

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }

    private void saveData() {
        String title = mTitleInput.getText().toString().trim();
        String file = mFileInput.getText().toString().trim();
        String description = mDescriptionInput.getText().toString().trim();
        String calories = mCaloriesInput.getText().toString().trim();
        String fat = mFatInput.getText().toString().trim();
        String protein = mProteinInput.getText().toString().trim();
        String carbo = mCarboInput.getText().toString().trim();

        if (TextUtils.isEmpty(title)) {
            mTitleInput.setError("Title is required");
            return;
        }

        if (TextUtils.isEmpty(file)) {
            mFileInput.setError("File is required");
            return;
        }

        if (TextUtils.isEmpty(description)) {
            mDescriptionInput.setError("Description is required");
            return;
        }

        if (TextUtils.isEmpty(calories)) {
            mCaloriesInput.setError("Calories are required");
            return;
        }

        if (TextUtils.isEmpty(fat)) {
            mFatInput.setError("Fat is required");
            return;
        }

        if (TextUtils.isEmpty(protein)) {
            mProteinInput.setError("Protein is required");
            return;
        }

        if (TextUtils.isEmpty(carbo)) {
            mCarboInput.setError("Carbohydrates are required");
            return;
        }

        String key = mDatabaseRef.push().getKey();
        Home home = new Home(title, file, description, calories, fat, protein, carbo);
        mDatabaseRef.child(key).setValue(home);

        Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show();
        mCarboInput.setText("");
        mCaloriesInput.setText("");
        mDescriptionInput.setText("");
        mFatInput.setText("");
        mFileInput.setText("");
        mProteinInput.setText("");
        mTitleInput.setText("");
    }
}
