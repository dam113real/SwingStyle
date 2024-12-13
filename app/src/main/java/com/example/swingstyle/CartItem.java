package com.example.swingstyle;

public class CartItem {
    private int productId;
    private String name;
    private double price;
    private int quantity;

    public CartItem(int productId, String name, double price, int quantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters y setters
    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    // Actualizar la cantidad
    public void updateQuantity(int quantity) {
        this.quantity += quantity;
    }

    // Para mostrar el precio total del item (cantidad * precio)
    public double getTotalPrice() {
        return price * quantity;
    }
}
