package com.example.gitrepoexplorer.infrastructure.controller.dto.response;

import com.example.gitrepoexplorer.infrastructure.domain.model.Repo;

public record PartiallyUpdateRepositoryResponseDto(Repo updateRepo) {
}
