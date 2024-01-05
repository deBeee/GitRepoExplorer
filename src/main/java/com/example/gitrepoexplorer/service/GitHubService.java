package com.example.gitrepoexplorer.service;

import com.example.gitrepoexplorer.infrastructure.controller.dto.response.UserRepositoriesAndBranchesResponseDto;

import java.util.List;

public interface GitHubService {
    List<UserRepositoriesAndBranchesResponseDto> fetchUserRepositories(String username);
}
