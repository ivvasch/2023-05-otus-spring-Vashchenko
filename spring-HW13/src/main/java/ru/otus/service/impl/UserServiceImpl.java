package ru.otus.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.model.User;
import ru.otus.repository.UserRepository;
import ru.otus.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> users = repository.findAll();
        Optional<User> first = users.stream().filter(user -> user.getName().equals(username)).findFirst();
        if (first.isEmpty()) {
            throw new UsernameNotFoundException("Unknown user: " + username);
        }
        return org.springframework.security.core.userdetails.User.builder()
                .username(username)
                .password(first.get().getPassword())
                .roles(first.get().getRole())
                .build();
    }
}
