package org.raddan.newspaper.auth.service;

import lombok.RequiredArgsConstructor;
import org.raddan.newspaper.model.User;
import org.raddan.newspaper.exception.custom.UserAlreadyExistsWithThatEmailException;
import org.raddan.newspaper.exception.custom.UserAlreadyExistsWithThatUsernameException;
import org.raddan.newspaper.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Alexander Dudkin
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User save(User user) {
        return repository.save(user);
    }

    public User create(User user) {
        if (repository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsWithThatUsernameException("Пользователь с таким именем уже существует");
        }

        if (repository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsWithThatEmailException("Пользователь с таким email уже существует");
        }

        return save(user);
    }

    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

}
