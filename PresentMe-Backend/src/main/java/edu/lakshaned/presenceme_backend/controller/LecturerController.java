package edu.lakshaned.presenceme_backend.controller;

import edu.lakshaned.presenceme_backend.entity.Lecturer;
import edu.lakshaned.presenceme_backend.repository.LecturerRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lecturers")
public class LecturerController {

    private final LecturerRepository lecturerRepository;

    public LecturerController(LecturerRepository lecturerRepository) {
        this.lecturerRepository = lecturerRepository;
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentLecturer(Authentication authentication) {

        String userId = authentication.getName();

        return lecturerRepository.findById(userId)
                .map(lecturer -> ResponseEntity.ok(lecturer))
                .orElse(ResponseEntity.notFound().build());
    }
}