package com.example.gitrepoexplorer.domain.crud;

import com.example.gitrepoexplorer.infrastructure.error.exceptions.BranchNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
@Log4j2
class BranchRetriever {

    private final RepoRetriever repoRetriever;
    private final BranchRepository branchRepository;

    public Set<Branch> getBranchesByRepoId(Long id){
        return this.repoRetriever.findById(id).getBranches();
    }

    public Branch findBranchById(Long id){
        return this.branchRepository.findById(id)
                .orElseThrow(() -> new BranchNotFoundException("Branch with id=%d not found".formatted(id)));
    }

}
