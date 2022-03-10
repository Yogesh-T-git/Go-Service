package com.user.goservice.Services;

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

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {
    private final ArrayList<Service> service;

    private final CartManager cartManager = new CartManager();

    public ServiceAdapter(ArrayList<Service> service, Context context) {
        this.service = service;
    }

    @NonNull
    @NotNull
    @Override
    public ServiceAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.serivce_items, parent, false);

        cartManager.getCartItems();
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull ServiceAdapter.ViewHolder holder, int position) {
        holder.serviceName.setText(service.get(position).serviceName);
        String priceString = "₹" + service.get(position).price;
        holder.price.setText(priceString);
        if (cartManager.cartItems.contains(service.get(position).serviceName)) {
            holder.add.setVisibility(View.GONE);
            holder.add.setEnabled(false);
            holder.remove.setVisibility(View.VISIBLE);
            holder.remove.setEnabled(true);

        } else {
            holder.remove.setVisibility(View.GONE);
            holder.remove.setEnabled(false);
            holder.add.setVisibility(View.VISIBLE);
            holder.add.setEnabled(true);
        }
        holder.add.setOnClickListener(view -> {
            holder.add.setVisibility(View.GONE);
            holder.add.setEnabled(false);
            holder.remove.setVisibility(View.VISIBLE);
            holder.remove.setEnabled(true);
            cartManager.addToCart(service.get(position).serviceName, service.get(position).price);
        });

        holder.remove.setOnClickListener(view -> {

            holder.remove.setVisibility(View.GONE);
            holder.remove.setEnabled(false);
            holder.add.setVisibility(View.VISIBLE);
            holder.add.setEnabled(true);
            cartManager.removeFromCart(service.get(position).serviceName);

        });
    }


    @Override
    public int getItemCount() {
        return service.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView serviceName, price;
        private android.widget.Button add, remove;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            serviceName = itemView.findViewById(R.id.serviceName);
            price = itemView.findViewById(R.id.priceTextView);
            add = itemView.findViewById(R.id.addItemButton);
            remove = itemView.findViewById(R.id.removeItemButton);

        }
    }
}