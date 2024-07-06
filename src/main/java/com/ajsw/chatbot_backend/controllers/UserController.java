package com.ajsw.chatbot_backend.controllers;

import com.ajsw.chatbot_backend.config.security.JwtService;
import com.ajsw.chatbot_backend.dto.AuthenticationResponse;
import com.ajsw.chatbot_backend.models.User;
import com.ajsw.chatbot_backend.services.UserService;
import com.ajsw.chatbot_backend.dto.LoginRequest;
import com.ajsw.chatbot_backend.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest registerRequest) {
        User user = userService.register(registerRequest);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            final org.springframework.security.core.userdetails.User userDetails =
                    (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
            final String jwt = jwtService.generateToken(userDetails);

            User userFromDb = userService.findByUsername(loginRequest.getUsername());
            return ResponseEntity.ok(new AuthenticationResponse(userFromDb, jwt));
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            return ResponseEntity.status(401).body("Invalid username or password");
        } catch (DisabledException e) {
            return ResponseEntity.status(401).body("User account is disabled");
        } catch (Exception e) {
            // for all other exceptions
            e.printStackTrace();
            return ResponseEntity.status(401).body("Authentication failed due to an unexpected error");
        }
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/deleteall")
    public ResponseEntity<?> deleteAll() {
        userService.deleteAll();
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(user));
    }
}