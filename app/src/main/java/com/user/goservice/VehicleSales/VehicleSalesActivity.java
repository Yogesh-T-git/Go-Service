package com.user.goservice.VehicleSales;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.user.goservice.Database.GetDataFromDatabase;
import com.user.goservice.R;

import java.sql.ResultSet;
import java.util.ArrayList;

public class VehicleSalesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<VehicleSales> vehicleSales = new ArrayList<>();
    private VehicleSaleAdapter vehicleSaleAdapter;
    private Button sellButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicel_sales);
        recyclerView = findViewById(R.id.vehicleSalesRecyclerView);
        sellButton = findViewById(R.id.sellButton);
        getVehiclesSaleData();
        vehicleSaleAdapter.setOnItemClickListener(position -> {
            startActivity(new Intent(getApplicationContext(),
                    SalesPageActivity.class)
                    .putExtra("registrationNo", vehicleSales.get(position).registrationNo));
        });
        sellButton.setOnClickListener(position -> {
            startActivity(new Intent(getApplicationContext(),
                    SellVehicleActivity.class));
        });

    }

    private void getVehiclesSaleData() {

        GetDataFromDatabase getData = new GetDataFromDatabase();
        String query = "SELECT * from vehiclesales;";

        getData.setQuery(query);
        try {
            ResultSet resultSet = getData.execute().get();
            while (resultSet.next()) {
                VehicleSales vehicleSale = new VehicleSales(resultSet.getString("model"),
                        resultSet.getString("price"), resultSet.getString("manufactureDate"),
                        resultSet.getString("registrationNo"), resultSet.getString("mile"),
                        resultSet.getString("condi"), resultSet.getString("url"),
                        resultSet.getString("description"));
                if (!vehicleSales.contains(vehicleSale))
                    vehicleSales.add(vehicleSale);

            }
        } catch (Exception e) {
            Log.e("Error", "VSA: " + e.getLocalizedMessage());
        }

        vehicleSaleAdapter = new VehicleSaleAdapter(vehicleSales);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(vehicleSaleAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}