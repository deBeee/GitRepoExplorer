package com.example.gitrepoexplorer.infrastructure.service.impl;

import com.example.gitrepoexplorer.domain.crud.RepoAdder;
import com.example.gitrepoexplorer.infrastructure.controller.dto.response.UserRepositoriesAndBranchesResponseDto;
import com.example.gitrepoexplorer.infrastructure.controller.dto.response.UserRepositoryAndBranchesResponseDto;
import com.example.gitrepoexplorer.domain.crud.Repo;
import com.example.gitrepoexplorer.infrastructure.error.exceptions.UserNotFoundException;
import com.example.gitrepoexplorer.infrastructure.proxy.GitHubProxy;
import com.example.gitrepoexplorer.infrastructure.proxy.dto.response.GitHubRepositoryDto;
import com.example.gitrepoexplorer.infrastructure.service.GitHubService;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.gitrepoexplorer.infrastructure.mappers.Mappers.mapRepositoryBranchesDtoListToBranchList;
import static com.example.gitrepoexplorer.infrastructure.mappers.Mappers.mapListOfUserRepositoriesAndBranchesResponseDtoToListOfRepo;

@Service
@AllArgsConstructor
@Log4j2
public class GitHubServiceImpl implements GitHubService {

    private final GitHubProxy gitHubClient;
    private final RepoAdder repoAdder;

    public UserRepositoriesAndBranchesResponseDto fetchUserRepositories(String username){
        List<GitHubRepositoryDto> userRepositoriesResponseDto = new ArrayList<>();
        try {
                userRepositoriesResponseDto = gitHubClient.fetchUserRepositioriesInfo(username);
        } catch (FeignException feignException) {
            if (feignException.status() == HttpStatus.NOT_FOUND.value()) {
                throw new UserNotFoundException("Provided user does not exist.");
            } else {
                log.info("feignException caught: " + feignException.getMessage());
            }
        }

        List<UserRepositoryAndBranchesResponseDto> repos = userRepositoriesResponseDto.stream()
                .filter(repository -> !repository.fork())
                .map(repository -> new UserRepositoryAndBranchesResponseDto(
                        repository.name(),
                        repository.owner().login(),
                        mapRepositoryBranchesDtoListToBranchList(
                                gitHubClient.fetchUserRepositoryBranchesInfo(username, repository.name())
                        )))
                .toList();

        addReposToDatabase(mapListOfUserRepositoriesAndBranchesResponseDtoToListOfRepo(repos));

        return new UserRepositoriesAndBranchesResponseDto(repos);
    }

    private void addReposToDatabase(List<Repo> repositories){
        this.repoAdder.addAll(repositories);
    }
}
