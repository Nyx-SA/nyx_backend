package com.github.grupo_s.nyx_app.passwordReset;

import lombok.Data;

@Data
public class ResetPassRequest {
    private String token;
    private String newPassword;
}
