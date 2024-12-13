package com.example.swingstyle;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.content.Intent;
import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ShopActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        dbHelper = new DatabaseHelper(this);

        // Cargar productos desde el archivo JSON en la base de datos
        loadProductsFromJson();

        // Mostrar productos en el RecyclerView
        loadProducts();

        // Configuración del TabLayout
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        TabLayout.Tab tabShop = tabLayout.getTabAt(1); // Posición del tab de Shop
        if (tabShop != null) {
            tabShop.select();
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0: // Home
                        lanzarHome();
                        break;
                    case 1: // Shop
                        // Ya estamos en Shop
                        break;
                    case 2: // News
                        // lanzarNews();
                        break;
                    case 3: // Athletes
                        // lanzarAthletes();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    // Método para cargar los productos desde la base de datos y mostrarlos
    private void loadProducts() {
        Cursor cursor = dbHelper.getProducts();
        productList = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                String image = cursor.getString(cursor.getColumnIndexOrThrow("image")); // Obtener la imagen de la base de datos

                // Crear el objeto Product con la imagen
                productList.add(new Product(id, name, price, description, image));
            } while (cursor.moveToNext());
            cursor.close();
        }

        // Configurar el adaptador para el RecyclerView
        productAdapter = new ProductAdapter(productList, this);
        recyclerView.setAdapter(productAdapter);
    }

    // Método para lanzar la actividad Home
    public void lanzarHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void loadProductsFromJson() {
        dbHelper.clearProductsTable(); // Limpia la tabla antes de insertar

        try {
            InputStream is = getAssets().open("products.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String json = new String(buffer, StandardCharsets.UTF_8);
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject productJson = jsonArray.getJSONObject(i);

                int id = productJson.getInt("id");
                String name = productJson.getString("name");
                double price = productJson.getDouble("price");
                String description = productJson.getString("description");
                String image = productJson.getString("image"); // El nombre del recurso de la imagen

                // Insertar en la base de datos
                dbHelper.insertProduct(id, name, price, description, image);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

