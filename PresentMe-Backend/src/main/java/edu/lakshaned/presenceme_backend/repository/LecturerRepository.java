package edu.lakshaned.presenceme_backend.repository;

import edu.lakshaned.presenceme_backend.entity.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LecturerRepository extends JpaRepository<Lecturer, String> {

    // Find lecturer using user ID
}