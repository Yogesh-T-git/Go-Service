package com.user.goservice.Services;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.user.goservice.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class WheelsFragment extends Fragment {
    private RecyclerView recyclerView;
    ArrayList<Service> wheelsServicesList = new ArrayList<Service>();


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_wheels, container, false);
        recyclerView = v.findViewById(R.id.wheels_recycle_view);
        getServices();

        return v;
    }

    private void getServices() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Services");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.child("wheelservice").getChildren()) {
                    wheelsServicesList.add(new Service(dataSnapshot.getKey(), Integer.parseInt(dataSnapshot.getValue().toString())));
                }

                ServiceAdapter serviceAdapter = new ServiceAdapter(wheelsServicesList, getContext());
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(serviceAdapter);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }


}
