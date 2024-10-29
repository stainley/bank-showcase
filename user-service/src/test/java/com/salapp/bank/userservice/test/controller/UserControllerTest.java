package com.salapp.bank.userservice.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salapp.bank.userservice.config.SecurityConfig;
import com.salapp.bank.userservice.controller.UserController;
import com.salapp.bank.userservice.exception.CustomAccessDeniedHandler;
import com.salapp.bank.userservice.exception.UserNotFoundException;
import com.salapp.bank.userservice.model.User;
import com.salapp.bank.userservice.security.CustomUserDetailsService;
import com.salapp.bank.userservice.security.JwtAuthenticationFilter;
import com.salapp.bank.userservice.security.JwtTokenProvider;
import com.salapp.bank.userservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
@ComponentScan(basePackages = {"com.salapp.bank.userservice.exception"})
@ContextConfiguration(classes = {UserControllerTest.class, UserController.class})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired
    private UserController userController;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SecurityConfig securityConfig;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    public void setup() throws Exception {
        AutoCloseable autoCloseable = MockitoAnnotations.openMocks(this);

        // Set up mock behavior for the jwtTokenProvider and customUserDetailsService
        User user = new User(999L, "user@example.com", "password", List.of(new SimpleGrantedAuthority("ROLE_USER")));
        when(customUserDetailsService.loadUserByUsername(user.getUsername())).thenReturn(user);

        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();

        // Set JWT secret manually if it’s not loaded from properties in the test environment
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtSecret", "Z2NlNjM5MGQ3MjE5NDI3YTUyMzIxNTI2Y2Y0NWU2NzI");

        // Set expiration times if needed
        ReflectionTestUtils.setField(jwtTokenProvider, "accessExpiration", 600000); // 10 minutes

        autoCloseable.close();
    }

    @Test
    @WithMockUser(roles = "USER")
    void testGetAllUsers() throws Exception {
        // Arrange
        User user = new User(1L, "john.doe@example.com", "password", Collections.emptyList());
        when(userService.findAllUsers()).thenReturn(List.of(user));

        // Act & Assert
        mockMvc.perform(get("/users").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("john.doe@example.com"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void testGetUserById_UserExists() throws Exception {
        // Arrange
        User user = new User(1L, "john.doe@example.com", "password", Collections.emptyList());
        when(userService.findUserById(1L)).thenReturn(Optional.of(user));

        // Act & Assert
        mockMvc.perform(get("/users/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void testGetUserById_UserNotFound() throws Exception {
        // Arrange: simulate a user ID that does not exist in the database.
        when(userService.findUserById(anyLong())).thenThrow(new UserNotFoundException("User not found"));

        // Act & Assert: Verify the response for a non-existent user ID.
        mockMvc.perform(get("/users/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateUser() throws Exception {
        // Arrange
        User user = new User(1L, "john.doe@example.com", "password", Collections.emptyList());
        when(userService.saveUser(any(User.class))).thenReturn(user);

        // Act & Assert
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"john.doe@example.com\",\"password\":\"password\"}")
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteUser() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/users/1").with(csrf()))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteUser(1L);
    }

    @Test
    void testDeleteUser_UserNotFound() throws Exception {
        // Arrange: Set up a valid JWT token for the user with ROLE_USER.
        User user = new User(999L, "user@example.com", "password", List.of(new SimpleGrantedAuthority("ROLE_USER")));
        String jwtToken = "new-access-token";

        // Mock JwtTokenProvider behavior
        when(jwtTokenProvider.generateAccessToken(any())).thenReturn(jwtToken);
        when(jwtTokenProvider.validateToken(jwtToken)).thenReturn(true);
        when(jwtTokenProvider.getUsernameFromJwtToken(jwtToken)).thenReturn(user.getUsername());

        // Set up the SecurityContext with the JWT for authorization
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities())
        );

        // Arrange: simulate a user ID that does not exist in the database.
        doThrow(new UserNotFoundException("User not found"))
                .when(userService)
                .deleteUser(999L);

        // Act & Assert: Verify the response for a non-existent user ID.
        mockMvc.perform(delete("/users/999")
                        .header("Authorization", "Bearer " + jwtToken)
                        .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void testTestUserService() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/users/test").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("User Service tested"));
    }


    //@Test
    void testTestAdminService_Unauthorized() throws Exception {
        // Arrange: Set up a valid JWT token for the user with ROLE_USER.
        User user = new User(999L, "user@example.com", "password", List.of(new SimpleGrantedAuthority("ROLE_USER")));
        String jwtToken = "new-access-token";

        // Mock JwtTokenProvider behavior
        when(jwtTokenProvider.generateAccessToken(user)).thenReturn(jwtToken);
        when(jwtTokenProvider.validateToken(jwtToken)).thenReturn(true);
        when(jwtTokenProvider.getUsernameFromJwtToken(jwtToken)).thenReturn(user.getUsername());


        // Set up the SecurityContext with the JWT for authorization
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities())
        );

        mockMvc.perform(get("/users/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isForbidden());

        // Verify that the access denied handler was called
        verify(customAccessDeniedHandler, times(1)).handle(any(), any(), any());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateUser_InvalidData() throws Exception {
        // Simulate invalid input for user creation (for example, missing email and password).
        String invalidUserJson = """
                {
                    "email": "",
                    "password": ""
                }
                """;

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidUserJson)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testTestAdminService_Authorized() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/users/admin").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Admin Service tested"));
    }


}