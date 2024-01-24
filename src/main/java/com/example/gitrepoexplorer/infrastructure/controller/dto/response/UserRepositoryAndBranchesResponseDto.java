package com.example.gitrepoexplorer.infrastructure.controller.dto.response;

import com.example.gitrepoexplorer.domain.crud.dto.BranchDto;

import java.util.Set;

public record UserRepositoryAndBranchesResponseDto(String repositoryName, String ownerLogin, Set<BranchDto> branches) {
}
