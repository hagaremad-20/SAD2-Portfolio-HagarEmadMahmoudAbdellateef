package com.mycompany.roky;
public class Admin extends User {
    public Admin(int userID, String name, String email, String phone, String address, String password) {
        super(userID, name, email, phone, address, password);
    }
    public void addProduct(Product product, Store store) {
        store.addProduct(product);
        System.out.println("Product added: " + product.getName());
    }
    public void updateProduct(Product updatedProduct, Store store) {
        store.findProductById(updatedProduct.getProductID()).ifPresentOrElse(p -> {
            p.setName(updatedProduct.getName());
            p.setPrice(updatedProduct.getPrice());
            p.setStockQuantity(updatedProduct.getStockQuantity());
            p.setCategory(updatedProduct.getCategory());
            System.out.println("Product updated successfully.");
        }, () -> System.out.println("Product not found!"));
    }
    public void removeProduct(int productID, Store store) {
        store.removeProductById(productID);
        System.out.println("If product existed, it was removed.");
    }
    public void addCategory(Category category, Store store) {
        store.addCategory(category);
        System.out.println("Category added: " + category.getName());
    }
}
