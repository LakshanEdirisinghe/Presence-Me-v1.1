package edu.lakshaned.presenceme_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ADMIN", schema = "main")
public class Admin {

    @Id
    @Column(name = "user_id")
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}