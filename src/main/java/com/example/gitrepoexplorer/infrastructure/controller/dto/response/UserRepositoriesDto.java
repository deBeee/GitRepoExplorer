package com.example.gitrepoexplorer.infrastructure.controller.dto.response;

import com.example.gitrepoexplorer.domain.crud.dto.RepoDto;

import java.util.List;

public record UserRepositoriesDto(List<RepoDto> userRepositories) {
}
