package com.example.nutrition.Helpers;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutrition.Entity.Home;
import com.example.nutrition.R;
import com.example.nutrition.Service.NextActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.myViewHolder> {
    Context context;
    ArrayList<Home> list;

    public HomeAdapter(Context context, ArrayList<Home> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HomeAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myViewHolder(LayoutInflater.from(this.context).inflate(R.layout.card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.myViewHolder holder, int position) {
        Home model = list.get(position);
        holder.title.setText(model.getTitle());
        Picasso.get()
                .load(model.getFile())
                .placeholder(R.drawable.kidneys)
                .error(R.drawable.copy)
                .into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context.getApplicationContext(), NextActivity.class);
                intent.putExtra("title",model.getTitle());
                intent.putExtra("file",model.getFile());
                intent.putExtra("description",model.getDescription());
                intent.putExtra("carbo",model.getCarbo());
                intent.putExtra("protein",model.getProtein());
                intent.putExtra("calories",model.getCalories());
                intent.putExtra("fat",model.getFat());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView title,title1;
        ImageView image;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewTitle);
            image = itemView.findViewById(R.id.imageView);

        }
    }
}
