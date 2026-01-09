package com.fastreserve.authservice.services;

import com.fastreserve.authservice.dtos.AuthResponse;
import com.fastreserve.authservice.dtos.LoginRequest;
import com.fastreserve.authservice.dtos.RegisterRequest;
import com.fastreserve.authservice.entities.User;
import com.fastreserve.authservice.repositories.UserRepository;
import com.fastreserve.authservice.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request){
        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>()));
        return AuthResponse.builder().token(jwtToken).username(user.getUsername()).email(user.getEmail()).build();
    }

    public AuthResponse login(LoginRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>()));
        return AuthResponse.builder().token(jwtToken).username(user.getUsername()).email(user.getEmail()).build();
    }
}
