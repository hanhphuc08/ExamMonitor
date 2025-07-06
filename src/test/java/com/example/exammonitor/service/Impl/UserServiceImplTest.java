package com.example.exammonitor.service.Impl;

import com.example.exammonitor.model.User;
import com.example.exammonitor.model.UserRole;
import com.example.exammonitor.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user1 = new User();
        user1.setId("u1");
        user1.setUsername("admin");
        user1.setPassword("admin123");
        user1.setFullName("Nguyen Van Admin");
        user1.setEmail("admin@example.com");
        user1.setRole(UserRole.ADMIN);
        user1.setCreatedAt(LocalDate.of(2024, 1, 1));

        user2 = new User();
        user2.setId("u2");
        user2.setUsername("gv");
        user2.setPassword("gv123");
        user2.setFullName("Tran Thi Giam Thi");
        user2.setEmail("gv@example.com");
        user2.setRole(UserRole.INVIGILATOR);
        user2.setCreatedAt(LocalDate.of(2024, 2, 2));
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));
        List<User> users = userService.getAllUsers();
        assertEquals(2, users.size());
        assertEquals("u1", users.get(0).getId());
        assertEquals("u2", users.get(1).getId());
    }

    @Test
    void testGetUserById() {
        when(userRepository.findById("u1")).thenReturn(Optional.of(user1));
        Optional<User> found = userService.getUserById("u1");
        assertTrue(found.isPresent());
        assertEquals("Nguyen Van Admin", found.get().getFullName());
    }

    @Test
    void testSaveUser() {
        when(userRepository.save(user1)).thenReturn(user1);
        User saved = userService.saveUser(user1);
        assertNotNull(saved);
        assertEquals("u1", saved.getId());
    }

    @Test
    void testDeleteUserById() {
        doNothing().when(userRepository).deleteById("u1");
        userService.deleteUserById("u1");
        verify(userRepository, times(1)).deleteById("u1");
    }

    @Test
    void testUpdateUser() {
        user1.setFullName("Nguyen Van Admin Updated");
        when(userRepository.save(user1)).thenReturn(user1);
        User updated = userService.saveUser(user1);
        assertEquals("Nguyen Van Admin Updated", updated.getFullName());
    }
} 