package com.example.gitrepoexplorer.infrastructure.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class JwtAuthenticator {

    private final AuthenticationManager authenticationManager;

    public String authenticateAndGenerateToken(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
        return "token123";
    }
}
