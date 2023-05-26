package com.example.library.Service.impl;

import com.example.library.Repository.UserRepo;
import com.example.library.Security.UserDetail;
import com.example.library.Service.UserService;
import com.example.library.model.Entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return new UserDetail(user);
    }

    @Override
    public User findByUsername(String username) {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return user;
    }

    @Override
    public Boolean checkDuplicateUserName(String username) {
        User user = userRepo.findByUsername(username);
        return user != null;
    }

    @Override
    public void saveUser(User user) {
        userRepo.save(user);
    }

    @Override
    public Boolean checkDuplicateEmail(String email) {
        User user = userRepo.findByEmail(email);
        return user != null;
    }
}
