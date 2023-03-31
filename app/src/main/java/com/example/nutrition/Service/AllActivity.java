package com.example.nutrition.Service;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nutrition.Entity.Home;
import com.example.nutrition.Helpers.HomeAdapter;
import com.example.nutrition.Homepage;
import com.example.nutrition.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    HomeAdapter adapter;
    ArrayList<Home> list;
    ProgressDialog loading;
    DatabaseReference reference;
    TextView vail,ans;
    String diseases;

    @Override
    protected void onStart() {
        super.onStart();

    /*    Intent intent=getIntent();
        String message = intent.getStringExtra("dis");
        ans.setText(message);*/
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);recyclerView=findViewById(R.id.recyclerview_all);
        loading = new ProgressDialog(this);
        vail=findViewById(R.id.avail);
        ans=findViewById(R.id.answer);
        final BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.navigation);

        /*reference = FirebaseDatabase.getInstance().getReference("food").child("ulcers");*/
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new HomeAdapter(this,list);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == 0) {
                    navigationView.setVisibility(View.VISIBLE);
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || (dy < 0 && navigationView.isShown())) {
                    navigationView.setVisibility(View.GONE);
                }
            }
        });
        this.loading.show();

        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot != null) {
                    loading.dismiss();
                    diseases = snapshot.child("diseases").getValue(String.class);
                    reference= FirebaseDatabase.getInstance().getReference("food").child(diseases);
                    reference.addValueEventListener(new ValueEventListener() {
                        public void onDataChange(DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                loading.dismiss();
                                list.add((Home) dataSnapshot.getValue(Home.class));
                            }
                            adapter.notifyDataSetChanged();
                            if (AllActivity.this.list.size() == 0) {
                                Toast.makeText(AllActivity.this, "No food", Toast.LENGTH_SHORT).show();
                            }
                            int total = 0;
                            for (int i = 0; i < AllActivity.this.list.size(); i++) {
                                total++;
                            }
                            vail.setText(String.valueOf(total) + " Available Food");

                        }

                        public void onCancelled(DatabaseError error) {
                            loading.dismiss();
                            Toast.makeText(AllActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
/*
        this.reference.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    loading.dismiss();
                    list.add((Home) dataSnapshot.getValue(Home.class));
                }
                adapter.notifyDataSetChanged();
                if (AllActivity.this.list.size() == 0) {
                    Toast.makeText(AllActivity.this, "No food", Toast.LENGTH_SHORT).show();
                }
                int total = 0;
                for (int i = 0; i < AllActivity.this.list.size(); i++) {
                    total++;
                }
                vail.setText(String.valueOf(total) + " Available Food");
            }

            public void onCancelled(DatabaseError error) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
*/
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
                        Intent aw = new Intent(getApplicationContext(), Profile.class);
                        startActivity(aw);
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