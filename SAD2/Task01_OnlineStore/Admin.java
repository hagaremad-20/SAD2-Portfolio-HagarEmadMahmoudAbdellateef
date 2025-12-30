package com.mycompany.roky;

public class Admin extends User {


    public Admin(int userID, String name, String email, String phone, String address, String password) {
        super(userID, name, email, phone, address, password);
    }
        /*
    public void addProduct(Product product) { }
    public void updateProduct(Product product) { }
    public void removeProduct(Product product) { }
    public void addCategory(Category category) { }*/
    
        public void addProduct(Product product) {
        Store.products.add(product);
        System.out.println("Product added: " + product.getName());
    }

    public void updateProduct(Product updatedProduct) {
        for (Product p : Store.products) {
            if (p.getProductID() == updatedProduct.getProductID()) {
                p.setName(updatedProduct.getName());
                p.setPrice(updatedProduct.getPrice());
                p.setStockQuantity(updatedProduct.getStockQuantity());
                p.setCategory(updatedProduct.getCategory());
                System.out.println("Product updated successfully.");
                return;
            }
        }
        System.out.println("Product not found!");
    }


    public void removeProduct(int productID) {
        for (Product p : Store.products) {
            if (p.getProductID() == productID) {
                Store.products.remove(p);
                System.out.println("Product removed: " + p.getName());
                return;
            }
        }
        System.out.println("Product not found.");
    }

    public void addCategory(Category category) {
        Store.categories.add(category);
        System.out.println("Category added: " + category.getName());
    }
}