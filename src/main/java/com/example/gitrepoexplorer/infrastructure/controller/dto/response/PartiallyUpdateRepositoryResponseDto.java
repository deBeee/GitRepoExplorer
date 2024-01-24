package com.example.gitrepoexplorer.infrastructure.controller.dto.response;

import com.example.gitrepoexplorer.domain.crud.Repo;

public record PartiallyUpdateRepositoryResponseDto(Repo updateRepo) {
}
