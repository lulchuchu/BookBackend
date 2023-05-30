package com.example.library.Controller;

import com.example.library.Exception.ResourceException;
import com.example.library.Security.jwt.JwtResponse;
import com.example.library.Security.jwt.JwtTokenProvider;
import com.example.library.Service.UserService;
import com.example.library.model.Entity.User;
import com.example.library.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtTokenProvider.generateToken(authentication);
            User userr = userService.findByUsername(user.getUsername());
            return ResponseEntity.ok(new JwtResponse(jwt, userr.getId(), userr.getUsername(), userr.getName(), userr.getRole()));
        }
        catch (Exception e){
            throw new ResourceException("Sai thông tin đăng nhập");
        }
    }

    @PostMapping("/admin/login")
    public ResponseEntity<?> adminLogin(@RequestBody User user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtTokenProvider.generateToken(authentication);
            User userr = userService.findByUsername(user.getUsername());
            if(!userr.getRole().name().equals("ADMIN")){
                throw new ResourceException("User khong co quyen truy cap");
            }
            return ResponseEntity.ok(new JwtResponse(jwt, userr.getId(), userr.getUsername(), userr.getName(), userr.getRole()));
        }
        catch (Exception e){
            throw new ResourceException("Sai thông tin đăng nhập");
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sai thông tin đăng nhập");

        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
            Role role = Role.USER;
            String password = user.getPassword();
            user.setRole(role);
            user.setPassword(passwordEncoder.encode(password));
            userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.OK).body("Created:\n" + user);
    }

    @PostMapping("/admin/register")
    public ResponseEntity<?> adminRegister(@RequestBody User user) {
            Role role = Role.ADMIN;
            String password = user.getPassword();
            user.setRole(role);
            user.setPassword(passwordEncoder.encode(password));
            userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.OK).body("Created:\n" + user);
        }

}
