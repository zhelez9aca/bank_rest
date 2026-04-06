package org.example.security;

import org.example.exception.IdIsInvalidException;
import org.example.exception.IncorrectLoginOrPasswordException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.example.exception.IncorrectRoleException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "app.jwt")

@Component
@Getter
@Setter
public class JWToken {
    private String secret;
    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public String generateJWT(String username){
        Map<String,Object> claims=new HashMap<>();
        return createToken(claims,username);

    }
    public String createToken(Map<String,Object>claims,String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }
    public Long extractUserId(String token) {
        Claims claims = parseClaims(token);
        Object raw = claims.get("userId");
        if (raw instanceof Number number) {
            return number.longValue();
        }
        if (raw instanceof String value && !value.isBlank()) {
            return Long.parseLong(value);
        }
        throw new IdIsInvalidException("Id is invalid");
    }
    public String extractRole(String token){
        var claims = parseClaims(token);
        String role = claims.get("role",String.class);
        if (role!=null&& !role.isBlank()){
            return "ROLE_".concat(role);
        }
        throw new IncorrectRoleException("Role is invalid");
    }
    public String extractLogin(String token){
        Claims claims = parseClaims(token);
        String login = claims.get("login",String.class);

        if (login!=null && !login.isBlank()){
            return login;
        };
        throw new IncorrectLoginOrPasswordException("Login is invalid");
    }
    public boolean isValid(String token){
        try{
            parseClaims(token);
            return true;
        }
        catch (RuntimeException ex){
            return false;
        }

    }

}
