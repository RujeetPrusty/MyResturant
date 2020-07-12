package com.example.rujeet.myresturant.Viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.rujeet.myresturant.Cart;
import com.example.rujeet.myresturant.Database.Database;
import com.example.rujeet.myresturant.Models.Order;
import com.example.rujeet.myresturant.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class CartViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

    public TextView cartItemName, cartItemPrice, cartItemQuantity;
    public ElegantNumberButton quick_quntity_btn;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        cartItemName = (TextView) itemView.findViewById(R.id.cart_item_name);
        cartItemPrice = (TextView)itemView.findViewById(R.id.cart_item_price);
        cartItemQuantity = (TextView)itemView.findViewById(R.id.cart_item_quantity);
        quick_quntity_btn = (ElegantNumberButton)itemView.findViewById(R.id.cart_item_quantity_btn);
        itemView.setOnCreateContextMenuListener(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0,0,getAdapterPosition(),"Delete");

    }
}
    public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

        private List<Order> orderList ;
        private Cart cart;


          public CartAdapter(List<Order> orderList, Cart cart) {
            this.orderList = orderList;
            this.cart = cart;

        }

        @NonNull
        @Override
        public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(cart).inflate(R.layout.cart_layout, viewGroup, false);
            CartViewHolder cartViewHolder = new CartViewHolder(view);
            return cartViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, final int i) {
              cartViewHolder.quick_quntity_btn.setNumber(orderList.get(i).getQuantity());

              cartViewHolder.quick_quntity_btn.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
                  @Override
                  public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                      Order order = orderList.get(i);
                      order.setQuantity(String.valueOf(newValue));
                      new Database(cart).updateCart(order);
//
                      int total= 0;
                      List<Order> Cart1 = new Database(cart).getCarts();
                      for (Order order1:Cart1){
                          total += (Integer.parseInt(order1.getPrice()))*(Integer.parseInt(order1.getQuantity()));
//
                          NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("en","in"));
                          cart.totalPrice.setText(numberFormat.format(total));
                      }
                  }
              });

            cartViewHolder.cartItemName.setText(orderList.get(i).getProductName());


            cartViewHolder.cartItemQuantity.setText(orderList.get(i).getQuantity());

            int price = (Integer.parseInt(orderList.get(i).getPrice())) * (Integer.parseInt(orderList.get(i).getQuantity()));
            Log.e("cartprice",""+price);
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
            cartViewHolder.cartItemPrice.setText(numberFormat.format(price));

        }

        @Override
        public int getItemCount() {
            return orderList.size();
        }
    }

