package com.user.goservice.Addusers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.user.goservice.Database.Database;
import com.user.goservice.Addvehicles.AddVehicleActivity;
import com.user.goservice.R;

import org.jetbrains.annotations.NotNull;

public class AddUserActivity extends AppCompatActivity {
    private Button nextButton;
    private EditText userNameEditText, emailEditText;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        nextButton = findViewById(R.id.nextButton);
        userNameEditText = findViewById(R.id.nameText);
        emailEditText = findViewById(R.id.emailText);
        phoneNumber = getIntent().getStringExtra("phoneNumber");
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userNameEditText.getText().toString().isEmpty())
                    userNameEditText.setError("Enter Username");
                if (emailEditText.getText().toString().isEmpty())
                    emailEditText.setError("Enter Email");
                if (!userNameEditText.getText().toString().isEmpty() && !emailEditText.getText().toString().isEmpty()) {
                    if (!isEMailValid(emailEditText.getText().toString())) {
                        emailEditText.setError("Enter a valid email");
                    } else {


                        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                                .getReference("Users");

                        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(new UserData(userNameEditText.getText().toString(),
                                        emailEditText.getText().toString(), phoneNumber))
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(AddUserActivity.this, "Users details added", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), AddVehicleActivity.class)
                                                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(AddUserActivity.this, "Something went wrong will adding Users details ", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                        updateToMysqlDb(userNameEditText.getText().toString(),
                                emailEditText.getText().toString(), phoneNumber);
                    }
                }
            }
        });
    }

    private void updateToMysqlDb(String username, String email, String phoneNumber) {
        Database database = new Database();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String query = "INSERT INTO users VALUES('" + uid + "','" + username + "','" + email + "'," + phoneNumber + ",null,null);";
        Log.e("TEST",query);
        database.setQuery(query, database.update);
        database.execute();

    }

    private boolean isEMailValid(String email) {
        boolean emailValid = false;
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailValid = true;
        }
        return emailValid;
    }
}

