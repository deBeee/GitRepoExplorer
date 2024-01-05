package com.example.gitrepoexplorer.infrastructure.proxy.dto.response;

public record RepositoryDto(String name, OwnerDto owner, Boolean fork) {
}
