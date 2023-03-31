package com.example.nutrition.Service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.ColorSpace;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nutrition.Entity.Home;
import com.example.nutrition.Helpers.HomeAdapter;
import com.example.nutrition.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class Search extends AppCompatActivity {
    HomeAdapter adapter;
    ArrayList<Home> list;
    RecyclerView listView;
    DatabaseReference reference;
    EditText search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        this.search = (EditText) findViewById(R.id.sea);
        this.listView = (RecyclerView) findViewById(R.id.search_lv);
        this.reference = FirebaseDatabase.getInstance().getReference("food");
        this.listView.setHasFixedSize(true);
        this.listView.setLayoutManager(new LinearLayoutManager(this));
        this.list = new ArrayList<>();
        HomeAdapter search_adapter = new HomeAdapter(this, this.list);
        this.adapter = search_adapter;
        this.listView.setAdapter(search_adapter);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.clearFlags(67108864);
            window.setStatusBarColor(getResources().getColor(R.color.startblue));
        }
        this.search.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(final CharSequence s, int start, int before, int count) {
                Search.this.reference.addValueEventListener(new ValueEventListener() {
                    public void onDataChange(DataSnapshot snapshot) {
                        Search.this.list.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Home model_data = (Home) dataSnapshot.getValue(Home.class);
                            if (String.valueOf(model_data.getTitle()).toLowerCase().contains(s) || String.valueOf(model_data.getDescription()).toLowerCase().contains(s)) {
                                Search.this.list.add(model_data);
                            }
                        }
                        Collections.reverse(Search.this.list);
                        Search.this.adapter.notifyDataSetChanged();
                    }

                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(Search.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            public void afterTextChanged(Editable s) {
            }
        });
    }
}