package com.user.goservice.Booking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.user.goservice.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {
    private final ArrayList<Order> orders;


    public OrdersAdapter(ArrayList<Order> orders, Context context) {
        this.orders = orders;
    }

    @NonNull
    @NotNull
    @Override
    public OrdersAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.order_view, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull OrdersAdapter.ViewHolder holder, int position) {
        holder.bikeNameTextView.setText(orders.get(position).vehicle);
        holder.bookingIdTextView.setText(orders.get(position).oid);
        holder.orderedOnDateTextView.setText(orders.get(position).date);
        holder.serviceCostTextView.setText(orders.get(position).serviceCost);
        holder.statusTextView.setText(orders.get(position).serviceStatus);

    }


    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView bikeNameTextView, bookingIdTextView, orderedOnDateTextView, serviceCostTextView, statusTextView;


        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            bikeNameTextView = itemView.findViewById(R.id.bikeNameTextView);
            bookingIdTextView = itemView.findViewById(R.id.bookingIdTextView);
            orderedOnDateTextView = itemView.findViewById(R.id.orderedOnDateTextView);
            serviceCostTextView = itemView.findViewById(R.id.serviceCostTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
        }
    }
}