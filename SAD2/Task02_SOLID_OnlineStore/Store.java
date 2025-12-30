package com.mycompany.roky;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
public class Store {
    private static Store instance;
    private final List<Product> products;
    private final List<Category> categories;
    private Store() {
        products = new ArrayList<>();
        categories = new ArrayList<>();
    }
    public static Store getInstance() {
        if (instance == null) instance = new Store();
        return instance;
    }
    public List<Product> getProducts() { return products; }
    public List<Category> getCategories() { return categories; }
    public void addProduct(Product p) { if (p != null) products.add(p); }
    public void removeProductById(int productId) {
        products.removeIf(p -> p.getProductID() == productId);
    }
    public Optional<Product> findProductById(int id) {
        return products.stream().filter(p -> p.getProductID() == id).findFirst();
    }
    public void addCategory(Category c) { if (c != null) categories.add(c); }
    public Optional<Category> findCategoryByIndex(int index) {
        if (index >= 0 && index < categories.size()) return Optional.of(categories.get(index));
        return Optional.empty();
    }
}
