package com.devsk.blog.services.impl;

import org.springframework.stereotype.Service;

import com.devsk.blog.domain.entities.User;
import com.devsk.blog.repositories.UserRepository;
import com.devsk.blog.services.UserService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User not found with id "+id));
    }

}
