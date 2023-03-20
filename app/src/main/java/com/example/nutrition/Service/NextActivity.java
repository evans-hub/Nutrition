package com.example.nutrition.Service;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nutrition.R;
import com.squareup.picasso.Picasso;

public class NextActivity extends AppCompatActivity {
ImageView img,sharing,pic;
TextView text,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        name=findViewById(R.id.foodname);
        text=findViewById(R.id.descriptions);
        img=findViewById(R.id.backto);
        sharing=findViewById(R.id.share);
        pic=findViewById(R.id.picture);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String naming = getIntent().getStringExtra("title");
        String image = getIntent().getStringExtra("file");
        String desc = getIntent().getStringExtra("description");
        desc = desc.replace(".", "\n\n");


        name.setText(naming);
        text.setText(desc);
        Picasso.get().load(image).error(R.drawable.backgroudimage).placeholder(R.drawable.backgroudimage).into(pic);
    }
}