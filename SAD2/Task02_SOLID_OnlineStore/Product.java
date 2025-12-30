package com.mycompany.roky;
public class Product {
    private int productID;
    private String name;
    private double price;
    private int stockQuantity;
    private Category category;
    public Product(int productID, String name, double price, int stockQuantity, Category category) {
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.stockQuantity = Math.max(0, stockQuantity);
        this.category = category;
    }
    public int getProductID() { return productID; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStockQuantity() { return stockQuantity; }
    public Category getCategory() { return category; }
    public void setName(String name) { if (name != null && !name.isEmpty()) this.name = name; }
    public void setPrice(double price) { if (price >= 0) this.price = price; }
    public void setStockQuantity(int stockQuantity) { if (stockQuantity >= 0) this.stockQuantity = stockQuantity; }
    public void setCategory(Category category) { if (category != null) this.category = category; }
    public void displayProduct() {
        System.out.println("Product: " + name + " | Price: $" + price);
    }
}
