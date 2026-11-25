package edu.lakshaned.presenceme_backend;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class PasswordHashTest {

    @Test
    void generatePasswordHash() {

        BCryptPasswordEncoder passwordEncoder =
                new BCryptPasswordEncoder();

        String password = "PresentMe@2026!";

        String hashedPassword = passwordEncoder.encode(password);

        System.out.println("BCrypt Hash: " + hashedPassword);
    }
}