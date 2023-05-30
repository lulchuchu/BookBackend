package com.example.library.Service.impl;

import com.example.library.Exception.AlreadyExistException;
import com.example.library.Exception.NotFoundException;
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
            throw new NotFoundException("User not found");
        }
        return new UserDetail(user);
    }

    @Override
    public User findByUsername(String username) {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        return user;
    }

    @Override
    public void saveUser(User user) {
        User u = userRepo.findByUsername(user.getUsername());
        User e = userRepo.findByEmail(user.getEmail());
        User p = userRepo.findByPhoneNumber(user.getPhoneNumber());
        if(u != null) {
            throw new AlreadyExistException("Username already exist");
        }
        if(e != null) {
            throw new AlreadyExistException("Email already exist");
        }
        if(p != null) {
            throw new AlreadyExistException("Phone number already exist");
        }
        userRepo.save(user);
    }
}
