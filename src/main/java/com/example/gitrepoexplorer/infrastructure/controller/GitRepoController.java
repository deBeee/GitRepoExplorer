package com.example.gitrepoexplorer.infrastructure.controller;

import com.example.gitrepoexplorer.domain.crud.*;
import com.example.gitrepoexplorer.domain.crud.dto.BranchDto;
import com.example.gitrepoexplorer.infrastructure.controller.dto.request.BranchRequestDto;
import com.example.gitrepoexplorer.infrastructure.controller.dto.request.PartiallyUpdateRepositoryRequestDto;
import com.example.gitrepoexplorer.infrastructure.controller.dto.request.UserRepositoryRequestDto;
import com.example.gitrepoexplorer.infrastructure.controller.dto.request.PartiallyUpdateBranchRequestDto;
import com.example.gitrepoexplorer.infrastructure.controller.dto.response.PartiallyUpdateRepositoryResponseDto;
import com.example.gitrepoexplorer.infrastructure.controller.dto.response.UserBranchesResponseDto;
import com.example.gitrepoexplorer.infrastructure.controller.dto.response.UserRepositoriesAndBranchesResponseDto;
import com.example.gitrepoexplorer.domain.crud.dto.RepoDto;
import com.example.gitrepoexplorer.infrastructure.controller.dto.response.UserRepositoriesDto;
import com.example.gitrepoexplorer.infrastructure.error.exceptions.UnsupportedMediaTypeException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static com.example.gitrepoexplorer.infrastructure.mappers.Mappers.*;

@RestController
@Log4j2
@AllArgsConstructor
public class GitRepoController {

    private final CrudFacade crudFacade;

    @GetMapping(path = "github/repos/{username}")
    public ResponseEntity<UserRepositoriesAndBranchesResponseDto> fetchGitInfoAndSaveItToDatabaseAndReturnIt(
            @PathVariable String username, @RequestHeader(value = "Accept", required = false) String accept) {
        if ("application/xml".equals(accept)) {
            throw new UnsupportedMediaTypeException("xml media type is not supported");
        }
        UserRepositoriesAndBranchesResponseDto response = this.crudFacade.fetchUserRepositories(username);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "database/repos")
    public ResponseEntity<UserRepositoriesDto> getUserRepositories(
            @PageableDefault(size = Integer.MAX_VALUE) Pageable pageable) {
        List<Repo> repositories = this.crudFacade.findAllRepos(pageable);
        return ResponseEntity.ok(mapListOfRepoToUserRepositoriesDto(repositories));
    }

    @GetMapping(path = "database/repo/{id}")
    public ResponseEntity<RepoDto> getUserRepositoriesById(@PathVariable Long id) {
        Repo userRepository = this.crudFacade.findRepoById(id);
        return ResponseEntity.ok(mapRepoToUserRepositoryFromDatabaseDto(userRepository));
    }

    @PostMapping(path = "database/repo")
    public ResponseEntity<RepoDto> postUserRepository(
            @RequestBody @Valid UserRepositoryRequestDto requestDto
    ) {
        Repo addedUserRepository = this.crudFacade.addRepo(mapUserRepositoryRequestDtoToRepo(requestDto));
        return ResponseEntity.ok(mapRepoToUserRepositoryFromDatabaseDto(addedUserRepository));
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
        Repo updatedRepo = this.crudFacade.updateRepoById(id, mapUserRepositoryRequestDtoToRepo(requestDto));
        return ResponseEntity.ok(mapRepoToUserRepositoryFromDatabaseDto(updatedRepo));
    }

    @PatchMapping(path = "database/repo/{id}")
    public ResponseEntity<PartiallyUpdateRepositoryResponseDto> partiallyUpdateRepositoryById(
            @PathVariable Long id, @RequestBody PartiallyUpdateRepositoryRequestDto requestDto
    ) {
        Repo updatedRepo = this.crudFacade.partiallyUpdateRepoById
                (id, mapPartiallyUpdateRepositoryRequestDtoToRepo(requestDto));
        return ResponseEntity.ok(mapRepoToPartiallyUpdateRepositoryResponseDto(updatedRepo));
    }

    @GetMapping(path = "database/repo/{id}/branches")
    public ResponseEntity<UserBranchesResponseDto> getBranchesByRepoId(@PathVariable Long id) {
        Set<Branch> branchesByRepoId = this.crudFacade.getBranchesByRepoId(id);
        return ResponseEntity.ok(new UserBranchesResponseDto(
                mapSetOfBranchToSetOfBranchDto(branchesByRepoId)));
    }

    @GetMapping(path = "database/branch/{id}")
    public ResponseEntity<BranchDto> getBranchById(@PathVariable Long id) {
        Branch branchFromDatabase = this.crudFacade.getBranchById(id);
        return ResponseEntity.ok(mapBranchToBranchDto(branchFromDatabase));
    }


    @PostMapping(path = "database/repo/{id}/branch")
    public ResponseEntity<UserBranchesResponseDto> addBranchByRepoId(
            @PathVariable Long id, @RequestBody @Valid BranchRequestDto requestDto) {
        Set<Branch> branches = this.crudFacade.addBranchToRepoById(id, mapBranchRequestDtoToBranch(requestDto));
        return ResponseEntity.ok(new UserBranchesResponseDto(
                mapSetOfBranchToSetOfBranchDto(branches)));
    }

    @DeleteMapping(path = "database/branch/{branchId}")
    public ResponseEntity<Void> deleteBranchByBranchId(@PathVariable Long branchId){
        this.crudFacade.deleteBranchByBranchId(branchId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(path = "database/branch/{branchId}")
    public ResponseEntity<BranchDto> partiallyUpdateBranchByBranchId(
            @PathVariable Long branchId, @RequestBody @Valid PartiallyUpdateBranchRequestDto requestDto) {
        Branch updatedBranch = this.crudFacade.partiallyUpdateBranchById(
                branchId, mapPartiallyUpdateBranchRequestDtoToBranch(requestDto));
        return ResponseEntity.ok(mapBranchToBranchDto(updatedBranch));
    }

}
