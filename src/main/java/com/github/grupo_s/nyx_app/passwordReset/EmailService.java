package com.github.grupo_s.nyx_app.passwordReset;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendPasswordResetEmail(String to, String token){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Password Reset Request");
        message.setText("Your password reset code is:" + token +
                "\n\nThis code will expire in 30 minutes." +
                "\n\n If you didn't request this, please ignore this email.");
    }
}
