package com.user.goservice.VehicleSales;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.squareup.picasso.Picasso;
import com.user.goservice.Database.GetDataFromDatabase;
import com.user.goservice.R;

import java.sql.ResultSet;

public class SalesPageActivity extends AppCompatActivity {
    private TextView usernameTextView, phoneNoTextView, modelTextView, priceTextView, registrationNoTextView,
            milesTextView, conditionTextView, descTextView, manufactureDate;
    private String url, uid;
    private ImageView carImageView;
    private Button contactButton;
    private CardView arCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_page);
        getViewById();
        getSaleData();

        Picasso.get().load(url).into(carImageView);
        contactButton.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SalesPageActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE}, 1);
            } else {
                String dial = "tel:" + phoneNoTextView.getText();
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
            }
        });

        arCardView.setOnClickListener(view -> {
                             startActivity(new Intent(getApplicationContext(),ARActivity.class));
        });
    }

    private void getSaleData() {
        String regNo = getIntent().getStringExtra("registrationNo");
        GetDataFromDatabase getData = new GetDataFromDatabase();
        String query = "select * from vehiclesales where registrationNo='" + regNo + "';";

        getData.setQuery(query);

        try {
            ResultSet resultSet = getData.execute().get();
            while (resultSet.next()) {
                modelTextView.setText(resultSet.getString("model"));
                registrationNoTextView.setText(resultSet.getString("registrationNo"));
                milesTextView.setText(resultSet.getString("mile"));
                conditionTextView.setText(resultSet.getString("condi"));
                descTextView.setText(resultSet.getString("description"));
                manufactureDate.setText(resultSet.getString("manufactureDate"));
                priceTextView.setText(resultSet.getString("price"));
                url = resultSet.getString("url");
                uid = resultSet.getString("uid");

            }
            GetDataFromDatabase getUserData = new GetDataFromDatabase();

            String query1 = "select * from users where uid='" + uid + "';";
            getUserData.setQuery(query1);
            ResultSet resultSet1 = getUserData.execute().get();
            while (resultSet1.next()) {
                usernameTextView.setText(resultSet1.getString("username"));
                phoneNoTextView.setText(resultSet1.getString("phoneno"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getViewById() {
        usernameTextView = findViewById(R.id.sellerNameTextView);
        phoneNoTextView = findViewById(R.id.sellerPhNoTextView);
        modelTextView = findViewById(R.id.vehicleModelTextView);
        priceTextView = findViewById(R.id.priceTextView);
        registrationNoTextView = findViewById(R.id.registrationTextView);
        milesTextView = findViewById(R.id.milesTextView);
        conditionTextView = findViewById(R.id.conditionTextView);
        descTextView = findViewById(R.id.descTextView);
        manufactureDate = findViewById(R.id.manufDateTextView);
        carImageView = findViewById(R.id.car_image_view);
        contactButton = findViewById(R.id.seellButton);
        arCardView = findViewById(R.id.arCardView);
    }
}