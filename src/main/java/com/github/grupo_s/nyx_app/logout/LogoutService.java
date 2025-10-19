package com.github.grupo_s.nyx_app.logout;

import com.github.grupo_s.nyx_app.jwt.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class LogoutService {

    private final TokenBlacklistRepository tokenBlacklistRepository;
    private final JwtService jwtService;

    public void logout(String token) {
        Date expiration = jwtService.getExpiration(token);

        TokenBlacklist blacklistedToken = TokenBlacklist.builder()
                .token(token)
                .blacklistedAt(LocalDateTime.now())
                .expiresAt(expiration.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime())
                .build();

        tokenBlacklistRepository.save(blacklistedToken);
    }

    public boolean isTokenBlacklisted(String token) {
        return tokenBlacklistRepository.existsByToken(token);
    }

    // Limpiar tokens expirados periódicamente
    @Scheduled(cron = "0 0 2 * * ?") // Todos los días a las 2 AM
    @Transactional
    public void cleanExpiredTokens() {
        tokenBlacklistRepository.deleteExpiredTokens(LocalDateTime.now());
    }
}
