package com.github.grupo_s.nyx_app.users;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String username;

    private String email;

    @NotNull
    @Pattern(regexp = "^.{8,}$", message = "The password needs to have at least 8 characters")
    private String password;

    @NotNull
    private LocalDateTime createdAt = LocalDateTime.now();
}
