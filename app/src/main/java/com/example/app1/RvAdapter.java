package com.example.app1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.HolderRetrofit> {
    Context context;
    List<ProductResult> productResults;

    public RvAdapter(Context context, List<ProductResult> productResults) {
        this.productResults = productResults;
        this.context = context;
    }

    @NonNull
    @Override
    public HolderRetrofit onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_rv_product_item, parent, false);
        return new HolderRetrofit(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderRetrofit holder, int position) {
        holder.ivTitle.setText(productResults.get(position).getTitle());
        Glide.with(context).load(productResults.get(position).getProductImage()).error(R.drawable.ic_launcher_background).into(holder.ivImage);
    }

    @Override
    public int getItemCount() {
        return productResults.size();
    }

    public static class HolderRetrofit extends RecyclerView.ViewHolder {
        TextView ivTitle;
        ImageView ivImage;

        public HolderRetrofit(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
            ivTitle = itemView.findViewById(R.id.ivTitle);
        }
    }
}