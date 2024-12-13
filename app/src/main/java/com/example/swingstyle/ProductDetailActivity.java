package com.example.swingstyle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Referencias a las vistas
        ImageView productImage = findViewById(R.id.productImage);
        TextView productName = findViewById(R.id.productName);
        TextView productPrice = findViewById(R.id.productPrice);
        TextView productDescription = findViewById(R.id.productDescription);
        Button btnAddToCart = findViewById(R.id.btnAddToCart);
        Button btnBackToShop = findViewById(R.id.btnBackToShop);

        // Obtener los datos del Intent
        String name = getIntent().getStringExtra("name");
        String description = getIntent().getStringExtra("description");
        double price = getIntent().getDoubleExtra("price", 0.0);
        String image = getIntent().getStringExtra("image");
        int productId = getIntent().getIntExtra("productId", -1); // Obtener el ID del producto

        // Asignar los datos a las vistas
        productName.setText(name);
        productPrice.setText(String.format("$%.2f", price));
        productDescription.setText(description);

        // Asignar la imagen
        int imageResourceId = getResources().getIdentifier(image, "drawable", getPackageName());
        if (imageResourceId != 0) {
            productImage.setImageResource(imageResourceId);
        } else {
            productImage.setImageResource(R.drawable.default_image); // Imagen predeterminada
        }

        // Acción del botón "Añadir al Carrito"
        btnAddToCart.setOnClickListener(view -> {
            CartItem item = new CartItem(productId, name, price, 1); // Añadir una unidad por defecto
            DatabaseHelper dbHelper = new DatabaseHelper(ProductDetailActivity.this);
            dbHelper.addItemToCart(item); // Añadir al carrito en la base de datos
            Toast.makeText(ProductDetailActivity.this, "Producto añadido al carrito", Toast.LENGTH_SHORT).show();
        });

        // Acción del botón "Volver a la Tienda"
        btnBackToShop.setOnClickListener(view -> {
            Intent intent = new Intent(ProductDetailActivity.this, ShopActivity.class);
            startActivity(intent);
            finish(); // Finaliza la actividad actual
        });
    }
}
