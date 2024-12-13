package com.example.swingstyle;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ecommerce.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear tabla de productos
        db.execSQL("CREATE TABLE products (id INTEGER PRIMARY KEY, name TEXT, price REAL, image TEXT, description TEXT)");
        // Crear tabla del carrito
        db.execSQL("CREATE TABLE cart (id INTEGER PRIMARY KEY, product_id INTEGER, quantity INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS products");
        db.execSQL("DROP TABLE IF EXISTS cart");
        onCreate(db);
    }

    // CRUD para productos
    public void insertProduct(int id, String name, double price, String description, String image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("name", name);
        values.put("price", price);
        values.put("image", image); // Almacenar la URL o el nombre de la imagen (si es necesario convertirla a BLOB, hacerlo)
        values.put("description", description);
        db.insert("products", null, values);
        db.close();
    }

    public Cursor getProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM products", null);
    }

    // CRUD para el carrito
    public void addToCart(int productId, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("product_id", productId);
        values.put("quantity", quantity);
        db.insert("cart", null, values);
        db.close();
    }
    public void addItemToCart(CartItem cartItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("product_id", cartItem.getProductId());
        values.put("name", cartItem.getName());
        values.put("price", cartItem.getPrice());
        values.put("quantity", cartItem.getQuantity());

        // Comprobar si el producto ya está en el carrito
        Cursor cursor = db.rawQuery("SELECT * FROM cart WHERE product_id = ?",
                new String[]{String.valueOf(cartItem.getProductId())});
        if (cursor != null && cursor.moveToFirst()) {
            // Si el producto ya existe, actualizamos la cantidad
            int newQuantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity")) + cartItem.getQuantity();
            ContentValues updateValues = new ContentValues();
            updateValues.put("quantity", newQuantity);
            db.update("cart", updateValues, "product_id = ?",
                    new String[]{String.valueOf(cartItem.getProductId())});
            cursor.close();
        } else {
            // Si no existe, insertamos el nuevo producto
            db.insert("cart", null, values);
        }
    }

    public Cursor getCartItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM cart", null);
    }
   /* public Cursor getCartItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT cart.product_id, products.name, products.price, cart.quantity FROM cart " +
                "JOIN products ON cart.product_id = products.id", null);
    }*/

    public void updateCartItem(int productId, int newQuantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("quantity", newQuantity);
        db.update("cart", values, "product_id = ?", new String[]{String.valueOf(productId)});
        db.close();
    }

    public void removeCartItem(int productId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("cart", "product_id = ?", new String[]{String.valueOf(productId)});
        db.close();
    }

    // Insertar productos desde una lista o JSON
    public void insertProductsFromJson(List<Product> products) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (Product product : products) {
            ContentValues values = new ContentValues();
            values.put("id", product.getId());
            values.put("name", product.getName());
            values.put("price", product.getPrice());
            values.put("description", product.getDescription());
            values.put("image", product.getImage()); // Asume que la imagen es una URL o nombre de archivo
            db.insert("products", null, values);
        }
        db.close();
    }

    public void clearProductsTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM products"); // Limpia la tabla
    }
}
