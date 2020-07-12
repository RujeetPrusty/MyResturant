package com.example.rujeet.myresturant.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.rujeet.myresturant.Models.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {

    private static final String DB_Name = "caribbeanDB.db";
    private static final int DB_Version = 1;

    public Database(Context context) {
        super(context, DB_Name, null, DB_Version);
    }

    public List<Order> getCarts(){

        SQLiteDatabase DB = getReadableDatabase();
        SQLiteQueryBuilder QB = new SQLiteQueryBuilder();

        String[] sqlSelect = {"ID","ProductId","ProductName","Quantity","Price"};
        String sqlTable = "OrderDetail";

        QB.setTables(sqlTable);
        Cursor cursor = QB.query(DB,sqlSelect,null,null,null,null,null);

        final List<Order> result = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                result.add(new Order(cursor.getInt(cursor.getColumnIndex("ID")),
                        cursor.getString(cursor.getColumnIndex("ProductId")),
                        cursor.getString(cursor.getColumnIndex("ProductName")),
                        cursor.getString(cursor.getColumnIndex("Quantity")),
                        cursor.getString(cursor.getColumnIndex("Price"))
                ));
            }while (cursor.moveToNext());
        }
        return result;
    }

    public void addToCart(Order order){
        SQLiteDatabase DB = getReadableDatabase();
        String query = String.format("INSERT INTO OrderDetail(ProductId,ProductName,Quantity,Price) VALUES('%s','%s','%s','%s');",
                order.getProductId(),
                order.getProductName(),
                order.getQuantity(),
                order.getPrice());
        DB.execSQL(query);
    }

    public void cleanCart(){
        SQLiteDatabase DB = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetail");
        DB.execSQL(query);
    }


    public void updateCart(Order order) {
        SQLiteDatabase DB = getReadableDatabase();
        String query = String.format("UPDATE OrderDetail SET Quantity = %s WHERE ID = %d",order.getQuantity(),order.getID());
        DB.execSQL(query);
    }
}
