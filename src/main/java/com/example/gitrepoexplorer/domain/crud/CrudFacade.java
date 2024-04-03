package com.example.gitrepoexplorer.domain.crud;

import com.example.gitrepoexplorer.domain.crud.dto.BranchDto;
import com.example.gitrepoexplorer.domain.crud.dto.RepoDto;
import com.example.gitrepoexplorer.infrastructure.controller.dto.request.BranchRequestDto;
import com.example.gitrepoexplorer.infrastructure.controller.dto.request.PartiallyUpdateBranchRequestDto;
import com.example.gitrepoexplorer.infrastructure.controller.dto.request.PartiallyUpdateRepositoryRequestDto;
import com.example.gitrepoexplorer.infrastructure.controller.dto.request.UserRepositoryRequestDto;
import com.example.gitrepoexplorer.infrastructure.controller.dto.response.PartiallyUpdateRepositoryResponseDto;
import com.example.gitrepoexplorer.infrastructure.controller.dto.response.UserBranchesResponseDto;
import com.example.gitrepoexplorer.infrastructure.controller.dto.response.UserRepositoriesDto;
import com.example.gitrepoexplorer.infrastructure.controller.dto.response.UserRepositoryAndBranchesResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static com.example.gitrepoexplorer.domain.crud.Mappers.*;

@Service
@AllArgsConstructor
public class CrudFacade {

    private final RepoRetriever repoRetriever;
    private final RepoAdder repoAdder;
    private final RepoDeleter repoDeleter;
    private final RepoUpdater repoUpdater;
    private final BranchRetriever branchRetriever;
    private final BranchAdder branchAdder;
    private final BranchDeleter branchDeleter;
    private final BranchUpdater branchUpdater;

    public UserRepositoriesDto findAllRepos(Pageable pageable) {
        List<Repo> allFoundRepos = this.repoRetriever.findAll(pageable);
        return mapListOfRepoToUserRepositoriesDto(allFoundRepos);
    }

    public RepoDto findRepoById(Long id) {
        return mapRepoToRepoDto(this.repoRetriever.findById(id));
    }

    public RepoDto addRepo(UserRepositoryRequestDto repo) {
        Repo addedRepo = this.repoAdder.add(mapUserRepositoryRequestDtoToRepo(repo));
        return mapRepoToRepoDto(addedRepo);
    }

    public void deleteAllRepos() {
        this.repoDeleter.deleteAll();
    }

    public void deleteRepoById(Long id) {
        this.repoDeleter.deleteById(id);
    }

    public RepoDto updateRepoById(Long id, UserRepositoryRequestDto repoFromRequest) {
        Repo repo = this.repoUpdater.updateById(id, mapUserRepositoryRequestDtoToRepo(repoFromRequest));
        return mapRepoToRepoDto(repo);
    }

    public PartiallyUpdateRepositoryResponseDto partiallyUpdateRepoById(Long id, PartiallyUpdateRepositoryRequestDto repoFromRequest) {
        Repo repoToUpdatePartially = mapPartiallyUpdateRepositoryRequestDtoToRepo(repoFromRequest);
        Repo partiallyUpdatedRepo = this.repoUpdater.partiallyUpdateById(id, repoToUpdatePartially);
        return mapRepoToPartiallyUpdateRepositoryResponseDto(partiallyUpdatedRepo);
    }

    public UserBranchesResponseDto getBranchesByRepoId(Long id) {
        Set<BranchDto> branchesDtos = mapSetOfBranchToSetOfBranchDto(this.branchRetriever.getBranchesByRepoId(id));
        return new UserBranchesResponseDto(branchesDtos);
    }

    public UserBranchesResponseDto addBranchToRepoById(Long id, BranchRequestDto branch) {
        Set<Branch> currentBranches = this.branchAdder.addBranchToRepoById(id, mapBranchRequestDtoToBranch(branch));
        return new UserBranchesResponseDto(mapSetOfBranchToSetOfBranchDto(currentBranches));
    }

    public void deleteBranchByBranchId(Long id) {
        this.branchDeleter.deleteBranchByBranchId(id);
    }

    public BranchDto partiallyUpdateBranchById(Long id, PartiallyUpdateBranchRequestDto branchFromRequest) {
        return mapBranchToBranchDto(this.branchUpdater.partiallyUpdateBranchById(id, mapPartiallyUpdateBranchRequestDtoToBranch(branchFromRequest)));
    }

    public void addAllRepos(List<UserRepositoryAndBranchesResponseDto> repositories) {
        this.repoAdder.addAll(Mappers.mapListOfUserRepositoriesAndBranchesResponseDtoToListOfRepo(repositories));
    }

    public BranchDto getBranchById(Long id) {
        return mapBranchToBranchDto(this.branchRetriever.findBranchById(id));
    }
}
