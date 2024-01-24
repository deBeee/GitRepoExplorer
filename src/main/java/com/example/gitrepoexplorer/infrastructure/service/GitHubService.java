package com.example.gitrepoexplorer.infrastructure.service;

import com.example.gitrepoexplorer.infrastructure.controller.dto.response.UserRepositoriesAndBranchesResponseDto;

public interface GitHubService {
    UserRepositoriesAndBranchesResponseDto fetchUserRepositories(String username);
}
