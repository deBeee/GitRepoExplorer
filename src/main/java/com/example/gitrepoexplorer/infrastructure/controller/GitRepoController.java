package com.example.gitrepoexplorer.infrastructure.controller;

import com.example.gitrepoexplorer.infrastructure.controller.dto.response.UserRepositoriesAndBranchesResponseDto;
import com.example.gitrepoexplorer.infrastructure.error.exceptions.UnsupportedMediaTypeException;
import com.example.gitrepoexplorer.service.GitHubService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Log4j2
@AllArgsConstructor
public class GitRepoController {

    private final GitHubService gitHubService;

    @GetMapping(value = "/{username}"/*, produces = MediaType.APPLICATION_JSON_VALUE*/)
    public ResponseEntity<List<UserRepositoriesAndBranchesResponseDto>> fetchGitInfo(@PathVariable String username,
                                                                                     @RequestHeader(value = "Accept", required = false) String accept) {
        if ("application/xml".equals(accept)) {
            throw new UnsupportedMediaTypeException("xml media type is not supported");
        }
        List<UserRepositoriesAndBranchesResponseDto> response = gitHubService.fetchUserRepositories(username);
        return ResponseEntity.ok(response);
    }

}
