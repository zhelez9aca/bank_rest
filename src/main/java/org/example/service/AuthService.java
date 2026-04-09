package org.example.service;

import org.example.dto.LoginRequestDTO;
import org.example.dto.RegisterRequestDTO;
import org.example.enums.RoleEnum;
import org.example.enums.UserStatusEnum;
import org.example.exception.BlockedUserException;
import org.example.exception.ConflictException;
import org.example.exception.IncorrectLoginOrPasswordException;
import org.example.model.Users;
import org.example.repository.UserRepository;
import org.example.security.JWToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;

@Service
public class AuthService {
    private final JWToken jwToken;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final boolean cookieSecure;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder encoder,
                       JWToken jwToken,
                       @Value("${app.jwt.cookie-secure:false}") boolean cookieSecure) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwToken = jwToken;
        this.cookieSecure = cookieSecure;
    }

    public ResponseEntity<?> register(RegisterRequestDTO request) {
        if (userRepository.existsByLogin(request.login())) {
            throw new ConflictException("Login already exists");
        }

        String passwordHash = encoder.encode(request.password());
        Users userEntity = new Users();
        userEntity.setPasswordHash(passwordHash);
        userEntity.setLogin(request.login());
        userEntity.setRole(RoleEnum.USER);
        userEntity.setUserStatus(UserStatusEnum.ACTIVE);
        userRepository.save(userEntity);
        return login(new LoginRequestDTO(request.login(), request.password()));
    }

    public ResponseEntity<?> login(LoginRequestDTO request) {
        Users userEntity = userRepository.findByLogin(request.login())
                .orElseThrow(() -> new IncorrectLoginOrPasswordException("No user with this login"));

        if (userEntity.getUserStatus() == UserStatusEnum.BLOCKED) {
            throw new BlockedUserException("User is blocked");
        }

        if (encoder.matches(request.password(), userEntity.getPasswordHash())) {
            Long userId = userEntity.getId();
            HashMap<String, Object> claims = new HashMap<>();
            claims.put("login", request.login());
            claims.put("userId", userId);
            claims.put("role", userEntity.getRole().name());
            var jwt = jwToken.createToken(claims, request.login());
            ResponseCookie cookie = ResponseCookie.from("access_token", jwt)
                    .httpOnly(true)
                    .secure(cookieSecure)
                    .sameSite("Lax")
                    .path("/")
                    .maxAge(Duration.ofHours(10))
                    .build();
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body("Log in");
        }
        throw new IncorrectLoginOrPasswordException("Login or password is incorrect");
    }
}
