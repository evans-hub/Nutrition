package com.example.nutrition.Service;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Recommendation extends AppCompatActivity {
    HomeAdapter adapter;
    DatabaseReference references;
    RecyclerView categories;
    String diseases,person;
    TextView name,lay;

    @Override
    protected void onStart() {
        super.onStart();

    }

    ArrayList<Home> list;
    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
        loading=new ProgressDialog(this);
        categories=findViewById(R.id.recycler_reco);
        lay=findViewById(R.id.layouts);
        TextView tt=findViewById(R.id.view);

        categories.setHasFixedSize(true);
        categories.setLayoutManager(new GridLayoutManager(this,2));
        list = new ArrayList<>();
        adapter = new HomeAdapter(this,list);
        categories.setAdapter(adapter);

        this.loading.show();
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot != null) {
                    loading.dismiss();
                    diseases = snapshot.child("diseases").getValue(String.class);
                    person = snapshot.child("name").getValue(String.class);
                    TextView per=findViewById(R.id.text2);
                    per.setText("Hello "+person);

                    name=findViewById(R.id.dis);
                    name.setText(diseases + " patient food");
                    lay.setText(diseases);
                    references= FirebaseDatabase.getInstance().getReference("food").child(diseases);
                    references.addValueEventListener(new ValueEventListener() {
                        public void onDataChange(DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                loading.dismiss();
                                list.add((Home) dataSnapshot.getValue(Home.class));
                            }
                            adapter.notifyDataSetChanged();

                        }

                        public void onCancelled(DatabaseError error) {
                            loading.dismiss();
                            Toast.makeText(Recommendation.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
       /* String type=lay.getText().toString();
        references= FirebaseDatabase.getInstance().getReference("food").child("ulcers");
        this.references.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    loading.dismiss();
                    list.add((Home) dataSnapshot.getValue(Home.class));
                }
                adapter.notifyDataSetChanged();

            }

            public void onCancelled(DatabaseError error) {
                loading.dismiss();
                Toast.makeText(Recommendation.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });*/

        tt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),AllActivity.class);
                startActivity(intent);
            }
        });
        final BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        categories.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.startblue));
        }
    }
}