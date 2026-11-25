package edu.lakshaned.presenceme_backend.service;

import edu.lakshaned.presenceme_backend.dto.LoginRequest;
import edu.lakshaned.presenceme_backend.dto.LoginResponse;
import edu.lakshaned.presenceme_backend.entity.User;
import edu.lakshaned.presenceme_backend.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.lakshaned.presenceme_backend.repository.AdminRepository;
import edu.lakshaned.presenceme_backend.repository.LecturerRepository;
import edu.lakshaned.presenceme_backend.repository.StudentRepository;

@Service
public class AuthService {

    private final AdminRepository adminRepository;
    private final LecturerRepository lecturerRepository;
    private final StudentRepository studentRepository;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AdminRepository adminRepository,
            LecturerRepository lecturerRepository,
            StudentRepository studentRepository
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminRepository = adminRepository;
        this.lecturerRepository = lecturerRepository;
        this.studentRepository = studentRepository;
    }

    private String identifyRole(String userId) {

        if (adminRepository.existsById(userId)) {
            return "ADMIN";
        }

        if (lecturerRepository.existsById(userId)) {
            return "LECTURER";
        }

        if (studentRepository.existsById(userId)) {
            return "STUDENT";
        }

        return null;
    }

    public LoginResponse login(LoginRequest request) {

        // 1. Find user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (user == null) {
            return new LoginResponse(
                    false,
                    "Invalid email or password",
                    null, null, null, null, null
            );
        }

        // 2. Check account status
        if (!"ACTIVE".equalsIgnoreCase(user.getAccountStatus())) {
            return new LoginResponse(
                    false,
                    "Your account is not active",
                    null, null, null, null, null
            );
        }

        // 3. Verify password
        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPasswordHash()
        )) {
            return new LoginResponse(
                    false,
                    "Invalid email or password",
                    null, null, null, null, null
            );
        }

        // Role identification will be added next.

        // 4. Identify user role
        String role = identifyRole(user.getUserId());

        if (role == null) {
            return new LoginResponse(
                    false,
                    "User role not found",
                    null, null, null, null, null
            );
        }

// 5. Return successful login response
        return new LoginResponse(
                true,
                "Login successful",
                null,
                role,
                user.getUserId(),
                user.getFirstName(),
                user.getLastName()
        );
    }
}