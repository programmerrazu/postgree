package com.razu.service.impl;

import com.razu.entity.Users;
import com.razu.repository.UserRepository;
import com.razu.service.UserService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Iterable<Users> findAllUser() {
        return userRepository.findAll();
    }

    @Override
    public Optional<Users> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Users findUserByUserName(String name) {
        return userRepository.findByFirstName(name);
    }

    @Override
    public Users save(Users user) {
        return userRepository.save(user);
    }

    @Override
    public Users update(Users user) {
        return userRepository.save(user);
    }

    @Override
    public Boolean delete(Users user) {
        userRepository.delete(user);
        return !userRepository.findById(user.getId()).isPresent();
    }
}
