package com.user.goservice.Addvehicles;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.user.goservice.Database.Database;
import com.user.goservice.Navigation.NavigationActivity;
import com.user.goservice.Navigation.VehicleActivity;
import com.user.goservice.R;

import java.util.Calendar;
import java.util.regex.Pattern;

public class AddVehicleActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private TextView changeDate, currentDateTextView, modelTextView, registrationNumberTextView;
    private Button nextButton;
    private LinearLayout bikeCardView, carCardView;
    private String vehicleType = "null";
    private ImageView carImageView, bikeImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);
        getView();

        changeDate.setOnClickListener(view -> {
            DatePickerFragment datePickerFragment = new DatePickerFragment();
            datePickerFragment.show(getSupportFragmentManager(), "date picker");
        });
        bikeImageView.setOnClickListener(view -> {
            vehicleType = "bike";
            bikeCardView.setBackgroundColor(getResources().getColor(R.color.theme_red));
            carCardView.setBackgroundColor(getResources().getColor(R.color.light_grey));
            Toast.makeText(AddVehicleActivity.this, "selected bike", Toast.LENGTH_SHORT).show();
        });
        carImageView.setOnClickListener(view -> {
            vehicleType = "car";
            bikeCardView.setBackgroundColor(getResources().getColor(R.color.light_grey));
            carCardView.setBackgroundColor(getResources().getColor(R.color.theme_red));
            Toast.makeText(AddVehicleActivity.this, "selected car", Toast.LENGTH_SHORT).show();
        });

        nextButton.setOnClickListener(view -> {
            String model = modelTextView.getText().toString().trim();
            String registration = registrationNumberTextView.getText().toString().trim().toUpperCase();
            if (model.isEmpty())
                modelTextView.setError("Cannot leave this field blank!");
            if (registration.isEmpty())
                registrationNumberTextView.setError("Cannot leave this field blank!");
            if (!registration.isEmpty() && !isRegistrationValid(registration))
                registrationNumberTextView.setError("Enter a valid registration number!");
            if (vehicleType.equals("null"))
                Toast.makeText(AddVehicleActivity.this, "Pick a vehicle type", Toast.LENGTH_LONG).show();

            if (!model.isEmpty() && !registration.isEmpty() && isRegistrationValid(registration) && !vehicleType.equals("null")) {

                updateVehicleDetails(registration, model, vehicleType, currentDateTextView.getText().toString());
            }
        });

    }

    private void updateVehicleDetails(String registration, String model, String vehicleType, String manufDate) {

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String vid = FirebaseDatabase.getInstance().getReference().push().getKey();

        Database database = new Database();
        String query = "INSERT INTO vehicles VALUES('" + vid + "','" + uid + "','" + registration + "','" + model + "','" + vehicleType + "','" + manufDate + "');";
        database.setQuery(query, database.update);
        database.execute();

        Database database1 = new Database();
        String query1 = "UPDATE users SET defaultVehicle = '" + vid + "' WHERE uid='" + uid + "';";
        database1.setQuery(query1, database.update);
        database1.execute();


        Toast.makeText(this, "Added vehicle successfully", Toast.LENGTH_SHORT).show();

        String activity = getIntent().getStringExtra("Activity");
        Intent intent;
        if (activity == null) {
            intent = new Intent(getApplicationContext(), NavigationActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        } else {
            intent = new Intent(getApplicationContext(), VehicleActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        startActivity(intent);


    }

    private boolean isRegistrationValid(String registration) {
        return Pattern.matches("^[A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{4}$", registration);
    }

    private void getView() {
        changeDate = findViewById(R.id.changeDateTextView);
        currentDateTextView = findViewById(R.id.displayDateTextView);
        nextButton = findViewById(R.id.saveButton);
        bikeCardView = findViewById(R.id.bikeCardView);
        carCardView = findViewById(R.id.carCardView);
        carImageView = findViewById(R.id.carImageView);
        bikeImageView = findViewById(R.id.bikeImageView);
        modelTextView = findViewById(R.id.modelTextView);
        registrationNumberTextView = findViewById(R.id.registrationTextView);
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