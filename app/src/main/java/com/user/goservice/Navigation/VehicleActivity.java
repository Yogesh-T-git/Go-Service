package com.user.goservice.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.user.goservice.Addvehicles.AddVehicleActivity;
import com.user.goservice.Database.GetDataFromDatabase;
import com.user.goservice.R;

import java.sql.ResultSet;
import java.util.ArrayList;

public class VehicleActivity extends AppCompatActivity {
    private ArrayList<Vehicle> vehicles = new ArrayList<>();
    private RecyclerView recyclerView;
    private TextView noVehiclesTextView;
    private Button addVehicleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);
        recyclerView = findViewById(R.id.recyclerView);
        noVehiclesTextView = findViewById(R.id.noVehiclesTextView);
        addVehicleButton = findViewById(R.id.seellButton);

        getVehiclesDetails();
        setRecyclerView();
        ImageView refreshImg = findViewById(R.id.refreshImg);
        refreshImg.setOnClickListener(view -> {
            setRecyclerView();
        });
        addVehicleButton.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), AddVehicleActivity.class)
                .putExtra("Activity", "Addvehicles")));

    }

    private void setRecyclerView() {
        if (vehicles.size() == 0)
            noVehiclesTextView.setVisibility(View.VISIBLE);
        else
            noVehiclesTextView.setVisibility(View.GONE);

        VehicleAdapter vehicleAdapter = new VehicleAdapter(vehicles, getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(vehicleAdapter);
    }

    private void getVehiclesDetails() {

        GetDataFromDatabase getDataFromDatabase = new GetDataFromDatabase();
        String query = "select * from vehicles where uid='" + FirebaseAuth.getInstance().getUid() + "';";
        try {
            getDataFromDatabase.setQuery(query);
            ResultSet resultSet = getDataFromDatabase.execute().get();
            while (resultSet.next()) {
                vehicles.add(new Vehicle(resultSet.getString("model"),
                        resultSet.getString("regno"), resultSet.getString("vid")
                        , resultSet.getString("vehicletype")));
            }
        } catch (Exception e) {
            Log.e("Error", e.getLocalizedMessage());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), NavigationActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}