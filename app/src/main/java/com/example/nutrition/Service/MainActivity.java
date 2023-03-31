package com.example.nutrition.Service;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.nutrition.Entity.Home;
import com.example.nutrition.Helpers.HomeAdapter;
import com.example.nutrition.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    HomeAdapter hyp,dia,ul,cov,canc;
    Query references;
    RecyclerView hyper,diab,ulcer,covi,cance;
    ArrayList<Home> listh, listd,listu,listco,listca;
    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hyper =findViewById(R.id.recycler);
        diab=findViewById(R.id.diabetes);
        ulcer=findViewById(R.id.ulcers);
        covi=findViewById(R.id.covid);
        cance=findViewById(R.id.cancer);
        loading=new ProgressDialog(this);
        final BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.navigation);



        hyper.setHasFixedSize(true);
        hyper.setLayoutManager(new GridLayoutManager(this,2));
        listh = new ArrayList<>();
        hyp = new HomeAdapter(this,listh);
        hyper.setAdapter(hyp);

        diab.setHasFixedSize(true);
        diab.setLayoutManager(new GridLayoutManager(this,2));
        listd = new ArrayList<>();
        dia = new HomeAdapter(this, listd);
        diab.setAdapter(dia);

        ulcer.setHasFixedSize(true);
        ulcer.setLayoutManager(new GridLayoutManager(this,2));
        listu = new ArrayList<>();
        ul = new HomeAdapter(this, listu);
        ulcer.setAdapter(ul);

        covi.setHasFixedSize(true);
        covi.setLayoutManager(new GridLayoutManager(this,2));
        listco = new ArrayList<>();
        cov = new HomeAdapter(this, listco);
        covi.setAdapter(cov);

        cance.setHasFixedSize(true);
        cance.setLayoutManager(new GridLayoutManager(this,2));
        listca = new ArrayList<>();
        canc = new HomeAdapter(this, listca);
        cance.setAdapter(canc);

        cance.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        ulcer.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        references= FirebaseDatabase.getInstance().getReference("food").child("diabetes").limitToFirst(2);
        this.references.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    loading.dismiss();
                    listd.add((Home) dataSnapshot.getValue(Home.class));
                }
                dia.notifyDataSetChanged();

            }

            public void onCancelled(DatabaseError error) {
                loading.dismiss();
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
        references= FirebaseDatabase.getInstance().getReference("food").child("hypertension").limitToFirst(2);
        this.references.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    loading.dismiss();
                    listh.add((Home) dataSnapshot.getValue(Home.class));
                }
                hyp.notifyDataSetChanged();

            }

            public void onCancelled(DatabaseError error) {
                loading.dismiss();
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
        references= FirebaseDatabase.getInstance().getReference("food").child("cancer").limitToFirst(2);
        this.references.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    loading.dismiss();
                    listca.add((Home) dataSnapshot.getValue(Home.class));
                }
                canc.notifyDataSetChanged();

            }

            public void onCancelled(DatabaseError error) {
                loading.dismiss();
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
        references= FirebaseDatabase.getInstance().getReference("food").child("ulcers").limitToFirst(2);
        this.references.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    loading.dismiss();
                    listu.add((Home) dataSnapshot.getValue(Home.class));
                }
                ul.notifyDataSetChanged();

            }

            public void onCancelled(DatabaseError error) {
                loading.dismiss();
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
        references= FirebaseDatabase.getInstance().getReference("food").child("covid").limitToFirst(2);
        this.references.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    loading.dismiss();
                    listco.add((Home) dataSnapshot.getValue(Home.class));
                }
                cov.notifyDataSetChanged();

            }

            public void onCancelled(DatabaseError error) {
                loading.dismiss();
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
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