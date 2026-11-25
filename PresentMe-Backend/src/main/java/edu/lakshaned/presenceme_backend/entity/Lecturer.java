package edu.lakshaned.presenceme_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "LECTURER")
public class Lecturer {

    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "lecturer_code")
    private String lecturerCode;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLecturerCode() {
        return lecturerCode;
    }

    public void setLecturerCode(String lecturerCode) {
        this.lecturerCode = lecturerCode;
    }
}