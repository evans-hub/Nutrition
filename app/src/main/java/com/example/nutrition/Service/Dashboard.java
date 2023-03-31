package com.example.nutrition.Service;

import java.util.*;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.nutrition.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.*;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.Arrays;
import java.util.*;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class Dashboard extends Activity {
    CheckBox fevers,coughs,blurrys,headaches,pains,fatigues,sores,weights;
    FirebaseAuth mAuth;
    DatabaseReference reference;
    Button btn,btn1;
    // Define a matrix of symptom probabilities for each disease
    private static final double[][] SYMPTOM_PROBABILITIES = {
            {0.9, 0.8, 0.7, 0.6,0.9, 0.8, 0.9, 0.1}, // ulcers
            {0.7, 0.6, 0.9, 0.4,0.8, 0.8, 0.7, 0.6}, // covid
            {0.6, 0.9, 0.4, 0.3,0.7, 0.5, 0.9, 0.4},  // diabetes
            {0.5, 0.8, 0.4, 0.9,0.9, 0.6, 0.6, 0.7}, // hypertension
            {0.7, 0.8, 0.5, 0.7,0.5, 0.8, 0.3, 0.7}, // cancer
    };

    // Define a list of diseases
    private static final String[] DISEASES = {
            "ulcers",
            "covid",
            "diabetes",
            "hypertension",
            "cancer"
    };

    private EditText inputSymptomsEditText;
    private TextView matchingDiseasesTextView;
    private Handler mHandler = new Handler();
    private Handler mHideHandler = new Handler();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        fevers=findViewById(R.id.fever);
        blurrys=findViewById(R.id.Blurry);
        headaches=findViewById(R.id.headache);
        pains=findViewById(R.id.abdominal);
        fatigues=findViewById(R.id.fatigue);
        sores=findViewById(R.id.sore);
        weights=findViewById(R.id.Weight);
        coughs=findViewById(R.id.cough);
        btn=findViewById(R.id.proceed);
        btn1=findViewById(R.id.cancel);
        inputSymptomsEditText = (EditText) findViewById(R.id.input_symptoms_edittext);
        matchingDiseasesTextView = (TextView) findViewById(R.id.matching_diseases_textview);
        reference=FirebaseDatabase.getInstance().getReference("users");
        mAuth=FirebaseAuth.getInstance();

        fevers.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String text = inputSymptomsEditText.getText().toString();
                String checkboxText = "fever";
                if (isChecked) {
                    if (!text.isEmpty()) {
                        text += ",";
                    }
                    text += checkboxText;
                } else {
                    String[] items = text.split(",");
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < items.length; i++) {
                        if (!items[i].equals(checkboxText)) {
                            if (i > 0) {
                                builder.append(",");
                            }
                            builder.append(items[i]);
                        }
                    }
                    text = builder.toString();
                }
                inputSymptomsEditText.setText(text);
            }
        });
        coughs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String text = inputSymptomsEditText.getText().toString();
                String checkboxText = "cough";
                if (isChecked) {
                    if (!text.isEmpty()) {
                        text += ",";
                    }
                    text += checkboxText;
                } else {
                    String[] items = text.split(",");
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < items.length; i++) {
                        if (!items[i].equals(checkboxText)) {
                            if (i > 0) {
                                builder.append(",");
                            }
                            builder.append(items[i]);
                        }
                    }
                    text = builder.toString();
                }
                inputSymptomsEditText.setText(text);
            }
        });
        headaches.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String text = inputSymptomsEditText.getText().toString();
                String checkboxText = "headache";
                if (isChecked) {
                    if (!text.isEmpty()) {
                        text += ",";
                    }
                    text += checkboxText;
                } else {
                    String[] items = text.split(",");
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < items.length; i++) {
                        if (!items[i].equals(checkboxText)) {
                            if (i > 0) {
                                builder.append(",");
                            }
                            builder.append(items[i]);
                        }
                    }
                    text = builder.toString();
                }
                inputSymptomsEditText.setText(text);
            }
        });
        weights.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String text = inputSymptomsEditText.getText().toString();
                String checkboxText = "weight loss";
                if (isChecked) {
                    if (!text.isEmpty()) {
                        text += ",";
                    }
                    text += checkboxText;
                } else {
                    String[] items = text.split(",");
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < items.length; i++) {
                        if (!items[i].equals(checkboxText)) {
                            if (i > 0) {
                                builder.append(",");
                            }
                            builder.append(items[i]);
                        }
                    }
                    text = builder.toString();
                }
                inputSymptomsEditText.setText(text);
            }
        });
        pains.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String text = inputSymptomsEditText.getText().toString();
                String checkboxText = "abdominal pains";
                if (isChecked) {
                    if (!text.isEmpty()) {
                        text += ",";
                    }
                    text += checkboxText;
                } else {
                    String[] items = text.split(",");
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < items.length; i++) {
                        if (!items[i].equals(checkboxText)) {
                            if (i > 0) {
                                builder.append(",");
                            }
                            builder.append(items[i]);
                        }
                    }
                    text = builder.toString();
                }
                inputSymptomsEditText.setText(text);
            }
        });
        fatigues.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String text = inputSymptomsEditText.getText().toString();
                String checkboxText = "fatigue";
                if (isChecked) {
                    if (!text.isEmpty()) {
                        text += ",";
                    }
                    text += checkboxText;
                } else {
                    String[] items = text.split(",");
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < items.length; i++) {
                        if (!items[i].equals(checkboxText)) {
                            if (i > 0) {
                                builder.append(",");
                            }
                            builder.append(items[i]);
                        }
                    }
                    text = builder.toString();
                }
                inputSymptomsEditText.setText(text);
            }
        });
        sores.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String text = inputSymptomsEditText.getText().toString();
                String checkboxText = "sore throat";
                if (isChecked) {
                    if (!text.isEmpty()) {
                        text += ",";
                    }
                    text += checkboxText;
                } else {
                    String[] items = text.split(",");
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < items.length; i++) {
                        if (!items[i].equals(checkboxText)) {
                            if (i > 0) {
                                builder.append(",");
                            }
                            builder.append(items[i]);
                        }
                    }
                    text = builder.toString();
                }
                inputSymptomsEditText.setText(text);
            }
        });
        blurrys.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String text = inputSymptomsEditText.getText().toString();
                String checkboxText = "blurry vision";
                if (isChecked) {
                    if (!text.isEmpty()) {
                        text += ",";
                    }
                    text += checkboxText;
                } else {
                    String[] items = text.split(",");
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < items.length; i++) {
                        if (!items[i].equals(checkboxText)) {
                            if (i > 0) {
                                builder.append(",");
                            }
                            builder.append(items[i]);
                        }
                    }
                    text = builder.toString();
                }
                inputSymptomsEditText.setText(text);
            }
        });
        Button matchButton = (Button) findViewById(R.id.submit_button);
        matchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressBar progressBar = findViewById(R.id.progress_bar);
                progressBar.setVisibility(View.VISIBLE);

                String inputSymptoms = inputSymptomsEditText.getText().toString();
