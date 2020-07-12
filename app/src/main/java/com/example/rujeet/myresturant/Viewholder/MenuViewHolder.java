package com.example.rujeet.myresturant.Viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rujeet.myresturant.R;

public class MenuViewHolder extends RecyclerView.ViewHolder {

    public ImageView menuImage;
    public TextView menuName;
    public TextView menuPrice;

    public MenuViewHolder(@NonNull View itemView) {
        super(itemView);
        menuImage = itemView.findViewById(R.id.menu_image);
        menuName = itemView.findViewById(R.id.menu_name);
        menuPrice = itemView.findViewById(R.id.menu_detais_price);
    }
}
