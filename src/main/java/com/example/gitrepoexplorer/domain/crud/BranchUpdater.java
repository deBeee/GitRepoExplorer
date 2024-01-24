package com.example.gitrepoexplorer.domain.crud;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Transactional
@Log4j2
class BranchUpdater {

    private final BranchRetriever branchRetriever;

    public Branch partiallyUpdateBranchById(Long id, Branch branchFromRequest){
        Branch branchFromDatabase = branchRetriever.findBranchById(id);
        if(branchFromRequest.getName() != null) {
            branchFromDatabase.setName(branchFromRequest.getName());
        }
        if(branchFromRequest.getLastCommitSha() != null){
            branchFromDatabase.setLastCommitSha(branchFromRequest.getLastCommitSha());
        }
        return branchFromDatabase;
    }
}
