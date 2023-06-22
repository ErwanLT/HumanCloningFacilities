package com.erwan.human.controller;

import com.erwan.human.config.JwtTokenUtil;
import com.erwan.human.domaine.Account;
import com.erwan.human.domaine.authentification.AuthRequest;
import com.erwan.human.domaine.authentification.AuthResponse;
import com.erwan.human.services.AuthentificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthentificationController {

    private final JwtTokenUtil jwtUtil;
    private final AuthentificationService authentificationService;

    public AuthentificationController(JwtTokenUtil jwtUtil, AuthentificationService authentificationService) {
        this.jwtUtil = jwtUtil;
        this.authentificationService = authentificationService;
    }


    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            Account user = authentificationService.authenticate(request);
            String accessToken = jwtUtil.generateAccessToken(user);
            AuthResponse response = new AuthResponse(user.getUsername(), accessToken);

            return ResponseEntity.ok().body(response);

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
