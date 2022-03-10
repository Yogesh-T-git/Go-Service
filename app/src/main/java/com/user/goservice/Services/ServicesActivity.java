package com.user.goservice.Services;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.user.goservice.Navigation.NavigationActivity;
import com.user.goservice.R;

public class ServicesActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private String fragment = "brake";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        Button viewCartButton = findViewById(R.id.proceedToCartButton);
        viewCartButton.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), NavigationActivity.class)
                    .putExtra("Fragment", "Cart"));
        });
        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewpager2);
        if (getIntent().getStringExtra("fragment") != null)
            fragment = getIntent().getStringExtra("fragment");
        setTabLayout();


    }


    private void setTabLayout() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        SectionsPagerAdapter fragmentStateAdapter = new SectionsPagerAdapter(fragmentManager, getLifecycle());
        viewPager2.setAdapter(fragmentStateAdapter);

        tabLayout.addTab(tabLayout.newTab().setText("Brakes"));
        tabLayout.addTab(tabLayout.newTab().setText("Electric"));
        tabLayout.addTab(tabLayout.newTab().setText("Engine"));
        tabLayout.addTab(tabLayout.newTab().setText("Gears"));
        tabLayout.addTab(tabLayout.newTab().setText("Wheels"));

        switch (fragment) {
            case "brake":
                viewPager2.setCurrentItem(0);
                tabLayout.selectTab(tabLayout.getTabAt(0));
                break;
            case "electric":
                viewPager2.setCurrentItem(1);
                tabLayout.selectTab(tabLayout.getTabAt(1));
                break;
            case "wheels":
                viewPager2.setCurrentItem(4);
                tabLayout.selectTab(tabLayout.getTabAt(4));
                break;
        }


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }
}
















