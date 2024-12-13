package com.example.swingstyle;

public class Product {
    private int id;
    private String name;
    private double price;
    private String description;
    private String image;  // Nueva propiedad para la imagen

    // Constructor actualizado para incluir la imagen
    public Product(int id, String name, double price, String description, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image; // Inicialización de la imagen
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;  // Getter para la imagen
    }

    public void setImage(String image) {
        this.image = image;  // Setter para la imagen
    }
}
