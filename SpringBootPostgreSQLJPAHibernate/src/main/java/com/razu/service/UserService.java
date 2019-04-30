package com.razu.service;

import com.razu.entity.Users;
import java.util.Optional;

public interface UserService {

    Iterable<Users> findAllUser();

    Optional<Users> findUserById(Long id);

    Users findUserByUserName(String name);

    Users save(Users user);

    Users update(Users user);

    Boolean delete(Users user);
}
