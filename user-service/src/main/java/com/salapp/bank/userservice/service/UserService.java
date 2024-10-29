package com.salapp.bank.userservice.service;

import com.salapp.bank.userservice.exception.DuplicateUserException;
import com.salapp.bank.userservice.exception.UserNotFoundException;
import com.salapp.bank.userservice.model.User;
import com.salapp.bank.userservice.payload.SignUpRequest;
import com.salapp.bank.userservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = findUserById(id).orElseThrow(() -> new UserNotFoundException("User not found " + id));

        userRepository.deleteById(user.getId());
    }

    @Transactional
    public User registerNewUser(SignUpRequest signUpRequest) {
        if (userRepository.findByEmail(signUpRequest.email()).isPresent()) {
            throw new DuplicateUserException("Email address already in use");
        }

        User user = new User(signUpRequest.email(), passwordEncoder.encode(signUpRequest.password()), List.of(new SimpleGrantedAuthority("ROLE_" + signUpRequest.role())));

        return userRepository.save(user);
    }
}
