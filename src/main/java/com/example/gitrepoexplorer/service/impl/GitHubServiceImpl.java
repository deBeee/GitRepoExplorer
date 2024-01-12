package com.example.gitrepoexplorer.service.impl;

import com.example.gitrepoexplorer.infrastructure.controller.dto.response.UserRepositoriesAndBranchesResponseDto;
import com.example.gitrepoexplorer.infrastructure.domain.model.Repo;
import com.example.gitrepoexplorer.infrastructure.domain.service.RepoAdder;
import com.example.gitrepoexplorer.infrastructure.error.exceptions.UserNotFoundException;
import com.example.gitrepoexplorer.infrastructure.proxy.GitHubProxy;
import com.example.gitrepoexplorer.infrastructure.proxy.dto.response.GitHubRepositoryDto;
import com.example.gitrepoexplorer.service.GitHubService;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.gitrepoexplorer.infrastructure.mappers.Mappers.mapRepositoryBranchesDtoListToBranchList;
import static com.example.gitrepoexplorer.infrastructure.mappers.Mappers.mapGitHubRepositoryDtoToRepo;

@Service
@AllArgsConstructor
@Log4j2
public class GitHubServiceImpl implements GitHubService {

    GitHubProxy gitHubClient;

    RepoAdder repoAdder;

    public List<UserRepositoriesAndBranchesResponseDto> fetchUserRepositories(String username){
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

        addReposToDatabase(mapGitHubRepositoryDtoToRepo(userRepositoriesResponseDto));

        return userRepositoriesResponseDto.stream()
                .filter(repository -> !repository.fork())
                .map(repository -> new UserRepositoriesAndBranchesResponseDto(
                        repository.name(),
                        repository.owner().login(),
                        mapRepositoryBranchesDtoListToBranchList(
                                gitHubClient.fetchUserRepositoryBranchesInfo(username, repository.name())
                )))
                .toList();
    }

    private void addReposToDatabase(List<Repo> repositories){
        repoAdder.addAll(repositories);
    }
}
