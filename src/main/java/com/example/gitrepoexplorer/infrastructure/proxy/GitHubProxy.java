package com.example.gitrepoexplorer.infrastructure.proxy;

import com.example.gitrepoexplorer.infrastructure.proxy.dto.response.RepositoryBranchesDto;
import com.example.gitrepoexplorer.infrastructure.proxy.dto.response.RepositoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "git-hub-client")
public interface GitHubProxy {

    //GET https://api.github.com/users/USERNAME/repos
    @GetMapping("/users/{username}/repos")
    List<RepositoryDto> fetchUserRepositioriesInfo(@PathVariable String username);


    //GET https://api.github.com/repos/kalqa/03-open-feign/branches
    @GetMapping("/repos/{username}/{repositoryname}/branches")
    List<RepositoryBranchesDto> fetchUserRepositoryBranchesInfo(
            @PathVariable String username,
            @PathVariable String repositoryname
    );
}
