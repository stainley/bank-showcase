package com.salapp.bank.userservice.integration.controller;

import com.salapp.bank.userservice.model.User;
import com.salapp.bank.userservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerIntegrationTest {

    private static final PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres:11-alpine")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpassword")
            .withExposedPorts(5432);

    @DynamicPropertySource
    public static void setDatabaseProperties(DynamicPropertyRegistry registry) {
        database.start();
        registry.add("spring.datasource.url", () -> "jdbc:postgresql://" + database.getHost() + ":" + database.getFirstMappedPort() + "/testdb");
        registry.add("spring.datasource.username", database::getUsername);
        registry.add("spring.datasource.password", database::getPassword);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
        User user = new User();
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setEmail("jane.doe@example.com");
        userRepository.save(user);
    }

    @WithMockUser(username = "testuser")
    @Test
    void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("Jane"));
    }


    @WithMockUser(username = "testuser", roles = {"ADMIN"})
    @Test
    void testDeleteUser_NotFoundUser() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/1")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        mockMvc.perform(get("/users/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }



}
