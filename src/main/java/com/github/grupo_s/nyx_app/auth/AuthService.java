package com.github.grupo_s.nyx_app.auth;

import com.github.grupo_s.nyx_app.jwt.JwtService;
import com.github.grupo_s.nyx_app.users.User;
import com.github.grupo_s.nyx_app.users.UserRepository;
import com.github.grupo_s.nyx_app.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    public AuthResponse login (LoginRequest request){
        authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user=userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token= jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse register (RegisterRequest request){
        User user = User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .email(request.getEmail())
                .role(request.getRole())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .createdAt(LocalDateTime.now())
                .build();

        User savedUser = userService.saveUser(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(savedUser))
                .build();
    }

}
