package com.example.gitrepoexplorer.infrastructure.controller.dto.request;

import com.example.gitrepoexplorer.domain.crud.dto.BranchDto;

import java.util.Set;

public record PartiallyUpdateRepositoryRequestDto(String owner, String name, Set<BranchDto> branches) {
}
