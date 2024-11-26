package com.example.taskmanagement.service;

import com.example.taskmanagement.model.Entity.User;
import com.example.taskmanagement.model.dto.UserDTO;
import com.example.taskmanagement.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserDTO userDTO;
    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create test data
        userDTO = new UserDTO();
        userDTO.setEmail("testuser@example.com");
        userDTO.setPassword("password");

        user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail(userDTO.getEmail());
        user.setPassword("encodedPassword");
    }

    @Test
    public void testRegister() {
        // Mock password encoding
        when(passwordEncoder.encode(userDTO.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Call the service method
        UserDTO registeredUser = userService.register(userDTO);

        // Verify interactions and results
        verify(userRepository, times(1)).save(any(User.class));
        assertNotNull(registeredUser);
        assertEquals(userDTO.getEmail(), registeredUser.getEmail());
        assertNotEquals(userDTO.getPassword(), registeredUser.getPassword()); // Password should not be returned
    }

    @Test
    public void testLoginSuccess() {
        // Mocking user repository and password matching
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(userDTO.getPassword(), user.getPassword())).thenReturn(true);

        // Call the login method
        Optional<UserDTO> loggedInUser = userService.login(userDTO.getEmail(), userDTO.getPassword());

        // Verify the results
        assertTrue(loggedInUser.isPresent());
        assertEquals(userDTO.getEmail(), loggedInUser.get().getEmail());
    }

    @Test
    public void testLoginFailure() {
        // Mocking user repository return empty
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.empty());

        // Call the login method
        Optional<UserDTO> loggedInUser = userService.login(userDTO.getEmail(), userDTO.getPassword());

        // Verify the results
        assertFalse(loggedInUser.isPresent());
    }

    @Test
    public void testLoadUserByUsernameSuccess() {
        // Mock user retrieval from repository
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.of(user));

        // Call the loadUserByUsername method
        UserDetailsImpl userDetails = (UserDetailsImpl) userService.loadUserByUsername(userDTO.getEmail());

        // Verify the results
        assertNotNull(userDetails);
        assertEquals(user.getEmail(), userDetails.getUsername());
        assertEquals(user.getId(), userDetails.getId());
    }

    @Test
    public void testLoadUserByUsernameFailure() {
        // Mock user not found
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.empty());

        // Assert exception is thrown
        assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername(userDTO.getEmail());
        });
    }
}
