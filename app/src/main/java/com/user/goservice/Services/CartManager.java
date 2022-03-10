package com.user.goservice.Services;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.user.goservice.Database.Database;
import com.user.goservice.Database.GetDataFromDatabase;

import java.sql.ResultSet;
import java.util.ArrayList;

public class CartManager {

    public ArrayList<String> cartItems = new ArrayList<>();

    public void addToCart(String serviceName, int price) {
        Database database = new Database();
        String uid = FirebaseAuth.getInstance().getUid();
        String query = "INSERT INTO CART VALUES('" + serviceName + "'," + price + ",'" + uid + "');";
        database.setQuery(query, database.update);
        database.execute();
    }

    public void removeFromCart(String serviceName) {
        Database database = new Database();
        String uid = FirebaseAuth.getInstance().getUid();
        String query = "DELETE from cart where uid='" + uid + "' and service='" + serviceName + "';";
        database.setQuery(query, database.update);
        database.execute();
    }

    public void removeAll() {
        Database database = new Database();
        String uid = FirebaseAuth.getInstance().getUid();
        String query = "DELETE from cart where uid='" + uid + "';";
        database.setQuery(query, database.update);
        database.execute();
    }

    public void getCartItems() {
        GetDataFromDatabase getCart = new GetDataFromDatabase();
        String query = "select * from cart where uid='" + FirebaseAuth.getInstance().getUid() + "'";
        getCart.setQuery(query, getCart.retrieve);

        try {
            ResultSet resultSet = getCart.execute().get();
            while (resultSet.next()) {
                cartItems.add(resultSet.getString("service"));


            }
        } catch (Exception e) {
            Log.e("Error", e.getMessage());

        }

    }
}
