package com.erwan.human.config;

import com.erwan.human.domaine.Account;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {
    private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; // 24 hour


    public String generateAccessToken(Account user) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("subject", user.getUsername());
        claims.put("role", user.getRole());

        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuer("Kamino")
                .setIssuedAt(new Date())
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .compact();
    }
}
