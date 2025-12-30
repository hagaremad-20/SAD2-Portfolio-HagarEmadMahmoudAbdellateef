package com.mycompany.roky;

public class User {
    
    private int userID;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String password;

    public User(int userID, String name, String email, String phone, String address, String password) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.password = password;
    }

    //  Getters
    public int getUserID() { return userID; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }

    // Setters 
    public void setName(String name) {
        if (name != null && !name.isEmpty()) this.name = name;
    }

    public void setPhone(String phone) {
        if (phone != null && !phone.isEmpty()) this.phone = phone;
    }

    public void setAddress(String address) {
        if (address != null && !address.isEmpty()) this.address = address;
    }


    //  Login 
    public boolean login(String email, String password) {
        if (this.email.equals(email) && this.password.equals(password)) {
            return true;
        }
        System.out.println("Login failed. Invalid email or password.");
        return false;
    }

    public void logout() {
        System.out.println(name + " has logged out.");
    }

    
    

}