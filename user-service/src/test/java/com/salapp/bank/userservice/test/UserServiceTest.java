package com.salapp.bank.userservice.test;

import com.salapp.bank.userservice.model.User;
import com.salapp.bank.userservice.repository.UserRepository;
import com.salapp.bank.userservice.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    public UserServiceTest() {

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindUserById() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findUserById(1L);

        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get().getId()).isEqualTo(1L);
    }

    @Test
    void testSaveUser() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");

        when(userRepository.save(user)).thenReturn(user);

        User result = userService.saveUser(user);

        Assertions.assertThat(result.getFirstName()).isEqualTo("John");
    }


}
