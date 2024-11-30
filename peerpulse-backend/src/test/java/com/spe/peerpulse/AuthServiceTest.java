package com.spe.peerpulse;

import com.spe.peerpulse.entity.Role;
import com.spe.peerpulse.entity.User;
import com.spe.peerpulse.model.request.RegisterRequest;
import com.spe.peerpulse.model.response.AuthResponse;
import com.spe.peerpulse.repository.UserRepository;
import com.spe.peerpulse.service.AuthService;
import com.spe.peerpulse.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegister_Success() {
        // given
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("John");
        registerRequest.setLastname("Doe");
        registerRequest.setEmail("johndoe@example.com");
        registerRequest.setPassword("password");
        registerRequest.setRole("STUDENT");
        registerRequest.setUsername("johndoe");
        registerRequest.setPhoneNumber("123456789");

        User mockUser = User.builder()
                .id(1L)
                .firstname(registerRequest.getFirstname())
                .lastname(registerRequest.getLastname())
                .email(registerRequest.getEmail())
                .password("encodedPassword")
                .role(Role.valueOf(registerRequest.getRole()))
                .username(registerRequest.getUsername())
                .phoneNumber(registerRequest.getPhoneNumber())
                .build();

        when(userRepository.save(any(User.class))).thenReturn(mockUser);
        when(jwtService.generateToken(any(User.class))).thenReturn("jwtToken");
        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("encodedPassword");

        // when
        AuthResponse authResponse = authService.register(registerRequest);

        // then
        assertNotNull(authResponse);
        assertNotNull(authResponse.getToken());
        assertEquals("jwtToken", authResponse.getToken());
    }

    @Test
    public void testRegister_Failure_DuplicateUser() {
        // given
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("John");
        registerRequest.setLastname("Doe");
        registerRequest.setEmail("johndoe@example.com");
        registerRequest.setPassword("password");
        registerRequest.setRole("STUDENT");
        registerRequest.setUsername("johndoe");
        registerRequest.setPhoneNumber("123456789");

        when(userRepository.save(any(User.class))).thenThrow(new RuntimeException("Duplicate User"));

        // when
        AuthResponse authResponse = authService.register(registerRequest);

        // then
        assertNotNull(authResponse);
        assertEquals("Duplicate User Found!", authResponse.getMessage());
    }
}
