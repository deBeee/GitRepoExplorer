package com.example.gitrepoexplorer.service.impl;

import com.example.gitrepoexplorer.infrastructure.controller.dto.response.UserRepositoriesAndBranchesResponseDto;
import com.example.gitrepoexplorer.infrastructure.error.exceptions.UserNotFoundException;
import com.example.gitrepoexplorer.infrastructure.mappers.Mappers;
import com.example.gitrepoexplorer.infrastructure.proxy.GitHubProxy;
import com.example.gitrepoexplorer.infrastructure.proxy.dto.response.RepositoryDto;
import com.example.gitrepoexplorer.service.GitHubService;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class GitHubServiceImpl implements GitHubService {

    GitHubProxy gitHubClient;

    public List<UserRepositoriesAndBranchesResponseDto> fetchUserRepositories(String username){
        List<RepositoryDto> userRepositoriesResponseDto = new ArrayList<>();
        try {
                userRepositoriesResponseDto = gitHubClient.fetchUserRepositioriesInfo(username);
        } catch (FeignException feignException) {
            if (feignException.status() == HttpStatus.NOT_FOUND.value()) {
                throw new UserNotFoundException("Provided user does not exist.");
            } else {
                log.info("feignException caught: " + feignException.getMessage());
            }
        }

        return userRepositoriesResponseDto.stream()
                .filter(repository -> !repository.fork())
                .map(repository -> new UserRepositoriesAndBranchesResponseDto(
                        repository.name(),
                        repository.owner().login(),
                        Mappers.mapRepositoryBranchesDtoListToBranchList(
                                gitHubClient.fetchUserRepositoryBranchesInfo(username, repository.name())
                )))
                .toList();
    }
}
