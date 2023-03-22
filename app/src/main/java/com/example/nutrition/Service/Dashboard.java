package com.example.nutrition.Service;

import java.util.*;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nutrition.R;

public class Dashboard extends Activity {

    // Define a matrix of symptom probabilities for each disease
    private static final double[][] SYMPTOM_PROBABILITIES = {
            {0.9, 0.8, 0.7, 0.6}, // Disease 1
            {0.7, 0.6, 0.5, 0.4}, // Disease 2
            {0.6, 0.5, 0.4, 0.3}  // Disease 3
    };

    // Define a list of diseases
    private static final String[] DISEASES = {
            "Disease 1",
            "Disease 2",
            "Disease 3"
    };

    private EditText inputSymptomsEditText;
    private TextView matchingDiseasesTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        inputSymptomsEditText = (EditText) findViewById(R.id.input_symptoms_edittext);
        matchingDiseasesTextView = (TextView) findViewById(R.id.matching_diseases_textview);

        Button matchButton = (Button) findViewById(R.id.submit_button);
        matchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputSymptoms = inputSymptomsEditText.getText().toString();

                // Split the input symptoms by spaces
                String[] inputSymptomsArray = inputSymptoms.split(" ");

                // Create a map to store the probability of each disease
                Map<String, Double> diseaseProbabilities = new HashMap<>();

                // Calculate the probability of each disease given the user's symptoms
                for (int i = 0; i < DISEASES.length; i++) {
                    double diseaseProbability = 1.0;
                    for (String symptom : inputSymptomsArray) {
                        int symptomIndex = Arrays.asList(new String[]{"Symptom 1", "Symptom 2", "Symptom 3", "Symptom 4"}).indexOf(symptom);
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

                // Display the matching diseases and their probabilities in the UI
                matchingDiseasesTextView.setText(matchingDiseasesStringBuilder.toString());
            }
        });
    }
}
