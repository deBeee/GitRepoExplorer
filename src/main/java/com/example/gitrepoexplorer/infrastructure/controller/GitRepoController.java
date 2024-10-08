package com.example.gitrepoexplorer.infrastructure.controller;

import com.example.gitrepoexplorer.domain.crud.CrudFacade;
import com.example.gitrepoexplorer.domain.crud.dto.BranchDto;
import com.example.gitrepoexplorer.domain.crud.dto.RepoDto;
import com.example.gitrepoexplorer.infrastructure.controller.dto.request.BranchRequestDto;
import com.example.gitrepoexplorer.infrastructure.controller.dto.request.PartiallyUpdateBranchRequestDto;
import com.example.gitrepoexplorer.infrastructure.controller.dto.request.PartiallyUpdateRepositoryRequestDto;
import com.example.gitrepoexplorer.infrastructure.controller.dto.request.UserRepositoryRequestDto;
import com.example.gitrepoexplorer.infrastructure.controller.dto.response.PartiallyUpdateRepositoryResponseDto;
import com.example.gitrepoexplorer.infrastructure.controller.dto.response.UserBranchesResponseDto;
import com.example.gitrepoexplorer.infrastructure.controller.dto.response.UserRepositoriesAndBranchesResponseDto;
import com.example.gitrepoexplorer.infrastructure.controller.dto.response.UserRepositoriesDto;
import com.example.gitrepoexplorer.infrastructure.error.exceptions.UnsupportedMediaTypeException;
import com.example.gitrepoexplorer.infrastructure.service.GitHubService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@AllArgsConstructor
public class GitRepoController {

    private final CrudFacade crudFacade;
    private final GitHubService gitHubService;

    @GetMapping(path = "github/repos/{username}")
    public ResponseEntity<UserRepositoriesAndBranchesResponseDto> fetchGitInfoAndSaveItToDatabaseAndReturnIt(
            @PathVariable String username, @RequestHeader(value = "Accept", required = false) String accept) {
        if ("application/xml".equals(accept)) {
            throw new UnsupportedMediaTypeException("xml media type is not supported");
        }
        UserRepositoriesAndBranchesResponseDto response = this.gitHubService.fetchUserRepositories(username);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "database/repos")
    public ResponseEntity<UserRepositoriesDto> getUserRepositories(
            @PageableDefault(size = Integer.MAX_VALUE) Pageable pageable) {
        UserRepositoriesDto repositories = this.crudFacade.findAllRepos(pageable);
        return ResponseEntity.ok(repositories);
    }

    @GetMapping(path = "database/repo/{id}")
    public ResponseEntity<RepoDto> getUserRepositoriesById(@PathVariable Long id) {
        RepoDto userRepository = this.crudFacade.findRepoById(id);
        return ResponseEntity.ok(userRepository);
    }

    @PostMapping(path = "database/repo")
    public ResponseEntity<RepoDto> postUserRepository(
            @RequestBody @Valid UserRepositoryRequestDto requestDto
    ) {
        RepoDto addedUserRepository = this.crudFacade.addRepo(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(addedUserRepository);
    }

    @DeleteMapping(path = "database/repos")
    public ResponseEntity<Void> deleteAllRepositories() {
        this.crudFacade.deleteAllRepos();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "database/repo/{id}")
    public ResponseEntity<Void> deleteRepositoryById(@PathVariable Long id) {
        this.crudFacade.deleteRepoById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "database/repo/{id}")
    public ResponseEntity<RepoDto> updateRepositoryById(
            @PathVariable Long id, @RequestBody @Valid UserRepositoryRequestDto requestDto
    ) {
        RepoDto updatedRepo = this.crudFacade.updateRepoById(id, requestDto);
        return ResponseEntity.ok(updatedRepo);
    }

    @PatchMapping(path = "database/repo/{id}")
    public ResponseEntity<PartiallyUpdateRepositoryResponseDto> partiallyUpdateRepositoryById(
            @PathVariable Long id, @RequestBody PartiallyUpdateRepositoryRequestDto requestDto
    ) {
        PartiallyUpdateRepositoryResponseDto updatedRepo = this.crudFacade.partiallyUpdateRepoById
                (id, requestDto);
        return ResponseEntity.ok(updatedRepo);
    }

    @GetMapping(path = "database/repo/{id}/branches")
    public ResponseEntity<UserBranchesResponseDto> getBranchesByRepoId(@PathVariable Long id) {
        UserBranchesResponseDto branchesByRepoId = this.crudFacade.getBranchesByRepoId(id);
        return ResponseEntity.ok(branchesByRepoId);
    }

    @GetMapping(path = "database/branch/{id}")
    public ResponseEntity<BranchDto> getBranchById(@PathVariable Long id) {
        BranchDto branchFromDatabase = this.crudFacade.getBranchById(id);
        return ResponseEntity.ok(branchFromDatabase);
    }


    @PostMapping(path = "database/repo/{id}/branch")
    public ResponseEntity<UserBranchesResponseDto> addBranchByRepoId(
            @PathVariable Long id, @RequestBody @Valid BranchRequestDto requestDto) {
        UserBranchesResponseDto branches = this.crudFacade.addBranchToRepoById(id,requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(branches);
    }

    @DeleteMapping(path = "database/branch/{id}")
    public ResponseEntity<Void> deleteBranchByBranchId(@PathVariable Long id){
        this.crudFacade.deleteBranchByBranchId(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(path = "database/branch/{id}")
    public ResponseEntity<BranchDto> partiallyUpdateBranchByBranchId(
            @PathVariable Long id, @RequestBody @Valid PartiallyUpdateBranchRequestDto requestDto) {
        BranchDto updatedBranch = this.crudFacade.partiallyUpdateBranchById(
                id, requestDto);
        return ResponseEntity.ok(updatedBranch);
    }

}
