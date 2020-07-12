package com.example.rujeet.myresturant;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rujeet.myresturant.Database.Database;
import com.example.rujeet.myresturant.Models.Order;
import com.example.rujeet.myresturant.Models.Request;
import com.example.rujeet.myresturant.Models.Users;
import com.example.rujeet.myresturant.Viewholder.CartAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import info.hoang8f.widget.FButton;
import mehdi.sakout.fancybuttons.FancyButton;

public class Cart extends AppCompatActivity {

    RecyclerView cartrecyclerView;
    DatabaseReference requestDbRef,userDbRef;

    public TextView totalPrice;
//    private FancyButton placeorder;
    private Button placeorder;

    List<Order> Cart2= new ArrayList<>();
    CartAdapter cartAdapter;

    private FirebaseAuth firebaseAuth;
    private String userPhoneKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser User = firebaseAuth.getCurrentUser();
        userPhoneKey = User.getPhoneNumber();



        requestDbRef = FirebaseDatabase.getInstance().getReference("Requests");

        cartrecyclerView = (RecyclerView)findViewById(R.id.cartlist);
        cartrecyclerView.setLayoutManager(new LinearLayoutManager(this));

        totalPrice = (TextView)findViewById(R.id.total_price);
        placeorder = (Button)findViewById(R.id.placeorder_btn);


        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Cart2.size()>0) {
                    showAlertDialog();
                    Toast.makeText(getApplicationContext(), "button clicked", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Cart is Empty",Toast.LENGTH_LONG).show();
                }

            }
        });

        loadOrderList();

    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Address");
        alertDialog.setMessage("Enter your address: ");

        final EditText address = new EditText(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        address.setLayoutParams(layoutParams);
        alertDialog.setView(address);

        alertDialog.setPositiveButton(Html.fromHtml("<font color='#FF7F27'>OK</font>"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Request request = new Request(userPhoneKey,
                        address.getText().toString(),
                        totalPrice.getText().toString(),
                        Cart2);
                requestDbRef.child(String.valueOf(System.currentTimeMillis())).setValue(request);

                new Database(getBaseContext()).cleanCart();
                Toast.makeText(getApplicationContext(),"order placed",Toast.LENGTH_LONG).show();
                finish();

            }
        });
        alertDialog.setNegativeButton(Html.fromHtml("<font color='#FF7F27'>CANCEL</font>"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();

    }



    private void loadOrderList() {
        Cart2 = new Database(this).getCarts();
        cartAdapter = new CartAdapter(Cart2,this);
        cartAdapter.notifyDataSetChanged();
        cartrecyclerView.setAdapter(cartAdapter);

        int total = 0;
        for (Order order:Cart2){
             Log.e("foorloop","rujeet");

            total += (Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("en","in"));
            totalPrice.setText(numberFormat.format(total));

        }


    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals("Delete")){
            deletecart(item.getOrder());
        }
        return true;
    }

    private void deletecart(int order) {
        Cart2.remove(order);
        new Database(this).cleanCart();
        for (Order item:Cart2){
            new Database(this).addToCart(item);

        }
        loadOrderList();

    }
}
