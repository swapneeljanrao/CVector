package com.mrcoder.cvector.model;

public class Users {
    String id;
    String username;
    String name;
    String email;
    String password;
    String createdAt;
    String imageURL;

    public Users(String id, String username, String name, String email, String password, String createdAt, String imageURL) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.imageURL = imageURL;
    }

    public Users(String username, String name, String email, String password, String createdAt, String imageURL) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.imageURL = imageURL;
    }

    public Users() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
