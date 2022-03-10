package com.user.goservice.VehicleSales;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.user.goservice.Database.Database;
import com.user.goservice.R;

import java.util.Calendar;
import java.util.regex.Pattern;

public class SellVehicleActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private TextView modelTextView, registrationTextView, priceTextView,
            conditionLayoutTextView, milesTextView, changeDateTextView, descTextView, displayDateTextView;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_vehicle);
        getViewById();
        saveButton.setOnClickListener(view -> {
            String model = modelTextView.getText().toString().trim();
            String registration = registrationTextView.getText().toString().trim().toUpperCase();
            if (model.isEmpty())
                modelTextView.setError("Cannot leave this field blank!");

            if (registration.isEmpty())
                registrationTextView.setError("Cannot leave this field blank!");

            if (!registration.isEmpty() && !isRegistrationValid(registration))
                registrationTextView.setError("Enter a valid registration number!");

            if (priceTextView.getText().toString().isEmpty())
                priceTextView.setError("Cannot leave this field blank!");

            if (milesTextView.getText().toString().isEmpty())
                milesTextView.setError("Cannot leave this field blank!");

            if (changeDateTextView.getText().toString().isEmpty())
                changeDateTextView.setError("Cannot leave this field blank!");

            if (descTextView.getText().toString().isEmpty())
                priceTextView.setError("Cannot leave this field blank!");

            if (conditionLayoutTextView.getText().toString().isEmpty())
                conditionLayoutTextView.setError("Cannot leave this field blank!");

            if (!model.isEmpty() && !registration.isEmpty() && isRegistrationValid(registration)
                    && !priceTextView.getText().toString().isEmpty() && !milesTextView.getText().toString().isEmpty()
                    && !changeDateTextView.getText().toString().isEmpty() && !descTextView.getText().toString().isEmpty()
                    && !conditionLayoutTextView.getText().toString().isEmpty()) {
                updateToDB(model, displayDateTextView.getText().toString(), registration,
                        milesTextView.getText().toString(), conditionLayoutTextView.getText().toString(),
                        descTextView.getText().toString(), priceTextView.getText().toString());

            }
        });
    }

    private void updateToDB(String model, String manufactureDate, String registrationNo, String mile,
                            String condi, String description, String price) {
        String uid = FirebaseAuth.getInstance().getUid();
        String url = "https://firebasestorage.googleapis.com/v0/b/goservice-4bbd3.appspot.com/o/" +
                "Vehicles%2F1.jpeg?alt=media&token=afb8a3f6-b785-4aa2-96d9-e4c0dfe5cb02";
        String query = "INSERT INTO vehiclesales values('" + model + "','" + manufactureDate + "','" +
                registrationNo + "'," + mile + ",'" + condi + "','" + url + "','" + description + "','" + uid + "'," + price + ");";

        Database database = new Database();

        try {
            database.setQuery(query, database.update);
            database.execute();
            Toast.makeText(this, "Posted vehicle successfully", Toast.LENGTH_LONG).show();
            onBackPressed();
        } catch (Exception e) {
            Log.e("Error", "SVA: " + e.getLocalizedMessage());
        }
    }


    private boolean isRegistrationValid(String registration) {
        return Pattern.matches("^[A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{4}$", registration);
    }

    private void getViewById() {
        modelTextView = findViewById(R.id.modelTextView);
        registrationTextView = findViewById(R.id.registrationTextView);
        priceTextView = findViewById(R.id.priceTextView);
        conditionLayoutTextView = findViewById(R.id.conditionLayoutTextView);
        milesTextView = findViewById(R.id.milesTextView);
        changeDateTextView = findViewById(R.id.changeDateTextView);
        descTextView = findViewById(R.id.descTextView);
        milesTextView = findViewById(R.id.milesTextView);
        displayDateTextView = findViewById(R.id.displayDateTextView);
        saveButton = findViewById(R.id.saveButton);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        String date = day + "/" + (month + 1) + "/" + year;
        displayDateTextView.setText(date);
    }
}