package com.user.goservice.Navigation;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.user.goservice.R;


public class NavigationActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(onItemSelectedListener);
        String fragment = "Home";
        if (getIntent().getStringExtra("Fragment") != null)
            fragment = getIntent().getStringExtra("Fragment");
        Fragment selectedFragment = new HomeFragment();
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        switch (fragment) {
            case "Home":
                selectedFragment = new HomeFragment();
                bottomNavigationView.setSelectedItemId(R.id.nav_home);
                break;
            case "Cart":
                selectedFragment = new CartFragment();
                bottomNavigationView.setSelectedItemId(R.id.nav_cart);
                break;
            case "Orders":
                selectedFragment = new OrdersFragment();
                bottomNavigationView.setSelectedItemId(R.id.nav_orders);
                break;
            case "Account":
                selectedFragment = new AccountFragment();
                bottomNavigationView.setSelectedItemId(R.id.nav_account);
                break;

        }


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, selectedFragment)
                .commit();
    }

    private final NavigationBarView.OnItemSelectedListener onItemSelectedListener = item -> {
        Fragment selectedFragment = null;
        switch (item.getItemId()) {
            case R.id.nav_home:
                selectedFragment = new HomeFragment();
                break;
            case R.id.nav_cart:
                selectedFragment = new CartFragment();
                break;
            case R.id.nav_orders:
                selectedFragment = new OrdersFragment();
                break;
            case R.id.nav_account:
                selectedFragment = new AccountFragment();
                break;

        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
        return true;
    };
}
