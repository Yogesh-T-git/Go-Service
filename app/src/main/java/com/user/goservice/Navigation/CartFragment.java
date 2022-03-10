package com.user.goservice.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.user.goservice.Booking.BookingActivity;
import com.user.goservice.Database.GetDataFromDatabase;
import com.user.goservice.R;
import com.user.goservice.Services.CartManager;
import com.user.goservice.Services.Service;
import com.user.goservice.Services.ServiceAdapter;
import com.user.goservice.Services.ServicesActivity;

import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.util.ArrayList;

public class CartFragment extends Fragment {
    private TextView vehicleName, clearTextView, noItemTextView, addTextView, totalTextView, itemCount;
    private CartManager cartManager;
    private RecyclerView recyclerView;
    private Button proceedButton;
    private String vehicle = "Vehicle name", vehicleNumber;
    private ArrayList<Service> cartItems = new ArrayList<>();
    private int totalCost = 0;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_cart, container, false);

        getViews(v);
        getCartDetails();
        getCartItems();
        setRecyclerView();
        calculateTotal();
        proceedButton.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), BookingActivity.class)
                    .putExtra("vehicle", vehicle)
                    .putExtra("vehicleNumber", vehicleNumber));
        });
        clearTextView.setOnClickListener(view -> {
            cartManager.removeAll();
            Toast.makeText(getContext(), "Cart cleared!!", Toast.LENGTH_SHORT).show();
            recyclerView.setVisibility(View.GONE);
            noItemTextView.setVisibility(View.VISIBLE);
            totalTextView.setText(R.string.rs_0);
            itemCount.setText("0");
        });

        addTextView.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), ServicesActivity.class)
                    .putExtra("vehicleName", vehicleName.getText())
                    .putExtra("vehicleNumber", vehicleNumber));
        });
        return v;
    }

    private void calculateTotal() {
        for (Service service : cartItems) {
            totalCost = totalCost + service.price;
        }
        totalTextView.setText(String.valueOf(totalCost));
        itemCount.setText(String.valueOf(cartItems.size()));

    }

    private void setRecyclerView() {
        if (cartItems.size() == 0) {
            noItemTextView.setVisibility(View.VISIBLE);
        }
        ServiceAdapter serviceAdapter = new ServiceAdapter(cartItems, getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        noItemTextView.setVisibility(View.GONE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(serviceAdapter);
    }

    private void getViews(View v) {
        vehicleName = v.findViewById(R.id.defaultVehicleTextView);
        clearTextView = v.findViewById(R.id.clearAllButton);
        recyclerView = v.findViewById(R.id.cartRecyclerView);
        noItemTextView = v.findViewById(R.id.noItemTextView);
        addTextView = v.findViewById(R.id.addButton);
        totalTextView = v.findViewById(R.id.totalCost);
        itemCount = v.findViewById(R.id.itemCount);
        proceedButton = v.findViewById(R.id.proceedButton);

        recyclerView.setVisibility(View.VISIBLE);
    }

    private void getCartDetails() {
        cartManager = new CartManager();
        cartManager.getCartItems();
        GetDataFromDatabase getVehicleName = new GetDataFromDatabase();
        String uid = FirebaseAuth.getInstance().getUid();
        String query = "select model,regno from vehicles where uid='" + uid + "';";
        getVehicleName.setQuery(query, getVehicleName.retrieve);
        try {
            ResultSet resultSet = getVehicleName.execute().get();

            while (resultSet.next()) {

                vehicle = resultSet.getString("model");
                vehicleNumber = resultSet.getString("regno");
            }
            vehicleName.setText(String.format("%s %s", vehicle, vehicleNumber));
        } catch (Exception e) {
            Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void getCartItems() {
        GetDataFromDatabase getCart = new GetDataFromDatabase();
        String query = "select * from cart where uid='" + FirebaseAuth.getInstance().getUid() + "'";
        getCart.setQuery(query, getCart.retrieve);

        try {
            ResultSet resultSet = getCart.execute().get();
            while (resultSet.next()) {
                cartItems.add(new Service(resultSet.getString("service"),
                        Integer.parseInt(resultSet.getString("price"))));


            }
        } catch (Exception e) {
            Log.e("Error", e.getMessage());

        }

    }
}
