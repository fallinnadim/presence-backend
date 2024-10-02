package com.fallinnadim.presence_app.service.impl;

import com.fallinnadim.presence_app.entity.Users;
import com.fallinnadim.presence_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        Users user = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("Email tidak terdaftar")
        );
        return mapToUserDetails(user);
    }

    private UserDetails mapToUserDetails(Users user) {
        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }
}

