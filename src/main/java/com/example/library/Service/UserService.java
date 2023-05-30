package com.example.library.Service;

import com.example.library.model.Entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDetails loadUserByUsername(String username);

    User findByUsername(String username);

//    Boolean checkDuplicateEmail(String email);
//
//    Boolean checkDuplicateUserName(String username);

    void saveUser(User user);
}
