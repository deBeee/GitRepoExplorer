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
class RepoUpdater {

    private final RepoRetriever repoRetriever;
    private final BranchDeleter branchDeleter;

    public Repo updateById(Long id, Repo repoFromRequest){
        Repo repoFromDatabase = repoRetriever.findById(id);

        this.branchDeleter.deleteAllCurrentBranchesRelatedWithRepo(repoFromDatabase);

        repoFromDatabase.setOwner(repoFromRequest.getOwner());
        repoFromDatabase.setName(repoFromRequest.getName());
        repoFromDatabase.setBranches(repoFromRequest.getBranches());

        log.info("Updating repository with id=" + id);

        return repoFromDatabase;
//      repoRepository.updateById(id, repoFromRequest);
    }

    public Repo partiallyUpdateById(Long id, Repo repoFromRequest) {
        Repo repoFromDatabase = repoRetriever.findById(id);
        if(repoFromRequest.getName() != null) {
            repoFromDatabase.setName(repoFromRequest.getName());
        }
        if(repoFromRequest.getOwner() != null){
            repoFromDatabase.setOwner(repoFromRequest.getOwner());
        }
        if(!repoFromRequest.getBranches().isEmpty()){
            this.branchDeleter.deleteAllCurrentBranchesRelatedWithRepo(repoFromDatabase);
            repoFromDatabase.setBranches(repoFromRequest.getBranches());
        }
        return repoFromDatabase;
    }
}
