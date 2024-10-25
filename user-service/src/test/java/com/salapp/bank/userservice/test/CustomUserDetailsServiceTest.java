package com.salapp.bank.userservice.test;

import com.salapp.bank.userservice.exception.UserNotFoundException;
import com.salapp.bank.userservice.model.User;
import com.salapp.bank.userservice.repository.UserRepository;
import com.salapp.bank.userservice.security.CustomUserDetailsService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService service;

    @Test
    void testLoadUserByUsername_Found() throws UsernameNotFoundException {
        // Given
        User user = new User("test@example.com", "password", null);
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        // When
        UserDetails loadUser = service.loadUserByUsername("test@example.com");

        //Then
        Assertions.assertThat(loadUser).isNotNull();
        Assertions.assertThat(loadUser.getUsername()).isEqualTo("test@example.com");
    }

    @Test
    void testLoadUserByUsername_NotFound() {
        // Given
        when(userRepository.findByEmail("unknown@example.com")).thenReturn(java.util.Optional.empty());

        // When and Then
        assertThatThrownBy(() -> service.loadUserByUsername("unknown@example.com"))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User not found: unknown@example.com");
    }

    @Test
    void testLoadUserByUsername_NullUsername() {
        // When and Then
        assertThatThrownBy(() -> service.loadUserByUsername(null))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void testLoadUserByUsername_EmptyUsername() {
        // When and Then
        assertThatThrownBy(() -> service.loadUserByUsername(""))
                .isInstanceOf(UserNotFoundException.class);
    }

}