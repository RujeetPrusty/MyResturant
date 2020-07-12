package com.example.rujeet.myresturant;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rujeet.myresturant.Models.Menu;
import com.example.rujeet.myresturant.Viewholder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MenuList extends AppCompatActivity {


    RecyclerView menuRecyclerView;
    DatabaseReference databaseReference;
    FirebaseRecyclerOptions<Menu> options;
    FirebaseRecyclerAdapter<Menu, MenuViewHolder> adapter;
    private String CuisineId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);

        menuRecyclerView = findViewById(R.id.menurecyclerview);
        menuRecyclerView.setHasFixedSize(true);



        menuRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (getIntent()!=null) {

            CuisineId = getIntent().getStringExtra("cuisineid");
            Toast.makeText(getApplicationContext(), CuisineId, Toast.LENGTH_LONG).show();
        }
        if (!CuisineId.isEmpty() && CuisineId!=null){
            loadMenu(CuisineId);
        }
    }

    private void loadMenu(String cuisineId) {

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Menu");
//        databaseReference.orderByChild("menuid").equalTo(cuisineId);
        if (databaseReference != null){

            Log.d("database:", "is not null");
        }
        else{
            Log.d("database:","is null");
        }
        options = new FirebaseRecyclerOptions.Builder<Menu>().setQuery(databaseReference.orderByChild("menuid").equalTo(cuisineId),Menu.class).build();

        adapter = new FirebaseRecyclerAdapter<Menu, MenuViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MenuViewHolder holder, final int position, @NonNull Menu model) {
                Picasso.get().load(model.getImage()).into(holder.menuImage);
                holder.menuName.setText(model.getMenuname());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String MenuId = adapter.getRef(position).getKey();
                        Intent intent = new Intent(MenuList.this,Menu_Details.class);
                        intent.putExtra("MenuId",MenuId);
                        startActivity(intent);

                    }
                });

            }

            @NonNull
            @Override
            public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.menu_list_layout,viewGroup,false);
                return new MenuViewHolder(view);
            }
        };
        adapter.startListening();
        menuRecyclerView.setAdapter(adapter);
    }
}
