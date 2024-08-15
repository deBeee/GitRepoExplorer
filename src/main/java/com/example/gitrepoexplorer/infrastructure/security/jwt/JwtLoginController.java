package com.example.gitrepoexplorer.infrastructure.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JwtLoginController {

    private final JwtAuthenticator jwtAuthenticator;

    @PostMapping("/login")
    public ResponseEntity<JwtReponseDto> authenticateAndGenerateToken(LoginRequestDto dto) {
        String token = jwtAuthenticator.authenticateAndGenerateToken(dto.login(), dto.password());
        return ResponseEntity.ok(
                JwtReponseDto.builder()
                .token(token)
                .build());
    }
}
