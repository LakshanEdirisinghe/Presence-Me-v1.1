package edu.lakshaned.presenceme_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "STUDENT")
public class Student {

    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "uni_id")
    private String uniId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUniId() {
        return uniId;
    }

    public void setUniId(String uniId) {
        this.uniId = uniId;
    }
}