package com.github.grupo_s.nyx_app.auth;


import com.github.grupo_s.nyx_app.users.Role;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotNull
    @Pattern(regexp = "^(?!\\d+$)[a-zA-Z0-9 ]*$", message = "The username can only contain alphanumeric characters")
    private String username;

    @NotNull
    private String email;

    @NotNull
    private String firstname;
    @NotNull
    private String lastname;

    @NotNull
    @Pattern(regexp = "^.{8,}$",
            message = "The password needs to have at least 8 characters")
    private String password;

    @NotNull
    private Role role;
}
