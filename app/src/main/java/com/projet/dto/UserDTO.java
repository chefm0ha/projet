package com.projet.dto;

import com.projet.models.User;

public class UserDTO {
    private String id;
    private String name;
    private String email;

    // Constructeur vide nécessaire pour Firestore
    public UserDTO() {}

    public UserDTO(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // Méthode de conversion de User vers UserDTO
    public static UserDTO fromUser(User user) {
        return new UserDTO(
            user.getId(),
            user.getName(),
            user.getEmail()
        );
    }

    // Méthode de conversion de UserDTO vers User
    public User toUser() {
        return new User(this.id, this.name, this.email);
    }

    // Getters et Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
} 