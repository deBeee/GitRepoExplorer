package com.example.gitrepoexplorer.domain.crud;

import com.example.gitrepoexplorer.infrastructure.error.exceptions.BranchNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
class BranchDeleter {

    private final BranchRepository branchRepository;
    private final RepoRetriever repoRetriever;

    public void deleteAllById(final Set<Long> branchesIdToDelete) {
        branchRepository.deleteByIdIn(branchesIdToDelete);
    }

    public void deleteAll() {
        branchRepository.deleteAll();
    }

    public void deleteAllCurrentBranchesRelatedWithRepo(Repo repoFromDatabase) {
        Set<Long> branchesIdToDelete = repoFromDatabase.getBranches().stream()
                .map(Branch::getId)
                .collect(Collectors.toSet());
        deleteAllById(branchesIdToDelete);
    }

    public void deleteBranchByBranchId(Long branchId) {
        if(!branchRepository.existsById(branchId)){
            throw new BranchNotFoundException("Branch with id=%d not found".formatted(branchId));
        }
        branchRepository.deleteById(branchId);
    }
}
