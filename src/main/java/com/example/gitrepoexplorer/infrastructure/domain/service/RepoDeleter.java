package com.example.gitrepoexplorer.infrastructure.domain.service;

import com.example.gitrepoexplorer.infrastructure.domain.repository.RepoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@AllArgsConstructor
@Transactional
public class RepoDeleter {

    RepoRepository repoRepository;
    RepoRetriever repoRetriever;

    public void deleteById(Long id){
        repoRetriever.existsById(id);
        log.info("Deleting repository with id=" + id);
        repoRepository.deleteById(id);
    }

    public void deleteAll(){
        log.info("Deleting all repositories");
        repoRepository.deleteAll();
    }
}
