package com.vishal.eVerse.controllers;

import com.vishal.eVerse.models.AuthenticationRequest;
import com.vishal.eVerse.models.AuthenticationResponse;
import com.vishal.eVerse.models.RegisterRequest;
import com.vishal.eVerse.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping ("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController{

    private  final AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){
        System.out.println("Request: " + request);
        return  ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        System.out.println("Request: " + request);
        return authenticationService.authenticate(request);
    }

    @PostMapping("/refresh_token")
    public void refreshToken(
          HttpServletRequest request,
          HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request,response);
    }

}
