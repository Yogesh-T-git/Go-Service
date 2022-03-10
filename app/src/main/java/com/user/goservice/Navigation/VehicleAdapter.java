package com.user.goservice.Navigation;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.user.goservice.Database.Database;
import com.user.goservice.Database.GetDataFromDatabase;
import com.user.goservice.R;

import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.util.ArrayList;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.ViewHolder> {

    private ArrayList<Vehicle> vehicles = new ArrayList<>();
    private Context context;
    private String defaultVehicle;
    private int defPos = 0;

    public VehicleAdapter(ArrayList<Vehicle> vehicles, Context context) {
        this.vehicles = vehicles;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public VehicleAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.vehicle_view, parent, false);
        getDefaultVehicle();
        return new VehicleAdapter.ViewHolder(view);
    }

    private void getDefaultVehicle() {
        String query = "Select defaultVehicle from users where uid='" + FirebaseAuth.getInstance().getUid() + "';";
        GetDataFromDatabase getDataFromDatabase = new GetDataFromDatabase();
        try {
            getDataFromDatabase.setQuery(query);
            ResultSet resultSet = getDataFromDatabase.execute().get();
            while (resultSet.next())
                defaultVehicle = resultSet.getString("defaultVehicle");
        } catch (Exception e) {
            Log.e("error", e.getLocalizedMessage());
        }
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull VehicleAdapter.ViewHolder holder, int position) {
        holder.vehicleName.setText(String.format("%s %s", holder.vehicleName.getText(), vehicles.get(position).vehicleName));
        holder.vehicleReg.setText(String.format("%s %s", holder.vehicleReg.getText(), vehicles.get(position).vehicleReg));
        setIcons(holder, position);


        holder.setButton.setOnClickListener(view -> {
            setHasDefault(vehicles.get(position).vid);
            Toast.makeText(context, "Default vehicle updated", Toast.LENGTH_SHORT).show();

        });
        holder.removeButton.setOnClickListener(view -> {
            removeVehicle(vehicles.get(position).vid, position);
        });
    }

    private void setIcons(ViewHolder holder, int position) {
        if (vehicles.get(position).vehicleType.equals("bike")) {
            holder.imgCar.setVisibility(View.GONE);
            holder.imgScooter.setVisibility(View.VISIBLE);
            defPos = position;
        } else {
            holder.imgCar.setVisibility(View.VISIBLE);
            holder.imgScooter.setVisibility(View.GONE);
        }
        if (vehicles.get(position).vid.equals(defaultVehicle)) {
            holder.cardView.setCardBackgroundColor(holder.itemView.getResources().getColor(R.color.quantum_googgreen200));
        } else {
            holder.cardView.setCardBackgroundColor(holder.itemView.getResources().getColor(R.color.white));
        }
    }

    private void removeVehicle(String vid, int position) {
        try {
            vehicles.remove(position);
            Database database = new Database();
            defaultVehicle=vehicles.get(0).vid;
            String query = "update users set defaultVehicle='" + vehicles.get(0) + "' where uid='" + FirebaseAuth.getInstance().getUid() + "';";
            database.setQuery(query, database.update);
            database.execute();
            Database database1 = new Database();
            String query1 = "DELETE FROM vehicles WHERE vid='" + vid + "';";
            database1.setQuery(query1, database1.update);
            database1.execute();

        } catch (Exception e) {
            Log.e("Error", e.getLocalizedMessage());
        }
    }

    private void setHasDefault(String vid) {
        try {
            Database database = new Database();
            String query = "update users set defaultVehicle='" + vid + "' where uid='" + FirebaseAuth.getInstance().getUid() + "';";
            database.setQuery(query, database.update);
            database.execute();

        } catch (Exception e) {
            Log.e("Error", e.getLocalizedMessage());
        }

    }

    @Override
    public int getItemCount() {
        return vehicles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView vehicleName, vehicleReg, setButton, removeButton;
        private ImageView imgScooter, imgCar;
        private CardView cardView;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            vehicleName = itemView.findViewById(R.id.vehicleTxt);
            vehicleReg = itemView.findViewById(R.id.vehicleNoTxt);
            setButton = itemView.findViewById(R.id.addItemButton);
            removeButton = itemView.findViewById(R.id.removeItemButton);
            imgScooter = itemView.findViewById(R.id.imgScooter);
            imgCar = itemView.findViewById(R.id.imgCar);
            cardView = itemView.findViewById(R.id.cardViewDefault);

        }
    }
}
