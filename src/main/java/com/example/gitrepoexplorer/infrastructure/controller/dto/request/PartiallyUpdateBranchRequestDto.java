package com.example.gitrepoexplorer.infrastructure.controller.dto.request;

import jakarta.validation.constraints.Pattern;

public record PartiallyUpdateBranchRequestDto(
        String name,
        @Pattern(regexp = "^[0-9a-fA-F]{40}$", message = "Invalid SHA-1 format")
        String lastCommitSha
) {
}
