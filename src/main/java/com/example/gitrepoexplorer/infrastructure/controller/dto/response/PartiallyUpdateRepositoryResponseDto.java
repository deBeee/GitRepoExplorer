package com.example.gitrepoexplorer.infrastructure.controller.dto.response;

import com.example.gitrepoexplorer.domain.crud.dto.RepoDto;

public record PartiallyUpdateRepositoryResponseDto(RepoDto updateRepo) {
}
