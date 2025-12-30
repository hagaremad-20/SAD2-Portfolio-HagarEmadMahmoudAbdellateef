package com.mycompany.roky;
public class StoreInitializer {
    public static void initializeStore() {
        Store store = Store.getInstance();
        store.addCategory(new Category(1, "Makeup", "Beauty"));
        store.addCategory(new Category(2, "Clothing", "Fashion"));
        store.addCategory(new Category(3, "Accessories", "Jewelry"));
        store.addProduct(new Product(1, "Lipstick", 19.99, 50, store.getCategories().get(0)));
        store.addProduct(new Product(2, "Dress", 49.99, 20, store.getCategories().get(1)));
        store.addProduct(new Product(3, "Gold Necklace", 29.50, 10, store.getCategories().get(2)));
        store.addProduct(new Product(4, "Eyeliner", 15.00, 30, store.getCategories().get(0)));
        store.addProduct(new Product(5, "Bracelet", 25.00, 15, store.getCategories().get(2)));
    }
}
