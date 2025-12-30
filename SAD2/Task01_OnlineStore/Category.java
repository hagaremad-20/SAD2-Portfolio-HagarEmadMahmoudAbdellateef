package com.mycompany.roky;

public class Category {

    private int categoryID;
    private String categoryName;
    private String description;

    public Category(int categoryID, String categoryName, String description) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.description = description;
    }

    public String getName() {
        return categoryName;
    }

    public void displayCategory() {
        System.out.println("Category: " + categoryName + " | " + description);
    }

}