package edu.lakshaned.presenceme_backend.repository;

import edu.lakshaned.presenceme_backend.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldFindUserByEmail() {

        Optional<User> user =
                userRepository.findByEmail("nimal@presentme.test");

        assertTrue(user.isPresent());

        assertEquals("Nimal", user.get().getFirstName());

    }
}