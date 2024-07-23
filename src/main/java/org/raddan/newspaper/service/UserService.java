package org.raddan.newspaper.service;

import lombok.RequiredArgsConstructor;
import org.raddan.newspaper.entity.User;
import org.raddan.newspaper.exception.custom.UnauthorizedException;
import org.raddan.newspaper.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public User create(User user) {
        if (userRepository.existsByUsername(user.getUsername()))
            throw new RuntimeException("Username already exists");
        if (userRepository.existsByEmail(user.getEmail()))
            throw new RuntimeException("Email already exists");

        return save(user);
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    public User getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() == "anonymousUser") {
            throw new UnauthorizedException("You are not authorized to perform this action!");
        }

        String username = authentication.getName();
        return getByUsername(username);
    }

}
