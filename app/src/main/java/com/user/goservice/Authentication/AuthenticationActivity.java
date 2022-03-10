package com.user.goservice.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;
import com.user.goservice.Authentication.OtpActivity;
import com.user.goservice.R;

public class AuthenticationActivity extends AppCompatActivity {

    private EditText phoneNoTextView;
    private FirebaseAuth firebaseAuth;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        phoneNoTextView = findViewById(R.id.phoneText);
        nextButton = findViewById(R.id.nextButton);
        firebaseAuth = FirebaseAuth.getInstance();
        nextButton.setOnClickListener(v -> Authenticate());
    }

    private void Authenticate() {
        String phoneNumber = phoneNoTextView.getText().toString();
        if (phoneNumber.isEmpty()) {
            phoneNoTextView.setError("Please enter a number");
        } else {
            nextButton.setEnabled(false);
            Intent intent = new Intent(getApplicationContext(), OtpActivity.class)
                    .putExtra("PhoneNumber", phoneNumber);
            startActivity(intent);

        }
    }




}