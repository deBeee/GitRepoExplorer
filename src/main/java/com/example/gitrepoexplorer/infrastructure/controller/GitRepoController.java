package com.example.gitrepoexplorer.infrastructure.controller;

import com.example.gitrepoexplorer.infrastructure.controller.dto.request.PartiallyUpdateRepositoryRequestDto;
import com.example.gitrepoexplorer.infrastructure.controller.dto.request.UserRepositoryRequestDto;
import com.example.gitrepoexplorer.infrastructure.controller.dto.response.PartiallyUpdateRepositoryResponseDto;
import com.example.gitrepoexplorer.infrastructure.controller.dto.response.UserRepositoriesAndBranchesResponseDto;
import com.example.gitrepoexplorer.infrastructure.controller.dto.response.UserRepositoryFromDatabaseDto;
import com.example.gitrepoexplorer.infrastructure.domain.model.Repo;
import com.example.gitrepoexplorer.infrastructure.domain.service.RepoAdder;
import com.example.gitrepoexplorer.infrastructure.domain.service.RepoDeleter;
import com.example.gitrepoexplorer.infrastructure.domain.service.RepoRetriever;
import com.example.gitrepoexplorer.infrastructure.domain.service.RepoUpdater;
import com.example.gitrepoexplorer.infrastructure.error.exceptions.UnsupportedMediaTypeException;
import com.example.gitrepoexplorer.service.GitHubService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.gitrepoexplorer.infrastructure.mappers.Mappers.*;

@RestController
@Log4j2
@AllArgsConstructor
@RequestMapping("/repos")
public class GitRepoController {

    private final GitHubService gitHubService;
    private final RepoRetriever repoRetriever;
    private final RepoAdder repoAdder;
    private final RepoDeleter repoDeleter;
    private final RepoUpdater repoUpdater;

    @GetMapping(path = "/{username}")
    public ResponseEntity<List<UserRepositoriesAndBranchesResponseDto>> fetchGitInfo(@PathVariable String username,
                                                                                     @RequestHeader(value = "Accept", required = false) String accept) {
        if ("application/xml".equals(accept)) {
            throw new UnsupportedMediaTypeException("xml media type is not supported");
        }
        List<UserRepositoriesAndBranchesResponseDto> response = this.gitHubService.fetchUserRepositories(username);
        return ResponseEntity.ok(response);
    }
    @GetMapping(path = "/database")
    public ResponseEntity<List<UserRepositoryFromDatabaseDto>> getUserRepositoriesFromDatabase(
            @PageableDefault(size = Integer.MAX_VALUE) Pageable pageable) {
        List<Repo> repositories = this.repoRetriever.findAll(pageable);
        return ResponseEntity.ok(mapListOfRepoToListOfUserRepositoryFromDatabaseDto(repositories));
    }
    @GetMapping(path = "/database/{id}")
    public ResponseEntity<UserRepositoryFromDatabaseDto> getUserRepositoriesFromDatabaseById(@PathVariable Long id){
        Repo userRepository = this.repoRetriever.findById(id);
        return ResponseEntity.ok(mapRepoToUserRepositoryFromDatabaseDto(userRepository));
    }
    @PostMapping(path = "/database")
    public ResponseEntity<UserRepositoryFromDatabaseDto> postUserRepositoryToDatabase(
            @RequestBody @Valid UserRepositoryRequestDto requestDto
    ) {
        Repo addedUserRepository = this.repoAdder.add(mapUserRepositoryRequestDtoToRepo(requestDto));
        return ResponseEntity.ok(mapRepoToUserRepositoryFromDatabaseDto(addedUserRepository));
    }
    @DeleteMapping(path = "/database")
    public ResponseEntity<Void> deleteDatabase(){
        this.repoDeleter.deleteAll();
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping(path = "/database/{id}")
    public ResponseEntity<Void> deleteRepositoryById(@PathVariable Long id){
        this.repoDeleter.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping(path = "/database/{id}")
    public ResponseEntity<Void> updateRepositoryById(
            @PathVariable Long id, @RequestBody @Valid UserRepositoryRequestDto requestDto
    ) {
        repoUpdater.updateById(id, mapUserRepositoryRequestDtoToRepo(requestDto));
        return ResponseEntity.ok().build();
    }
    @PatchMapping(path = "/database/{id}")
    public ResponseEntity<PartiallyUpdateRepositoryResponseDto> partiallyUpdateRepositoryById(
            @PathVariable Long id, @RequestBody PartiallyUpdateRepositoryRequestDto requestDto
    ) {
        Repo updatedRep = repoUpdater.partiallyUpdateById
                (id, mapPartiallyUpdateRepositoryRequestDtoToRepo(requestDto));
        return ResponseEntity.ok(mapRepoToPartiallyUpdateRepositoryResponseDto(updatedRep));
    }
}
