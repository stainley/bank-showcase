package com.salapp.bank.userservice.security;

import com.salapp.bank.userservice.exception.UserNotFoundException;
import com.salapp.bank.userservice.model.User;
import com.salapp.bank.userservice.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByEmail(username)
                .map(User::create)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + username));
    }
}
