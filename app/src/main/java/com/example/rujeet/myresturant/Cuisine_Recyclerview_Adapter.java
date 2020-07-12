package com.example.rujeet.myresturant;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Cuisine_Recyclerview_Adapter extends RecyclerView.Adapter<Cuisine_Recyclerview_Adapter.MyViewHolder> {

    Context context;
    List<Starter_Model> CuisineList;

    public Cuisine_Recyclerview_Adapter(Context mcontext, List<Starter_Model> cuisineList) {
        this.context = mcontext;
        this.CuisineList = cuisineList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.cuisine_layout,viewGroup,false);
        MyViewHolder myViewHolder = new MyViewHolder(view,context);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Starter_Model starter_model = CuisineList.get(i);
        ImageView background = myViewHolder.background_img;
        background.setImageResource(starter_model.getImage());

        TextView cuisinename = myViewHolder.cuisinename;
        cuisinename.setText(starter_model.getText());

    }

    @Override
    public int getItemCount() {
        return CuisineList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView background_img;
        TextView cuisinename;
        Context context;

        public MyViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            itemView.setOnClickListener(this);

//            background_img = itemView.findViewById(R.id.card_background);
//            cuisinename = itemView.findViewById(R.id.cuisine_name);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(this.context,Chinese_Cuisine.class);
            this.context.startActivity(intent);
        }
    }
}
