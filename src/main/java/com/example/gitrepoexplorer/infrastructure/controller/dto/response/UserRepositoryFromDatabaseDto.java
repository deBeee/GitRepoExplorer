package com.example.gitrepoexplorer.infrastructure.controller.dto.response;

public record UserRepositoryFromDatabaseDto(Long id, String owner, String name) {
}
