package com.example.exammonitor.repository;

import com.example.exammonitor.model.User;
import com.example.exammonitor.model.UserRole;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class UserRepositoryIntegrationTest {
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void testInsertAndFindUser() {
        User user = new User();
        user.setId("u1");
        user.setUsername("admin");
        user.setPassword("admin123");
        user.setFullName("Nguyen Van Admin");
        user.setEmail("admin@example.com");
        user.setRole(UserRole.ADMIN);
        user.setCreatedAt(LocalDate.of(2024, 1, 1));

        userRepository.save(user);

        Optional<User> found = userRepository.findById("u1");
        assertTrue(found.isPresent());
        assertEquals("Nguyen Van Admin", found.get().getFullName());
        assertEquals(UserRole.ADMIN, found.get().getRole());
    }

    @Test
    void testFindByUsername() {
        User user = new User();
        user.setId("u2");
        user.setUsername("gv");
        user.setPassword("gv123");
        user.setFullName("Tran Thi Giam Thi");
        user.setEmail("gv@example.com");
        user.setRole(UserRole.INVIGILATOR);
        user.setCreatedAt(LocalDate.of(2024, 2, 2));

        userRepository.save(user);

        Optional<User> found = userRepository.findByUsername("gv");
        assertTrue(found.isPresent());
        assertEquals("Tran Thi Giam Thi", found.get().getFullName());
        assertEquals(UserRole.INVIGILATOR, found.get().getRole());
    }

    @Test
    void testDeleteUser() {
        User user = new User();
        user.setId("u3");
        user.setUsername("test");
        user.setPassword("test123");
        user.setFullName("Test User");
        user.setEmail("test@example.com");
        user.setRole(UserRole.INVIGILATOR);
        user.setCreatedAt(LocalDate.of(2024, 3, 3));

        userRepository.save(user);
        assertTrue(userRepository.findById("u3").isPresent());

        userRepository.deleteById("u3");
        assertFalse(userRepository.findById("u3").isPresent());
    }
} 