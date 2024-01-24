package com.example.gitrepoexplorer.infrastructure.mappers;

import com.example.gitrepoexplorer.domain.crud.Branch;
import com.example.gitrepoexplorer.domain.crud.Repo;
import com.example.gitrepoexplorer.domain.crud.dto.BranchDto;
import com.example.gitrepoexplorer.domain.crud.dto.RepoDto;
import com.example.gitrepoexplorer.infrastructure.controller.dto.request.BranchRequestDto;
import com.example.gitrepoexplorer.infrastructure.controller.dto.request.PartiallyUpdateBranchRequestDto;
import com.example.gitrepoexplorer.infrastructure.controller.dto.request.PartiallyUpdateRepositoryRequestDto;
import com.example.gitrepoexplorer.infrastructure.controller.dto.request.UserRepositoryRequestDto;
import com.example.gitrepoexplorer.infrastructure.controller.dto.response.PartiallyUpdateRepositoryResponseDto;
import com.example.gitrepoexplorer.infrastructure.controller.dto.response.UserRepositoriesDto;
import com.example.gitrepoexplorer.infrastructure.controller.dto.response.UserRepositoryAndBranchesResponseDto;
import com.example.gitrepoexplorer.infrastructure.proxy.dto.response.RepositoryBranchesDto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface Mappers {

    static Set<BranchDto> mapRepositoryBranchesDtoListToBranchList(List<RepositoryBranchesDto> repositoryBranchesDto) {
        return repositoryBranchesDto.stream()
                .map(branchesDto -> new BranchDto(branchesDto.name(), branchesDto.commit().sha()))
                .collect(Collectors.toSet());
    }

    static Set<Branch> mapSetOfBranchDtoToSetOfBranch(Set<BranchDto> branchDtos) {
        if (branchDtos == null) return new HashSet<>();
        return branchDtos.stream()
                .map(dto -> new Branch(dto.name(), dto.lastCommitSha()))
                .collect(Collectors.toSet());
    }

    static Set<BranchDto> mapSetOfBranchToSetOfBranchDto(Set<Branch> branches) {
        return branches.stream()
                .map(branch -> new BranchDto(branch.getName(), branch.getLastCommitSha()))
                .collect(Collectors.toSet());
    }

    static List<Repo> mapListOfUserRepositoriesAndBranchesResponseDtoToListOfRepo(
            List<UserRepositoryAndBranchesResponseDto> userRepositoriesAndBranchesResponseDtos) {
        return userRepositoriesAndBranchesResponseDtos.stream()
                .map(dto -> new Repo(
                                dto.ownerLogin(),
                                dto.repositoryName(),
                                mapSetOfBranchDtoToSetOfBranch(dto.branches())
                        )
                )
                .toList();
    }

    static UserRepositoriesDto mapListOfRepoToUserRepositoriesDto(List<Repo> repositories) {
        List<RepoDto> userRepositories = repositories.stream()
                .map(repo -> new RepoDto(
                        repo.getId(), repo.getOwner(), repo.getName(),
                        mapSetOfBranchToSetOfBranchDto(repo.getBranches())))
                .toList();
        return new UserRepositoriesDto(userRepositories);
    }

    static RepoDto mapRepoToUserRepositoryFromDatabaseDto(Repo repository) {
        return new RepoDto(
                repository.getId(), repository.getOwner(), repository.getName(),
                mapSetOfBranchToSetOfBranchDto(repository.getBranches()));
    }

    static Repo mapUserRepositoryRequestDtoToRepo(UserRepositoryRequestDto userRepositoryRequestDto) {
        return new Repo(userRepositoryRequestDto.owner(), userRepositoryRequestDto.name(),
                mapSetOfBranchDtoToSetOfBranch(userRepositoryRequestDto.branches()));
    }

    static Repo mapPartiallyUpdateRepositoryRequestDtoToRepo(PartiallyUpdateRepositoryRequestDto dto) {
        return new Repo(dto.owner(), dto.name(), mapSetOfBranchDtoToSetOfBranch(dto.branches()));
    }

    static PartiallyUpdateRepositoryResponseDto mapRepoToPartiallyUpdateRepositoryResponseDto(Repo repo) {
        return new PartiallyUpdateRepositoryResponseDto(repo);
    }

    static Branch mapBranchRequestDtoToBranch(BranchRequestDto dto) {
        return new Branch(dto.name(), dto.lastCommitSha());
    }

    static Branch mapPartiallyUpdateBranchRequestDtoToBranch(PartiallyUpdateBranchRequestDto dto){
        return new Branch(dto.name(), dto.lastCommitSha());
    }

    static BranchDto mapBranchToBranchDto(Branch branch){
        return new BranchDto(branch.getName(), branch.getLastCommitSha());
    }
}
