package com.example.gitrepoexplorer.infrastructure.proxy;

import com.example.gitrepoexplorer.infrastructure.proxy.dto.response.RepositoryBranchesDto;
import com.example.gitrepoexplorer.infrastructure.proxy.dto.response.GitHubRepositoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "git-hub-client")
public interface GitHubProxy {

    @GetMapping("/users/{username}/repos")
    List<GitHubRepositoryDto> fetchUserRepositioriesInfo(@PathVariable String username);

    @GetMapping("/repos/{username}/{repositoryname}/branches")
    List<RepositoryBranchesDto> fetchUserRepositoryBranchesInfo(
            @PathVariable String username,
            @PathVariable String repositoryname
    );
}
