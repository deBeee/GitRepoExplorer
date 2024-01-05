package com.example.gitrepoexplorer.infrastructure.controller.dto.response;

import java.util.List;

public record UserRepositoriesAndBranchesResponseDto(String repositoryName, String ownerLogin, List<BranchDto> branches) {
}
