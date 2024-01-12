package com.example.gitrepoexplorer.infrastructure.proxy.dto.response;

public record GitHubRepositoryDto(String name, OwnerDto owner, Boolean fork) {
}
