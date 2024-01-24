package com.example.gitrepoexplorer.infrastructure.controller.dto.response;

import java.util.List;

public record UserRepositoriesAndBranchesResponseDto(
        List<UserRepositoryAndBranchesResponseDto> userRepositoriesAndBranches
) {
}
