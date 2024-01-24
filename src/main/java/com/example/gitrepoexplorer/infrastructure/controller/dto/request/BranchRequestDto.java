package com.example.gitrepoexplorer.infrastructure.controller.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record BranchRequestDto(
        @NotNull(message = "name must not be null")
        @NotEmpty(message = "name must not be empty")
        String name,

        @NotNull(message = "last commit sha must not be null")
        @NotEmpty(message = "last commit sha must not be empty")
        @Pattern(regexp = "^[0-9a-fA-F]{40}$", message = "Invalid SHA-1 format")
        String lastCommitSha
) {
}
