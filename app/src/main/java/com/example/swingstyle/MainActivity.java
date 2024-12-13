package com.example.swingstyle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.home);
       /* ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
       // DatabaseHelper.insertSampleProducts(this);


        // Obtener el TabLayout
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        // **Seleccionar manualmente el tab de Home (posición 0)**
        TabLayout.Tab tabHome = tabLayout.getTabAt(0); // Posición del tab de Home
        if (tabHome != null) {
            tabHome.select();
        }


        // Configurar listener para detectar selección de tabs
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0: // Home
                        // Ya estamos en Home, no hacemos nada
                        break;
                    case 1: // Shop
                        lanzarShop();
                        break;
                    case 2: // News
                       // lanzarNews();
                        break;
                    case 3: // Athletes
                      //  lanzarAthletes();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
    // Método que se llama al hacer clic en el ícono del carrito
    public void goToCart(View view) {
        Intent intent = new Intent(MainActivity.this, CartActivity.class);
        startActivity(intent);
    }

    public void lanzarShop() {
        Intent intent = new Intent(this, ShopActivity.class);
        startActivity(intent);
    }
  /*  public void lanzarShop(View view){
        Intent i = new Intent(this,ShopActivity.class);
        startActivity(i);
    }

    public void pulsaShop(View view){
        lanzarShop(null);
    }*/
  private void initializeProducts() {
      DatabaseHelper dbHelper = new DatabaseHelper(this);

      dbHelper.insertProduct(1, "Gold Leone", 40, null, "Slim Fit Gloves");
      dbHelper.insertProduct(2, "Blood Leone", 45, null, "High-Performance Gloves");
      dbHelper.insertProduct(3, "Silver Strike", 50, null, "Durable Training Gloves");
  }
}