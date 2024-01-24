package com.example.gitrepoexplorer.domain.crud.dto;

import java.util.Set;

public record RepoDto(Long id, String owner, String name, Set<BranchDto> branches) {
}
