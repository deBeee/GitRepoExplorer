package com.example.gitrepoexplorer.infrastructure.mappers;

import com.example.gitrepoexplorer.infrastructure.controller.dto.response.BranchDto;
import com.example.gitrepoexplorer.infrastructure.proxy.dto.response.RepositoryBranchesDto;

import java.util.List;

public interface Mappers {

    static List<BranchDto> mapRepositoryBranchesDtoListToBranchList(List<RepositoryBranchesDto> repositoryBranchesDto){
        return repositoryBranchesDto.stream()
                .map(branchesDto -> new BranchDto(branchesDto.name(), branchesDto.commit().sha()))
                .toList();
    }
}
