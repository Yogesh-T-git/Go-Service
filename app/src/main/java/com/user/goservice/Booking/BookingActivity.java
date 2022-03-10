package com.user.goservice.Booking;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.user.goservice.Addvehicles.DatePickerFragment;
import com.user.goservice.Database.Database;
import com.user.goservice.Database.GetDataFromDatabase;
import com.user.goservice.Navigation.NavigationActivity;
import com.user.goservice.R;
import com.user.goservice.Services.CartManager;
import com.user.goservice.Services.Service;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BookingActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private final ArrayList<Service> cartItems = new ArrayList<>();
    private TextView vehicleNameTextView;
    private TextView vehicleNoTextView;
    private TextView serviceCostTextView;
    private TextView serviceCountTextView;
    private TextView totalTextView;
    private TextView changeDate, currentDateTextView;
    private Button placeOrderButton;
    private EditText addressEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        getView();
        setOrderDetails();
        changeDate.setOnClickListener(view -> {
            DatePickerFragment datePickerFragment = new DatePickerFragment();
            datePickerFragment.show(getSupportFragmentManager(), "date picker");
        });
        placeOrderButton.setOnClickListener(view -> {
            if (!addressEditText.getText().toString().isEmpty()) {
                Database database = new Database();
                String query = "INSERT INTO orders values(null,'Waiting for accepting','" +
                        currentDateTextView.getText().toString() + "','" +
                        addressEditText.getText().toString() + "',null,'" +
                        FirebaseAuth.getInstance().getUid() + "');";

                database.setQuery(query, database.update);
                database.execute();


                GetDataFromDatabase getDataFromDatabase = new GetDataFromDatabase();
                String query1 = "select MAX(oid) from orders where uid='" + FirebaseAuth.getInstance().getUid() + "';";
                getDataFromDatabase.setQuery(query1, getDataFromDatabase.retrieve);
                int oid = 0;
                try {
                    ResultSet resultSet = getDataFromDatabase.execute().get();
                    while (resultSet.next()) {
                        oid = resultSet.getInt("MAX(oid)");
                    }

                    for (Service service : cartItems) {
                        Database serviceDatabase = new Database();
                        String query2 = "INSERT INTO services values(" + oid + ",'" + service.serviceName + "'," + service.price + ");";
                        serviceDatabase.setQuery(query2, database.update);
                        serviceDatabase.execute();
                    }

                } catch (Exception e) {
                    Log.e("Error", e.getLocalizedMessage());
                }


                Toast.makeText(this, "Order placed successfully, " +
                        "Thank you for choosing our service.", Toast.LENGTH_SHORT).show();
                new CartManager().removeAll();
                placeOrderButton.setEnabled(false);
                startActivity(new Intent(getApplicationContext(), NavigationActivity.class)
                        .putExtra("Fragment", "Orders"));
            } else {
                addressEditText.setError("Cannot leave this field blank");
            }
        });

    }

    private void setOrderDetails() {
        String vehicleName, vehicleNumber;
        vehicleName = getIntent().getStringExtra("vehicle");
        vehicleNumber = getIntent().getStringExtra("vehicleNumber");
        vehicleNameTextView.setText(vehicleName);
        vehicleNoTextView.setText(vehicleNumber);

        getCartItems();
        int totalCost = 0;
        for (Service service : cartItems) {
            totalCost = totalCost + service.price;
        }

        serviceCostTextView.setText(String.valueOf(totalCost));
        serviceCountTextView.setText(String.valueOf(cartItems.size()));
        totalTextView.setText(String.valueOf(totalCost));
        String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        currentDateTextView.setText(date);
    }

    private void getView() {
        vehicleNameTextView = findViewById(R.id.vehicleNameTextView);
        vehicleNoTextView = findViewById(R.id.vehicleNoTextView);
        serviceCostTextView = findViewById(R.id.serviceCostTextView);
        serviceCountTextView = findViewById(R.id.serviceCountTextView);
        totalTextView = findViewById(R.id.totalTextView);
        changeDate = findViewById(R.id.changeDateTextView);
        currentDateTextView = findViewById(R.id.scheduleDateTextView);
        placeOrderButton = findViewById(R.id.placeOrderButton);
        addressEditText = findViewById(R.id.addressEditText);
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

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        String date = day + "/" + (month + 1) + "/" + year;
        currentDateTextView.setText(date);
    }
}