package com.example.gitrepoexplorer.domain.crud;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
@Log4j2
@Transactional
class BranchAdder {

    private final RepoRetriever repoRetriever;
    public Set<Branch> addBranchToRepoById(Long id, Branch branch){
        Repo repo = this.repoRetriever.findById(id);
        return repo.addBranch(branch);
    }

}
