package edu.lakshaned.presenceme_backend.dto;

public class LoginResponse {

    private boolean success;
    private String message;
    private String token;
    private String role;
    private String userId;
    private String firstName;
    private String lastName;

    public LoginResponse(
            boolean success,
            String message,
            String token,
            String role,
            String userId,
            String firstName,
            String lastName
    ) {
        this.success = success;
        this.message = message;
        this.token = token;
        this.role = role;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Getters

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    public String getRole() {
        return role;
    }

    public String getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}