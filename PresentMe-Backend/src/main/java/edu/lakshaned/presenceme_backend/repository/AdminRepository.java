package edu.lakshaned.presenceme_backend.repository;

import edu.lakshaned.presenceme_backend.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, String> {
}