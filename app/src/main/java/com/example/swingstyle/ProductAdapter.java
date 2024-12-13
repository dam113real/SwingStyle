package com.example.swingstyle;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> productList;
    private Context context;

    public ProductAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.nameTextView.setText(product.getName());
        holder.priceTextView.setText(product.getPrice() + " $");
        holder.descriptionTextView.setText(product.getDescription());

        String imageName = product.getImage();
        int imageResourceId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        if (imageResourceId != 0) {
            holder.imageView.setImageResource(imageResourceId);
        } else {
            holder.imageView.setImageResource(R.drawable.default_image);
        }

        // Configurar click listener
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("name", product.getName());
            intent.putExtra("description", product.getDescription());
            intent.putExtra("price", product.getPrice());
            intent.putExtra("image", product.getImage());
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, priceTextView, descriptionTextView;
        ImageView imageView; // Añadimos la variable ImageView

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.productName);
            priceTextView = itemView.findViewById(R.id.productPrice);
            descriptionTextView = itemView.findViewById(R.id.productDescription);
            imageView = itemView.findViewById(R.id.productImage); // Referencia al ImageView
        }
    }
}
