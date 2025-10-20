package com.github.grupo_s.nyx_app.auth;

import com.github.grupo_s.nyx_app.jwt.JwtService;
import com.github.grupo_s.nyx_app.passwordReset.*;
import com.github.grupo_s.nyx_app.users.User;
import com.github.grupo_s.nyx_app.users.UserRepository;
import com.github.grupo_s.nyx_app.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final EmailService emailService;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    public void forgotPassword(ForgotPassRequest request) {
        User user = userService.getUserByEmail(request.getEmail());

        if (user == null) {
            //Por seguridad no devolvemos si el usuario existe o no, simplemente devolves la misma respuesta que si existiera
            return;
        }

        String token = String.format("%06d", new Random().nextInt(999999));
        System.out.println("Token generado: " + token);

        PassResetToken resetToken = PassResetToken.builder()
                .token(token)
                .user(user)
                .expiryDate(LocalDateTime.now().plusMinutes(30))
                .used(false)
                .build();

        tokenRepository.save(resetToken);
        emailService.sendPasswordResetEmail(user.getEmail(),token);
    }

    public void resetPassword(ResetPassRequest request){
        PassResetToken resetToken = tokenRepository.findByToken(request.getToken())
                .orElseThrow(() -> new RuntimeException("Invalid reset token"));

        //Validaciones
        if (resetToken.isUsed()){
            throw new RuntimeException("This reset token has already been used");
        }

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("This reset token has expired");
        }

        //Cambiar contraseña
        User user = resetToken.getUser();
        user.setPassword(request.getNewPassword());
        userService.updateUser(user.getId(),user);

        //Marcar token como usado
        resetToken.setUsed(true);
        tokenRepository.save(resetToken);
    }

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
