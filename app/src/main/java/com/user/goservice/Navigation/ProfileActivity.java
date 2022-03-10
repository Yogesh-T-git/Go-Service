package com.user.goservice.Navigation;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.user.goservice.Database.GetDataFromDatabase;
import com.user.goservice.R;

import java.sql.ResultSet;

public class ProfileActivity extends AppCompatActivity {
    private TextView nameText, emailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        nameText = findViewById(R.id.nameText);
        emailText = findViewById(R.id.emailText);
        getUserDetails();
    }

    private void getUserDetails() {
        GetDataFromDatabase usersData = new GetDataFromDatabase();
        String query = "select username, email from users where uid='" + FirebaseAuth.getInstance().getUid() + "';";
        usersData.setQuery(query, usersData.retrieve);
        try {
            ResultSet resultSet = usersData.execute().get();

            while (resultSet.next()) {
                nameText.setText(resultSet.getString("username"));
                emailText.setText(resultSet.getString("email"));
            }
        } catch (Exception e) {
            Log.e("Error", e.getLocalizedMessage());
        }

    }

}