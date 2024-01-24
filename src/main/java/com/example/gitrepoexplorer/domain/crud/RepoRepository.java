package com.example.gitrepoexplorer.domain.crud;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

interface RepoRepository extends Repository<Repo, Long> {

    @Query("""
            SELECT r FROM Repo r
            join fetch r.branches
            """)
    List<Repo> findAll(Pageable pageable);

    @Query("SELECT r FROM Repo r WHERE r.id = :id")
    Optional<Repo> findById(Long id);

    @Modifying
    @Query("DELETE FROM Repo r WHERE r.id = :id")
    void deleteById(Long id);

    @Modifying
    @Query("DELETE FROM Repo r")
    void deleteAll();

    @Modifying
    @Query("""
            UPDATE Repo r
            SET r.owner = :#{#newRepo.owner}, r.name = :#{#newRepo.name}, r.branches = :#{#newRepo.branches}
            WHERE r.id = :id
            """)
    void updateById(Long id, Repo newRepo);

    Repo save(Repo repo);

    <S extends Repo> List<S> saveAll(Iterable<S> repositories);

    boolean existsById(Long id);
}
