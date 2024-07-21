package com.example.gitrepoexplorer.infrastructure.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.gitrepoexplorer.infrastructure.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.time.*;

@Component
@RequiredArgsConstructor
class JwtAuthenticator {

    private final AuthenticationManager authenticationManager;
    private final JwtConfigurationProperties properties;
    private final Clock clock;


    //by passing UsernamePasswordAuthenticationToken to authenticate method in AuthenticationManager
    //it will automatically choose the default DaoAuthenticationProvider which will execute our
    //customized loadByUsername method along with PasswordEncoder to get user from database
    //and authentacate it or not depending on what loadByUsername method returns.
    //So in Aunthentication object we have what loadByUsername method returns
    public String authenticateAndGenerateToken(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
        return createToken((SecurityUser) authentication.getPrincipal());
    }

    private String createToken(SecurityUser user) {
        String secretKey = properties.secret();
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        Instant now = LocalDateTime.now(clock).toInstant(ZoneOffset.UTC);
        Instant expiresAt = now.plus(Duration.ofMinutes(properties.expirationMinutes()));
        String issuer = properties.issuer();
        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuedAt(now)
                .withExpiresAt(expiresAt)
                .withIssuer(issuer)
                .withClaim("roles", user.getAuthoritiesAsString())
                .sign(algorithm);
    }
}
