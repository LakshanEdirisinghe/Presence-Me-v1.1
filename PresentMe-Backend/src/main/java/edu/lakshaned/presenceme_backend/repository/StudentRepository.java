package edu.lakshaned.presenceme_backend.repository;

import edu.lakshaned.presenceme_backend.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String> {
}