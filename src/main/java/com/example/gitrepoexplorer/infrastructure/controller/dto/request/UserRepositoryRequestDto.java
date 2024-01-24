package com.example.gitrepoexplorer.infrastructure.controller.dto.request;

import com.example.gitrepoexplorer.domain.crud.dto.BranchDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record UserRepositoryRequestDto(
        @NotNull(message = "owner must not be null")
        @NotEmpty(message = "owner must not be empty")
        String owner,

        @NotNull(message = "name must not be null")
        @NotEmpty(message = "name must not be empty")
        String name,

        @NotNull(message = "branches must not be null")
        @NotEmpty(message = "branches must not be empty (at least one main branch required)")
        Set<BranchDto> branches
) {
}
