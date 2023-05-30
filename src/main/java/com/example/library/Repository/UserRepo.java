package com.example.library.Repository;

import com.example.library.model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    User findByEmail(String email);
    User findByPhoneNumber(String phoneNumber);
}
