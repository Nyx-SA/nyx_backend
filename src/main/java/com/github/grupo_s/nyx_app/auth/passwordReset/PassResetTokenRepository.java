package com.github.grupo_s.nyx_app.auth.passwordReset;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PassResetTokenRepository extends JpaRepository<PassResetToken, Long> {

    Optional<PassResetToken> findByToken(String token);
}
