package com.example.gitrepoexplorer.infrastructure.mappers;

import com.example.gitrepoexplorer.infrastructure.controller.dto.request.PartiallyUpdateRepositoryRequestDto;
import com.example.gitrepoexplorer.infrastructure.controller.dto.request.UserRepositoryRequestDto;
import com.example.gitrepoexplorer.infrastructure.controller.dto.response.BranchDto;
import com.example.gitrepoexplorer.infrastructure.controller.dto.response.PartiallyUpdateRepositoryResponseDto;
import com.example.gitrepoexplorer.infrastructure.controller.dto.response.UserRepositoryFromDatabaseDto;
import com.example.gitrepoexplorer.infrastructure.domain.model.Repo;
import com.example.gitrepoexplorer.infrastructure.proxy.dto.response.GitHubRepositoryDto;
import com.example.gitrepoexplorer.infrastructure.proxy.dto.response.RepositoryBranchesDto;

import java.util.List;

public interface Mappers {

    static List<BranchDto> mapRepositoryBranchesDtoListToBranchList(List<RepositoryBranchesDto> repositoryBranchesDto){
        return repositoryBranchesDto.stream()
                .map(branchesDto -> new BranchDto(branchesDto.name(), branchesDto.commit().sha()))
                .toList();
    }
    static List<Repo> mapGitHubRepositoryDtoToRepo(List<GitHubRepositoryDto> gitHubGitHubRepositoryDtos) {
        return gitHubGitHubRepositoryDtos.stream()
                .map(gitHubGitHubRepositoryDto -> new Repo(gitHubGitHubRepositoryDto.owner().login(), gitHubGitHubRepositoryDto.name()))
                .toList();
    }
    static List<UserRepositoryFromDatabaseDto> mapListOfRepoToListOfUserRepositoryFromDatabaseDto(List<Repo> repositories){
        return repositories.stream()
                .map(repo -> new UserRepositoryFromDatabaseDto(repo.getId(), repo.getOwner(), repo.getName()))
                .toList();
    }
    static UserRepositoryFromDatabaseDto mapRepoToUserRepositoryFromDatabaseDto(Repo repository){
        return new UserRepositoryFromDatabaseDto(repository.getId(), repository.getOwner(), repository.getName());
    }
    static Repo mapUserRepositoryRequestDtoToRepo(UserRepositoryRequestDto userRepositoryRequestDto){
        return new Repo(userRepositoryRequestDto.owner(), userRepositoryRequestDto.name());
    }
    static Repo mapPartiallyUpdateRepositoryRequestDtoToRepo(PartiallyUpdateRepositoryRequestDto dto) {
        return new Repo(dto.owner(), dto.name());
    }
    static PartiallyUpdateRepositoryResponseDto mapRepoToPartiallyUpdateRepositoryResponseDto(Repo repo){
        return new PartiallyUpdateRepositoryResponseDto(repo);
    }
}
