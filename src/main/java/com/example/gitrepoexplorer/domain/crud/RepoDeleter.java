package com.example.gitrepoexplorer.domain.crud;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@Service
@AllArgsConstructor
@Transactional
class RepoDeleter {

    private final RepoRepository repoRepository;
    private final RepoRetriever repoRetriever;
    private final BranchDeleter branchDeleter;

    public void deleteById(Long id) {
        Repo repoToDelete = repoRetriever.findById(id);

        Set<Long> branchesIdToDelete = repoToDelete.getBranches().stream()
                .map(Branch::getId)
                .collect(Collectors.toSet());

        this.branchDeleter.deleteAllById(branchesIdToDelete);

        repoRepository.deleteById(id);

        log.info("Deleting repository with id=" + id);
    }

    public void deleteAll(){
        this.branchDeleter.deleteAll();
        this.repoRepository.deleteAll();
        log.info("Deleting all repositories");
    }
}
