package com.salapp.bank.userservice.integration;

import com.salapp.bank.userservice.model.User;
import com.salapp.bank.userservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

//@SpringBootTest
//@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    //@Autowired
    private MockMvc mockMvc;

    //@Autowired
    private UserRepository userRepository;

    //@BeforeEach
    void setup() {
        userRepository.deleteAll();
        User user = new User();
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setEmail("jane.doe@example.com");
        userRepository.save(user);
    }

    //@Test
    void testGetAllUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstname").value("Jane"))
                .andDo(MockMvcResultHandlers.print());
    }

    //@Test
    void testCreateUser() throws Exception {
        String userJson = """
                {
                    "firstname": "Jane",
                    "lastname": "Doe",
                    "email": "jane.doe@example.com",
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname").value("Jane"))
                .andDo(MockMvcResultHandlers.print());
    }

}
