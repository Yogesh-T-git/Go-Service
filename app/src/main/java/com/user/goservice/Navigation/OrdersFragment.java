package com.user.goservice.Navigation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.user.goservice.Booking.Order;
import com.user.goservice.Booking.OrdersAdapter;
import com.user.goservice.Database.GetDataFromDatabase;
import com.user.goservice.R;

import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.util.ArrayList;

public class OrdersFragment extends Fragment {
    private final ArrayList<Order> orders = new ArrayList<>();

    private Spinner filterSpinner;
    private final ArrayList<String> filter = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_orders, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);

        getOrdersDetails();
        OrdersAdapter ordersAdapter = new OrdersAdapter(orders, getContext());
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(ordersAdapter);

        return v;
    }

    private void getOrdersDetails() {
        GetDataFromDatabase database = new GetDataFromDatabase();
        String query = "select * from orders,vehicles where orders.uid='" + FirebaseAuth.getInstance().getUid() + "';";
        database.setQuery(query, database.retrieve);
        try {
            ResultSet resultSet = database.execute().get();
            while (resultSet.next()) {
                orders.add(new Order("0", resultSet.getString("status"),
                        resultSet.getString("orderdate"), resultSet.getString("model"), resultSet.getString("oid")));
            }
        } catch (Exception e) {
            Log.e("error", e.getLocalizedMessage());
        }
        for (Order order : orders) {
            GetDataFromDatabase getService = new GetDataFromDatabase();
            String query1 = "select * from services where oid=" + order.oid + ";";
            getService.setQuery(query1, database.retrieve);
            try {
                ResultSet resultSet = getService.execute().get();
                int count = 0;
                while (resultSet.next()) {
                    count = count + resultSet.getInt("price");
                }
                orders.get(orders.indexOf(order)).serviceCost = String.valueOf(count);

            } catch (Exception e) {
                Log.e("Error", e.getLocalizedMessage());
            }
        }
    }
}
