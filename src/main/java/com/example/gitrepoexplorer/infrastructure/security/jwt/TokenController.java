package com.example.gitrepoexplorer.infrastructure.security.jwt;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @GetMapping("/token")
    public ResponseEntity<JwtTokenResponseDto> getToken(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof OidcUser oidcUser) {
            //this Google token doesn't contain user roles yet - no authorization can be performed
            String token = oidcUser.getIdToken().getTokenValue();
            return ResponseEntity.ok(new JwtTokenResponseDto(token));
        }
        return ResponseEntity.notFound().build();
    }
}