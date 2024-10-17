package com.vishal.eVerse.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.vishal.eVerse.models.*;
import com.vishal.eVerse.models.token.Token;
import com.vishal.eVerse.models.token.TokenType;
import com.vishal.eVerse.models.user.Role;
import com.vishal.eVerse.models.user.User;
import com.vishal.eVerse.repository.TokenRepository;
import com.vishal.eVerse.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private  final UserRepository userRepository;
    private  final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password( passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        var savedUser = userRepository.save(user);
        var token = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser,token);
        return AuthenticationResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();

    }

    public  ResponseEntity<?> authenticate(AuthenticationRequest request) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (AuthenticationException exception) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        }
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var token = jwtService.generateToken(user);
        return ResponseEntity.ok(AuthenticationResponse.builder()
                .accessToken(token)
                .refreshToken(jwtService.generateRefreshToken(user))
                .build());

    }

    public void saveUserToken(User user, String token){
        var tokenData = Token.builder()
                .token(token)
                .tokenType(TokenType.BEARER)
                .user(user)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(tokenData);
    }

    public void revokeAllUserToken(User user){
        var validUserTokens = tokenRepository.findAllTokensByUserId(user.getId());
        if(validUserTokens.isEmpty()){
            return;
        }
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    )throws IOException {
       final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
       final String refreshToken;
       final String userEmail;
       if (authHeader == null || !authHeader.startsWith("Bearer ")) {
           return;
       }
       refreshToken = authHeader.substring(7);
       userEmail = jwtService.extractUsername(refreshToken);
       if (userEmail != null) {
              var user = userRepository.findByEmail(userEmail).orElseThrow();
           if (jwtService.isTokenValid(refreshToken, user)) {
               revokeAllUserToken(user);
               var token = jwtService.generateToken(user);
               var newRefreshToken = jwtService.generateRefreshToken(user);
               saveUserToken(user, token);
               var authResponse = AuthenticationResponse.builder()
                       .accessToken(token)
                       .refreshToken(newRefreshToken)
                       .build();
               new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
           }
       }
    }
}
