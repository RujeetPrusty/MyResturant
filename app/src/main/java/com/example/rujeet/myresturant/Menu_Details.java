package com.example.rujeet.myresturant;

import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.rujeet.myresturant.Database.Database;
import com.example.rujeet.myresturant.Models.Menu;
import com.example.rujeet.myresturant.Models.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Menu_Details extends AppCompatActivity {
    private TextView MenuPrice;
    private ImageView MenuDetailsImage;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton AddtoCartbtn;
    ElegantNumberButton MenuQuntitybtn;

    DatabaseReference MenudatabaseReference;

    String MenuId;
    Menu CurrentMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__details);


        MenudatabaseReference = FirebaseDatabase.getInstance().getReference().child("Menu");
        MenuQuntitybtn = (ElegantNumberButton)findViewById(R.id.menuQuntitybtn);
        AddtoCartbtn = (FloatingActionButton)findViewById(R.id.addtocart);

        AddtoCartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Database(getBaseContext()).addToCart(new Order(
                        MenuId,
                        CurrentMenu.getMenuname(),
                        MenuQuntitybtn.getNumber(),
                        CurrentMenu.getMenuprice()
                ));
                Toast.makeText(getApplicationContext(),"Added to Cart",Toast.LENGTH_LONG).show();
            }
        });

        MenuPrice = (TextView)findViewById(R.id.menu_detais_price);
        MenuDetailsImage = (ImageView)findViewById(R.id.menu_details_image);

        collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        if (getIntent()!=null){
            MenuId = getIntent().getStringExtra("MenuId");
        }
        if (!MenuId.isEmpty()){
            getMenuDetails(MenuId);
        }

    }

    private void getMenuDetails(String menuId) {
        MenudatabaseReference.child(MenuId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                CurrentMenu = dataSnapshot.getValue(Menu.class);

                Picasso.get().load(CurrentMenu.getImage()).into(MenuDetailsImage);
                collapsingToolbarLayout.setTitle(CurrentMenu.getMenuname());
                MenuPrice.setText(CurrentMenu.getMenuprice());

//                if (dataSnapshot.exists() && dataSnapshot.hasChild("image")){
//
//                    String menudetailsImage =dataSnapshot.child("image").getValue().toString();
//
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });
    }
}
