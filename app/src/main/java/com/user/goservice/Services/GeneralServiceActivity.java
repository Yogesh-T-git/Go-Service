package com.user.goservice.Services;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.user.goservice.R;
import com.user.goservice.Navigation.NavigationActivity;

public class GeneralServiceActivity extends AppCompatActivity {
    private Button proceedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_service);
        proceedButton = findViewById(R.id.addToCartButton);
        Button viewCartButton = findViewById(R.id.proceedToCartButton);

        CartManager cartManager = new CartManager();
        cartManager.getCartItems();

        proceedButton.setEnabled(true);
        viewCartButton.setVisibility(View.GONE);
        if (cartManager.cartItems.contains("General service")) {
            proceedButton.setEnabled(false);
            viewCartButton.setVisibility(View.VISIBLE);

        }
        viewCartButton.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), NavigationActivity.class)
                    .putExtra("Fragment", "Cart"));
        });
        proceedButton.setOnClickListener(view -> {
            proceedButton.setEnabled(false);
            proceedButton.setEnabled(false);
            viewCartButton.setVisibility(View.VISIBLE);
            cartManager.addToCart("General service", 2500);
            Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show();
        });
    }

}