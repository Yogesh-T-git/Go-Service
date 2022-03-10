package com.user.goservice.VehicleSales;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.user.goservice.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class VehicleSaleAdapter extends RecyclerView.Adapter<VehicleSaleAdapter.ViewHolder> {

    private final ArrayList<VehicleSales> vehicleSales;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public VehicleSaleAdapter(ArrayList<VehicleSales> vehicleSales) {
        this.vehicleSales = vehicleSales;

    }

    @NonNull
    @NotNull
    @Override
    public VehicleSaleAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.vehicle_sales_view, parent, false);

        return new VehicleSaleAdapter.ViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull VehicleSaleAdapter.ViewHolder holder, int position) {
        VehicleSales currentVehicle = this.vehicleSales.get(position);

        holder.model.setText(currentVehicle.model);
        holder.price.setText(String.format("%s %s ", currentVehicle.price, "₹"));
        holder.desc.setText(currentVehicle.description);
        Picasso.get().load(currentVehicle.url).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return vehicleSales.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView model;
        private final TextView price;
        private final TextView desc;

        public ViewHolder(@NonNull @NotNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            model = itemView.findViewById(R.id.vehicleModelTextView);
            price = itemView.findViewById(R.id.priceTextView);
            desc = itemView.findViewById(R.id.descTextView);
            imageView = itemView.findViewById(R.id.car_image_view);
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }
}
