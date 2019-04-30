package com.razu.repository;

import com.razu.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {

    Users findByFirstName(String firstName);
}
