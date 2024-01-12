package com.example.gitrepoexplorer.infrastructure.domain.service;

import com.example.gitrepoexplorer.infrastructure.domain.model.Repo;
import com.example.gitrepoexplorer.infrastructure.domain.repository.RepoRepository;
import com.example.gitrepoexplorer.infrastructure.error.exceptions.RepositoryNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@AllArgsConstructor
public class RepoRetriever {

    RepoRepository repoRepository;

    public List<Repo> findAll(Pageable pageable) {
        log.info("Retrieving all paged repositories");
        return repoRepository.findAll(pageable);
    }

    public Repo findById(Long id) {
        log.info("Retrieving repository by given id: " + id);
        return repoRepository.findById(id)
                .orElseThrow(() ->
                        new RepositoryNotFoundException("repository with given id=%d not found".formatted(id)));
    }

    public void existsById(Long id) {
        if(!repoRepository.existsById(id)){
            throw new RepositoryNotFoundException("repository with given id= %d not found".formatted(id));
        }
    }
}
