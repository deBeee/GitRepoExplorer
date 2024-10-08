package com.example.gitrepoexplorer.domain.crud;


import com.example.gitrepoexplorer.domain.crud.Repo;
import com.example.gitrepoexplorer.domain.crud.RepoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@AllArgsConstructor
@Transactional
class RepoAdder {

    private final RepoRepository repoRepository;

    public List<Repo> addAll(List<Repo> repositories){
        log.info("adding all repositories");
        return repoRepository.saveAll(repositories);
    }

    public Repo add(Repo repository) {
        log.info("adding repository: " + repository);
        return repoRepository.save(repository);
    }
}
