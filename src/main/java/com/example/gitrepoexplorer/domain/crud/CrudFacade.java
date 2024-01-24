package com.example.gitrepoexplorer.domain.crud;

import com.example.gitrepoexplorer.infrastructure.controller.dto.response.UserRepositoriesAndBranchesResponseDto;
import com.example.gitrepoexplorer.infrastructure.service.GitHubService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class CrudFacade {
    private final GitHubService gitHubService;
    private final RepoRetriever repoRetriever;
    private final RepoAdder repoAdder;
    private final RepoDeleter repoDeleter;
    private final RepoUpdater repoUpdater;
    private final BranchRetriever branchRetriever;
    private final BranchAdder branchAdder;
    private final BranchDeleter branchDeleter;
    private final BranchUpdater branchUpdater;

    public UserRepositoriesAndBranchesResponseDto fetchUserRepositories(String username) {
        return this.gitHubService.fetchUserRepositories(username);
    }

    public List<Repo> findAllRepos(Pageable pageable) {
        return this.repoRetriever.findAll(pageable);
    }

    public Repo findRepoById(Long id) {
        return this.repoRetriever.findById(id);
    }

    public Repo addRepo(Repo repo) {
        return this.repoAdder.add(repo);
    }

    public void deleteAllRepos() {
        this.repoDeleter.deleteAll();
    }

    public void deleteRepoById(Long id) {
        this.repoDeleter.deleteById(id);
    }

    public Repo updateRepoById(Long id, Repo repoFromRequest) {
        return this.repoUpdater.updateById(id, repoFromRequest);
    }

    public Repo partiallyUpdateRepoById(Long id, Repo repoFromRequest){
        return this.repoUpdater.partiallyUpdateById(id, repoFromRequest);
    }

    public Set<Branch> getBranchesByRepoId(Long id){
        return this.branchRetriever.getBranchesByRepoId(id);
    }

    public Set<Branch> addBranchToRepoById(Long id, Branch branch){
        return this.branchAdder.addBranchToRepoById(id, branch);
    }

    public void deleteBranchByBranchId(Long id){
        this.branchDeleter.deleteBranchByBranchId(id);
    }

    public Branch partiallyUpdateBranchById(Long id, Branch branchFromRequest) {
        return this.branchUpdater.partiallyUpdateBranchById(id, branchFromRequest);
    }

    public void addAllRepos(List<Repo> repositories){
        this.repoAdder.addAll(repositories);
    }

    public Branch getBranchById(Long id) {
        return this.branchRetriever.findBranchById(id);
    }
}