if (inputSymptoms.isEmpty()){
    Toast.makeText(Dashboard.this, "You must have at least one symptom", Toast.LENGTH_SHORT).show();
    progressBar.setVisibility(View.GONE);
}
            else{
    // Split the input symptoms by spaces
    String[] inputSymptomsArray = inputSymptoms.split(",");

    // Create a map to store the probability of each disease
    Map<String, Double> diseaseProbabilities = new HashMap<>();

    // Calculate the probability of each disease given the user's symptoms
    for (int i = 0; i < DISEASES.length; i++) {
        double diseaseProbability = 1.0;
        for (String symptom : inputSymptomsArray) {
            int symptomIndex = Arrays.asList(new String[]{"fever","cough","sore throat","weight loss","blurry vision","abdominal pains","headache","fatigue"}).indexOf(symptom);
            diseaseProbability *= SYMPTOM_PROBABILITIES[i][symptomIndex];
        }
        diseaseProbabilities.put(DISEASES[i], diseaseProbability);
    }

    // Sort the diseases by probability in descending order
    List<Map.Entry<String, Double>> sortedDiseases = new ArrayList<>(diseaseProbabilities.entrySet());
    Collections.sort(sortedDiseases, new Comparator<Map.Entry<String, Double>>() {
        public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
            return o2.getValue().compareTo(o1.getValue());
        }
    });

    // Create a string with the matching diseases and their probabilities
    StringBuilder matchingDiseasesStringBuilder = new StringBuilder();
    matchingDiseasesStringBuilder.append("Matching diseases:\n");
    for (Map.Entry<String, Double> disease : sortedDiseases) {
        matchingDiseasesStringBuilder.append(disease.getKey() + ": " + disease.getValue() + "\n");
    }
    // Get the disease with the highest probability
    Map.Entry<String, Double> topDisease = sortedDiseases.get(0);

// Create a string with the matching disease and its probability
    //String matchingDiseaseString = "Matching disease:\n" + topDisease.getKey() + ": " + topDisease.getValue();

// Display the matching disease and its probability in the UI

    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            matchingDiseasesTextView.setText("Analysing");
        }
    };

    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            ProgressBar progressBar = findViewById(R.id.progress_bar);
            progressBar.setVisibility(View.GONE);
            TextView des=findViewById(R.id.desc);
            des.setVisibility(View.VISIBLE);
            matchingDiseasesTextView.setText(topDisease.getKey());
            matchButton.setVisibility(View.GONE);
            btn.setVisibility(View.VISIBLE);
            btn1.setVisibility(View.VISIBLE);

        }
    };
    progressBar = findViewById(R.id.progress_bar);

// Start the Handler to display the text after a delay
    mHandler.postDelayed(mRunnable, 10); // Delay for 1 second
    progressBar.setVisibility(View.VISIBLE);
// Start the Handler to hide the ProgressBar after a delay
    mHideHandler.postDelayed(mHideRunnable, 5000); // Delay for 10 seconds


    //matchingDiseasesTextView.setText(topDisease.getKey());
    btn1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String uuid=mAuth.getCurrentUser().getUid().toString();
            reference.child(uuid).child("diseases").setValue(topDisease.getKey()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Intent intent=new Intent(getApplicationContext(),Recommendation.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    });
    btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            matchButton.setVisibility(View.VISIBLE);
            btn.setVisibility(View.GONE);
            btn1.setVisibility(View.GONE);
        }
    });




    // Display the matching diseases and their probabilities in the UI
    // matchingDiseasesTextView.setText(matchingDiseasesStringBuilder.toString());

}
            }
        });
    }
}
