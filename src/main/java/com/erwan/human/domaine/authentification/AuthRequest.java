package com.erwan.human.domaine.authentification;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
