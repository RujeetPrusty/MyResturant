package com.example.rujeet.myresturant.Viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rujeet.myresturant.R;

public class CuisineViewHolder extends RecyclerView.ViewHolder {

    public ImageView cuisineImage;
    public TextView cuisineName;



    public CuisineViewHolder(@NonNull View itemView) {
        super(itemView);
        cuisineImage = itemView.findViewById(R.id.cuisineimage);
        cuisineName = itemView.findViewById(R.id.cuisinename);


    }

}
