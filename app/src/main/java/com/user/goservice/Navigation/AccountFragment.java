package com.user.goservice.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.user.goservice.MainActivity;
import com.user.goservice.R;
import com.user.goservice.VehicleSales.VehicleSalesActivity;

public class AccountFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account, container, false);

        TextView logoutTextView = v.findViewById(R.id.logoutTextview);
        TextView myOrdersTextView = v.findViewById(R.id.myOrdersTextview);
        TextView profileTextview = v.findViewById(R.id.profileTextview);
        TextView myVehicleTextView = v.findViewById(R.id.MyVehicleTextView);
        TextView vehicleSalesTextView = v.findViewById(R.id.VehicleSalesTextView);


        logoutTextView.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getContext(), MainActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        });
        myOrdersTextView.setOnClickListener(view ->
                startActivity(new Intent(getContext(), NavigationActivity.class)
                        .putExtra("Fragment", "Orders")));

        profileTextview.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), ProfileActivity.class));
        });
        myVehicleTextView.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), VehicleActivity.class));
        });
        vehicleSalesTextView.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), VehicleSalesActivity.class));
        });

        return v;

    }
}
