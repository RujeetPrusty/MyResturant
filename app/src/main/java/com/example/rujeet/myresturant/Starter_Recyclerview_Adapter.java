//package com.example.rujeet.myresturant;
//
//import android.content.Context;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import java.util.List;
//
//public class Starter_Recyclerview_Adapter extends RecyclerView.Adapter<Starter_Recyclerview_Adapter.MyViewHolder> {
//
//    Context context;
//    List<Starter_Model> list;
//
//    public Starter_Recyclerview_Adapter(Context context, List<Starter_Model> starterList) {
//        this.context = context;
//        this.list = starterList;
//    }
//
//    @NonNull
//    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        View view = LayoutInflater.from(context).inflate(R.layout.cuisine_layout,viewGroup,false);
//        MyViewHolder myViewHolder = new MyViewHolder(view);
//
//        return myViewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
//        Starter_Model starter_model= list.get(i);
//        ImageView image = myViewHolder.imageView;
//        image.setImageResource(starter_model.getImage());
//        TextView text = myViewHolder.textView;
//        text.setText(starter_model.getText());
//        ImageView image = myViewHolder.background_img;
//        image.setImageResource(starter_model.getImage());
//
//
//
//
//    }

//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    public static class MyViewHolder extends RecyclerView.ViewHolder{
//
//        ImageView imageView, background_img;
//        TextView textView;
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//            imageView = itemView.findViewById(R.id.imagecardview);
//            background_img = itemView.findViewById(R.id.card_background);
//            textView  = itemView.findViewById(R.id.cuisine_name);
//        }
//    }
//}
