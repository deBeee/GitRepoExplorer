package com.example.gitrepoexplorer.infrastructure.security.jwt;

import lombok.Builder;

@Builder
public record JwtReponseDto(String token) {
}
