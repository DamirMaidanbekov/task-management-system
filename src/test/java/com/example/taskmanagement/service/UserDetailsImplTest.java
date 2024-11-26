package com.example.taskmanagement.service;

import com.example.taskmanagement.model.Entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserDetailsImplTest {

    @Mock
    private User user;

    private UserDetailsImpl userDetails;
    private UUID userId;
    private String userEmail;
    private String userPassword;
    private Collection<? extends GrantedAuthority> authorities;

    @BeforeEach
    public void setUp() {
        // Initialize test data
        userId = UUID.randomUUID();
        userEmail = "testuser@example.com";
        userPassword = "password";
        authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        // Create UserDetailsImpl instance using the build method
        User mockUser = new User();
        mockUser.setId(userId);
        mockUser.setEmail(userEmail);
        mockUser.setPassword(userPassword);

        userDetails = UserDetailsImpl.build(mockUser);
    }

    @Test
    public void testGetId() {
        // Assert that the ID is correctly set
        assertEquals(userId, userDetails.getId());
    }

    @Test
    public void testGetUsername() {
        // Assert that the username (email) is correctly set
        assertEquals(userEmail, userDetails.getUsername());
    }

    @Test
    public void testGetPassword() {
        // Assert that the password is correctly set
        assertEquals(userPassword, userDetails.getPassword());
    }

    @Test
    public void testGetAuthorities() {
        // Assert that the authorities are correctly set
        assertEquals(authorities, userDetails.getAuthorities());
    }

    @Test
    public void testIsAccountNonExpired() {
        // Assert that account is non-expired
        assertTrue(userDetails.isAccountNonExpired());
    }

    @Test
    public void testIsAccountNonLocked() {
        // Assert that account is non-locked
        assertTrue(userDetails.isAccountNonLocked());
    }

    @Test
    public void testIsCredentialsNonExpired() {
        // Assert that credentials are non-expired
        assertTrue(userDetails.isCredentialsNonExpired());
    }

    @Test
    public void testIsEnabled() {
        // Assert that the account is enabled
        assertTrue(userDetails.isEnabled());
    }
}
