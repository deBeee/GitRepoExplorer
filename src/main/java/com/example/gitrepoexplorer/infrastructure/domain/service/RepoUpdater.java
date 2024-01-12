package com.example.gitrepoexplorer.infrastructure.domain.service;

import com.example.gitrepoexplorer.infrastructure.domain.model.Repo;
import com.example.gitrepoexplorer.infrastructure.domain.repository.RepoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@AllArgsConstructor
@Transactional
public class RepoUpdater {

    RepoRepository repoRepository;
    RepoRetriever repoRetriever;

    public void updateById(Long id, Repo repoFromRequest){
        repoRetriever.existsById(id);
        log.info("Updating repository with id=" + id);
        repoRepository.updateById(id, repoFromRequest);
    }

    public Repo partiallyUpdateById(Long id, Repo repoFromRequest) {
        Repo repoFromDatabase = repoRetriever.findById(id);
        Repo.RepoBuilder repoBuilder = Repo.builder();
        if(repoFromRequest.getName() == null){
            repoBuilder.name(repoFromDatabase.getName());
        } else {
            repoBuilder.name(repoFromRequest.getName());
        }
        if(repoFromRequest.getOwner() == null){
            repoBuilder.owner(repoFromDatabase.getOwner());
        } else {
            repoBuilder.owner(repoFromRequest.getOwner());
        }
        repoBuilder.id(id);
        Repo repoToSave = repoBuilder.build();
        updateById(id, repoToSave);
        return repoToSave;
    }
}
